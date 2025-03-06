<template>
    <div class="mypanel">
        <div class="hd">

                新建轮播图

        </div>


        <table class="smart-table" border="" cellspacing="1" width="100%">



            <tr>

                <td colspan="4">

                    <dw-upload :host-head="hostHead" class-name="horizontal-banner" v-model="jiaodiantu.tupian"></dw-upload>
                </td>
            </tr>

            <tr>
                <td align="right">标题:</td>
                <td>
                    <input name="title" v-model="jiaodiantu.title" placeholder="标题"
                           v-validate="{required:true,messages:{required:'请输入标题'}}" class="input-txt" type="text"/>

                </td>
                <td align="right">链接地址:</td>
                <td><input name="href" v-model="jiaodiantu.href" v-validate="{required:true,messages:{required:'请输入链接地址'}}"  class="input-txt"   type="text" /></td>
            </tr>


            <tr>
                <td align="right">显示优先级:</td>
                <td>


                    <select v-model="jiaodiantu.pindex" name="pindex" class="mydropdown">
                        <option value="0">第一级</option>
                        <option value="2">第二季</option>
                        <option value="3">第三级</option>
                        <option value="4">第四级</option>
                        <option value="5">第五级</option>
                    </select>

                </td>

            </tr>


            <tr>
                <td colspan="4">

                    <el-button @click="submitHandler" type="warning"><i class="el-icon-check"></i>提交</el-button>

                </td>
            </tr>


        </table>


    </div>
</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "jiaodiantuadd",
        data() {
            return {
                actiontype: 'save',
                errors: {},
                hostHead: this.$app.contextPath,
                "jiaodiantu": {
                    "title": "",
                    "tupian": "/upload/nopic.jpg",
                    "xtype": "站内",
                    "href":"",
                    "pindex": "0",
                },
            }
        },
        methods: {
            //begin load
            async load() {
                let id = this.$route.query.id;
                if (id != null) {
                    this.actiontype = "update";
                    this.pageTitle = "编辑轮播图";
                    let url = "admin/jiaodiantu/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null)
                        this.jiaodiantu = res.data;
                }
            },//end load
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/jiaodiantu/save",
                    actionTip: "轮播图新增成功"
                };
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/jiaodiantu/update";
                    defaultOptions.actionTip = "轮播图更新成功";
                }
                const  validRes=this.myValidator.valid(this);
                console.log("valRes",validRes);
                if (!validRes)
                    return ;
                let util = new VueUtil(this);
                let params = {...this.jiaodiantu};
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
                this.$router.push("/admin/jiaodiantumanager");

            }
        },
        created() {
            this.load();
        }
    }
</script>

<style scoped>

</style>
