<template>

    <div class="mypanel">

        <table class="smart-table" border="1" cellspacing="1" width="100%">


            <tr>
                <td width="10%" class="tlabel" align="right">学期周:</td>
                <td width="*">
                    <input name="name" v-model="termweek.name" placeholder="学期周"
                           v-validate="{required:true,messages:{required:'请输入学期周'}}" class="input-txt" type="text"/>


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
        name: "termweekadd",
        data() {
            return {
                actiontype: 'save',
                hostHead: this.$app.contextPath,
                fileList: [],
                errors: {},
                "termweek": {
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
                    this.pageTitle = "编辑学期周";
                    let url = "admin/termweek/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null) {
                        this.termweek = res.data;
                    }
                }
            },//end load
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/termweek/save",
                    actionTip: "学期周新增成功"
                };
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/termweek/update";
                    defaultOptions.actionTip = "学期周更新成功";
                }
                const validRes = this.myValidator.valid(this);
                console.log("valRes", validRes);
                if (!validRes)
                    return;


                let util = new VueUtil(this);
                let params = {...this.termweek};
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
