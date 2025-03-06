<template>
    <div class="mypanel">
        <div class="hd">

                编辑账户


        </div>

        <div class="bd">

           <table class="smart-table" border="1" cellspacing="1" width="100%">


            <tr>
                <td class="tlabel" width="10%" align="right">用户名:</td>
                <td  width="*">
                    {{users.username}}
                </td>
                <td colspan="2" rowspan="6">

                    <dw-upload :host-head="hostHead" v-model="users.xiangpian"></dw-upload>
                </td>
            </tr>




               <tr>
                   <td class="tlabel" align="right">角色:</td>
                   <td>

                     {{accountInfo.rolename}}

                   </td>
               </tr>



               <tr>
                <td class="tlabel"  align="right">姓名:</td>
                <td width="*">
                    <input name="realname" v-model="users.realname" placeholder="姓名"
                           v-validate="{required:true,messages:{required:'请输入姓名'}}" class="input-txt" type="text"/>

                </td>
            </tr>




            <tr>
                <td class="tlabel" align="right">性别:</td>
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
            </tr>
            <tr>
                <td class="tlabel" align="right">邮箱:</td>
                <td colspan="3" width="*">
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
</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "modifyinfo",
        data() {
            return {

                actiontype: 'save',
                hostHead: this.$app.contextPath,
                errors: {},
                listRole:[],
                "users": {
                    "username": "",
                    "password": "",
                    "sex": "",
                    "tel": "",
                    "realname": "",
                    "nickname": "",
                    "email": "",
                    "creator": this.accountInfo.username,
                    "xiangpian": "/upload/nopic.jpg",


                },
            }
        },
        props:{
            accountInfo:{
                type:Object
            }
        },
        methods: {
            //begin load
            async load() {
                let id =this.accountInfo.id;
                if (id != null) {
                    this.actiontype = "update";
                    this.pageTitle = "编辑用户信息";
                    let url = "admin/users/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null)
                        this.users = res.data;
                }
            },//end load
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/users/update",
                    actionTip: "更新成功"
                };
                const  validRes=this.myValidator.valid(this);
                if (!validRes)
                    return ;
                let util = new VueUtil(this);
                let params = {...this.users};
                let {data: res} = await util.http.post(defaultOptions.url, params);
                if (res.stateCode <= 0) {
                    util.alert(res.des, '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }
                util.message({
                    message: defaultOptions.actionTip,
                    type: 'success',
                    duration: 2000
                });
                this.$router.push("/admin/accountinfo");
            },
            async getRoles() {
                let url = "admin/sysrole/list";
                let param = {
                    "ispaged":"-1"
                };
                let util = new VueUtil(this);
                let {data:res} = await util.http.post(url, param);
                this.listRole = res.data;

            },
        },
        created() {
            this.getRoles();
            this.load();
        }

    }
</script>

<style scoped>

</style>
