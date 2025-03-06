<template>

    <div id="app">
        <div class="mypanel1">
            <div class="hd">
                <div class="title">后台用户管理</div>
            </div>
            <div class="bd">

        <div class="search-options">

            <table cellspacing="0" width="100%">
                <tbody>
                <tr>
                    <td>用户名:<div  style="display: inline-block;width:280px;"><el-input  @keyup.enter.native="search"  v-model="username" placeholder="用户名" type="text" /></div>
                        <el-button type="primary" icon="el-icon-search"  @click="search">查找</el-button>

                    </td>
                </tr>
                </tbody>
            </table>


        </div>
        <div class="action-details">
            <el-button type="danger" @click="deleteRec" icon="el-icon-delete">删除</el-button>
        </div>


        <table  width="100%" border="0" cellspacing="0"
                cellpadding="0" class="datatable">
            <thead>
            <tr>
                <th>
                    <input type="checkbox" @click="selectedAllHandler" v-model="selectedAll"/>
                </th>
                <th>
                    <b>用户名</b>
                </th>
                <th>
                    <b>角色</b>
                </th>
                <th>
                    <b>创建时间</b>
                </th>
                <th>
                    <b>绑定邮箱</b>
                </th>
                <th>
                    <b>电话</b>
                </th>
                <th>
                    <b>登录次数</b>
                </th>
                <th>
                    <b>真名</b>
                </th>
                <th>
                    <b>昵称</b>
                </th>
                <th>
                    <b>性别</b>
                </th>

                <th>
                    操作
                </th>
            </tr>
            </thead>
            <tbody>

            <tr v-show="listUsers.length==0">
                <td colspan="20">
                    没有相关系统学生信息
                </td>
            </tr>


            <tr v-for="user in listUsers" :key="user.id">
                <td>

                    <input  class="check" name="ids" type="checkbox" v-model="user.rowSelected" :value="user.id"/>
                </td>
                <td>
                    {{user.username}}
                </td>


                <td>
                    {{user.rolename}}

                </td>
                <td>
                    {{user.createtime}}

                </td>
                <td>
                    {{user.email}}

                </td>
                <td>
                    {{user.tel}}
                </td>
                <td>
                    {{user.logtimes}}

                </td>
                <td>
                    {{user.realname}}
                </td>
                <td>
                    {{user.nickname}}
                </td>
                <td>
                    {{user.sex}}
                </td>

                <td>
                    <el-button-group>
                        <route-button :url="'/admin/usersadd?id='+user.id" icon="el-icon-edit" text="编辑"></route-button>

                    </el-button-group>
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

    </div>
</template>

<script>
    export default {
        data(){
            return {
                username:"",
                hostHead: app.contextPath,
                selectedAll:false,
                listUsers:[],
                pageindex:1, //初始页
                pagesize:10,
                total:10
            }

        },
        methods:{
            pagesizeChange: function (pagesize) {
                this.pagesize = pagesize;
            },
            pageindexChange: function(pageindex){
                this.pageindex = pageindex;
                console.log(this.pageindex);
                this.search();
            },
            async search(){
                let url="admin/users/list";
                let param={
                    currentpageindex:this.pageindex,
                    pagesize:this.pagesize,
                    username:this.username
                };
                console.log("this.pageindex="+this.pageindex);
                let {data:res}=await this.$http.post(url,param);
                if(res!=null){
                    console.log("服务端返回数据",res);
                    let pageInfo=res.data;
                    this.total=pageInfo.total;
                    this.listUsers=pageInfo.list;
                    console.log(pageInfo);
                }
            },
            async deleteRec(){
                let url="admin/users/delete";
                let hasChecked=this.listUsers.some(c=>{
                    return c.rowSelected==true;
                });
                if(!hasChecked){
                    this.$alert('请选择需要删除的记录', '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }

                let ids=this.listUsers.filter(c=>c.rowSelected).map(c=>c.id);
                console.log("ids",ids);
                this.$confirm('是否要删除学生?', '系统提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(async ()=>{
                   let {data:res}=await this.$http.post(url, {ids},{emulateJSON:false});
                   console.log("服务器端返回",res);
                    if (res!= null && res.stateCode > 0) {
                        this.search();
                    }
                }).catch(()=>{});
            },
            selectedAllHandler(){
                console.log(this.selectedAll);
                if(this.listUsers!=null){
                    this.listUsers.forEach(c=>{
                        c.rowSelected=!this.selectedAll;
                    });
                }
            }
        },
        created(){
            this.search();
        }
    }
</script>

<style scoped>

</style>
