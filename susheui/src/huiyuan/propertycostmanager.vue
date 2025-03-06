<template>
    <div class="panel panel-default">

        <div class="panel-heading">
           电费缴费管理

        </div>

        <!-- 搜索控件开始 -->
        <div class="search-options">


            <table cellspacing="0" width="100%">
                <tbody>

                <tr>
                    <td>


                        订单号:<input name="ddno" @keyup.enter="search" v-model="ddno" class="input-txt" type="text"/>


                        <el-button type="success" icon="el-icon-search" @click="search">搜索</el-button>
                    </td>
                </tr>
                </tbody>
            </table>


        </div>
        <!-- 搜索控件结束 -->

        <table  width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
            <thead>
            <tr>


                <th><b>订单号</b></th>


                <th><b>寝室号</b></th>

                <th><b>缴费月份</b></th>


                <th><b>类目</b></th>


                <th><b>费用（元）</b></th>


                <th><b>创建时间</b></th>


                <th><b>状态</b></th>




                <th>
                    操作
                </th>

            </tr>

            </thead>
            <tbody>


            <tr v-for="(propertycost,index) in  listPropertycost" :key="propertycost.id">

                <td>
                    {{propertycost.ddno}}
                </td>


                <td>
                    {{propertycost.ldname}}--{{propertycost.roomname}}
                </td>



                <td>
                    {{propertycost.jfyue}}
                </td>


                <td>
                    {{propertycost.typename}}
                </td>


                <td>
                    {{propertycost.fee}}¥
                </td>


                <td>
                    {{propertycost.createtime}}
                </td>


                <td>
                    <div v-if="propertycost.state==1">等待付款</div>
                    <div v-if="propertycost.state==2">已付款</div>
                    <div v-if="propertycost.state==3">已取消</div>
                </td>







                <td>
                    <el-button-group>
                        <el-button v-if="propertycost.state==1" type="primary" @click="showPaydlg(propertycost)"><i class="el-icon-info"></i> 付款
                        </el-button>
                        <el-button v-if="propertycost.state==1" type="danger" @click="cancelHandler(propertycost)"><i class="el-icon-close"></i> 取消
                        </el-button>
                        <el-button type="warning" @click="showInfodlg(propertycost)"><i class="el-icon-info"></i> 详细
                        </el-button>
                    </el-button-group>
                </td>
            </tr>

            <tr v-if="listPropertycost.length==0">
                <td colspan="20">
                    没有相关电费缴费信息
                </td>
            </tr>


            </tbody>
        </table>
        <el-pagination
            @size-change="pagesizeChange"
            @current-change="pageindexChange"
            :current-page="pageindex"
            :page-sizes="[pagesize]"
            :page-size="pagesize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
        <div class="clear"></div>


        <el-dialog :visible.sync="infodlg" title="电费缴费详细">

            <table class="smart-table" border="1" cellspacing="1" width="100%">


                <tr>
                    <td class="tlabel" width="15%" align="right">订单号:</td>
                    <td width="*">
                        {{propertycost.ddno}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="15%" align="right">寝室号:</td>
                    <td width="*">
                        {{propertycost.ldname}}--{{propertycost.roomname}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="15%" align="right">类目:</td>
                    <td width="*">
                        {{propertycost.typename}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="15%" align="right">费用（元）:</td>
                    <td width="*">
                        {{propertycost.fee}}¥
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">创建时间:</td>
                    <td>
                        {{propertycost.createtime}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="15%" align="right">状态:</td>
                    <td width="*">
                        <div v-if="propertycost.state==1">等待付款</div>
                        <div v-if="propertycost.state==2">已付款</div>
                        <div v-if="propertycost.state==3">已取消</div>
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">缴费月份:</td>
                    <td>
                        {{propertycost.jfyue}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="15%" align="right">说明:</td>
                    <td width="*">
                        <div v-html="propertycost.des"></div>

                    </td>
                </tr>


            </table>


        </el-dialog>


        <el-dialog :visible.sync="paydlg" title="电费缴费付款">

            <table class="smart-table" border="1" cellspacing="1" width="100%">


                <tr>
                    <td class="tlabel" width="15%" align="right">订单号:</td>
                    <td width="*">
                        {{propertycost.ddno}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="15%" align="right">寝室号:</td>
                    <td width="*">
                        {{propertycost.ldname}}--{{propertycost.roomname}}
                    </td>
                </tr>




                <tr>
                    <td class="tlabel" width="15%" align="right">类目:</td>
                    <td width="*">
                        {{propertycost.typename}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="15%" align="right">费用（元）:</td>
                    <td width="*">
                        {{propertycost.fee}}¥
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">创建时间:</td>
                    <td>
                        {{propertycost.createtime}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="15%" align="right">状态:</td>
                    <td width="*">
                        <div v-if="propertycost.state==1">等待付款</div>
                        <div v-if="propertycost.state==2">已付款</div>
                        <div v-if="propertycost.state==3">已取消</div>
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">缴费月份:</td>
                    <td>
                        {{propertycost.jfyue}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="15%" align="right">说明:</td>
                    <td width="*">
                        <div v-html="propertycost.des"></div>

                    </td>
                </tr>

                <tr>
                    <td  align="right">支付密码:</td>
                    <td colspan="3">
                        <div style="width:180px;display: inline-block; ">
                            <el-input type="password" v-model="paypwd"/>
                        </div>
                    </td>
                </tr>


            </table>

            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="payHandler">确 定</el-button>
                <el-button @click="paydlg= false">取 消</el-button>
            </div>


        </el-dialog>

    </div>
</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "propertycostmanager",
        data() {
            return {
                "ddno": "",
                hostHead: this.$app.contextPath,
                selectedAll: false,
                listPropertycost: [],
                pageindex: 1, //初始页
                pagesize: 10,
                total: 10,
                infodlg: false,
                paydlg:false,
                paypwd:"",
                propertycost: {}

            }

        },
        props:{
            accountInfo:{
                type:Object
            }
        },
        methods: {
            pagesizeChange: function (pagesize) {
                this.pagesize = pagesize;
            },
            pageindexChange: function (pageindex) {
                this.pageindex = pageindex;
                console.log(this.pageindex);
                this.search();
            },

            async search() {
                let url = "admin/propertycost/list";
                let param = {
                    currentpageindex: this.pageindex,
                    pagesize: this.pagesize,
                    "roomid":this.accountInfo.unitid,
                    "ldid":this.accountInfo.ldid,
                    "ddno": this.ddno,
                };
                let util = new VueUtil(this);
                console.log("this.pageindex=" + this.pageindex);
                let res = await util.http.post(url, param);
                if (res.data != null) {
                    let pageInfo = res.data.data;
                    this.total = pageInfo.total;
                    this.listPropertycost = pageInfo.list;
                    console.log(pageInfo);
                }
            },
            async deleteRec() {
                let url = "admin/propertycost/delete";
                let util = new VueUtil(this);
                let hasChecked = this.listPropertycost.some(c => {
                    return c.rowSelected == true;
                });
                if (!hasChecked) {
                    util.alert('请选择需要删除的记录', '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }

                let ids = this.listPropertycost.filter(c => c.rowSelected).map(c => c.id);
                let res = util.confirm('是否要删除数据?', '系统提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    util.http.post(url, {ids}, {emulateJSON: false}).then(res => {
                        if (res.data != null && res.data.stateCode > 0) {
                            this.search();
                        }
                    });
                }).catch(() => {
                });
            },

            selectedAllHandler() {
                console.log(this.selectedAll);
                if (this.listPropertycost != null) {
                    this.listPropertycost.forEach(c => {
                        c.rowSelected = !this.selectedAll;
                    });
                }
            },
            showInfodlg(propertycost) {
                this.propertycost = propertycost;
                this.infodlg = true;
            },
            showPaydlg(propertycost) {
                this.propertycost = propertycost;
                this.paydlg = true;
            },
            async cancelHandler(order){
                let util = new VueUtil(this);
                let res = util.confirm('确定要取消订单?', '系统提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    util.http.post("admin/propertycost/cancel", {id:order.id}, {emulateJSON: false}).then(res => {
                        if (res.data != null && res.data.stateCode > 0) {
                            this.search();
                        }
                    });
                }).catch(() => {
                });

            },

            async payHandler(){

                let util = new VueUtil(this);

                let {data:res} = await util.http.post("admin/propertycost/payment", {
                    id:this.propertycost.id,
                    hyid:this.accountInfo.id,
                    paypwd:this.paypwd
                });
                if (res!=null&&res.stateCode<0){
                    this.$message.error(res.des);
                    return ;
                }
                this.paydlg=false;
                this.$message.success("支付成功");
                this.search();
            }
        },
        created() {
            this.search();
        }
    }
</script>

<style scoped>

</style>
