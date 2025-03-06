<template>
    <div class="panel panel-default">

        <div class="panel-heading">
            <i class="el-icon-"></i>公告管理
        </div>

        <div class="bd">
            <!-- 搜索控件开始 -->
            <div class="search-options">


                <table cellspacing="0" width="100%">
                    <tbody>

                    <tr>
                        <td>


                            标题: <input name="title" @keyup.enter="search" v-model="title" class="input-txt"
                                       type="text"/>


                            <el-button icon="el-icon-search" type="warning" @click="search">
                                搜索
                            </el-button>
                        </td>
                    </tr>
                    </tbody>
                </table>


            </div>

            <div class="action-details">

                <el-button-group>
                    <el-button type="danger" icon="el-icon-delete" @click="deleteRec">删除</el-button>
                    <route-button :url="`/admin/noticeadd`">发布公告</route-button>


                </el-button-group>


            </div>

            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
                <thead>
                <tr>
                    <th>
                        <input type="checkbox" @click="selectedAllHandler" v-model="selectedAll"/>
                    </th>


                    <th><b>标题</b></th>


                    <th><b>发布人</b></th>


                    <th><b>发布时间</b></th>


                    <th><b>点击次数</b></th>


                    <th>
                        操作
                    </th>

                </tr>

                </thead>
                <tbody>


                <tr v-for="(notice) in  listNotice" :key="notice.id">
                    <td>
                        <input class="check" name="ids" type="checkbox" v-model="notice.rowSelected"/>


                    </td>


                    <td>
                        {{notice.title}}
                    </td>


                    <td>
                        {{notice.pubren}}
                    </td>


                    <td>
                        {{notice.pubtime}}
                    </td>


                    <td>
                        {{notice.clickcount}}
                    </td>


                    <td>
                        <el-button-group>
                            <router-link class="el-button--small el-button el-button--primary" :to="`/admin/noticeadd?id=${notice.id}`"><i class="el-icon-edit"></i>编辑
                            </router-link>
                            <el-button size="small" type="warning" @click="showInfodlg(notice)"><i class="el-icon-info"></i> 详细</el-button>
                        </el-button-group>


                    </td>
                </tr>

                <tr v-if="listNotice.length==0">
                    <td colspan="20">
                        没有相关公告信息
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


        <el-dialog :visible.sync="infodlg" title="公告详细">

            <table class="smart-table" border="1" cellspacing="1" width="100%">


                <tr>
                    <td class="tlabel" width="20%" align="right">标题:</td>
                    <td width="*">
                        {{notice.title}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">发布人:</td>
                    <td width="*">
                        {{notice.pubren}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">发布时间:</td>
                    <td>
                        {{notice.pubtime}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel"  align="right">点击次数:</td>
                    <td width="*">
                        {{notice.clickcount}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">内容:</td>
                    <td>
                        <div v-html="notice.dcontent"></div>
                    </td>
                </tr>


            </table>


        </el-dialog>
    </div>
</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "noticemanager",
        data() {
            return {
                "title": "",
                hostHead: this.$app.contextPath,
                selectedAll: false,
                listNotice: [],
                pageindex: 1, //初始页
                pagesize: 10,
                total: 10,
                infodlg:false,
                notice:{}
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
            async deleteRec() {
                let url = "admin/notice/delete";
                let util = new VueUtil(this);
                let hasChecked = this.listNotice.some(c => {
                    return c.rowSelected == true;
                });
                if (!hasChecked) {
                    util.alert('请选择需要删除的记录', '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }

                let ids = this.listNotice.filter(c => c.rowSelected).map(c => c.id);
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
                if (this.listNotice != null) {
                    this.listNotice.forEach(c => {
                        c.rowSelected = !this.selectedAll;
                    });
                }
            },
            showInfodlg(notice){
                this.notice=notice;
                this.infodlg = true;
            },
        },
        created() {
            this.search();
        }
    }
</script>

<style scoped>

</style>
