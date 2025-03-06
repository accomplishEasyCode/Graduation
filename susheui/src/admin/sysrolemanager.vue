<template>
    <div class="mypanel1">

        <div class="hd">
            后台角色管理
        </div>

        <div class="bd">
        <!-- 搜索控件开始 -->
        <div class="search-options">


            <table cellspacing="0" width="100%">
                <tbody>

                <tr>
                    <td>


                        名称:<input name="name" @keyup.enter="search" v-model="name" class="input-txt" type="text"/>


                        <el-button  type="primary" @click="search">
                            <i class="el-icon-search"></i>搜索
                        </el-button>
                    </td>
                </tr>
                </tbody>
            </table>


        </div>
        <!-- 搜索控件结束 -->

        <div class="action-details">
            <div class="el-button-group">

                <!--{{export-view}}-->
                <el-button type="danger" @click="deleteRec" icon="el-icon-delete">删除</el-button>

                <pop-dialg-button @refresh="search" width="50%" title="新增角色" css-class="el-button" url="/admin/sysroleadd"><i  class="el-icon-plus"></i>新增角色</pop-dialg-button>

            </div>
        </div>

        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
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


            <tr v-for="(sysrole,index) in  listSysrole" :key="sysrole.id">
                <td>
                    <input class="check" name="ids" type="checkbox" v-model="sysrole.rowSelected"/>


                </td>


                <td>
                    {{sysrole.name}}
                </td>


                <td>
                    <div class="el-button-group">
                        <pop-dialg-button @refresh="search" width="50%" title="编辑角色" css-class="el-button el-button--warning" :url="'/admin/sysroleadd?id='+sysrole.id"><i  class="el-icon-edit"></i>修改</pop-dialg-button>

                    </div>
                </td>
            </tr>

            <tr v-if="listSysrole.length==0">
                <td colspan="20">
                    没有相关后台角色信息
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

<script>
    import  {VueUtil} from "../util/vueutil";
    import PopDialgButton from "../common/mydialog/PopDialogButton";

    export default {
        name: "sysrolemanager",
        components: {PopDialgButton},
        data() {
            return {
                "name": "",
                acldlg:false,
                listSysresource: [],
                hostHead: this.$app.contextPath,
                selectedAll: false,
                checkedIds:[],
                listAcl:[],
                listSysrole: [],
                sysrole:{},
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

            async setCheckedIds() {
                let url = "admin/roleacl/list";
                let param = {
                    "roleid": this.sysrole.id,
                };
                this.checkedIds=[];

                let {data: res} = await this.$http.post(url, param);
                if (res != null) {
                    this.listAcl = res.data;
                    if(this.listAcl!=null&&this.listAcl.length>0) {
                        this.checkedIds = this.listAcl.map(c => c.id);
                        console.log("this.checkedIds=",this.checkedIds);
                    }
                }
            },


            checkHandler(data, checked, indeterminate) {
                if(checked){
                    let index=  this.checkedIds.findIndex(c=>c==data.id);
                    if(index==-1)
                        this.checkedIds.push(data.id);
                }else{
                    let index=  this.checkedIds.findIndex(c=>c==data.id);
                    if(index!=-1)
                        this.checkedIds.splice(index,1);
                }
                console.log(data, checked, indeterminate);
            },
            async search() {
                let url = "admin/sysrole/list";
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
                    this.listSysrole = pageInfo.list;
                    console.log(pageInfo);
                }
            },
            async deleteRec() {
                let url = "admin/sysrole/delete";
                let util = new VueUtil(this);
                let hasChecked = this.listSysrole.some(c => {
                    return c.rowSelected == true;
                });
                if (!hasChecked) {
                    util.alert('请选择需要删除的记录', '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }

                let ids = this.listSysrole.filter(c => c.rowSelected).map(c => c.id);
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
                if (this.listSysrole != null) {
                    this.listSysrole.forEach(c => {
                        c.rowSelected = !this.selectedAll;
                    });
                }
            },

        },
        created() {
            this.search();
        }
    }
</script>

<style scoped>

</style>
