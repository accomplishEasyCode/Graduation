<template>

    <div class="mypanel">

        <div class="hd">
             我的简介

        </div>
        <div class="bd">
        <table cellpadding="0" cellspacing="1" border="1" class="smart-table" width="100%">


            <tr>
                <td align="right" width="100%" class="tlabel">个人简介:
                    <el-button @click="edlg=true" type="danger"> <i class="el-icon-edit"></i>编辑</el-button>
                </td>

            </tr>

            <tr>
                <td align="right"  width="100%" class="tlabel">

                    <div style="padding:28px 18px;" v-html="huiyuan.des"></div>
                </td>

            </tr>






        </table>



        <el-dialog title="个人简介" :visible.sync="edlg">

            <table cellpadding="0" cellspacing="1" class="grid" width="100%">
                <tr>

                    <td >
                        <quill-editor v-model="huiyuan.des" :options="{placeholder: '说明'}"></quill-editor>
                    </td>
                </tr>


            </table>

            <span slot="footer" class="dialog-footer">
			<el-button type="primary" @click="submitHandler">确 定</el-button>
			<el-button @click="edlg = false">取 消</el-button>
		  </span>



        </el-dialog>


    </div>


    </div>
</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "mydes",
        data(){
            return {
                actiontype:'save',
                edlg:false,
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
                if(id!=null){
                    let url="admin/huiyuan/load";
                    let {data:res}=await util.http.post(url,{id:id});
                    console.log("res",res);
                    if(res!=null&&res.data!=null)
                        this.huiyuan=res.data;
                }
            },//end load
            async submitHandler(){
                let util=new VueUtil(this);
                let {data:res}= await util.http.post("admin/huiyuan/des",{id:this.huiyuan.id,des:this.huiyuan.des});
                if(res!=null&&res.data!=null) {
                    this.edlg=false;
                    this.huiyuan = res.data;
                }
            }

        },
        created(){
            this.load();
        }

    }
</script>

<style scoped>

</style>
