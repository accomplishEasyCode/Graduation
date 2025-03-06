<template>
    <div id="app">

        <div class="panel panel-default">
            <div class="panel-heading">

                <span v-if="actiontype=='save'">新增用户</span>
                <span v-if="actiontype=='update'">编辑用户</span>

            </div>
            <div class="panel-body">
                <table class="smart-table" border="1" cellspacing="1" width="100%">


                    <tr>
                        <td class="tlabel" width="10%" align="right">用户名:</td>
                        <td width="*">
                            <input name="username" v-model="users.username" placeholder="用户名"
                                   v-validate="{required:true,messages:{required:'请输入用户名'}}" class="input-txt" type="text"/>
                        </td>
                        <td colspan="2" rowspan="6">

                            <dw-upload :host-head="hostHead" v-model="users.xiangpian"></dw-upload>
                        </td>
                    </tr>


                    <tr>
                        <td class="tlabel" width="10%" align="right">密码:</td>
                        <td width="*">
                            <input name="password" v-model="users.password" placeholder="密码"
                                   v-validate="{required:true,messages:{required:'请输入密码'}}" class="input-txt" type="password"/>
                        </td>
                    </tr>



                    <tr>
                        <td class="tlabel" align="right">角色:</td>
                        <td>

                            <el-select   v-model="users.roleid">
                                <el-option v-for="item in listRole" :key="item.id" :value="item.id" :label="item.name"></el-option>
                            </el-select>

                        </td>
                    </tr>



                    <tr>
                        <td class="tlabel" width="10%" align="right">姓名:</td>
                        <td width="*">
                            <input name="realname" v-model="users.realname" placeholder="姓名"
                                   v-validate="{required:true,messages:{required:'请输入姓名'}}" class="input-txt" type="text"/>
                        </td>
                    </tr>




                    <tr>
                        <td  class="tlabel" align="right">性别:</td>
                        <td width="*">
                            <el-radio-group v-model="users.sex">
                                <el-radio label="男"></el-radio>
                                <el-radio label="女"></el-radio>
                            </el-radio-group>

                        </td>
                    </tr>


                    <tr>
                        <td class="tlabel" align="right">电话:</td>
                        <td width="*">
                            <input name="tel" v-model="users.tel" placeholder="电话"
                                   v-validate="{required:true,messages:{required:'请输入电话'}}" class="input-txt" type="text"/>
                        </td>
                    </tr>



                    <tr>
                        <td class="tlabel" align="right">昵称:</td>
                        <td width="*">
                            <input name="nickname" v-model="users.nickname" placeholder="昵称"
                                   v-validate="{required:true,messages:{required:'请输入昵称'}}" class="input-txt" type="text"/>
                        </td>

                        <td class="tlabel" align="right">邮箱:</td>
                        <td width="*">
                            <input name="email" v-model="users.email" placeholder="邮箱"
                                   v-validate="{required:true,messages:{required:'请输入邮箱'}}" class="input-txt" type="text"/>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="4">

                            <el-button icon="el-icon-check" @click="submitHandler" type="danger">提交</el-button>

                        </td>
                    </tr>


                </table>

            </div>
        </div>

    </div>

</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "usersadd",
        data(){
            return{
                dangerinput: false,
                pageTitle:'新增学生信息',
                errors: {},
                listRole:[],
                hostHead:this.$app.contextPath,
                actiontype:'save',
                "users": {
                    "username": "",
                    "password": "",
                    "sex": "男",
                    "tel": "",
                    "roleid":1,
                    "realname": "",
                    "nickname": "",
                    "email": "",
                    "creator":this.accountInfo.username,
                    "xiangpian": "/upload/nopic.jpg",
                },
            }
        },
        created(){
            this.load();
            this.getRoles();
        },
        props:{
            accountInfo: {type:Object}
        },
        methods:{
            uploaded(res, file) {
                console.log("上传成功",res.data);
                if(res.data!=null&&res.data.length>0)
                    this.users.xiangpian=res.data[0].relativeUrl;
            },
            async getRoles() {
                let url = "admin/sysrole/list";
                let param = {
                    "ispaged":"-1"
                };
                let util = new VueUtil(this);
                let {data:res} = await util.http.post(url, param);
                console.log("sysrolecontroller返回",res);
                this.listRole = res.data;
                if(this.listRole!=null&&this.listRole.length>0){
                    this.users.roleid=this.listRole[0].id;
                }

            },
            submitHandler(){

                if(this.actiontype==="save")
                    this.doSubmit({url:"/admin/users/save",actionTip: "新增学生成功"});
                if(this.actiontype==="update")
                    this.doSubmit({url:"/admin/users/update",actionTip: "更新学生成功"});

            },
            async doSubmit(options){
                let defaultOptions={
                    url:"/admin/users/add",
                    actionTip:"新增学生成功"
                };
                Object.assign(defaultOptions,options);
                let params={...this.users};
                console.log("params=",params);
                const  validRes=this.myValidator.valid(this);
                if (!validRes)
                    return ;

                let {data:res}= await this.$http.post(defaultOptions.url,params);
                console.log("服务端返回",res);
                if(res.stateCode<=0){
                    this.$alert(res.des, '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }
                this.$message({
                    message:defaultOptions.actionTip,
                    type:'success',
                    duration:3000
                });
                this.$router.push("/admin/usersmanager");
            },
            preupload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            async load(){

                let id=this.$route.query.id;
                if(id!=null){
                    this.actiontype="update";
                    this.pageTitle="编辑学生信息";
                    let url="/admin/users/load";
                    let {data:res}=await this.$http.post(url,{id:id});
                    console.log("res",res);
                    if(res!=null&&res.data!=null)
                        this.users=res.data;
                }
            }

        }
    }
</script>

<style scoped>

</style>
