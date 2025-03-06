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
<!--                <div class="nav-control">-->
<!--                    <div class="hamburger">-->
<!--                        <span class="line"></span>-->
<!--                        <span class="line"></span>-->
<!--                        <span class="line"></span>-->
<!--                    </div>-->
<!--                </div>-->

                <div class="hcontent">
                    <div class="cleft">
                        <div class="title"></div>
                    </div>
                    <div class="cright">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <route-template url="/huiyuan/xinximanager">

                                    <svg width="28" height="28" viewBox="0 0 28 28" fill="none" xmlns="http://www.w3.org/2000/svg">
                                        <path fill-rule="evenodd" clip-rule="evenodd" d="M25.6666 8.16666C25.6666 5.5895 23.5771 3.5 21 3.5C17.1161 3.5 10.8838 3.5 6.99998 3.5C4.42281 3.5 2.33331 5.5895 2.33331 8.16666V23.3333C2.33331 23.8058 2.61798 24.2305 3.05315 24.4113C3.48948 24.5922 3.99115 24.4918 4.32481 24.1582C4.32481 24.1582 6.59281 21.8902 7.96714 20.517C8.40464 20.0795 8.99733 19.8333 9.61683 19.8333H21C23.5771 19.8333 25.6666 17.7438 25.6666 15.1667V8.16666ZM23.3333 8.16666C23.3333 6.87866 22.2891 5.83333 21 5.83333C17.1161 5.83333 10.8838 5.83333 6.99998 5.83333C5.71198 5.83333 4.66665 6.87866 4.66665 8.16666V20.517L6.31631 18.8673C7.19132 17.9923 8.37899 17.5 9.61683 17.5H21C22.2891 17.5 23.3333 16.4558 23.3333 15.1667V8.16666ZM8.16665 15.1667H17.5C18.144 15.1667 18.6666 14.644 18.6666 14C18.6666 13.356 18.144 12.8333 17.5 12.8333H8.16665C7.52265 12.8333 6.99998 13.356 6.99998 14C6.99998 14.644 7.52265 15.1667 8.16665 15.1667ZM8.16665 10.5H19.8333C20.4773 10.5 21 9.97733 21 9.33333C21 8.68933 20.4773 8.16666 19.8333 8.16666H8.16665C7.52265 8.16666 6.99998 8.68933 6.99998 9.33333C6.99998 9.97733 7.52265 10.5 8.16665 10.5Z" fill="#FE634E"/>
                                    </svg>
                                </route-template>
                            </li>

                            <li class="nav-item">
                                <el-dropdown class="user-name" trigger="click">
                                  <span style="color: #0f0f0f" class="el-dropdown-link">
                                      {{accountInfo.accountname}}-{{accountInfo.name}}(学生)
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
                this.$app.mycas.logout(this.$app.mycas.huiyuan_key);
                this.$router.push("/admin/login");
            },

            async modifyHandler(){
                let url="admin/huiyuan/modifypw";
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
                this.$router.push("/huiyuan/accountinfo");
            }

        },
        created() {
            this.accountInfo=this.$app.mycas.getAccount(this.$app.mycas.huiyuan_key);
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
