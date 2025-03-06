<template>

    <div class="mypanel">
        <div class="hd">登录密码修改</div>
        <div class="bd">
            <table  cellspacing="1"  class="smart-table" border="1" cellpadding="0" width="100%" >

                <tr>
                    <td class="tlabel" align="right" >原始密码 :</td>
                    <td align="left" >
                        <el-input style="width: 400px;"
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
                        <el-input style="width: 400px;"
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
                        <el-input style="width: 400px;"
                                  prefix-icon="el-icon-lock"
                                  type="password"
                                  placeholder="请输入确认密码"
                                  v-model="repassword2"
                                  clearable>
                        </el-input>

                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <el-button @click="modifyHandler" type="primary" >确 定</el-button>

                    </td>
                </tr>

            </table>

        </div>
    </div>

</template>

<script>
    export default {
        name: "modifypw",
        data(){
            return {
                password1:"",
                repassword1:"",
                repassword2:""
            }
        },
        props:{
            accountInfo:{
                type:Object
            }
        },
        methods:{
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

                this.$router.push("/huiyuan/accountinfo");

            },
        }
    }
</script>

<style scoped>

</style>
