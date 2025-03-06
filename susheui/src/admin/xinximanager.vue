<template>
    <div class="mypanel1">
        <div class="hd">
            资讯管理
        </div>
        <!-- 搜索控件开始 -->
        <div class="search-options">

            <table cellspacing="0" width="100%">
                <tbody>
                <tr>
                    <td>
                        标题:
                        <div style="width:200px;display: inline-block;">
                            <el-input placeholder="标题" v-model="title"/>
                        </div>

                        类别:
                        <el-cascader v-model="lmvalue"
                                     :options="options"
                                     :props="{ checkStrictly: true }"
                                     clearable></el-cascader>

                        <el-button @click="search" type="danger" icon="el-icon-search">
                            搜索
                        </el-button>
                    </td>
                </tr>
                </tbody>
            </table>

        </div>
        <!-- 搜索控件结束 -->
        <div class="clear">
        </div>
        <div class="action-details">
            <el-button-group>
                <span @click="selectedAllHandler" class="el-button el-button--primary"><i class="el-icon-check"></i>选择</span>
                <span @click="deleteRec" class="el-button el-button--danger"> <i class="el-icon-delete"></i>删除</span>
            </el-button-group>

        </div>


        <table id="xinxi" width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
            <thead>
            <tr>
                <th>
                    <input type="checkbox" @click="selectedAllHandler" v-model="selectedAll"/>
                </th>


                <th><b>标题</b></th>


                <th><b>类别</b></th>


                <th><b>发布时间</b></th>
                <th><b>点击次数</b></th>

                <th>
                    操作
                </th>

            </tr>

            </thead>
            <tbody>


            <tr v-for="(xinxi,index) in  listXinxi" :key="xinxi.id">
                <td>
                    <input class="check" name="ids" type="checkbox" v-model="xinxi.rowSelected"/>


                </td>


                <td>
                    {{xinxi.title}}
                </td>


                <td>
                    {{xinxi.lmname}}
                </td>



                <td>
                    {{xinxi.pubtime}}
                </td>


                <td>
                    <i class="fa fa-eye"></i>{{xinxi.clickcount}}次
                </td>


                <td>
                    <el-button-group>

                        <route-button icon="el-icon-edit" :url="`/admin/xinxiadd?id=${xinxi.id}`">编辑
                        </route-button>


                    </el-button-group>
                </td>
            </tr>

            <tr v-if="listXinxi.length==0">
                <td colspan="20">
                    没有相关资讯信息
                </td>
            </tr>


            </tbody>
        </table>

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


        <div class="clear"></div>
    </div>
</template>

<script>
    import  {VueUtil} from "../util/vueutil";

    export default {
        name: "xinximanager",
        data() {

           return {
               hostHead: this.$app.contextPath,
               listXinxi: [],
               options: [],
               lmvalue: [],
               pageindex: 1,
               pagesize: 10,
               total: 10,
               selectedAll: false,
               title: ""
           }

        },
        async created() {
            console.log("created");
            this.search();
            this.getLanmus();

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
            search() {

                let param = {
                    currentpageindex: this.pageindex,
                    pagesize: this.pagesize,
                    title: this.title
                };
                if (this.lmvalue != null && this.lmvalue.length > 0)
                    param.lmid = this.lmvalue[0];
                if (this.lmvalue != null && this.lmvalue.length >= 2)
                    param.subtypeid = this.lmvalue[1];

                let url = "admin/xinxi/list";
                this.$http.post(url, param).then(res => {
                    console.log(res.data);
                    if (res.data != null && res.data.data != null) {
                        let pageInfo = res.data.data;
                        this.total = pageInfo.total;
                        if (pageInfo.list != null) {
                            pageInfo.list.forEach(c => {
                                c.rowSelected = false;
                            });
                            this.listXinxi = pageInfo.list;
                        }
                    }
                });
            },

            async getLanmus() {
                let url = "admin/xinxi/cascadelanmu";
                let {data: res} = await this.$http.post(url, {
                    id: this.lanmuid
                });
                if (res.stateCode < 0) {
                    this.$message.error(res.des);
                    return;
                }
                this.options = res.data;
            },
            selectedAllHandler() {

                this.selectedAll = !this.selectedAll;
                if (this.listXinxi != null) {
                    this.listXinxi.forEach(c => {
                        c.rowSelected = this.selectedAll;
                    });
                }
            },
            async deleteOne(id) {
                let util = new VueUtil(this);
                if (id == null)
                    return;
                let ids = [].push(id);
                let res = util.confirm('是否要删除数据?', '系统提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    util.http.post(url, {ids}, {emulateJSON: false}).then(res => {
                        if (res.data != null && res.data.stateCode > 0) {
                            util.message.success("删除成功");
                            this.search();
                        }
                    });
                }).catch(() => {
                });
            },
            async deleteRec() {
                let url = "admin/xinxi/delete";
                let util = new VueUtil(this);
                let ids = this.listXinxi.filter(c => c.rowSelected).map(c => c.id);
                if (ids != null && ids.length <= 0) {
                    util.alert('请选择需要删除的记录', '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }


                let res = util.confirm('是否要删除数据?', '系统提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    util.http.post(url, {ids}, {emulateJSON: false}).then(res => {
                        if (res.data != null && res.data.stateCode > 0) {
                            util.message.success("删除成功");
                            this.search();
                        }
                    });
                }).catch(() => {
                });
            },

        }
    }
</script>

<style scoped>

</style>
