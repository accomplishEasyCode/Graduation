<template>

    <div class="mypanel1">
        <div class="hd">

          运动器械租赁
        </div>
        <div class="bd">
        <!-- 搜索控件开始 -->
        <div class="search-options">

            <table cellspacing="0" width="100%">
                <tbody>
                <tr>
                    <td>商品名称<input name="spname" v-model="spname" class="input-txt" type="text" id="spname" />

                        <el-button type="primary" icon="el-icon-search" @click="getShangpin(0,0,spname)">搜索</el-button>


                    </td>
                </tr>
                </tbody>
            </table>

        </div>
        <!-- 搜索控件结束 -->

        <div class="video-list">

            <div class="filter-box">

                <div class="item">

                    <div class="title">商品类别:</div>
                    <div class="content-list">
                        <ul>
                            <li v-for="(item,index) in listSpcategory">
                                <span class="subtype" :class="{active:index==selectedIndex}"  @click="getShangpin(item.id,index)" >{{item.name}}</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="bd">

                <div v-for="shangpin in listShangpin" class="item">
                    <div class="card">

                        <div class="pic">
                            <router-link :to="'/huiyuan/shangpininfo?id='+shangpin.id">

                                <img class="image" :src="hostHead+shangpin.tupian"/>
                            </router-link>

                        </div>
                        <div class="name">
                            {{shangpin.name}}

                        </div>
                        <div class="price">
                            {{shangpin.kucun}}{{shangpin.danwei}}

                           {{shangpin.price}}¥
                        </div>

                    </div>
                </div>


                <div style="width:100%;" v-if="listShangpin.length==0" class="no-record-tip">
                    <div  class="content">

                        <i class="fa fa-warning"></i>
                        暂无相关商品
                    </div>

                </div>



            </div>
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
    import ss  from "../util/SmartStorage"
    export default {
        name: "spguide.vue",
        data() {
            return {
                hostHead: this.$app.contextPath,
                listShangpin: [],
                listSpcategory: [],
                spname: "",
                pageindex: 1,
                typeid: 0,
                subtypeid: 0,
                selectedIndex: 0,
                lanmu: {},
                pagesize: 10,
                total: 10,
                name: "",
                type: "1"
            }
        },
        async created() {
            console.log("created");

            this.getSpcategory();
            this.getShangpin(this.typeid);
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
                this.getShangpin(this.typeid);

            },
            getSpcategory(){
                let url = "admin/spcategory/list";
                this.$http.post(url,{"ispaged":"-1"}).then(res => {
                    console.log(res.data);
                    if(res.data!=null&&res.data.data!=null) {
                        this.listSpcategory = res.data.data;
                    }
                });
            },
            getShangpin(typeid,index,spname) {
                this.typeid=typeid;
                this.selectedIndex=index;
                let param={
                    currentpageindex:this.pageindex,
                    pagesize:this.pagesize
                };
                if(typeid!=0)
                    param.typeid=typeid;
                if(spname!=null)
                    param.name=spname;

                let url = "admin/shangpin/list";
                this.$http.post(url,param).then(res => {
                    console.log(res.data);
                    if(res.data!=null&&res.data.data!=null) {
                        let pageInfo=res.data.data;
                        this.total=pageInfo.total;
                        this.listShangpin = pageInfo.list;
                    }
                });
            },
            addToCart(shangpin){

                let cart=new SmartStorage();
                let cartitem={
                    id:shangpin.id,
                    name:shangpin.name,
                    tupian:shangpin.tupian,
                    count:1,
                    selected:true,
                    price:shangpin.price,
                    typename:shangpin.typename
                };
                cart.add(shangpin.id,cartitem);

                this.$router.push("/huiyuan/mycart");

            }


        }
    }
</script>

<style scoped>

</style>
