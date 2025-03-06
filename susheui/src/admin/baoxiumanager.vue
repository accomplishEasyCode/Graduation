<template>
    <div id="app">

        <div class="panel panel-default">
            <div class="panel-heading">报修管理</div>
            <div class="panel-body">

                <!-- 搜索控件开始 -->
                <div class="search-options">


                    <table cellspacing="0" width="100%">
                        <tbody>

                        <tr>
                            <td>


                                订单号: <input name="ddno" @keyup.enter="search" v-model="ddno" class="input-txt" type="text"/>


                                <el-button type="success" icon="el-icon-search" @click="search">搜索</el-button>
                            </td>
                        </tr>
                        </tbody>
                    </table>


                </div>
                <!-- 搜索控件结束 -->

                <div class="clear">
                </div>


                <div class="action-details">
                    <el-button-group>
                        <!--{{export-view}}-->
                        <el-button type="danger" icon="el-icon-delete" @click="deleteRec">删除</el-button>

                    </el-button-group>
                </div>

                <table id="baoxiu" width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
                    <thead>
                    <tr>
                        <th>
                            <input type="checkbox" @click="selectedAllHandler" v-model="selectedAll"/>
                        </th>

                        <th><b>订单号</b></th>



                        <th><b>宿舍号</b></th>



                        <th><b>状态</b></th>


                        <th><b>报修时间</b></th>


                        <th><b>报修人</b></th>




                        <th>
                            操作
                        </th>

                    </tr>

                    </thead>
                    <tbody>


                    <tr v-for="(baoxiu,index) in  listBaoxiu" :key="baoxiu.id">
                        <td>
                            <input class="check" name="ids" type="checkbox" v-model="baoxiu.rowSelected"/>


                        </td>


                        <td>
                            {{baoxiu.ddno}}
                        </td>

                        <td>
                            {{baoxiu.sbno}}
                        </td>




                        <td>
                            <span v-if="baoxiu.state==1">待维修</span>
                            <span v-if="baoxiu.state==2">已安排维修</span>
                            <span v-if="baoxiu.state==3">暂不能处理</span>
                        </td>



                        <td>
                            {{baoxiu.createtime}}
                        </td>


                        <td>
                            {{baoxiu.bxname}}--{{baoxiu.bxaccount}}
                        </td>



                        <td>
                            <el-button-group>

                                <el-button @click="showBxdlg(baoxiu)" v-if="baoxiu.state==1" type="warning" icon="el-icon-arrow-right" >维修处理</el-button>


                                <pop-dialog-button title="报修信息查看" css-class="el-button el-button--warning" width="50%" :url="`/huiyuan/baoxiudetails?id=${baoxiu.id}`"><i class="el-icon-info"></i>详细</pop-dialog-button>

                            </el-button-group>

                        </td>
                    </tr>

                    <tr v-if="listBaoxiu.length==0">
                        <td colspan="20">
                            没有相关报修信息
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

            </div>
        </div>



        <el-dialog title="报修处理" :visible.sync="bxdlg">

            <table cellpadding="0" cellspacing="1" border="1" class="smart-table" width="100%">


                <tr>
                    <td align="right">订单号:</td>
                    <td>

                        {{baoxiu.ddno}}

                    </td>
                </tr>
                <tr>
                    <td align="right">宿舍号:</td>
                    <td>

                        {{baoxiu.sbno}}

                    </td>
                </tr>


                <tr>
                    <td align="right">报修时间:</td>
                    <td>
                        {{baoxiu.createtime}}
                    </td>
                </tr>


                <tr>
                    <td align="right">报修人:</td>
                    <td>

                        {{baoxiu.bxname}}--{{baoxiu.bxaccount}}

                    </td>
                </tr>


                <tr>
                    <td align="right">图片:</td>
                    <td>

                        <img :src="hostHead+baoxiu.tupian" width="200" height="200"/>

                    </td>
                </tr>

                <tr>
                    <td align="right">处理结果:</td>
                    <td>

                        <el-radio-group v-model="state">
                            <el-radio :label="2">已安排处理</el-radio>
                            <el-radio :label="3">暂无法处理</el-radio>
                        </el-radio-group>

                    </td>
                </tr>


                <tr>
                    <td align="right">故障说明:</td>
                    <td>


                        <div v-html="baoxiu.des"></div>


                    </td>
                </tr>



                <tr>
                    <td align="right">处理说明:</td>
                    <td colspan="3">
                        <el-input type="textarea" v-model="replydes" :row="4"></el-input>

                    </td>
                </tr>



            </table>

            <div slot="footer" class="dialog-footer">
                <el-button @click="submitCheck" type="primary" icon="el-icon-check">提交</el-button>
                <el-button @click="bxdlg=false">取 消</el-button>
            </div>


        </el-dialog>

    </div>
</template>

<script>
    import {VueUtil} from "../util/vueutil";
    import PopDialogButton from "../common/mydialog/PopDialogButton";

    export default {
        name: "baoxiumanager",
        components: {PopDialogButton},
        data() {
            return {
                "ddno": "",
                hostHead: this.$app.contextPath,
                selectedAll: false,
                listBaoxiu: [],
                pageindex: 1, //初始页
                pagesize: 10,
                total: 10,
                bxdlg:false,
                baoxiu:{},
                state:2,
                replydes:""
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
            showBxdlg(baoxiu){
                this.baoxiu=baoxiu;
                this.bxdlg = true;
            },
            async search() {
                let url = "admin/baoxiu/list";
                let param = {
                    currentpageindex: this.pageindex,
                    pagesize: this.pagesize,
                    "ddno": this.ddno,
                };
                if (this.roleid==2)
                    param.bxrid="${sessionScope.users.id}";
                let util = new VueUtil(this);
                console.log("this.pageindex=" + this.pageindex);
                let res = await util.http.post(url, param);
                if (res.data != null) {
                    let pageInfo = res.data.data;
                    this.total = pageInfo.total;
                    this.listBaoxiu = pageInfo.list;
                    console.log(pageInfo);
                }
            },
            async deleteRec() {
                let url = "admin/baoxiu/delete";
                let util = new VueUtil(this);
                let hasChecked = this.listBaoxiu.some(c => {
                    return c.rowSelected == true;
                });
                if (!hasChecked) {
                    util.alert('请选择需要删除的记录', '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }

                let ids = this.listBaoxiu.filter(c => c.rowSelected).map(c => c.id);
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
            exportHandler() {
                window.location.href = this.hostHead + "/admin/baoxiu/expor";
            },
            selectedAllHandler() {
                console.log(this.selectedAll);
                if (this.listBaoxiu != null) {
                    this.listBaoxiu.forEach(c => {
                        c.rowSelected = !this.selectedAll;
                    });
                }
            },
            async submitCheck(){

                let util=new VueUtil(this);

                let url="admin/baoxiu/shenpi";
                let {data:res}=await util.http.post(url,{
                    id:this.baoxiu.id,
                    "state":this.state,
                    replydes:this.replydes
                });
                console.log("res",res);
                if (res != null && res.stateCode < 0){
                    util.message({
                        message: defaultOptions.actionTip,
                        type: 'error',
                        duration: 2000
                    });
                    return;
                }


                this.bxdlg=false;
                this.$message.success("成功");
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
