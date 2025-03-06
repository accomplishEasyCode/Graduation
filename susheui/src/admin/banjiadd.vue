<template>


    <div>
            <table class="smart-table" border="1" cellspacing="1" width="100%">


                <tr>
                    <td width="10%" class="tlabel" align="right">名称:</td>
                    <td width="*">
                        <input name="name" v-model="banji.name" placeholder="名称"
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

<script type="text/javascript">

    import {VueUtil} from "../util/vueutil";


    export default {
        name: "banjiadd",
        data() {
            return {
                actiontype: 'save',
                hostHead: this.$app.contextPath,
                fileList: [],
                errors: {},
                "banji": {
                    "name": "",
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

                if (id != null) {
                    this.actiontype = "update";
                    this.pageTitle = "编辑班级";
                    let url = "admin/banji/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null) {
                        this.banji = res.data;


                    }
                }
            },//end load
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/banji/save",
                    actionTip: "班级新增成功"
                };
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/banji/update";
                    defaultOptions.actionTip = "班级更新成功";
                }
                const validRes = this.myValidator.valid(this);
                console.log("valRes", validRes);
                if (!validRes)
                    return;


                let util = new VueUtil(this);
                let params = {...this.banji};
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
        created() {
            this.load();
        }

    }

</script>
