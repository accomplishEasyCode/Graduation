<template>

    <el-dialog title="查看学生信息" :visible.sync="visible">

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
                <td width="10%" align="right">班级:</td>
                <td width="*">
                    {{huiyuan.bjname}}
                </td>
            </tr>





            <tr>
                <td align="right">介绍:</td>
                <td colspan="3">
                    <div v-html="huiyuan.des"></div>
                </td>
            </tr>


        </table>

    </el-dialog>


</template>

<script>
import {VueUtil} from "@/util/vueutil";

export default {
    name: "huiyuandetails",
    data(){
        return {
            visible:false,
            hostHead: this.$app.contextPath,
            huiyuan:{},
        }
    },
    methods:{
        async show(item) {

            if (item != null) {
                let url = "admin/huiyuan/load";
                let util = new VueUtil(this);
                let {data: res} = await util.http.post(url, {id: item.id});
                console.log("res", res);
                if (res != null && res.data != null) {
                    this.huiyuan = res.data;

                }
            }
            this.visible=true;
        },
    }
}
</script>

<style scoped>

</style>
