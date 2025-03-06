<template>
    <div class="mypanel">

        <div class="hd">
           我的订单
        </div>

        <div class="bd">
        <!-- 搜索控件开始 -->
        <div class="search-options">


            <table cellspacing="0" width="100%">
                <tbody>

                <tr>
                    <td>


                        订单编号: <input name="ddno" @keyup.enter="search" v-model="ddno" class="input-txt" type="text"/>


                        <el-button type="primary" icon="el-icon-search" @click="search">
                            搜索
                        </el-button>
                    </td>
                </tr>
                </tbody>
            </table>


        </div>
        <!-- 搜索控件结束 -->

        <div class="action-details">
            <div class="el-button-group">

                <!--{{export-view}}-->
                <el-button type="danger" icon="el-icon-delete" @click="deleteRec">删除</el-button>
            </div>
        </div>

        <table  width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
            <thead>
            <tr>
                <th>
                    <input type="checkbox" @click="selectedAllHandler" v-model="selectedAll"/>
                </th>


                <th><b>订单编号</b></th>


                <th style="width: 285px;"><b>商品名称</b></th>


                <th><b>预定日期</b></th>



                <th><b>预定人</b></th>


                <th><b>金额(元)</b></th>


                <th><b>状态</b></th>



                <th>
                    操作
                </th>

            </tr>

            </thead>
            <tbody>


            <tr v-for="(appointorder,index) in  listAppointorder" :key="appointorder.id">
                <td>
                    <input class="check" name="ids" type="checkbox" v-model="appointorder.rowSelected"/>


                </td>


                <td>
                    {{appointorder.ddno}}
                </td>

                <td>
                    {{appointorder.pname}}
                </td>


                <td>
                    {{appointorder.yydate}}
                </td>



                <td>
                    {{appointorder.hyname}}
                </td>


                <td>
                    {{appointorder.fee}}¥
                </td>


                <td>
                    <span v-if="appointorder.state==1">已预订</span>
                    <span v-if="appointorder.state==2">已付款</span>
                    <span v-if="appointorder.state==3">已取消</span>
                </td>



                <td>

                    <el-dropdown>
                        <el-button icon="el-icon-edit" type="primary">
                            操作<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">


                            <el-dropdown-item  @click.native="showOrderInfo(appointorder)" > <i class="el-icon-info"></i>查看</el-dropdown-item>


                        </el-dropdown-menu>
                    </el-dropdown>

                </td>
            </tr>

            <tr v-if="listAppointorder.length==0">
                <td colspan="20">
                    没有相关订单信息
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


        <el-dialog title="订单查看" :visible.sync="orderinfodlg">
            <table class="smart-table" border="1" cellspacing="1" width="100%">


                <tr>
                    <td class="tlabel" width="15%" align="right">订单编号:</td>
                    <td width="*">
                        {{appointorder.ddno}}
                    </td>
                </tr>

                <tr>
                    <td class="tlabel" width="10%" align="right">商品名称:</td>
                    <td width="*">
                        {{appointorder.pname}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">预定日期:</td>
                    <td>
                        {{appointorder.yydate}}
                    </td>
                </tr>





                <tr>
                    <td class="tlabel" width="10%" align="right">预定人:</td>
                    <td width="*">
                        {{appointorder.hyname}}-{{appointorder.hyaccount}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="10%" align="right">联系电话:</td>
                    <td width="*">
                        {{appointorder.mobile}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="10%" align="right">状态:</td>
                    <td width="*">
                        <span v-if="appointorder.state==1">已预订</span>
                        <span v-if="appointorder.state==2">已付款</span>
                        <span v-if="appointorder.state==3">已取消</span>
                    </td>
                </tr>





                <tr>
                    <td class="tlabel" align="right">说明:</td>
                    <td>
                        <div v-html="appointorder.des"></div>
                    </td>
                </tr>



            </table>


        </el-dialog>



        <el-dialog title="订单支付" :visible.sync="paymentdlg">

            <table class="smart-table" border="1" cellspacing="1" width="100%">


                <tr>
                    <td class="tlabel" width="15%" align="right">订单编号:</td>
                    <td width="*">
                        {{appointorder.ddno}}
                    </td>
                </tr>

                <tr>
                    <td class="tlabel" width="10%" align="right">商品名称:</td>
                    <td width="*">
                        {{appointorder.pname}}
                    </td>
                </tr>




                <tr>
                    <td class="tlabel" align="right">预定时间:</td>
                    <td>
                        {{appointorder.yydate}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="10%" align="right">金额（元）:</td>
                    <td width="*">
                        {{appointorder.fee}}¥
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="10%" align="right">预定人:</td>
                    <td width="*">
                        {{appointorder.hyname}}--{{appointorder.hyaccount}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="10%" align="right">联系电话:</td>
                    <td width="*">
                        {{appointorder.mobile}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="10%" align="right">状态:</td>
                    <td width="*">
                        <span v-if="appointorder.state==1">等待付款</span>
                        <span v-if="appointorder.state==2">已付款</span>
                        <span v-if="appointorder.state==3">已取消</span>
                    </td>
                </tr>



                <tr>
                    <td class="tlabel" align="right">说明:</td>
                    <td>
                        <div v-html="appointorder.des"></div>
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
                <el-button type="primary" @click="paymentHandler">确 定</el-button>
                <el-button @click="paymentdlg= false">取 消</el-button>
            </div>
        </el-dialog>
        </div>

    </div>

</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "appointordermanager",
        data() {
            return {
                "ddno": "",
                hostHead: this.$app.contextPath,
                selectedAll: false,
                listAppointorder: [],
                pageindex: 1, //初始页
                pagesize: 10,
                total: 10,
                orderinfodlg:false,
                appointorder:{},
                paymentdlg:false,
                paypwd:""
            }

        },
        props:{
          accountInfo: {
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
                let url = "admin/appointorder/list";
                let param = {
                    currentpageindex: this.pageindex,
                    pagesize: this.pagesize,
                    "hyid":this.accountInfo.id,
                    "ddno": this.ddno,
                };
                let util = new VueUtil(this);
                console.log("this.pageindex=" + this.pageindex);
                let res = await util.http.post(url, param);
                if (res.data != null) {
                    let pageInfo = res.data.data;
                    this.total = pageInfo.total;
                    this.listAppointorder = pageInfo.list;
                    console.log(pageInfo);
                }
            },
            async deleteRec() {
                let url = "admin/appointorder/delete";
                let util = new VueUtil(this);
                let hasChecked = this.listAppointorder.some(c => {
                    return c.rowSelected == true;
                });
                if (!hasChecked) {
                    util.alert('请选择需要删除的记录', '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }

                let ids = this.listAppointorder.filter(c => c.rowSelected).map(c => c.id);
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
                if (this.listAppointorder != null) {
                    this.listAppointorder.forEach(c => {
                        c.rowSelected = !this.selectedAll;
                    });
                }
            },
            showOrderInfo(order){
                this.appointorder=order;
                this.orderinfodlg=true;
            },
            async cancelHandler(order){
                let util = new VueUtil(this);
                let res = util.confirm('确定要取消订单?', '系统提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    util.http.post("admin/appointorder/cancel", {id:order.id}, {emulateJSON: false}).then(res => {
                        if (res.data != null && res.data.stateCode > 0) {
                            this.search();
                        }
                    });
                }).catch(() => {
                });

            },
            showPayment(order){
                this.appointorder=order;
                this.paymentdlg=true;
            },
            async paymentHandler(){

                let util = new VueUtil(this);

                let {data:res} = await util.http.post("admin/appointorder/payment", {
                    id:this.appointorder.id,
                    paypwd:this.paypwd
                });
                if (res!=null&&res.stateCode<0){
                    this.$message.error(res.des);
                    return ;
                }
                this.paymentdlg=false;
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
