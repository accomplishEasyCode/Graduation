<template>
    <div class="panel panel-default" >
        <div class="panel-heading">

                <div v-if="actiontype=='save'">报修申请</div>
                <div v-if="actiontype=='update'">编辑报修</div>

        </div>


        <table class="smart-table" border="1" cellspacing="1" width="100%">


            <tr>
                <td width="10%" class="tlabel" align="right">宿舍号:</td>
                <td width="*">
                    <input name="sbno" v-model="baoxiu.sbno" placeholder="宿舍号"
                           v-validate="{required:true,messages:{required:'请输入设备号'}}" class="input-txt" type="text"/>


                </td>
            </tr>


            <tr>
                <td class="tlabel" align="right">图片:</td>
                <td>

                    <dw-upload :host-head="hostHead" v-model="baoxiu.tupian"></dw-upload>
                </td>
            </tr>



            <tr>
                <td align="right">故障说明:</td>
                <td colspan="3">
                    <quill-editor v-model="baoxiu.des" :options="{placeholder: '故障说明'}"></quill-editor>
                </td>
            </tr>


            <tr>
                <td colspan="4">

                    <el-button type="primary" icon="el-icon-check" @click="submitHandler">提交</el-button>

                </td>
            </tr>


        </table>


    </div>

</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "baoxiuadd",
        data() {
            return {

                actiontype: 'save',
                hostHead: this.$app.contextPath,
                fileList: [],
                errors: {},
                "baoxiu": {
                    "sbno": "",
                    "typeid": 1,
                    "createtime": "",
                    "bxrid": "",
                    "tupian": "/upload/nopic.jpg",
                    "des": "",
                    "replydes": "",
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
                if (id !=null) {
                    this.actiontype = "update";
                    this.pageTitle = "编辑报修";
                    let url = "admin/baoxiu/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null) {
                        this.baoxiu = res.data;
                    }
                }
            },//end load
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/baoxiu/save",
                    actionTip: "报修新增成功"
                };
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/baoxiu/update";
                    defaultOptions.actionTip = "报修更新成功";
                }
                this.baoxiu.bxrid=this.accountInfo.id;
                const validRes = this.myValidator.valid(this);
                console.log("valRes", validRes);
                if (!validRes)
                    return;
                let util = new VueUtil(this);
                let params = {...this.baoxiu};
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
                this.$router.push("/huiyuan/baoxiumanager");

            },


        },
        created() {
            this.load();
        }
    }
</script>

<style scoped>

</style>
