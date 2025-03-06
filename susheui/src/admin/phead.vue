<template>

    <div>
        <div class="phead">

                <div class="brand-logo">
                    <a href="#">
                        <img src="../admin/images/logo.png"/>

                    </a>
                    <div class="sys-title">

                        宿舍管理系统


                    </div>

                </div>


                <div class="hcontent">
                    <div class="cleft">
                        <div class="title"></div>
                    </div>
                    <div class="cright">
                        <ul class="navbar-nav">

                            <li class="nav-item">
                                <el-dropdown class="user-name" trigger="click">
                                  <span style="color: #0f0f0f" class="el-dropdown-link">
                                      {{accountInfo.username}}({{accountInfo.rolename}})
                                    <i class="el-icon-caret-bottom"></i>
                                  </span>
                                    <el-dropdown-menu slot="dropdown">
                                        <el-dropdown-item @click.native="accountHandler">用户信息</el-dropdown-item>
                                        <el-dropdown-item @click.native="modifypwdlg=true">修改密码</el-dropdown-item>
                                        <el-dropdown-item divided @click.native="exitHandler">退出登录</el-dropdown-item>
                                    </el-dropdown-menu>
                                </el-dropdown>


                            </li>
                        </ul>

                    </div>
                </div>




            </div>

        <el-dialog
            title="账户密码设置"
            :close-on-click-modal="false"
            :visible.sync="modifypwdlg">
            <table  cellspacing="1"  class="smart-table" border="1" cellpadding="0" width="100%" >

                <tr>
                    <td class="tlabel" align="right" >原始密码 :</td>
                    <td align="left" >
                        <el-input
                            prefix-icon="el-icon-lock"
                            type="password"
                            placeholder="请输入原始密码"
                            v-model="password1"
                            clearable>
                        </el-input>

                    </td>
                </tr>
                <tr>
                    <td class="tlabel" align="right">修改密码 :</td>
                    <td align="left">
                        <el-input
                            prefix-icon="el-icon-lock"
                            type="password"
                            placeholder="请输入修改密码"
                            v-model="repassword1"
                            clearable>
                        </el-input>

                    </td>
                </tr>
                <tr>
                    <td class="tlabel" align="right" >确认密码 :</td>
                    <td align="left">
                        <el-input
                            prefix-icon="el-icon-lock"
                            type="password"
                            placeholder="请输入确认密码"
                            v-model="repassword2"
                            clearable>
                        </el-input>

                    </td>
                </tr>

            </table>



            <span slot="footer" class="dialog-footer">
                        <el-button @click="modifyHandler" type="primary" >确 定</el-button>
                        <el-button @click="modifypwdlg = false">取 消</el-button>
                    </span>
        </el-dialog>

    </div>


</template>

<script>
    export default {
        name: "phead",
        data(){
            return {
                accountInfo:{},
                modifypwdlg:false,
                password1:"",
                repassword1:"",
                repassword2:""

            }
        },
        methods:{
            async exitHandler () {
                let res= await this.$confirm('确定要退出?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    center: true
                });
                console.log(res);
                this.$app.mycas.logout(this.$app.mycas.users_key);

                this.$router.push("/admin/login");
            },

            async modifyHandler(){
                let url="admin/users/modifypw";
                if (this.password1==""){
                    this.$message.error("请输入原始密码");
                    return ;
                }
                if (this.repassword1==""){
                    this.$message.error("请输入修改密码");
                    return ;
                }
                if (this.repassword2==""){
                    this.$message.error("请输入确认密码");
                    return ;
                }
                if(this.repassword1!=this.repassword2){
                    this.$message.error("两次密码不一致");
                    return ;
                }
                if(this.accountInfo.id==null||this.accountInfo.id==""){
                    this.$message.error("账户非法");
                    return;
                }

                let {data:res}= await this.$http.post(url, {
                    id: this.accountInfo.id,
                    password1: this.password1,
                    repassword1: this.repassword1
                });
                if (res!=null&&res.stateCode<0){
                    this.$message.error(res.des);
                    return ;
                }
                this.$message.success("修改成功");
                this.modifypwdlg=false;
            },
            accountHandler(){
                this.$router.push("/admin/accountinfo");
            }

        },
        created() {
            this.accountInfo=this.$app.mycas.getAccount(this.$app.mycas.users_key);

        },
    }
</script>

<style scoped>
    .header {
        background-color: #23262e !important;
        position: relative;
        box-sizing: border-box;
        width: 100%;
        height: 70px;
        font-size: 22px;
        color: #fff;
    }

    .collapse-btn {
        float: left;
        padding: 0 21px;
        cursor: pointer;
        line-height: 70px;
    }

    .header .logo {
        float: left;
        width: 250px;
        line-height: 70px;
    }

    .header-right {
        float: right;
        padding-right: 50px;
    }

    .header-user-con {
        display: flex;
        height: 70px;
        align-items: center;
    }

    .btn-fullscreen {
        transform: rotate(45deg);
        margin-right: 5px;
        font-size: 24px;
    }

    .btn-bell,
    .btn-fullscreen {
        position: relative;
        width: 30px;
        height: 30px;
        text-align: center;
        border-radius: 15px;
        cursor: pointer;
    }

    .btn-bell-badge {
        position: absolute;
        right: 0;
        top: -2px;
        width: 8px;
        height: 8px;
        border-radius: 4px;
        background: #f56c6c;
        color: #fff;
    }

    .btn-bell .el-icon-bell {
        color: #fff;
    }

    .user-name {
        margin-left: 10px;
    }

    .user-avator {
        margin-left: 20px;
    }

    .user-avator img {
        display: block;
        width: 40px;
        height: 40px;
        border-radius: 50%;
    }

    .el-dropdown-link {
        color: #fff;
        cursor: pointer;
    }

    .el-dropdown-menu__item {
        text-align: center;
    }

    .collapse-btn:hover {
        background-color: #009688 !important;
    }
</style>
