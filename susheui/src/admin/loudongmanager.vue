<template>
    <div class="panel panel-default">

        <div class="panel-heading">
            楼栋管理
        </div>

        <!-- 搜索控件开始 -->
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
        <!-- 搜索控件结束 -->


        <div class="action-details">
            <el-button-group>
                <!--{{export-view}}-->
                <el-button type="danger" icon="el-icon-delete" @click="deleteRec">删除</el-button>
                <route-button icon="el-icon-plus" url="/admin/loudongadd">新增</route-button>

            </el-button-group>
        </div>

        <table id="loudong" width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
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


            <tr v-for="(loudong,index) in  listLoudong" :key="loudong.id">
                <td>
                    <input class="check" name="ids" type="checkbox" v-model="loudong.rowSelected"/>


                </td>


                <td>
                    {{ loudong.name }}
                </td>


                <td>
                    <el-button-group>
                        <router-link class="el-button el-button--primary el-button--small"
                                     :to="`/admin/loudongadd?id=${loudong.id}`"><i class="el-icon-edit"> </i>编辑
                        </router-link>


                    </el-button-group>
                </td>
            </tr>

            <tr v-if="listLoudong.length==0">
                <td colspan="20">
                    没有相关楼栋信息
                </td>
            </tr>


            </tbody>
        </table>
        <el-pagination
            background
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


<script type="text/javascript">

import {VueUtil} from "../util/vueutil";

export default {

    name: "loudongmanager",
    data() {
        return {


            "name": "",


            hostHead: this.$app.contextPath,
            selectedAll: false,
            listLoudong: [],
            pageindex: 1, //初始页
            pagesize: 10,
            total: 10,
            infodlg: false,
            loudong: {}

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
            let url = "admin/loudong/list";
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
                this.listLoudong = pageInfo.list;
                console.log(pageInfo);
            }
        },
        async deleteRec() {
            let url = "admin/loudong/delete";
            let util = new VueUtil(this);
            let hasChecked = this.listLoudong.some(c => {
                return c.rowSelected == true;
            });
            if (!hasChecked) {
                util.alert('请选择需要删除的记录', '系统提示', {
                    confirmButtonText: '确定'
                });
                return;
            }

            let ids = this.listLoudong.filter(c => c.rowSelected).map(c => c.id);
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
            window.location.href = this.hostHead + "/admin/loudong/expor";
        },
        selectedAllHandler() {
            console.log(this.selectedAll);
            if (this.listLoudong != null) {
                this.listLoudong.forEach(c => {
                    c.rowSelected = !this.selectedAll;
                });
            }
        },
        showInfodlg(loudong) {
            this.loudong = loudong;
            this.infodlg = true;
        },
    },
    created() {
        this.search();
    }
};

</script>
