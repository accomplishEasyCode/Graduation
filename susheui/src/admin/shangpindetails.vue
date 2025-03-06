<template>
    <div class="mypanel1">

        <div class="bd">
            <table cellpadding="0" cellspacing="1" border="1" class="smart-table" width="100%">


            <tr>
                <td class="tlabel" width="10%" align="right">名称:</td>
                <td width="*">
                    {{shangpin.name}}
                </td>
            </tr>


            <tr>
                <td class="tlabel" width="10%" align="right">商品类别:</td>
                <td width="*">
                    {{shangpin.typename}}
                </td>
            </tr>


            <tr>
                <td class="tlabel" width="10%" align="right">图片:</td>
                <td width="*">
                    <img width="200" height="200" :src="hostHead+shangpin.tupian"/>

                </td>
            </tr>


            <tr>
                <td class="tlabel" width="10%" align="right">价格（元）:</td>
                <td width="*">
                    {{shangpin.price}}¥
                </td>
            </tr>


            <tr>
                <td class="tlabel" width="10%" align="right">品牌:</td>
                <td width="*">
                    {{shangpin.pinpai}}
                </td>
            </tr>



            <tr>
                <td class="tlabel" width="10%" align="right">库存:</td>
                <td width="*">
                    {{shangpin.kucun}}{{shangpin.danwei}}
                </td>
            </tr>



            <tr>
                <td class="tlabel" align="right">创建时间:</td>
                <td>
                    {{shangpin.createtime}}
                </td>
            </tr>


            <tr>
                <td class="tlabel" align="right">说明:</td>
                <td>
                    <div v-html="shangpin.des"></div>
                </td>
            </tr>


        </table>
        </div>
    </div>
</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "shangpindetails",
        data() {
            return {
                actiontype: 'save',
                hostHead: this.$app.contextPath,
                "shangpin": {},
            }
        },
        methods: {
            //begin load
            async load() {
                let id = this.params.id;
                let util = new VueUtil(this);
                if (id != "") {
                    let url = "admin/shangpin/load";
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null)
                        this.shangpin = res.data;
                }
            },//end load

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
