<template>
    <div class="container" >


       <div v-if="!over" class="panel panel-primary">
           <div class="panel-heading">学生在线注册 <span style="color:white;margin-left:60px;"  @click="navigatorTo">登录</span></div>
           <div class="panel-body">

               <table class="smart-table" border="1" cellspacing="1" width="100%">


                   <tr>
                       <td width="10%" align="right">账号:</td>
                       <td width="*">
                           <input name="accountname" v-model="huiyuan.accountname" placeholder="账号"
                                  v-validate="{required:true,messages:{required:'请输入账号'}}" class="input-txt" type="text"/>


                       </td>
                   </tr>


                   <tr>
                       <td width="10%" align="right">姓名:</td>
                       <td width="*">
                           <input name="name" v-model="huiyuan.name" placeholder="姓名"
                                  v-validate="{required:true,messages:{required:'请输入姓名'}}" class="input-txt" type="text"/>


                       </td>
                   </tr>



                   <tr>
                       <td width="10%" align="right">密码:</td>
                       <td width="*">
                           <input name="password" v-model="huiyuan.password" placeholder="密码"
                                  v-validate="{required:true,messages:{required:'请输入密码'}}" class="input-txt" type="password"/>


                       </td>
                   </tr>


                   <tr>
                       <td align="right">性别:</td>
                       <td>
                           <el-radio-group v-model="huiyuan.sex">
                               <el-radio label="男"> 男</el-radio>
                               <el-radio label="女"> 女</el-radio>
                           </el-radio-group>
                       </td>
                   </tr>





                   <tr>
                       <td width="10%" align="right">身份证:</td>
                       <td width="*">
                           <input name="idcardno" v-model="huiyuan.idcardno" placeholder="身份证"
                                  v-validate="{required:true,idcardno:true,messages:{required:'请输入身份证',idcardno:'请输入正确的身份证号'}}" class="input-txt" type="text"/>


                       </td>
                   </tr>


                   <tr>
                       <td align="right">相片:</td>
                       <td>

                           <dw-upload :host-head="hostHead" v-model="huiyuan.photo"></dw-upload>
                       </td>
                   </tr>


                   <tr>
                       <td width="10%" align="right">籍贯:</td>
                       <td width="*">
                           <input name="jiguan" v-model="huiyuan.jiguan" placeholder="籍贯"
                                  v-validate="{required:true,messages:{required:'请输入籍贯'}}" class="input-txt" type="text"/>


                       </td>
                   </tr>


                   <tr>
                       <td width="10%" align="right">班级:</td>
                       <td width="*">
                           <el-select  v-model="huiyuan.bjid">
                               <el-option v-for="item in listBanji" :value="item.id" :label="item.name"></el-option>
                           </el-select>

                       </td>
                   </tr>



                   <tr>
                       <td width="10%" align="right">生日:</td>
                       <td width="*">
                           <el-date-picker v-model="huiyuan.birthday" format="yyyy-MM-dd" value-format="yyyy-MM-dd" type="date"
                                           placeholder="选择日期">
                           </el-date-picker>
                       </td>
                   </tr>



                   <tr>
                       <td width="10%" align="right">地址:</td>
                       <td width="*">
                           <input name="address" v-model="huiyuan.address" placeholder="地址"
                                  v-validate="{required:true,messages:{required:'请输入地址'}}" class="input-txt" type="text"/>


                       </td>
                   </tr>




                   <tr>
                       <td colspan="4">

                           <el-button type="primary" icon="el-icon-check" @click="submitHandler">提交</el-button>

                       </td>
                   </tr>


               </table>

           </div>
       </div>

        <div v-else class="panel panel-warning">
            <div class="panel-heading">注册结果</div>
            <div class="panel-body">
                <div class="no-record-tip">
                    <div class="content">
                        <i class="el-icon-check"></i> {{huiyuan.accountname}}注册成功
                        <router-link to="/admin/login">登录系统</router-link>
                    </div>
                </div>
            </div>
        </div>


    </div>
</template>

<script>
    import {VueUtil} from "./util/vueutil";

    export default {
        name: "register",
        data() {
            return {
                over:false,
                actiontype: 'save',
                hostHead: this.$app.contextPath,
                fileList: [],
                listHytype:[],
                listBanji:[],
                errors: {},
                "huiyuan": {
                    "accountname": "",
                    "name": "",
                    "sex": "男",
                    "tel": "",
                    "bjid":1,
                    "idcardno": "",
                    "photo": "/upload/nopic.jpg",
                    "jiguan": "",
                    "birthday": new Date().format("yyyyy-MM-dd"),
                    "address": "",
                    "password": "",
                    "des": "",
                },
            }
        },
        methods: {
            //begin load

            async getBanjis() {
                let url = "admin/banji/list";
                let {data: res} = await this.$http.post(url,{"ispaged":'-1'});
                if (res.stateCode < 0) {
                    this.$message.error(res.des);
                    return;
                }
                this.listBanji = res.data;
                if(this.listBanji!=null&&this.listBanji.length>0){
                    this.huiyuan.bjid=this.listBanji[0].id;
                }
            },
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/huiyuan/save",
                    actionTip: "客户新增成功"
                };
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/huiyuan/update";
                    defaultOptions.actionTip = "客户更新成功";
                }
                const validRes = this.myValidator.valid(this);
                console.log("valRes", validRes);
                if (!validRes)
                    return;


                let util = new VueUtil(this);
                let params = {...this.huiyuan};
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
                this.over=true;
            },

            navigatorTo(){
                this.$app.key="/admin/login";
                this.$router.push("/admin/login");
            }
        },
        async created() {

           this.getBanjis();
        }

    }
</script>

<style scoped>
  @import "~bootstrap/dist/css/bootstrap.css";
</style>
