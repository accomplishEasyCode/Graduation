<template>
    <div >

        <table class="smart-table" border="1" cellspacing="1" width="100%">


            <tr>
                <td class="tlabel" width="10%" align="right">订单号:</td>
                <td width="*">
                    {{baoxiu.ddno}}
                </td>
            </tr>

            <tr>
                <td class="tlabel" width="10%" align="right">宿舍号:</td>
                <td width="*">
                    {{baoxiu.sbno}}
                </td>
            </tr>



            <tr>
                <td class="tlabel" width="10%" align="right">状态:</td>
                <td width="*">
                    <span v-if="baoxiu.state==1">待维修</span>
                    <span v-if="baoxiu.state==2">已安排维修</span>
                    <span v-if="baoxiu.state==3">暂不能处理</span>
                </td>
            </tr>



            <tr>
                <td class="tlabel" align="right">报修时间:</td>
                <td>
                    {{baoxiu.createtime}}
                </td>
            </tr>


            <tr>
                <td class="tlabel" width="10%" align="right">报修人:</td>
                <td width="*">
                    {{baoxiu.bxname}}--{{baoxiu.bxaccount}}
                </td>
            </tr>


            <tr>
                <td class="tlabel" width="10%" align="right">图片:</td>
                <td width="*">

                    <img :src="hostHead+baoxiu.tupian" width="200" height="200"/>

                </td>
            </tr>


            <tr>
                <td class="tlabel" width="10%" align="right">故障说明:</td>
                <td width="*">

                    <div v-html="baoxiu.des"></div>


                </td>
            </tr>


            <tr v-if="baoxiu.state!=1">
                <td class="tlabel" align="right">处理:</td>
                <td>
                    <div v-html="baoxiu.replydes"></div>
                </td>
            </tr>


        </table>
    </div>
</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "baoxiudetails",
        data() {
            return {
                actiontype: 'save',
                hostHead: this.$app.contextPath,
                "baoxiu": {},
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
                let id =this.params.id;
                let util = new VueUtil(this);
                if (id != null) {
                    let url = "admin/baoxiu/load";
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null)
                        this.baoxiu = res.data;
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
