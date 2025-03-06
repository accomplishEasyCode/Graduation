<template>
    <div class="mypanel">
        <div class="hd">
            编辑账户
        </div>

        <div class="bd">

             <table class="smart-table"  cellspacing="1" border="1" width="100%">

                 <tr>
                     <td width="10%" align="right">学号:</td>
                     <td width="*">
                          {{huiyuan.accountname}}

                     </td>

                     <td colspan="2" rowspan="6">

                         <dw-upload :host-head="hostHead" v-model="huiyuan.photo"></dw-upload>
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
                                v-validate="{required:true,messages:{required:'请输入身份证'}}" class="input-txt" type="text"/>


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
                    <el-button @click="submitHandler" type="danger" icon="el-icon-check">提交</el-button>
                </td>
            </tr>



        </table>
        </div>

    </div>

</template>

<script>
    import  {VueUtil} from "../util/vueutil";

    export default {
        name: "modifyinfo",
        data() {
            return {

                actiontype: 'save',
                errors:"",
                hostHead:this.$app.contextPath,
                "huiyuan": {
                    "accountname": "",
                    "address": "",
                    "email": "",
                    "idcardno": "",
                    "nickname": "",
                    "name": "",
                    "sex": "男",
                    "touxiang": "/upload/nopic.jpg",
                    "des": ""
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
                let id = this.accountInfo.id;
                if (id !=null) {
                    this.actiontype = "update";
                    this.pageTitle = "编辑学生";
                    let url = "admin/huiyuan/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null)
                        this.huiyuan = res.data;
                }
            },//end load
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/huiyuan/save",
                    actionTip: "学生新增成功"
                };
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/huiyuan/update";
                    defaultOptions.actionTip = "学生更新成功";
                }
                const  validRes=this.myValidator.valid(this);
                console.log("valRes",validRes);
                if (!validRes)
                    return ;
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
                this.$router.push("/huiyuan/accountinfo");

            }
        },
        created() {
            this.load();
        }

    }
</script>

<style scoped>

</style>
