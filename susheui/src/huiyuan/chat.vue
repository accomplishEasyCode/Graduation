<template>
    <div class="chat-box">
        <div class="panel panel-default">
            <div class="panel-heading"> {{accountInfo.name}}--{{accountInfo.accountname}}在线交流</div>
            <div class="panel-body">
                <div class="messages-content">
                   <template v-for="item in listLeaveword" >
                        <div    class="item">
                            <div class="message-user">
                                <div class="avatar">
                                    <img :src="hostHead+item.pbrphoto"/>
                                </div>
                                <div class="name">{{item.pbrname}}--{{item.pubtime}}</div>
                            </div>
                            <div class="message-wrap">{{item.dcontent}}</div>

                        </div>
                        <div v-if="item.state==2"  class="item outgoing">

                            <div class="message-user">
                                <div class="avatar">
                                    <img :src="hostHead+item.rpphoto"/>
                                </div>
                                <div class="name">{{item.rpname}}--{{item.replytime}}</div>
                            </div>
                            <div class="message-wrap">{{item.replycontent}}</div>

                        </div>
                   </template>
                </div>

            </div>
            <div class="panel-footer">
                <el-input v-model="dcontent" type="textarea" :rows="2" placeholder="在此输入文字信息..."></el-input>
                <span @click="sendHandler" class="el-button--danger el-button">
                    发送<i class="el-icon-arrow-right"></i>
                </span>
            </div>
        </div>
    </div>

</template>

<script>

    import "../admin/css/chat.css"
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "chat",
        data() {
            return {

                hostHead: this.$app.contextPath,
                listLeaveword:[],
                dcontent:"",
                listNotice:[],
                "hyid": this.accountInfo.id,
            }
        },
        props:{
            accountInfo: {
                type:Object
            }
        },
        methods: {
            //begin load
            async sendHandler(){
                let url="admin/leaveword/save";
                let res=await  this.$http.post(url,{
                    dcontent:this.dcontent,
                    hyid:this.hyid
                });
                if(res.data.stateCode>0){
                    this.dcontent="";
                    this.getLeaveword();
                }

            },
            async getLeaveword(){
                let url = "admin/leaveword/list";
                let res=await this.$http.post(url,{hyid:this.hyid,"ispaged":"-1"});
                console.log(res.data);
                this.listLeaveword=res.data.data;
            },

            async getNotices() {
                let url = "admin/notice/list";
                let util = new VueUtil(this);
                let {data:res} = await util.http.post(url, {ispaged:"-1"});
                if (res!=null&&res.stateCode<0){
                    this.$message.error(res.des);
                    return ;
                }
                if(res.data!= null) {
                    this.listNotice = res.data;
                }
            },

        },
        created() {
            this.getLeaveword();
            this.getNotices();
        }

    }
</script>

<style scoped>

</style>
