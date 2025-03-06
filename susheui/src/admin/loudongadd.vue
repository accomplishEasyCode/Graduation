<template>

    <div class="panel panel-default">
        <div class="panel-heading">

            <div v-if="actiontype=='save'"><i class="el-icon-plus"></i>新建楼栋</div>
            <div v-if="actiontype=='update'"><i class="el-icon-edit"></i>编辑楼栋</div>

        </div>


        <table class="smart-table" border="1" cellspacing="1" width="100%">


            <tr>
                <td width="10%" class="tlabel" align="right">名称:</td>
                <td width="*">
                    <input name="name" v-model="loudong.name" placeholder="名称"
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
    name: "loudongadd",
    data() {
        return {
            actiontype: 'save',
            hostHead: this.$app.contextPath,
            fileList: [],
            errors: {},
            "loudong": {


                "name": "",


            },
        }
    },
    methods: {
        //begin load
        async load() {
            let id = this.$route.query.id;

            if (id != null) {
                this.actiontype = "update";
                this.pageTitle = "编辑楼栋";
                let url = "admin/loudong/load";
                let util = new VueUtil(this);
                let {data: res} = await util.http.post(url, {id: id});
                console.log("res", res);
                if (res != null && res.data != null) {
                    this.loudong = res.data;


                }
            }
        },//end load
        async submitHandler() {

            let defaultOptions = {
                url: "admin/loudong/save",
                actionTip: "楼栋新增成功"
            };
            if (this.actiontype == "update") {
                defaultOptions.url = "admin/loudong/update";
                defaultOptions.actionTip = "楼栋更新成功";
            }
            const validRes = this.myValidator.valid(this);
            console.log("valRes", validRes);
            if (!validRes)
                return;


            let util = new VueUtil(this);
            let params = {...this.loudong};
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
            this.$router.push("/admin/loudongmanager");

        },


    },
    created() {
        this.load();
    }

}

</script>
