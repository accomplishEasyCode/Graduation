<template>
    <div id="app" class="wrap" style="background-color:#f8f8f8;" >


        <div class="image-text-box3">


            <div class="filter-box">

                <div class="item">

                    <div class="title">类别:</div>
                    <div class="content-list">
                        <ul>

                            <li v-for="(item,index) in listLanmu">
                                <span class="subtype" :class="{active:index==selectedIndex}"  @click="getXinxi(item.id,index)" >{{item.name}}</span>
                            </li>


                        </ul>
                    </div>
                </div>

            </div>

            <div class="bd">


                <div v-for="xinxi in listXinxi" class="item">
                    <div class="image-ar">
                        <router-link :to="'/huiyuan/xinxiinfo?id='+xinxi.id"><img :src="hostHead+xinxi.images[0]"/></router-link>
                    </div>
                    <div class="text-ar">
                        <div class="title"><router-link :to="'/huiyuan/xinxiinfo?id='+xinxi.id">{{xinxi.title}}</router-link></div>
                        <div class="time">{{xinxi.pubtime}}</div>
                        <div class="abstract">{{xinxi.abstact}}</div>
                    </div>
                </div>


                <div v-if="listXinxi.length==0" style="width: 100%;" class="no-record-tip white-paper">
                    <div class="content">
                        <i class="fa fa-warning"></i> 没有相关内容
                    </div>
                </div>

            </div>

        </div>

        <div class="clear"></div>
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

</template>

<script>
    import  {VueUtil} from "../util/vueutil";

    export default {
        name: "xinxilist",
        data() {

            return {
                hostHead: this.$app.contextPath,
                listXinxi: [],
                pageindex: 1,
                lanmuid: 0,
                listLanmu: [],
                pagesize: 10,
                total: 10,
                selectedIndex: 0,
                name: ""
            }

        },
        async created() {

            this.getXinxi(this.lanmuid);
            this.getLanmu();

        },
        async mounted() {


        },
        methods: {
            pagesizeChange: function (pagesize) {
                this.pagesize = pagesize;
            },
            pageindexChange: function(pageindex){
                this.pageindex = pageindex;
                console.log(this.pageindex);
                this.getXinxi(this.lanmuid);
            },
            getXinxi(lmid,index) {
                this.selectedIndex=index;
                let param={
                    currentpageindex:this.pageindex,
                    pagesize:this.pagesize
                };
                if(lmid!=0)
                    param.lmid=lmid;

                let url = "admin/xinxi/list";
                this.$http.post(url,param).then(res => {
                    console.log(res.data);
                    if(res.data!=null&&res.data.data!=null) {
                        let pageInfo=res.data.data;
                        this.total=pageInfo.total;
                        this.listXinxi = pageInfo.list;
                    }
                });
            },

            getLanmu(){
                let url = "admin/lanmu/list";
                this.$http.post(url,{"ispaged":"-1"}).then(res => {
                    console.log(res.data);
                    if(res.data!=null&&res.data.data!=null) {
                        this.listLanmu =res.data.data;
                    }
                });
            }

        }
    }
</script>

<style scoped>

</style>
