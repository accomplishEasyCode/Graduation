<template>
    <div class="mypanel">

        <div class="bd">

            <table cellpadding="0" cellspacing="1" border="1" class="smart-table" width="100%">


                <tr>
                    <td class="tlabel" width="20%" align="right">订单号:</td>
                    <td width="*">
                        {{tiaosu.ddno}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">标题:</td>
                    <td width="*">
                        {{tiaosu.title}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">申请时间:</td>
                    <td>
                        {{tiaosu.createtime}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel"  align="right">申请人:</td>
                    <td width="*">
                        {{tiaosu.hyname}}--{{tiaosu.hyaccount}}
                    </td>
                </tr>







                <tr>
                    <td class="tlabel"  align="right">宿舍:</td>
                    <td width="*">
                         {{tiaosu.ldname}}{{tiaosu.unitname}}
                    </td>
                </tr>



                <tr>
                    <td class="tlabel" align="right">说明:</td>
                    <td>
                        <div v-html="tiaosu.des"></div>
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">结果:</td>
                    <td>


                        <el-select v-model="state">
                            <el-option label="审核通过" :value="2"></el-option>
                            <el-option label="审核不合格" :value="3"></el-option>
                        </el-select>


                    </td>
                </tr>



                <tr>
                    <td class="tlabel" align="right">处理说明:</td>
                    <td colspan="3">
                        <textarea name="reply" v-model="replydes" style="width:48%;height:80px;"></textarea>
                    </td>
                </tr>

                <tr>
                    <td colspan="4">
                        <el-button @click="submitCheck" type="danger" icon="el-icon-check">提交审批</el-button>
                    </td>
                </tr>


            </table>
        </div>
    </div>
</template>


<script type="text/javascript">

    import {VueUtil} from "../util/vueutil";

    export default {
        name: "tiaosuspnext",
        data() {
            return {
                "state": 2,
                replydes: "",
                hostHead: this.$app.contextPath,
                "tiaosu": {},
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
                let util = new VueUtil(this);
                if (id != null) {
                    let url = "admin/tiaosu/load";
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null)
                        this.tiaosu = res.data;
                }
            },//end load
            async submitCheck() {
                let id = this.params.id;
                let util = new VueUtil(this);
                if (id == null) {
                    this.$message.error("id不能为空");
                    return ;
                }
                let url = "admin/tiaosu/shenpi";
                let {data: res} = await util.http.post(url, {
                    id: id,
                    "state": this.state,
                    replydes: this.replydes
                });
                console.log("res", res);
                if (res != null && res.stateCode < 0) {
                    this.$message.error(res.des);
                    return;
                }
                this.$emit("close",false);

            }

        },
        created() {
            this.load();
        }

    };

</script>
