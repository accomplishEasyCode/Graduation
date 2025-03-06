<template>

    <div >

        <div v-if="!over" class="wrap round-block">

            <div class="line-title">
                当前位置：<router-link to="/e/index">首页</router-link> >> 商品信息>>{{shangpin.name }}
            </div>

            <div class="fn-clear"></div>
            <div class="shangpin-info clearfix">
                <div class="pic-area">
                    <div class="big-img">
                        <img :src="hostHead+shangpin.tupian"/>
                    </div>

                </div>


                <div class="props">
                    <div class="title">

                        {{shangpin.name}}
                    </div>
                    <div class="subtitle">

                    </div>
                    <div class="price-block clearfix">

                        <div class="mall-price clearfix">
                            <div class="label">价格</div>
                            <div class="price"> ¥ {{shangpin.price}}</div>

                        </div>
                    </div>


                    <div style="margin-top:20px " class="prop">
                        <dl class="clearfix">
                            <dt>品牌</dt>
                            <dd>{{shangpin.pinpai}}</dd>
                        </dl>
                    </div>




                    <div class="prop">


                        <el-button-group>
                            <el-button type="primary" @click="shoucangHandler" ><i class="fa fa-star"></i>收藏</el-button>


                            <el-button v-if="shangpin.kucun>0" @click="showAppointdlg" type="danger">
                                <i class="fa fa-plus"></i>我要下单
                            </el-button>

                        </el-button-group>

                    </div>


                </div>
            </div>


            <el-tabs value="first">
                <el-tab-pane label="商品介绍" name="first">
                    <div class="brief-content" v-html="shangpin.des"></div>
                </el-tab-pane>

            </el-tabs>


            <loginmodal :show.sync="openLogin"></loginmodal>

            <el-dialog title="商品预定" :visible.sync="appointdlg">


                <table width="100%" class="smart-table" border="1">


                    <tr>
                        <td class="tlabel">名称</td>
                        <td>
                            {{shangpin.name}}
                        </td>
                    </tr>

                    <tr>
                        <td class="tlabel">金额(元)</td>
                        <td>
                            {{shangpin.price}}¥
                        </td>
                    </tr>


                <template v-if="huiyuan!=null">

                    <tr >
                        <td class="tlabel">姓名</td>
                        <td>{{huiyuan.name}}</td>
                    </tr>
                    <tr >
                        <td class="tlabel">身份证号</td>
                        <td>{{huiyuan.idcardno}}</td>
                    </tr>

                    <tr>
                        <td class="tlabel">电话</td>
                        <td>{{huiyuan.tel}}</td>
                    </tr>
                </template>


                    <tr>
                        <td width="20%" class="tlabel">数量</td>
                        <td>
                            <el-input-number v-model="appointorder.count"  :min="1" :max="10" label="数量"></el-input-number>
                        </td>
                    </tr>



                    <tr>
                        <td class="tlabel">说明</td>
                        <td>
                            <el-input  type="textarea" v-model="appointorder.des"/>
                        </td>
                    </tr>

                </table>


                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="submitHandler">确 定</el-button>
                    <el-button @click="appointdlg= false">取 消</el-button>
                </div>

            </el-dialog>

        </div>
        <div class="wrap round-block" v-else>
            <div class="no-record-tip">
                <div class="content">
                    <i class="el-icon-check"></i>下单成功<router-link to="/huiyuan/appointordermanager">我的订单</router-link>
                </div>
            </div>
        </div>


    </div>
</template>

<script>

    import {VueUtil} from "../util/vueutil";

    export default {
        name: "shangpininfo",
        data(){
            return {
                listComment: [],
                bigPic: "",
                count: 1,
                hostHead: this.$app.contextPath,
                listRecomment: [],
                selectedIndex: 0,
                shangpin: {},
                openLogin: false,
                appointdlg:false,
                idcardno:"",
                over:false,
                appointorder:{
                    yydate:new Date().format("yyyy-MM-dd"),
                    mobile:"",
                    hyid:"",
                    fee:10,
                    count:1,
                    danjia:100,
                    targetid:1,
                }


            }

        },
        created(){

            let id=this.$route.query.id;
            this.getShangpin(id);


        },
        methods:{
           async  getComment(id){
                let url="admin/spcomment/list";
                let {data:res}=await this.$http.post(url,{
                    spid:id,
                    "ispaged":"-1"
                });
               console.log(res);
               //this.listComment=res.data;
            },

            isLogin() {
                return this.$app.mycas.isLogin(this.$app.mycas.huiyuan_key);
            },



            select(item,index){
                this.bigPic=item;
                this.selectedIndex=index;
            },
            async getShangpin(id){
                let url="admin/shangpin/info";
                let p1={id:id};

                if(this.hyid!="")
                    p1.hyid=this.hyid;
                let {data:res}=await this.$http.post(url,p1);
                this.shangpin=res.data;
                this.bigPic=this.shangpin.images[0];


            },


            async shoucangHandler(){

                let url="admin/shoucang/save";
                console.log("shanppin.id=",this.shangpin.id);

                let {data:res}=await this.$http.post(url,{
                    targetid:this.shangpin.id,
                    targetname:this.shangpin.name,
                    tupian:this.shangpin.tupian,
                    hyid:this.huiyuan.id,
                    xtype:"shangpin",
                    href:"/huiyuan/shangpininfo?id="+this.shangpin.id
                });
                if(res!=null&&res.stateCode<0){
                    this.$message.error(res.des);
                    return;
                }
                this.$message.success("收藏成功");
            },

            async showAppointdlg(){
                if(this.huiyuan==null) {
                    this.openLogin = true;
                    return;
                }
                this.appointdlg=true;
                this.appointorder.targetid = this.shangpin.id;
                this.appointorder.hyid = this.huiyuan.id;
                this.appointorder.mobile = this.huiyuan.tel;
                this.appointorder.danjia = this.shangpin.price;
                this.appointorder.fee=this.appointorder.count*this.appointorder.danjia;
            },
            async submitHandler(){
                if (this.huiyuan==""){
                    this.$message.error("登录后再预定");
                    return;
                }
                if (this.huiyuan.idcardno==""){
                    this.$message.error("请完善个人信息报名");
                    return;
                }
                let util=new VueUtil(this);
                this.appointorder.fee=this.appointorder.count*this.appointorder.danjia;
                let params={...this.appointorder};
                let {data:res}= await util.http.post("admin/appointorder/save",params);
                if(res.stateCode<=0){
                    util.alert(res.des, '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }
                util.message({
                    message:"成功",
                    type:'success',
                    duration:2000
                });
                this.appointdlg = false;
                this.over=true;

            },

        },
        computed:{
            huiyuan(){
                return this.$app.mycas.getAccount(this.$app.mycas.huiyuan_key);
            }

        },


    }
</script>

<style scoped>

</style>
