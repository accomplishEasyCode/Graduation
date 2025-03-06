<template>

    <div >


        <div class="wrap  white-paper">
            <div class="line-title">
                当前位置：首页&gt;&gt; 系统公告
            </div>

            <div style="min-height:600px;" class="info">
                <h1>
                    {{notice.title}}
                </h1>
                <h5>
                    来源: {{notice.pubren}}
                    浏览:<span id="count">2次</span>
                    发布时间：{{notice.pubtime}}
                </h5>
                <div class="news-content">
                    <div v-html="notice.dcontent">
                    </div>
                </div>

            </div>
        </div>

        <bottom></bottom>


    </div>
</template>

<script>


    export default {
        name: "noticeinfo",
        data(){
            return {

                hostHead:this.$app.contextPath,
                notice:{}

            }
        },
        async created() {
            this.getInfo();
        },
        components: {},
        methods: {
            async getInfo() {
                let url = "admin/notice/load";
                let {data:res}=await this.$http.post(url, {
                    id: this.$route.query.id
                });
                if (res!=null&&res.stateCode<0){
                    this.$message.error(res.des);
                    return ;
                }
                this.notice = res.data;
            }
        }
    }
</script>

<style scoped>

</style>
