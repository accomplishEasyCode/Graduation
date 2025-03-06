<template>
    <div class="mypanel1">
        <div class="hd">

                <div v-if="actiontype=='save'">新建商品</div>
                <div v-if="actiontype=='update'">编辑商品</div>

        </div>


        <table class="smart-table" border="1" cellspacing="1" width="100%">


            <tr>
                <td class="tlabel" width="10%" align="right">名称:</td>
                <td width="*">
                    <input name="name" v-model="shangpin.name" placeholder="名称"
                           v-validate="{required:true,messages:{required:'请输入名称'}}" class="input-txt" type="text"/>


                </td>
            </tr>


            <tr>
                <td class="tlabel" align="right">商品类别:</td>
                <td>

                    <el-select v-model="shangpin.typeid">
                        <el-option v-for="item in listSpcategory " :label="item.name" :value="item.id"></el-option>
                    </el-select>

                </td>
            </tr>


            <tr>
                <td class="tlabel" align="right">图片:</td>
                <td>

                    <dw-upload :host-head="hostHead" v-model="shangpin.tupian"></dw-upload>
                </td>
            </tr>


            <tr>
                <td class="tlabel" width="10%" align="right">价格（元）:</td>
                <td width="*">
                    <input name="price" v-model="shangpin.price" placeholder="价格（元）"
                           v-validate="{required:true,messages:{required:'请输入价格（元）'}}" class="input-txt" type="text"/>


                </td>
            </tr>


            <tr>
                <td class="tlabel" width="10%" align="right">品牌:</td>
                <td width="*">
                    <input name="pinpai" v-model="shangpin.pinpai" placeholder="品牌"
                           v-validate="{required:true,messages:{required:'请输入品牌'}}" class="input-txt" type="text"/>


                </td>
            </tr>



            <tr>
                <td class="tlabel" width="10%" align="right">单位:</td>
                <td width="*">
                    <input name="danwei" v-model="shangpin.danwei" placeholder="单位"
                           v-validate="{required:true,messages:{required:'请输入单位'}}" class="input-txt" type="text"/>


                </td>
            </tr>





            <tr>
                <td class="tlabel" align="right">说明:</td>
                <td colspan="3">
                    <quill-editor v-model="shangpin.des" :options="{placeholder: '说明'}"></quill-editor>
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
        name: "shangpinadd",
        data() {
            return {

                actiontype: 'save',
                listSpcategory:[],
                hostHead: this.$app.contextPath,
                fileList: [],
                errors: {},
                "shangpin": {
                    "name": "",
                    "typeid": "",
                    "tupian": "/upload/nopic.jpg",
                    "price": "",
                    "pinpai": "",
                    "xinghao": "",
                    "kucun": "0",
                    "danwei": "",
                    "des": "",
                },
            }
        },
        methods: {
            //begin load
            async load() {
                let id =this.$route.query.id;
                if (id != null) {
                    this.actiontype = "update";
                    this.pageTitle = "编辑商品";
                    let url = "admin/shangpin/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null) {
                        this.shangpin = res.data;
                    }
                }
            },//end load
            async getSpcates() {

                    let util = new VueUtil(this);
                    let {data: res} = await this.$http.post("admin/spcategory/list", {"ispaged": "-1"});
                    console.log("res", res);
                    if (res != null && res.data != null) {
                        this.listSpcategory = res.data;
                        if(this.listSpcategory!=null&&this.listSpcategory.length>0)
                             this.shangpin.typeid=this.listSpcategory[0].id;
                    }
            },
            async submitHandler() {
                let defaultOptions = {
                    url: "admin/shangpin/save",
                    actionTip: "商品新增成功"
                };
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/shangpin/update";
                    defaultOptions.actionTip = "商品更新成功";
                }
                const validRes = this.myValidator.valid(this);
                console.log("valRes", validRes);
                if (!validRes)
                    return;
                let util = new VueUtil(this);
                let params = {...this.shangpin};
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
                this.$router.push("/admin/shangpinmanager");

            },


        },
       async created() {
            await this.getSpcates();
            await this.load();
        }
    }
</script>

<style scoped>

</style>
