<template>
    <div class="panel panel-default">

        <div class="panel-heading">
            寝室评分管理
        </div>
        <div class="panel-body">
        <!-- 搜索控件开始 -->
        <div class="search-options">


            <table cellspacing="0" width="100%">
                <tbody>

                <tr>
                    <td>

                        学期周:<div style="width: 120px;display: inline-block;">
                        <el-select v-model="weekid">
                            <el-option v-for="item in listTermweek" :value="item.id" :label="item.name" :key="item.id">

                            </el-option>
                        </el-select>
                    </div>
                        <button class="btn btn-danger" @click="search">
                            <i class="fa fa-search"></i>搜索
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>


        </div>
        <!-- 搜索控件结束 -->

        <div class="clear">
        </div>


        <div class="action-details">
            <div class="btn-group">

                <!--{{export
            -view}}-->
                <span class="btn btn-success" @click="deleteRec">删除</span>
            </div>
        </div>

        <table id="qspingfen" width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
            <thead>
            <tr>
                <th>
                    <input type="checkbox" @click="selectedAllHandler" v-model="selectedAll"/>
                </th>


                <th><b>寝室</b></th>
                <th><b>周</b></th>


                <th><b>得分</b></th>


                <th><b>评分人</b></th>


                <th><b>评分时间</b></th>


                <th>
                    操作
                </th>

            </tr>

            </thead>
            <tbody>


            <tr v-for="(qspingfen,index) in  listQspingfen" :key="qspingfen.id">
                <td>
                    <input class="check" name="ids" type="checkbox" v-model="qspingfen.rowSelected"/>


                </td>

                <td>
                    {{qspingfen.qsname}}
                </td>
                <td>
                    {{qspingfen.weekname}}
                </td>


                <td>
                    {{qspingfen.score}}分
                </td>


                <td>
                    {{qspingfen.pfren}}
                </td>


                <td>
                    {{qspingfen.createtime}}
                </td>


                <td>
                    <div class="btn-group">
                        <a class="btn btn-danger" :href="hostHead+'/admin/qspingfenadd.jsp?id='+qspingfen.id"><i
                            class="fa fa-edit"></i>修改</a>

                    </div>
                </td>
            </tr>

            <tr v-if="listQspingfen.length==0">
                <td colspan="20">
                    没有相关寝室评分信息
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
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "qspingfenmanager",
        data() {
            return {
                weekid:1,
                hostHead: this.$app.contextPath,
                selectedAll: false,
                listQspingfen: [],
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
                let url = "admin/qspingfen/list";
                let param = {
                    currentpageindex: this.pageindex,
                    pagesize: this.pagesize,
                    "weekid": this.weekid,
                };
                let util = new VueUtil(this);
                console.log("this.pageindex=" + this.pageindex);
                let res = await util.http.post(url, param);
                if (res.data != null) {
                    let pageInfo = res.data.data;
                    this.total = pageInfo.total;
                    this.listQspingfen = pageInfo.list;
                    console.log(pageInfo);
                }
            },
            async deleteRec() {
                let url = "admin/qspingfen/delete";
                let util = new VueUtil(this);
                let hasChecked = this.listQspingfen.some(c => {
                    return c.rowSelected == true;
                });
                if (!hasChecked) {
                    util.alert('请选择需要删除的记录', '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }

                let ids = this.listQspingfen.filter(c => c.rowSelected).map(c => c.id);
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
            async getWeeks() {
                let url = "admin/termweek/list";
                let util = new VueUtil(this);
                console.log("this.pageindex=" + this.pageindex);
                let {data:res} = await util.http.post(url, {
                    ispaged:"-1"
                });
                if (res.data != null) {
                    this.listTermweek = res.data;
                }
            },
            selectedAllHandler() {
                console.log(this.selectedAll);
                if (this.listQspingfen != null) {
                    this.listQspingfen.forEach(c => {
                        c.rowSelected = !this.selectedAll;
                    });
                }
            }
        },
        async created() {
            await this.getWeeks();
            await this.search();
        }
    }
</script>

<style scoped>

</style>
