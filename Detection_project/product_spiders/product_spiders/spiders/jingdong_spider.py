import time
import scrapy
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from ..items import JingdongItem
from scrapy.http import HtmlResponse

def smooth_scroll_to_bottom(driver, duration=5):
    last_height = driver.execute_script("return document.body.scrollHeight")
    current_position = 0
    step = last_height / (duration * 20)
    for _ in range(int(duration * 20)):
        current_position += step
        driver.execute_script(f"window.scrollTo(0, {current_position});")
        time.sleep(1 / 20)
    driver.execute_script(f"window.scrollTo(0, {last_height});")
    time.sleep(2)

class JingdongSpider(scrapy.Spider):
    name = 'jingdong'
    custom_settings = {
        'ITEM_PIPELINES': {'product_spiders.pipelines.JingdongPipeline': 300},
        'DOWNLOADER_MIDDLEWARES': {
            'scrapy.downloadermiddlewares.useragent.UserAgentMiddleware': None,
            'product_spiders.middlewares.RandomUserAgentMiddleware': 400,  # 随机User-Agent
            'scrapy.downloadermiddlewares.httpproxy.HttpProxyMiddleware': 1,  # 如果使用代理
        },
        'USER_AGENT': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36',  # 常见的浏览器User-Agent
        'REDIRECT_ENABLED': True,  # 启用重定向
        'REDIRECT_MAX_TIMES': 3,  # 设置最大重定向次数
    }

    def __init__(self, *args, **kwargs):
        super(JingdongSpider, self).__init__(*args, **kwargs)
        self.user_inputs = kwargs.get('user_inputs')
        self.start_url = f'https://search.jd.com/Search?keyword={self.user_inputs}&enc=utf-8'
        self.product_num = 1
        self.page = 1
        self.max_page = 1

        options = webdriver.ChromeOptions()
        options.add_argument("--disable-extensions")
        options.add_argument('--start-maximized')
        options.add_argument('--disable-gpu')
        options.add_argument('--no-sandbox')
        options.add_argument('--disable-dev-shm-usage')
        options.add_argument('user-data-dir=E:\\Code\\product_spiders\\product_spiders\\spiders\\User Data2')
        self.driver = webdriver.Chrome(options=options)

    def start_requests(self):
        yield scrapy.Request(url=self.start_url, callback=self.parse_with_selenium)

    def parse_with_selenium(self, response):
        self.driver.get(self.start_url)
        search_box = self.driver.find_element(By.XPATH, '//*[@id="key"]')
        search_box.send_keys(self.user_inputs)
        search_box.send_keys(Keys.RETURN)
        self.driver.implicitly_wait(10)
        time.sleep(5)

        while self.page <= self.max_page:
            smooth_scroll_to_bottom(self.driver, duration=10)
            self.driver.implicitly_wait(10)
            time.sleep(3)

            jd_product_list = self.driver.find_elements(By.XPATH, "//*[contains(@class, 'gl-i-wrap')]")
            for jd_product in jd_product_list:
                jd_product_data = JingdongItem()
                jd_product_data["input"] = self.user_inputs
                jd_product_data["id"] = self.product_num
                self.product_num += 1

                try:
                    jd_product_data["link"] = jd_product.find_element(By.XPATH, ".//div[@class='p-name']/a").get_attribute("href")
                except:
                    jd_product_data["link"] = "NULL"

                try:
                    jd_product_data["title"] = jd_product.find_element(By.XPATH, ".//div[@class='p-name']").text
                except:
                    jd_product_data["title"] = "NULL"

                try:
                    jd_product_data["price"] = jd_product.find_element(By.CLASS_NAME, "p-price").text
                except:
                    jd_product_data["price"] = "NULL"

                try:
                    jd_product_data["merchants"] = jd_product.find_element(By.CLASS_NAME, "curr-shop").text
                except:
                    jd_product_data["merchants"] = "NULL"

                # 添加 platform 字段
                jd_product_data["platform"] = "jingdong"

                yield jd_product_data

        self.driver.quit()
