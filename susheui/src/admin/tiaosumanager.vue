<template>
    <div class="mypanel1">

        <div class="hd">
            宿舍申请管理
        </div>

        <!-- 搜索控件开始 -->
        <div class="search-options">


            <table cellspacing="0" width="100%">
                <tbody>

                <tr>
                    <td>


                      订单号  <input name="ddno" @keyup.enter="search" v-model="ddno" class="input-txt" type="text"/>


                        <el-button type="success" icon="el-icon-search" @click="search">搜索</el-button>
                    </td>
                </tr>
                </tbody>
            </table>


        </div>
        <!-- 搜索控件结束 -->


        <div class="action-details">
            <el-button-group>
                <!--{{export-view}}-->
                <el-button type="danger" size="small" icon="el-icon-delete" @click="deleteRec">删除</el-button>

            </el-button-group>
        </div>

        <table id="tiaosu" width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
            <thead>
            <tr>
                <th>
                    <input type="checkbox" @click="selectedAllHandler" v-model="selectedAll"/>
                </th>


                <th><b>订单号</b></th>


                <th><b>标题</b></th>


                <th><b>申请时间</b></th>


                <th><b>申请人</b></th>


                <th><b>状态</b></th>


                <th><b>宿舍申请</b></th>


                <th>
                    操作
                </th>

            </tr>

            </thead>
            <tbody>


            <tr v-for="(tiaosu,index) in  listTiaosu" :key="tiaosu.id">
                <td>
                    <input class="check" name="ids" type="checkbox" v-model="tiaosu.rowSelected"/>


                </td>


                <td>
                    {{tiaosu.ddno}}
                </td>


                <td>
                    {{tiaosu.title}}
                </td>


                <td>
                    {{tiaosu.createtime}}
                </td>


                <td>
                    {{tiaosu.hyname}}--{{tiaosu.hyaccount}}
                </td>


                <td>
                    <el-tag v-if="tiaosu.state==1">等待审核</el-tag>
                    <el-tag v-if="tiaosu.state==2">审核通过</el-tag>
                    <el-tag v-if="tiaosu.state==3">审核不通过</el-tag>
                </td>


                <td>
                    {{tiaosu.ldname}}{{tiaosu.unitname}}
                </td>




                <td>
                    <el-button-group>
                        <pop-dialog-button v-if="tiaosu.state==1" title="宿舍申请审核" css-class="el-button el-button--primary el-button--small" @refresh="search" width="80%" :url="'/admin/tiaosuspnext?id='+tiaosu.id">审核</pop-dialog-button>
                        <el-button size="small" type="warning" @click="showInfodlg(tiaosu)"><i class="el-icon-info"></i> 详细 </el-button>
                    </el-button-group>
                </td>
            </tr>

            <tr v-if="listTiaosu.length==0">
                <td colspan="20">
                    没有相关宿舍申请信息
                </td>
            </tr>


            </tbody>
        </table>
        <el-pagination
                background
                @size-change="pagesizeChange"
                @current-change="pageindexChange"
                :current-page="pageindex"
                :page-sizes="[pagesize]"
                :page-size="pagesize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
        </el-pagination>
        <div class="clear"></div>


        <el-dialog :visible.sync="infodlg" title="宿舍申请详细">

            <table class="smart-table" border="1" cellspacing="1" width="100%">


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
                    <td class="tlabel" align="right">状态:</td>
                    <td width="*">
						<el-tag v-if="tiaosu.state==1">等待审核</el-tag>
						<el-tag v-if="tiaosu.state==2">审核通过</el-tag>
						<el-tag v-if="tiaosu.state==3">审核不通过</el-tag>
                    </td>
                </tr>


                <tr>
                    <td class="tlabel"  align="right">宿舍楼:</td>
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


                <tr v-if="tiaosu.state!=1">
                    <td class="tlabel" align="right">回复:</td>
                    <td>
                        <div v-html="tiaosu.spdes"></div>
                    </td>
                </tr>


            </table>


        </el-dialog>
    </div>
</template>


<script type="text/javascript">

    import {VueUtil} from "../util/vueutil";
    import PopDialogButton from "../common/mydialog/PopDialogButton";

    export default {

        name: "tiaosumanager",
        components: {PopDialogButton},
        data() {
            return {


                "ddno": "",


                hostHead: this.$app.contextPath,
                selectedAll: false,
                listTiaosu: [],
                pageindex: 1, //初始页
                pagesize: 10,
                total: 10,
                infodlg: false,
                tiaosu: {}

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
                let url = "admin/tiaosu/list";
                let param = {
                    currentpageindex: this.pageindex,
                    pagesize: this.pagesize,
                    "ddno": this.ddno,
                };
                let util = new VueUtil(this);
                console.log("this.pageindex=" + this.pageindex);
                let res = await util.http.post(url, param);
                if (res.data != null) {
                    let pageInfo = res.data.data;
                    this.total = pageInfo.total;
                    this.listTiaosu = pageInfo.list;
                    console.log(pageInfo);
                }
            },
            async deleteRec() {
                let url = "admin/tiaosu/delete";
                let util = new VueUtil(this);
                let hasChecked = this.listTiaosu.some(c => {
                    return c.rowSelected == true;
                });
                if (!hasChecked) {
                    util.alert('请选择需要删除的记录', '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }

                let ids = this.listTiaosu.filter(c => c.rowSelected).map(c => c.id);
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
                if (this.listTiaosu != null) {
                    this.listTiaosu.forEach(c => {
                        c.rowSelected = !this.selectedAll;
                    });
                }
            },
            showInfodlg(tiaosu) {
                this.tiaosu = tiaosu;
                this.infodlg = true;
            },
        },
        created() {
            this.search();
        }
    };

</script>
