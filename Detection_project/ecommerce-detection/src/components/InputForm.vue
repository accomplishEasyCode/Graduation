<template>
  <el-card class="input-form">
    <h3 class="form-title">输入关键词和选择目标网站</h3>
    <el-form ref="form" :model="form" label-width="80px" class="form-content">
      <el-form-item label="关键词">
        <el-input v-model="form.keyword" placeholder="请输入关键词"></el-input>
      </el-form-item>
      <el-form-item label="目标网站">
        <el-select v-model="form.target" placeholder="请选择目标网站">
          <el-option label="淘宝" value="taobao"></el-option>
          <el-option label="京东" value="jingdong"></el-option>
        </el-select>
      </el-form-item>
      <div class="button-container">
        <el-button type="primary" :disabled="isDisabled" @click="startSpider" class="center-button">
          {{ isDisabled ? `请等待 ${remainingTime}s 后启动` : "启动爬虫" }}
        </el-button>
      </div>
    </el-form>
  </el-card>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      form: {
        keyword: "", // 用户输入的关键词
        target: "", // 用户选择的目标网站
      },
      isDisabled: false, // 按钮是否禁用
      remainingTime: 0, // 剩余的冷却时间
    };
  },
  methods: {
    async startSpider() {
      if (!this.form.keyword || !this.form.target) {
        this.$message.warning("请完整填写关键词和目标网站！");
        return;
      }

      try {
        // 启动爬虫
        console.log("发送的参数：", this.form);
        await axios.post("/api/start_spider", {
          keyword: this.form.keyword, // 传递关键词
          target: this.form.target,  // 传递目标网站
        });
        this.$message.success("爬虫已启动，请稍后刷新数据！");

        // 启动冷却倒计时
        this.startCooldown();

        // 将关键词和目标网站传递给父组件
        this.$emit("keywordSubmitted", { keyword: this.form.keyword, target: this.form.target });

        // 获取爬取到的数据
        const fetchedData = await this.fetchData();
        this.$emit("dataFetched", fetchedData); // 将爬取数据传递给父组件
      } catch (error) {
        console.error("启动爬虫失败：", error);
        this.$message.error("启动爬虫失败，请检查后端连接！");
      }
    },
    async fetchData() {
      // 获取后端爬虫抓取的数据
      // console.log("/api/get_items发出去的数据",this.form.target);
      console.log("关键词为：", this.form.keyword);
      const response = await axios.get("/api/get_items", {
        params: {
          user_text: this.form.keyword, // 将关键词作为查询参数
          target: this.form.target,    // 将目标网站作为查询参数
        },
      });
      console.log("获取到的数据：", response.data);
      return response.data || []; // 返回爬取到的数据
    },
    startCooldown() {
      // 设置按钮为禁用状态，并开始倒计时
      this.isDisabled = true;
      this.remainingTime = 30; // 冷却时间 30 秒
      const interval = setInterval(() => {
        this.remainingTime -= 1;
        if (this.remainingTime <= 0) {
          clearInterval(interval);
          this.isDisabled = false;
        }
      }, 1000); // 每秒更新一次
    },
  },
};
</script>

<style scoped>
.input-form {
  max-width: 600px;
  margin: 0 auto;
}

.form-title {
  text-align: center;
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 20px;
}

.form-content {
  margin-top: 20px;
}

.button-container {
  text-align: center;
  margin-top: 20px;
}

.center-button {
  width: 150px;
}
</style>
