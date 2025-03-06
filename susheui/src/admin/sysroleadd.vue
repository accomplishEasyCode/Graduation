<template>
    <div >


        <table class="smart-table" cellspacing="1" width="100%">


            <tr>
                <td width="10%" align="right">名称:</td>
                <td width="*">
                    <input name="name" v-model="sysrole.name" placeholder="名称"
                           v-validate="{required:true,messages:{required:'请输入名称'}}" class="input-txt" type="text"/>
                </td>
            </tr>


            <tr>
                <td colspan="4">

                    <el-button @click="submitHandler"  type="warning"><i class="fa fa-check"></i>提交</el-button>

                </td>
            </tr>


        </table>


    </div>
</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "sysroleadd",
        data() {
            return {

                actiontype: 'save',
                hostHead: '${pageContext.request.contextPath}',
                errors: {},
                "sysrole": {


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
                    this.pageTitle = "编辑后台角色";
                    let url = "admin/sysrole/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null)
                        this.sysrole = res.data;
                }
            },//end load
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/sysrole/save",
                    actionTip: "后台角色新增成功"
                };
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/sysrole/update";
                    defaultOptions.actionTip = "后台角色更新成功";
                }
                const  validRes=this.myValidator.valid(this);
                if (!validRes)
                    return ;
                let util = new VueUtil(this);
                let params = {...this.sysrole};
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
            }
        },
        created() {
            this.load();
        }

    }
</script>

<style scoped>

</style>
