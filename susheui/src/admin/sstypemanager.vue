<template>
    <div class="panel panel-default">

        <div class="panel-heading">
            宿舍类型管理
        </div>

        <!-- 搜索控件开始 -->
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
        <!-- 搜索控件结束 -->


        <div class="action-details">
            <el-button-group>
                <!--{{export-view}}-->
                <el-button type="danger" icon="el-icon-delete" @click="deleteRec">删除</el-button>
                <route-button icon="el-icon-plus" url="/admin/sstypeadd">新增</route-button>

            </el-button-group>
        </div>

        <table id="sstype" width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
            <thead>
            <tr>
                <th>
                    <input type="checkbox" @click="selectedAllHandler" v-model="selectedAll"/>
                </th>


                <th><b>名称</b></th>


                <th><b>居住人数</b></th>


                <th>
                    操作
                </th>

            </tr>

            </thead>
            <tbody>


            <tr v-for="(sstype,index) in  listSstype" :key="sstype.id">
                <td>
                    <input class="check" name="ids" type="checkbox" v-model="sstype.rowSelected"/>


                </td>


                <td>
                    {{ sstype.name }}
                </td>


                <td>
                    {{ sstype.renshu }}人
                </td>


                <td>
                    <el-button-group>
                        <router-link class="el-button el-button--primary el-button--small"
                                     :to="`/admin/sstypeadd?id=${sstype.id}`"><i class="el-icon-edit"> </i>编辑
                        </router-link>

                        <el-button size="small" type="warning" @click="showInfodlg(sstype)"><i class="el-icon-info"></i>
                            详细
                        </el-button>
                    </el-button-group>
                </td>
            </tr>

            <tr v-if="listSstype.length==0">
                <td colspan="20">
                    没有相关宿舍类型信息
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


        <el-dialog :visible.sync="infodlg" title="宿舍类型详细">

            <table class="smart-table" border="1" cellspacing="1" width="100%">


                <tr>
                    <td class="tlabel" width="10%" align="right">名称:</td>
                    <td width="*">
                        {{ sstype.name }}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="10%" align="right">居住人数:</td>
                    <td width="*">
                        {{ sstype.renshu }}
                    </td>
                </tr>


            </table>


        </el-dialog>
    </div>
</template>


<script type="text/javascript">

import {VueUtil} from "../util/vueutil";

export default {

    name: "sstypemanager",
    data() {
        return {


            "name": "",


            hostHead: this.$app.contextPath,
            selectedAll: false,
            listSstype: [],
            pageindex: 1, //初始页
            pagesize: 10,
            total: 10,
            infodlg: false,
            sstype: {}

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
            let url = "admin/sstype/list";
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
                this.listSstype = pageInfo.list;
                console.log(pageInfo);
            }
        },
        async deleteRec() {
            let url = "admin/sstype/delete";
            let util = new VueUtil(this);
            let hasChecked = this.listSstype.some(c => {
                return c.rowSelected == true;
            });
            if (!hasChecked) {
                util.alert('请选择需要删除的记录', '系统提示', {
                    confirmButtonText: '确定'
                });
                return;
            }

            let ids = this.listSstype.filter(c => c.rowSelected).map(c => c.id);
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
            window.location.href = this.hostHead + "/admin/sstype/expor";
        },
        selectedAllHandler() {
            console.log(this.selectedAll);
            if (this.listSstype != null) {
                this.listSstype.forEach(c => {
                    c.rowSelected = !this.selectedAll;
                });
            }
        },
        showInfodlg(sstype) {
            this.sstype = sstype;
            this.infodlg = true;
        },
    },
    created() {
        this.search();
    }
};

</script>
