<template>
    <div class="panel-default panel">
        <div class="panel-heading">

                新建公告

        </div>

        <div class="panel-body">

            <table class="smart-table" border="1s" cellspacing="1" width="100%">





            <tr>
                <td width="10%" align="right">标题:</td>
                <td width="*">
                    <input name="title" v-model="notice.title" placeholder="标题"
                           v-validate="{required:true,messages:{required:'请输入标题'}}" class="input-txt" type="text"/>

                </td>
            </tr>

            <tr>
                <td align="right">内容:</td>
                <td colspan="3">
                    <quill-editor v-model="notice.dcontent" :options="{placeholder: '内容'}"></quill-editor>
                </td>
            </tr>


            <tr>
                <td colspan="4">

                    <el-button @click="submitHandler" type="danger"><i class="el-icon-check"></i>提交</el-button>

                </td>
            </tr>


        </table>
        </div>

    </div>
</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "noticeadd",
        data() {
            return {

                actiontype: 'save',
                hostHead: '${pageContext.request.contextPath}',
                errors: {},
                "notice": {
                    "title": "",
                    "pubren": this.accountInfo.username,
                    "dcontent": "",
                },
            }
        },
        props:{
            accountInfo:Object
        },
        methods: {
            //begin load
            async load() {
                let id = this.$route.query.id;
                if (id !=null) {
                    this.actiontype = "update";
                    this.pageTitle = "编辑公告";
                    let url = "admin/notice/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null)
                        this.notice = res.data;
                }
            },//end load
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/notice/save",
                    actionTip: "公告新增成功"
                };
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/notice/update";
                    defaultOptions.actionTip = "公告更新成功";
                }
                const  validRes=this.myValidator.valid(this);
                if (!validRes)
                    return ;
                let util = new VueUtil(this);
                let params = {...this.notice};
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
                this.$router.push("/admin/noticemanager");
            }
        },
        created() {
            this.load();
        }
    }
</script>

<style scoped>

</style>
