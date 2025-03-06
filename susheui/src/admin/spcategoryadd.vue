<template>
    <div class="mypanel1">
        <div class="hd">

            <div v-if="actiontype=='save'">新建商品类别</div>
            <div v-if="actiontype=='update'">编辑商品类别</div>

        </div>

        <div class="bd">
            <table class="smart-table" border="1" cellspacing="1" width="100%">


                <tr>
                    <td width="10%" align="right">名称:</td>
                    <td width="*">
                        <input name="name" v-model="spcategory.name" placeholder="名称"
                               v-validate="{required:true,messages:{required:'请输入名称'}}" class="input-txt" type="text"/>


                    </td>
                </tr>


                <tr>
                    <td colspan="4">

                        <el-button type="primary" icon="el-icon-check" @click="submitHandler">提交</el-button>

                    </td>
                </tr>


            </table>
        </div>


    </div>


</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "spcategoryadd",
        data() {
            return {

                actiontype: 'save',
                hostHead: this.$app.contextPath,
                fileList: [],
                errors: {},
                "spcategory": {
                    "name": ""
                },
            }
        },
        methods: {
            //begin load
            async load() {
                let id =this.$route.query.id;
                if (id !=null) {
                    this.actiontype = "update";
                    this.pageTitle = "编辑商品类别";
                    let url = "admin/spcategory/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null) {
                        this.spcategory = res.data;
                    }
                }
            },//end load
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/spcategory/save",
                    actionTip: "商品类别新增成功"
                };
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/spcategory/update";
                    defaultOptions.actionTip = "商品类别更新成功";
                }
                const validRes = this.myValidator.valid(this);
                console.log("valRes", validRes);
                if (!validRes)
                    return;


                let util = new VueUtil(this);
                let params = {...this.spcategory};
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
                this.$router.push( "/admin/spcategorymanager");
            },


        },
        created() {
            this.load();
        }

    }
</script>

<style scoped>

</style>
