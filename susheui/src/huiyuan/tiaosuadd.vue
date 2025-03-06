<template>

    <div class="mypanel">
        <div class="hd">

            <div v-if="actiontype=='save'">宿舍申请申请</div>
            <div v-if="actiontype=='update'">编辑宿舍申请</div>

        </div>


        <table v-if="accountInfo.state==1" class="smart-table" border="1" cellspacing="1" width="100%">



            <tr>
                <td width="20%" class="tlabel" align="right">标题:</td>
                <td width="*">
                    <input name="title" v-model="tiaosu.title" placeholder="标题"
                           v-validate="{required:true,messages:{required:'请输入标题'}}" class="input-txt" type="text"/>


                </td>
            </tr>


            <tr>
                <td  class="tlabel" align="right">申请宿舍:</td>
                <td width="*">
                    <el-cascader
                        v-model="ldvalue"
                        :options="listLoudong"
                        :props="{ expandTrigger: 'hover' }"></el-cascader>



                </td>
            </tr>

            <tr>
                <td align="right">说明:</td>
                <td colspan="3">
                    <quill-editor v-model="tiaosu.des" :options="{placeholder: '说明'}"></quill-editor>
                </td>
            </tr>



            <tr>
                <td colspan="4">

                    <el-button type="primary" icon="el-icon-check" @click="submitHandler">提交</el-button>

                </td>
            </tr>


        </table>


        <div v-else class="no-record-tip">
            <div class="content">
                <i class="el-icon-warning"></i>你已经分配了宿舍
            </div>
        </div>
    </div>

</template>

<script type="text/javascript">

    import {VueUtil} from "../util/vueutil";


    export default {
        name: "tiaosuadd",
        data() {
            return {
                actiontype: 'save',
                hostHead: this.$app.contextPath,
                fileList: [],
                ldvalue:[],
                listLoudong:[],
                errors: {},
                "tiaosu": {
                    "title": "",
                    "applyor": this.accountInfo.id,
                    "ldid": 1,
                    "unitid": 1,
                    "oldqs": "",
                    "des": "",
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
                    this.pageTitle = "编辑宿舍申请";
                    let url = "admin/tiaosu/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null) {
                        this.tiaosu = res.data;
                        this.ldvalue = [];
                        this.ldvalue.push(this.tiaosu.ldid,this.tiaosu.unitid);
                    }
                }
            },//end load
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/tiaosu/save",
                    actionTip: "宿舍申请新增成功"
                };
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/tiaosu/update";
                    defaultOptions.actionTip = "宿舍申请更新成功";
                }
                const validRes = this.myValidator.valid(this);
                console.log("valRes", validRes);
                if (!validRes)
                    return;
                if(this.accountInfo.state==2){
                    this.$message.error("已经分配了宿舍");
                    return;
                }
               if(this.ldvalue.length<2){
                   this.$message.error("请选择宿舍");
                   return;
               }
                //this.tiaosu.oldqs=this.accountInfo.ldname+this.accountInfo.unitname;
                this.tiaosu.ldid=this.ldvalue[0];
                this.tiaosu.unitid=this.ldvalue[1];
                let util = new VueUtil(this);
                let params = {...this.tiaosu};
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
                this.$router.push("/huiyuan/tiaosumanager");

            },

            async getLoudong(){
                let url = "admin/loudong/cascadelist";
                let {data:res}=await this.$http.post(url);
                if (res.stateCode<0){
                    this.$message.error(res.des);
                    return ;
                }
                this.listLoudong=res.data;

                if (this.listLoudong!=null&&this.listLoudong.length>0){
                    this.ldvalue=[];
                    this.ldvalue.push(this.listLoudong[0].value)
                    if (this.listLoudong[0].children!=null&&this.listLoudong[0].children.length>0){
                        this.ldvalue.push(this.listLoudong[0].children[0].value);
                    }

                }

            },


        },
       async created() {
          await  this.getLoudong();
          await   this.load();
        }

    }

</script>
