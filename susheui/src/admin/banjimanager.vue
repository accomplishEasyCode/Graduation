<template>
    <div class="panel panel-default">

        <div class="panel-heading">
            班级管理
        </div>

        <div class="panel-body">
            <div class="search-options">


                <table cellspacing="0" width="100%">
                    <tbody>

                    <tr>
                        <td>


                            名称: <input name="name" @keyup.enter="search" v-model="name" class="input-txt" type="text"/>


                            <el-button type="success" icon="el-icon-search" @click="search">搜索</el-button>
                        </td>
                    </tr>
                    </tbody>
                </table>


            </div>


            <div class="action-details">
                <el-button-group>
                    <!--{{export-view}}-->
                    <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteRec">删除</el-button>
                    <pop-dialog-button title="新增班级" @refresh="search" width="60%"  css-class="el-button el-button--primary el-button--mini" :url="`/admin/banjiadd`"><i class="el-icon-plus"></i>新增班级</pop-dialog-button>

                </el-button-group>
            </div>

            <table  width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
                <thead>
                <tr>
                    <th>
                        <input type="checkbox" @click="selectedAllHandler" v-model="selectedAll"/>
                    </th>


                    <th><b>名称</b></th>


                    <th>
                        操作
                    </th>

                </tr>

                </thead>
                <tbody>


                <tr v-for="(banji,index) in  listBanji" :key="banji.id">
                    <td>
                        <input class="check" name="ids" type="checkbox" v-model="banji.rowSelected"/>


                    </td>


                    <td>
                        {{banji.name}}
                    </td>


                    <td>
                        <el-button-group>
                            <pop-dialog-button title="编辑班级" @refresh="search" width="60%"  css-class="el-button el-button--primary el-button--mini" :url="`/admin/banjiadd?id=${banji.id}`"><i class="el-icon-edit"></i>编辑</pop-dialog-button>
                        </el-button-group>
                    </td>
                </tr>

                <tr v-if="listBanji.length==0">
                    <td colspan="20">
                        没有相关班级信息
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



        </div>
    </div>
</template>


<script type="text/javascript">

    import {VueUtil} from "../util/vueutil";
    import PopDialogButton from "../common/mydialog/PopDialogButton";

    export default {

        name: "banjimanager",
        components: {PopDialogButton},
        data() {
            return {


                "name": "",


                hostHead: this.$app.contextPath,
                selectedAll: false,
                listBanji: [],
                pageindex: 1, //初始页
                pagesize: 10,
                total: 10,
                infodlg: false,
                banji: {}

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
                let url = "admin/banji/list";
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
                    this.listBanji = pageInfo.list;
                    console.log(pageInfo);
                }
            },
            async deleteRec() {
                let url = "admin/banji/delete";
                let util = new VueUtil(this);
                let hasChecked = this.listBanji.some(c => {
                    return c.rowSelected == true;
                });
                if (!hasChecked) {
                    util.alert('请选择需要删除的记录', '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }

                let ids = this.listBanji.filter(c => c.rowSelected).map(c => c.id);
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
                window.location.href = this.hostHead + "/admin/banji/expor";
            },
            selectedAllHandler() {
                console.log(this.selectedAll);
                if (this.listBanji != null) {
                    this.listBanji.forEach(c => {
                        c.rowSelected = !this.selectedAll;
                    });
                }
            },
            showInfodlg(banji) {
                this.banji = banji;
                this.infodlg = true;
            },
        },
        created() {
            this.search();
        }
    };

</script>
