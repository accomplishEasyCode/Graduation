<template>
    <div class="mypanel1">
        <div class="hd">

                <span v-if="actiontype=='save'">发布资讯信息</span>
                <span v-if="actiontype=='update'">编辑资讯信息</span>

        </div>
       <div class="bd">
          <table class="smart-table" border="1" cellspacing="1" width="100%">

            <tr>
                <td width="10%" align="right">标题:</td>
                <td width="*">
                    <input name="title" placeholder="标题" v-validate="{required:true,messages:{required:'请输入标题'}}"
                           v-model="xinxi.title" class="input-txt" type="text"/>
                </td>
            </tr>
            <tr>
                <td align="right">图片:</td>
                <td>
                    <dw-upload v-model="xinxi.tupian" :host-head="hostHead"></dw-upload>
                </td>
            </tr>
            <tr>
                <td align="right">分类:</td>
                <td>
                    <el-cascader
                        v-model="lmvalue"
                        :options="cascadeData"
                        :props="{ expandTrigger: 'hover' }"></el-cascader>
                </td>
            </tr>




            <tr>
                <td align="right">介绍:</td>
                <td colspan="3">
                    <quill-editor v-model="xinxi.dcontent"></quill-editor>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <el-button type="primary" @click="submitHandler" icon="el-icon-plus">提交</el-button>

                </td>
            </tr>
        </table>
       </div>
    </div>
</template>

<script>
    import  {VueUtil} from "../util/vueutil";

    export default {
        name: "xinxiadd",
        data() {
            return {

                actiontype: 'save',
                lmvalue:[],
                fileList: [],
                listStag:[],
                cascadeData: [],
                hostHead: this.$app.contextPath,
                "xinxi": {
                    "title": "",
                    "lmid": 0,
                    "subtypeid":1,
                    "tupian": "/upload/nopic.jpg",
                    "clickcount": 0,
                    "agreecount": 0,
                    "againstcount": 0,
                    "pubren": this.accountInfo.id,
                    "state": 1,
                    "tuijian": 1,
                    "tagid": 1,
                    "dcontent": ""
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
                let id = this.$route.query.id;
                if (id != null) {
                    this.actiontype = "update";
                    this.pageTitle = "编辑资讯";
                    let url = "admin/xinxi/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null) {
                        this.xinxi = res.data;
                        this.lmvalue=[];
                        this.lmvalue.push(this.xinxi.lmid);
                    }
                }
            },//end load
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/xinxi/save",
                    actionTip: "资讯新增成功"
                };
                let util = new VueUtil(this);
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/xinxi/update";
                    defaultOptions.actionTip = "资讯更新成功";
                }
                const validRes = this.myValidator.valid(this);
                if (!validRes)
                    return;

                if (this.lmvalue.length<1){
                    util.message.error("请选择资讯类别");
                    return ;
                }
                this.xinxi.lmid=this.lmvalue[0];
                let params = {...this.xinxi};
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
              this.$router.push( "/admin/xinximanager");
            },
            async getLanmus(){
                let url = "admin/xinxi/cascadelanmu";
                let {data:res}=await this.$http.post(url,{
                    id:this.lanmuid
                });
                if (res.stateCode<0){
                    this.$message.error(res.des);
                    return ;
                }
                this.cascadeData=res.data;

                if (this.cascadeData!=null&&this.cascadeData.length>0){
                    this.lmvalue.push(this.cascadeData[0].value)
                    if (this.cascadeData[0].children!=null&&this.cascadeData[0].children.length>0){
                        this.lmvalue.push(this.cascadeData[0].children[0].value);
                    }

                }
            },

        },
        async created() {
            await this.getLanmus();

            await this.load();
        }

    }
</script>

<style scoped>

</style>
