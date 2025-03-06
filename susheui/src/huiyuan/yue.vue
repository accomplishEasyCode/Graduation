<template>


    <div class="panel panel-default">

        <div class="panel-heading">
           账户余额

        </div>


        <div class="panel-body">
                <div style="padding:30px;font-size:18px;">
                    我的余额<span style="font-size:24px;font-weight:800;color:#f00;">{{huiyuan.yue}}元</span>

                    <el-button @click="chongzhiDlg=true" type="primary">我要充</el-button>

                </div>



                <el-dialog title="账户充值" :visible.sync="chongzhiDlg">

                    <table class="grid" cellspacing="1" width="100%">
                        <tr>
                            <td width="15%" align="right">金额:</td>
                            <td width="*">
                                <input name="name" v-model="fee"  placeholder="名称"
                                       v-validate="{required:true,digits:true,messages:{required:'请输入金额',digits:'请输入正确的金额'}}" class="input-txt" type="text"/>

                            </td>
                        </tr>

                        <tr>
                            <td width="15%" align="right">支付方式:</td>
                            <td  width="*">

                                <el-radio-group v-model="paytype">
                                    <el-radio :label="1">
                                        <img src="../admin/images/alipay.jpg" alt="支付宝" />
                                    </el-radio>
                                    <el-radio :label="2">
                                        <img src="../admin/images/wechat.jpg" alt="微信" />
                                    </el-radio>

                                </el-radio-group>


                            </td>
                        </tr>
                    </table>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="chongzhiHandler" type="primary" >确 定</el-button>
                        <el-button @click="chongzhiDlg = false">取 消</el-button>
                    </div>
                </el-dialog>


        </div>


    </div>


</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "yue",
        data(){
            return {
                actiontype:'save',
                "chongzhiDlg":false,
                fee:100,
                paytype:1,
                hostHead:this.$app.contextPath,
                "huiyuan":{
                },
            }
        },
        props:{
            accountInfo:{
                type:Object
            }
        },
        methods:{
            //begin load
            async load(){
                let id=this.accountInfo.id;
                let util=new VueUtil(this);
                if(id!=""){
                    let url="admin/huiyuan/load";
                    let {data:res}=await util.http.post(url,{id:id});
                    console.log("res",res);
                    if(res!=null&&res.data!=null)
                        this.huiyuan=res.data;
                }
            },//end load
            async chongzhiHandler(){
                const validRes = this.myValidator.valid(this);
                if (!validRes)
                    return;

                let util=new VueUtil(this);
                let {data:res}=await util.http.post("admin/huiyuan/chongzhi",{
                    hyid:this.accountInfo.id,
                    amount:this.fee
                });
                if (res!=null&&res.stateCode<0){
                    this.$message.error(res.des);
                    return;
                }
                this.$message.success("充值成功");
                this.chongzhiDlg=false;
                this.load();
            }

        },
        created(){
            this.load();
        }


    }
</script>

<style scoped>

</style>
