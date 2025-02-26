#
# from flask import Flask, request, jsonify
# from flask_cors import CORS
# from scrapy.crawler import CrawlerProcess
# from scrapy.utils.project import get_project_settings
# from product_spiders.spiders.jingdong_spider import JingdongSpider
# from product_spiders.spiders.taobao_spider import TaobaoProductSpider
# import multiprocessing
# import pymysql
#
# app = Flask(__name__)
# CORS(app)
#
# def get_db_connection():
#     """创建数据库连接"""
#     return pymysql.connect(
#         host='localhost',
#         user='root',
#         password='root',
#         database='ecommerce',
#         charset='utf8mb4',
#         cursorclass=pymysql.cursors.DictCursor
#     )
#
# @app.route('/api/start_spider', methods=['POST'])
# def start_spider():
#     try:
#         data = request.get_json()
#         user_text = data.get('keyword')
#         user_web = data.get('target')
#         if not user_text or not user_web:
#             return jsonify({'error': 'Both user_text and user_web are required'}), 400
#
#         process = multiprocessing.Process(target=run_spider, args=(user_text, user_web))
#         process.start()
#
#         return jsonify({'message': f'Spider started for {user_web} with search term "{user_text}"'}), 200
#
#     except Exception as e:
#         print(f"Error: {e}")
#         return jsonify({'error': 'Failed to start spider'}), 500
#
# def run_spider(user_text, user_web):
#     process = CrawlerProcess(get_project_settings())
#     if user_web == 'taobao':
#         process.crawl(TaobaoProductSpider, user_inputs=user_text)
#     elif user_web == 'jingdong':
#         process.crawl(JingdongSpider, user_inputs=user_text)
#     else:
#         print("Invalid web choice.")
#         return
#     process.start()
#
# @app.route('/api/store_item', methods=['POST'])
# def store_item():
#     try:
#         item = request.get_json()
#         connection = get_db_connection()
#         cursor = connection.cursor()
#
#         # 从请求中获取 target 字段并将其作为平台
#         platform = request.args.get('target') or item.get('platform', '')  # 使用 request.args 或 item 中的 platform
#
#         # 检查 link 是否已存在
#         check_sql = "SELECT COUNT(*) as count FROM products WHERE link = %s"
#         cursor.execute(check_sql, (item.get('link', ''),))
#         result = cursor.fetchone()
#
#         if result['count'] > 0:
#             # 如果数据库中已存在相同的 link，跳过插入操作
#             cursor.close()
#             connection.close()
#             return jsonify({"message": "Item already exists, skipping insertion"}), 200
#
#         # 插入新数据
#         insert_sql = """
#         INSERT INTO products (input, title, price, link, merchants, platform, product_category, Authorization)
#         VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
#         """
#         values = (
#             item.get('input', ''),
#             item.get('title', ''),
#             item.get('price', ''),
#             item.get('link', ''),
#             item.get('merchants', ''),
#             platform,  # 使用从请求中提取的 platform
#             item.get('product_category', ''),
#             item.get('Authorization', '')
#         )
#         cursor.execute(insert_sql, values)
#         connection.commit()
#         cursor.close()
#         connection.close()
#
#         return jsonify({"message": "Item successfully received and stored"}), 200
#
#     except Exception as e:
#         print(f"Error: {e}")
#         return jsonify({"error": "Failed to process item"}), 500
#
# @app.route('/api/get_items', methods=['GET'])
# def get_items():
#     try:
#         # 获取查询参数 user_text
#         keyword = request.args.get('user_text')
#         # target = request.args.get('target')
#         if not keyword:
#             return jsonify({"error": "User_text is required"}), 400
#
#         # 数据库查询
#         connection = get_db_connection()
#         cursor = connection.cursor()
#
#         # 使用参数绑定的正确方式，避免 SQL 注入
#         sql = """
#         SELECT *
#         FROM products
#         WHERE input = %s
#         ORDER BY crawl_time DESC
#         """
#         cursor.execute(sql, (keyword,))  # 参数以元组形式传递
#
#         items = cursor.fetchall()
#         cursor.close()
#         connection.close()
#
#         # 返回查询结果
#         return jsonify(items), 200
#
#     except Exception as e:
#         # 打印详细错误信息以便调试
#         print(f"Error: {e}")
#         return jsonify({"error": "Failed to fetch items"}), 500
#
#
# if __name__ == '__main__':
#     app.run(host='0.0.0.0', port=5000, debug=True)
from flask import Flask, request, jsonify
from flask_cors import CORS
from scrapy.crawler import CrawlerProcess
from scrapy.utils.project import get_project_settings
from product_spiders.spiders.jingdong_spider import JingdongSpider
from product_spiders.spiders.taobao_spider import TaobaoProductSpider
import multiprocessing
import pymysql

app = Flask(__name__)
CORS(app)

def get_db_connection():
    """创建数据库连接"""
    return pymysql.connect(
        host='localhost',
        user='root',
        password='root',
        database='ecommerce',
        charset='utf8mb4',
        cursorclass=pymysql.cursors.DictCursor
    )

@app.route('/api/start_spider', methods=['POST'])
def start_spider():
    try:
        data = request.get_json()
        user_text = data.get('keyword')
        user_web = data.get('target')
        if not user_text or not user_web:
            return jsonify({'error': 'Both user_text and user_web are required'}), 400

        process = multiprocessing.Process(target=run_spider, args=(user_text, user_web))
        process.start()

        return jsonify({'message': f'Spider started for {user_web} with search term "{user_text}"'}), 200

    except Exception as e:
        print(f"Error: {e}")
        return jsonify({'error': 'Failed to start spider'}), 500

def run_spider(user_text, user_web):
    process = CrawlerProcess(get_project_settings())
    if user_web == 'taobao':
        process.crawl(TaobaoProductSpider, user_inputs=user_text)
    elif user_web == 'jingdong':
        process.crawl(JingdongSpider, user_inputs=user_text)
    else:
        print("Invalid web choice.")
        return
    process.start()

# @app.route('/api/store_item', methods=['POST'])
# def store_item():
#     try:
#         item = request.get_json()
#         connection = get_db_connection()
#         cursor = connection.cursor()
#
#         # 从请求中获取 target 字段并将其作为平台
#         platform = request.args.get('target') or item.get('platform', '')  # 使用 request.args 或 item 中的 platform
#
#         # 检查 link 是否已存在
#         check_sql = "SELECT COUNT(*) as count FROM products WHERE link = %s"
#         cursor.execute(check_sql, (item.get('link', ''),))
#         result = cursor.fetchone()
#
#         if result['count'] > 0:
#             # 如果数据库中已存在相同的 link，跳过插入操作
#             cursor.close()
#             connection.close()
#             return jsonify({"message": "Item already exists, skipping insertion"}), 200
#
#         # 插入新数据
#         insert_sql = """
#         INSERT INTO products (input, title, price, link, merchants, platform, product_category, Authorization)
#         VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
#         """
#         values = (
#             item.get('input', ''),
#             item.get('title', ''),
#             item.get('price', ''),
#             item.get('link', ''),
#             item.get('merchants', ''),
#             platform,  # 使用从请求中提取的 platform
#             item.get('product_category', ''),
#             item.get('Authorization', '')
#         )
#         cursor.execute(insert_sql, values)
#         connection.commit()
#         cursor.close()
#         connection.close()
#
#         return jsonify({"message": "Item successfully received and stored"}), 200
#
#     except Exception as e:
#         print(f"Error: {e}")
#         return jsonify({"error": "Failed to process item"}), 500
@app.route('/api/store_item', methods=['POST'])
def store_item():
    try:
        item = request.get_json()
        connection = get_db_connection()
        cursor = connection.cursor()

        # 从请求中获取 target 字段，并优先将其作为 platform
        platform = item.get('platform', request.json.get('target', ''))  # 优先使用 target 字段

        # 检查 link 是否已存在
        check_sql = "SELECT COUNT(*) as count FROM products WHERE link = %s"
        cursor.execute(check_sql, (item.get('link', ''),))
        result = cursor.fetchone()

        if result['count'] > 0:
            # 如果数据库中已存在相同的 link，跳过插入操作
            cursor.close()
            connection.close()
            return jsonify({"message": "Item already exists, skipping insertion"}), 200

        # 插入新数据
        insert_sql = """
        INSERT INTO products (input, title, price, link, merchants, platform, product_category, Authorization)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
        """
        values = (
            item.get('input', ''),
            item.get('title', ''),
            item.get('price', ''),
            item.get('link', ''),
            item.get('merchants', ''),
            platform,  # 使用 target 作为平台字段
            item.get('product_category', ''),
            item.get('Authorization', '')
        )
        cursor.execute(insert_sql, values)
        connection.commit()
        cursor.close()
        connection.close()

        return jsonify({"message": "Item successfully received and stored"}), 200

    except Exception as e:
        print(f"Error: {e}")
        return jsonify({"error": "Failed to process item"}), 500

@app.route('/api/get_items', methods=['GET'])
def get_items():
    try:
        # 获取查询参数 user_text 和 target
        keyword = request.args.get('user_text')
        target = request.args.get('target')
        if not keyword or not target:
            return jsonify({"error": "Both user_text and target are required"}), 400

        # 数据库查询
        connection = get_db_connection()
        cursor = connection.cursor()

        # 使用参数绑定的正确方式，避免 SQL 注入
        sql = """
        SELECT * 
        FROM products 
        WHERE input = %s AND platform = %s 
        ORDER BY crawl_time DESC
        """
        cursor.execute(sql, (keyword, target))  # 参数以元组形式传递

        items = cursor.fetchall()
        cursor.close()
        connection.close()

        # 返回查询结果
        return jsonify(items), 200

    except Exception as e:
        # 打印详细错误信息以便调试
        print(f"Error: {e}")
        return jsonify({"error": "Failed to fetch items"}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
