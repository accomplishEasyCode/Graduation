<template>
    <div  class="mypanel1">

        <div class="hd">
            商品管理
        </div>

        <div class="bd">
            <div class="search-options">


                <table cellspacing="0" width="100%">
                    <tbody>

                    <tr>
                        <td>


                            名称:<input name="name" @keyup.enter="search" v-model="name" class="input-txt" type="text"/>


                            <el-button type="success" icon="el-icon-search" @click="search">搜索</el-button>
                        </td>
                    </tr>
                    </tbody>
                </table>


            </div>

            <div class="action-details">
                <div class="el-button-group">
                    <!--{{export-view}}-->
                    <el-button type="danger" icon="el-icon-delete" @click="deleteRec">删除</el-button>
                </div>
            </div>

            <table  width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
                <thead>
                <tr>
                    <th>
                        <input type="checkbox" @click="selectedAllHandler" v-model="selectedAll"/>
                    </th>


                    <th width="200"><b>名称</b></th>


                    <th><b>商品类别</b></th>



                    <th><b>价格（元）</b></th>


                    <th><b>品牌</b></th>



                    <th><b>库存</b></th>

                    <th><b>创建时间</b></th>


                    <th>
                        操作
                    </th>

                </tr>

                </thead>
                <tbody>


                <tr v-for="(shangpin,index) in  listShangpin" :key="shangpin.id">
                    <td>
                        <input class="check" name="ids" type="checkbox" v-model="shangpin.rowSelected"/>


                    </td>


                    <td>
                        {{shangpin.name}}
                    </td>


                    <td>
                        {{shangpin.typename}}
                    </td>



                    <td>
                        {{shangpin.price}}¥
                    </td>


                    <td>
                        {{shangpin.pinpai}}
                    </td>




                    <td>
                        <div v-if="shangpin.kucun<10" style="color:red" class="weight14font">
                            {{shangpin.kucun}}{{shangpin.danwei}}
                        </div>
                        <div v-else class="weight14font">
                            {{shangpin.kucun}}{{shangpin.danwei}}
                        </div>


                    </td>

                    <td>
                        {{shangpin.createtime}}
                    </td>


                    <td>
                        <el-button-group>
                            <route-button :url="'/admin/shangpinadd?id='+shangpin.id" type="primary" icon="el-icon-edit">编辑</route-button>
                            <pop-dialog-button title="商品采购" :xdata="{userid:accountInfo.id}" @refresh="search" css-class="el-button el-button--danger" :url="'/admin/caigouadd?spid='+shangpin.id"><i class="el-icon-plus"></i>加库存</pop-dialog-button>
                            <pop-dialog-button title="查看商品" css-class="el-button el-button--warning" :url="'/admin/shangpindetails?id='+shangpin.id"><i class="el-icon-info"></i>详情</pop-dialog-button>

                        </el-button-group>
                    </td>
                </tr>

                <tr v-if="listShangpin.length==0">
                    <td colspan="20">
                        没有相关商品信息
                    </td>
                </tr>


                </tbody>
            </table>
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
    import  {VueUtil} from "../util/vueutil";
    import PopDialogButton from "../common/mydialog/PopDialogButton";
    export default {
        name: "shangpinmanager",
        components: {PopDialogButton},
        data() {
            return {
                "name": "",
                hostHead: this.$app.contextPath,
                selectedAll: false,
                listShangpin: [],
                pageindex: 1, //初始页
                pagesize: 10,
                total: 10
            }

        },
        props:{
            accountInfo:{
                type:Object
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
                let url = "admin/shangpin/list";
                let param = {
                    currentpageindex: this.pageindex,
                    pagesize: this.pagesize,
                    "name": this.name,
                };
                let util = new VueUtil(this);
                console.log("this.pageindex=" + this.pageindex);
                let res = await util.http.post(url, param);
                if (res.data != null) {
                    let pageInfo = res.data.data;
                    this.total = pageInfo.total;
                    this.listShangpin = pageInfo.list;
                    console.log(pageInfo);
                }
            },
            async deleteRec() {
                let url = "admin/shangpin/delete";
                let util = new VueUtil(this);
                let hasChecked = this.listShangpin.some(c => {
                    return c.rowSelected == true;
                });
                if (!hasChecked) {
                    util.alert('请选择需要删除的记录', '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }

                let ids = this.listShangpin.filter(c => c.rowSelected).map(c => c.id);
                let res = util.confirm('是否要删除数据?', '系统提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    util.http.post(url, {ids}, {emulateJSON: false}).then(res => {
                        if (res.data != null && res.data.stateCode > 0) {
                            this.search();
                        }
                    });
                }).catch(() => {
                });
            },
            selectedAllHandler() {
                console.log(this.selectedAll);
                if (this.listShangpin != null) {
                    this.listShangpin.forEach(c => {
                        c.rowSelected = !this.selectedAll;
                    });
                }
            }
        },
        created() {
            this.search();
        }
    }
</script>

<style scoped>

</style>
