<template>
    <div class="mypanel">

        <div class="action-details">
            <el-button-group>
                <el-button  @click="selectedAllHandler" type="success"><i class="el-icon-check"></i>选择</el-button>
                <el-button  @click="deleteRc" type="danger"><i class="el-icon-delete"></i>删除</el-button>
            </el-button-group>

        </div>
        <div class="lw-ct-wrap">
            <div class="hd">
                <div class="title">全部留言</div>
                <div class="subtitle">{{listLeaveword.length}}条</div>
            </div>
            <div class="bd">
                <div v-for="lw in listLeaveword"  :key="lw.id" class="lw-item">
                    <div class="img-area">
                        <img :src="hostHead+lw.pbrphoto" class="image"/>
                        <span><input type="checkbox" name="id" :value="lw.id"  v-model="lw.rowSelected"/>  </span>
                    </div>
                    <div class="txt-area">
                        <div class="title">
                            <div class="prop">{{lw.pbrname}}</div>
                            <div class="prop">{{lw.pubtime}}</div>
                        </div>
                        <div class="data">
                            {{lw.dcontent}}
                        </div>
                        <div v-if="lw.state==1" class="data">
                            <span @click="openReplydlg(lw)" class="el-button el-button--warning"><i class="fa fa-reply-all"></i>回复</span>
                        </div>


                    </div>
                    <div v-if="lw.state==2" class="reply-area">
                        <div class="hd">
                            <div class="prop">
                                {{lw.rpname}}
                            </div>
                            <div class="prop">
                                {{lw.replytime}}
                            </div>
                        </div>
                        <div class="bd">

                            <div class="img-area">
                                <img :src="hostHead+lw.rpphoto" class="image"/>
                            </div>
                            <div class="txt-area">
                                {{lw.replycontent}}
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div v-if="listLeaveword.length==0" class="no-record-tip">
                <div class="content">没有留言记录</div>
            </div>
        </div>
        <el-dialog title="回复" :visible.sync="replyDlg">

            <div class="strow">{{lw.dcontent}}</div>
            <div class="stdrow">
                <textarea  name="replycontent"  maxlength="500" class="lwta" v-model="replycontent"   placeholder="回复咨询" ></textarea>
            </div>

            <div slot="footer" class="dialog-footer">
                <el-button @click="replyDlg = false">取 消</el-button>
                <el-button @click="replyHandler" type="primary" >确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    import  {VueUtil} from "../util/vueutil";

    export default {
        name: "leavewordmanager",
        data() {
            return {
                listLeaveword: [],
                selectedAll: false,
                hostHead:this.$app.contextPath,
                replyDlg: false,
                lw: {},
                replycontent: "",
                checkedIds: []
            }
        },
        props:{
            accountInfo:{
                type:Object
            }
        },
        created() {
            this.getLeaveword()
        },
        methods: {
            async getLeaveword() {
                let url = "admin/leaveword/list";
                let {data:res} = await this.$http.post(url,{"ispaged":"-1"});
                if (res.stateCode<0){
                    this.$message.error(res.des);
                    return ;
                }
                console.log(res.data);
                res.data.forEach(c=> {
                    c.rowSelected=false;
                })
                this.listLeaveword = res.data;
            },
            selectedAllHandler(){
                this.selectedAll=!this.selectedAll;
                if(this.listLeaveword!=null){
                    this.listLeaveword.forEach(c=>{
                        c.rowSelected=this.selectedAll;
                    });
                }
            },
            async deleteRc(){
                let url = "admin/leaveword/delete";
                if(this.listLeaveword.length==0){
                    alert("删毛线,没有记录");
                    return;
                }
                let ids=this.listLeaveword.filter(c=>c.rowSelected).map(c=>c.id);
                if (ids.length<=0){
                    this.$message.error("删除毛线,先选择再删除");
                    return;
                }

                let res = await this.$http.post(url,{
                    ids
                });
                console.log(res.data);
                if(res.data.stateCode>0){
                    this.getLeaveword();
                }

            },
            openReplydlg(lw){
                this.lw=lw;
                this.replyDlg=true;
            },
            async replyHandler(){

                if(this.replycontent==""){
                    ths.$message.error("输入回复内容");
                    return;
                }

                let url="admin/leaveword/reply";
                let res=await  this.$http.post(url,{
                    replycontent:this.replycontent,
                    id:this.lw.id,
                    replyren:this.accountInfo.username
                });
                if(res.data.stateCode>0){
                    this.replycontent="";
                    this.replyDlg=false;
                    this.$message.success("成功");
                    this.getLeaveword();
                }
            }

        }
    }
</script>

<style scoped>

</style>
