<template>

    <div >


        <div class="wrap white-paper  round-block">


            <div style="min-height:600px;" class="simple-grid-list">

                <div class="title">系统公告</div>

                <ul>

                    <li v-for="notice in listNotice" :key="notice.id">
                        <router-link :to="'/huiyuan/noticeinfo?id='+notice.id">{{notice.title}}
                            <span  class="date">
                             {{notice.pubtime}}
                            </span>
                        </router-link>
                    </li>

                </ul>


            </div>

            <el-pagination
                @size-change="pagesizeChange"
                @current-change="pageindexChange"
                :current-page="pageindex"
                :page-sizes="[pagesize]"
                :page-size="pagesize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "noticelist",
        data() {
            return {
                "title": "",
                hostHead:this.$app.contextPath,
                selectedAll: false,
                listNotice: [],
                pageindex: 1, //初始页
                pagesize: 10,
                total: 10
            }

        },
        methods: {
            pagesizeChange: function (pagesize) {
                this.pagesize = pagesize;
            },
            pageindexChange: function (pageindex) {
                this.pageindex = pageindex;
                console.log(this.pageindex);
                this.search();
            },
            async search() {
                let url = "admin/notice/list";
                let param = {
                    currentpageindex: this.pageindex,
                    pagesize: this.pagesize,
                    "title": this.title,
                };
                let util = new VueUtil(this);
                console.log("this.pageindex=" + this.pageindex);
                let res = await util.http.post(url, param);
                if (res.data != null) {
                    let pageInfo = res.data.data;
                    this.total = pageInfo.total;
                    this.listNotice = pageInfo.list;
                    console.log(pageInfo);
                }
            },

        },
        created() {
            this.search();
        },

    }
</script>

<style scoped>

</style>
