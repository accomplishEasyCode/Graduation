# import os
# from scrapy import cmdline
#
# def main(user_text,user_web):
#     project_path = 'F:\Code\huawei(1)\huawei\product_spiders'
#     os.chdir(project_path)
#     user_inputs = user_text
#     web = user_web
#     cmdline.execute(f'scrapy crawl {web} -a user_inputs={user_inputs} --nolog'.split())
#
# if __name__ == '__main__':
#
#     user_text = "华为"
#     user_web = "taobao" #taobao jingdong
#     main(user_text,user_web)
# # import pandas as pd
# # import re
# #
# # def load_excel_data(file_path):
# #     df = pd.read_excel(file_path)
# #     return df
# #
# #
# # def clean_shop_name(shop_name):
# #     shop_name = re.sub(r"^天猫-", "", shop_name)
# #     shop_name = re.sub(r"^京东-", "", shop_name)
# #     return shop_name.strip()  # 去除两端的空格
# #
# # excel_file_path = "./授权表.xlsx"
# # excel_data = load_excel_data(excel_file_path)
# #
# # excel_data['cleaned_shop_name'] = excel_data['店铺名'].apply(clean_shop_name)
# #
# # print(excel_data['cleaned_shop_name'])