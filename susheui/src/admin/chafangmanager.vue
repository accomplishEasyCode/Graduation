<template>
    <div class="mypanel">

        <div class="hd">
            查房管理
        </div>

        <!-- 搜索控件开始 -->
        <div class="search-options">


            <table cellspacing="0" width="100%">
                <tbody>

                <tr>
                    <td>
                        查房日期:
                        <el-date-picker
                            v-model="cfdate"
                            type="date"
                            value-format="yyyy-MM-dd"
                            format="yyyy-MM-dd"
                            placeholder="开始日期">
                        </el-date-picker>

                        <el-button type="success" icon="el-icon-search" @click="search">搜索</el-button>
                    </td>
                </tr>
                </tbody>
            </table>


        </div>
        <!-- 搜索控件结束 -->


        <div class="action-details">
            <el-button-group>
                <!--{{export-view}}-->
                <el-button type="danger" icon="el-icon-delete" @click="deleteRec">删除</el-button>

            </el-button-group>
        </div>

        <table id="chafang" width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
            <thead>
            <tr>
                <th>
                    <input type="checkbox" @click="selectedAllHandler" v-model="selectedAll"/>
                </th>


                <th><b>学号</b></th>
                <th><b>姓名</b></th>
                <th><b>宿舍</b></th>
                <th><b>查房日期</b></th>
                <th><b>结果</b></th>
                <th>
                    操作
                </th>

            </tr>

            </thead>
            <tbody>


            <tr v-for="(chafang,index) in  listChafang" :key="chafang.id">
                <td>
                    <input class="check" name="ids" type="checkbox" v-model="chafang.rowSelected"/>


                </td>


                <td>
                    {{chafang.targetaccount}}
                </td>

                <td>
                    {{chafang.targetname}}
                </td>
                <td>
                    {{chafang.ldname}}{{chafang.unitname}}
                </td>
                <td>
                    {{chafang.cfdate}}
                </td>
                <td>
                    {{chafang.result}}
                </td>


                <td>
                    <el-button-group>
                        <route-button icon="el-icon-edit" :url="`/admin/chafangadd?id=${chafang.id}`">编辑</route-button>

                        <el-button type="warning" @click="showInfodlg(chafang)"><i class="el-icon-info"></i> 详细
                        </el-button>
                    </el-button-group>
                </td>
            </tr>

            <tr v-if="listChafang.length==0">
                <td colspan="20">
                    没有相关查房信息
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
        <div class="clear"></div>


        <el-dialog :visible.sync="infodlg" title="查房详细">

            <table class="smart-table" border="1" cellspacing="1" width="100%">


                <tr>
                    <td class="tlabel" width="20%" align="right">学号:</td>
                    <td width="*">
                        {{chafang.targetaccount}}
                    </td>
                </tr>

                <tr>
                    <td class="tlabel"  align="right">姓名:</td>
                    <td width="*">
                        {{chafang.targetname}}
                    </td>
                </tr>
                <tr>
                    <td class="tlabel"  align="right">宿舍:</td>
                    <td width="*">
                        {{chafang.ldname}}{{chafang.unitname}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">查房日期:</td>
                    <td>
                        {{chafang.cfdate}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="10%" align="right">结果:</td>
                    <td width="*">
                        {{chafang.result}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">说明:</td>
                    <td>
                        <div v-html="chafang.des"></div>
                    </td>
                </tr>


            </table>


        </el-dialog>
    </div>
</template>


<script type="text/javascript">

    import {VueUtil} from "../util/vueutil";

    export default {

        name: "chafangmanager",
        data() {
            return {


                hostHead: this.$app.contextPath,
                selectedAll: false,
                listChafang: [],
                pageindex: 1, //初始页
                pagesize: 10,
                total: 10,
                infodlg: false,
                cfdate:new Date().format("yyyy-MM-dd"),
                chafang: {}

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
                let url = "admin/chafang/list";
                let param = {
                    currentpageindex: this.pageindex,
                    pagesize: this.pagesize,

                    "cfdate": this.cfdate,


                };
                let util = new VueUtil(this);
                console.log("this.pageindex=" + this.pageindex);
                let res = await util.http.post(url, param);
                if (res.data != null) {
                    let pageInfo = res.data.data;
                    this.total = pageInfo.total;
                    this.listChafang = pageInfo.list;
                    console.log(pageInfo);
                }
            },
            async deleteRec() {
                let url = "admin/chafang/delete";
                let util = new VueUtil(this);
                let hasChecked = this.listChafang.some(c => {
                    return c.rowSelected == true;
                });
                if (!hasChecked) {
                    util.alert('请选择需要删除的记录', '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }

                let ids = this.listChafang.filter(c => c.rowSelected).map(c => c.id);
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
            exportHandler() {
                window.location.href = this.hostHead + "/admin/chafang/expor";
            },
            selectedAllHandler() {
                console.log(this.selectedAll);
                if (this.listChafang != null) {
                    this.listChafang.forEach(c => {
                        c.rowSelected = !this.selectedAll;
                    });
                }
            },
            showInfodlg(chafang) {
                this.chafang = chafang;
                this.infodlg = true;
            },
        },
        created() {
            this.search();
        }
    };

</script>
