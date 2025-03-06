<template>

    <table class="smart-table" border="1" cellspacing="1" width="100%">



            <tr>
                <td class="tlabel" width="20%" align="right">名称:</td>
                <td width="*">
                    {{shangpin.name}}
                </td>
            </tr>


            <tr>
                <td class="tlabel" align="right">器材类别:</td>
                <td width="*">
                    {{shangpin.typename}}
                </td>
            </tr>


            <tr>
                <td class="tlabel"  align="right">图片:</td>
                <td width="*">
                    <img width="200" height="200" :src="hostHead+shangpin.tupian"/>

                </td>
            </tr>


            <tr>
                <td class="tlabel"  align="right">价格（元）:</td>
                <td width="*">
                    {{shangpin.price}}¥
                </td>
            </tr>


            <tr>
                <td class="tlabel"  align="right">品牌:</td>
                <td width="*">
                    {{shangpin.pinpai}}
                </td>
            </tr>





            <tr>
                <td class="tlabel" align="right">库存:</td>
                <td width="*">
                    {{shangpin.kucun}}{{shangpin.danwei}}
                </td>
            </tr>

            <tr>
                <td class="tlabel" align="right">进货数量:</td>
                <td width="*">

                    <el-input-number v-model="caigou.count" :step="20"></el-input-number>


                </td>
            </tr>

            <tr>
                <td class="tlabel" align="right">进货价格:（元）</td>
                <td width="*">
                    <input name="price" v-model="caigou.price" placeholder="价格(元)"
                           v-validate="{required:true,number:true,messages:{required:'请输入价格',number:'请输入正确的价格'}}" class="input-txt" type="text"/>


                </td>
            </tr>



            <tr>
                <td class="tlabel" align="right">供应商:</td>
                <td>

                    <my-select name="supplyid" v-model="caigou.supplyid"
                                     :url="hostHead+'/admin/supplyor/list?ispaged=-1'"></my-select>

                </td>
            </tr>


            <tr>
                <td  align="right">说明:</td>
                <td width="*">

                    <el-input style="width:400px"
                              type="textarea"
                              :rows="2"
                              placeholder="请输入说明"
                              v-model="caigou.des">
                    </el-input>


                </td>
            </tr>


            <tr>
                <td colspan="4">

                    <el-button type="primary" icon="el-icon-check" @click="submitHandler">提交</el-button>

                </td>
            </tr>


        </table>

</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "caigouadd",
        data() {
            return {

                actiontype: 'save',
                hostHead: this.$app.contextPath,
                fileList: [],
                errors: {},
                shangpin:{

                },
                "caigou": {
                    "userid": "",
                    "spid": "",
                    "count": 40,
                    "danwei": "",
                    "supplyid": "",
                    "des": "",
                },
            }
        },
        props:{
           params:{
               type:Object
           }
        },
        methods: {
            //begin load
            async load() {
                let id = this.params.id;
                if (id !=null) {
                    this.actiontype = "update";
                    this.pageTitle = "编辑进货";
                    let url = "admin/caigou/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null) {
                        this.caigou = res.data;
                    }
                }
            },//end load
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/caigou/save",
                    actionTip: "进货新增成功"
                };
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/caigou/update";
                    defaultOptions.actionTip = "进货更新成功";
                }
                const validRes = this.myValidator.valid(this);
                console.log("valRes", validRes);
                if (!validRes)
                    return;
                this.caigou.spid=this.shangpin.id;
                this.caigou.danwei=this.shangpin.danwei;
                this.caigou.userid=this.params.userid;
                let util = new VueUtil(this);
                let params = {...this.caigou};
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
                this.$emit("close",false);
            },
            async getShangpinInfo(){
                let spid = this.params.spid;
                if (spid != null) {
                    let url = "admin/shangpin/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: spid});
                    console.log("res", res);
                    if (res != null && res.data != null) {
                        this.shangpin = res.data;
                    }
                }
            }


        },
        created() {
            this.load();
            this.getShangpinInfo();
        }
    }
</script>

<style scoped>

</style>
