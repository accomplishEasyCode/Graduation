<template>
    <div class="mypanel1">
        <div class="hd">

            <div v-if="actiontype=='save'">新建栏目</div>
            <div v-if="actiontype=='update'">编辑栏目</div>

        </div>


        <table class="smart-table" border="1" cellspacing="1" width="100%">


            <tr>
                <td width="10%" align="right">名称:</td>
                <td width="*">
                    <input name="name" v-model="lanmu.name" placeholder="名称"
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

</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "lanmuadd",
        data() {
            return {

                actiontype: 'save',
                hostHead: '${pageContext.request.contextPath}',
                fileList: [],
                errors: {},
                "lanmu": {
                    "name": "",
                },
            }
        },
        methods: {
            //begin load
            async load() {
                let id =this.params.id;
                if (id != null) {
                    this.actiontype = "update";
                    this.pageTitle = "编辑栏目";
                    let url = "admin/lanmu/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null) {
                        this.lanmu = res.data;
                    }
                }
            },//end load
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/lanmu/save",
                    actionTip: "栏目新增成功"
                };
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/lanmu/update";
                    defaultOptions.actionTip = "栏目更新成功";
                }
                const validRes = this.myValidator.valid(this);
                console.log("valRes", validRes);
                if (!validRes)
                    return;


                let util = new VueUtil(this);
                let params = {...this.lanmu};
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


        },
        props:{
          params:{
              type:Object
          }
        },
        created() {
            this.load();
        }
    }
</script>

<style scoped>

</style>
