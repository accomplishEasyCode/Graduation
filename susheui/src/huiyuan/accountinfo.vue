<template>

    <div class="mypanel">

        <div class="hd">
            账户信息
        </div>
        <div class="bd">


            <table cellpadding="0" cellspacing="1" border="1" class="smart-table" width="100%">


                <tr>
                    <td width="10%" align="right">账号:</td>
                    <td width="*">
                        {{huiyuan.accountname}}
                    </td>
                    <td colspan="2" rowspan="6" width="*">
                        <img :src="hostHead+huiyuan.photo" width="200" height="200"/>

                    </td>
                </tr>


                <tr>
                    <td width="10%" align="right">姓名:</td>
                    <td width="*">
                        {{huiyuan.name}}
                    </td>
                </tr>


                <tr>
                    <td width="10%" align="right">性别:</td>
                    <td width="*">
                        {{huiyuan.sex}}
                    </td>
                </tr>



                <tr>
                    <td width="10%" align="right">身份证:</td>
                    <td width="*">
                        {{huiyuan.idcardno}}
                    </td>
                </tr>



                <tr>
                    <td width="10%" align="right">籍贯:</td>
                    <td width="*">
                        {{huiyuan.jiguan}}
                    </td>
                </tr>

                <tr>
                    <td align="right">注册时间:</td>
                    <td>
                        {{huiyuan.createtime}}
                    </td>
                </tr>

                <tr>
                    <td align="right">出生年月:</td>
                    <td>
                        {{huiyuan.birthday}}
                    </td>



                    <td  align="right">地址:</td>
                    <td width="*">
                        {{huiyuan.address}}
                    </td>


                </tr>

                <tr>
                    <td align="right">宿舍:</td>
                    <td >
                        <el-tag v-if="huiyuan.state==1" type="danger">待分宿舍</el-tag>
                        <el-tag v-if="huiyuan.state==2" type="success">{{huiyuan.ldname}}--{{huiyuan.unitname}}</el-tag>

                    </td>
                </tr>


                <tr>
                    <td align="right">介绍:</td>
                    <td colspan="3">
                        <div v-html="huiyuan.des"></div>
                    </td>
                </tr>


            </table>

        </div>
    </div>

</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "accountinfo",
        data() {
            return {
                actiontype: 'save',
                hostHead: this.$app.contextPath,
                "huiyuan": {},
            }
        },
        props:{
            accountInfo:{
               type:Object
            }
        },
        methods: {
            //begin load
            async load() {
                let id = this.accountInfo.id;
                let util = new VueUtil(this);
                if (id != null) {
                    let url = "admin/huiyuan/load";
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null)
                        this.huiyuan = res.data;
                }
            },//end load

        },
        created() {
            this.load();
        }
    }
</script>

<style scoped>

</style>
