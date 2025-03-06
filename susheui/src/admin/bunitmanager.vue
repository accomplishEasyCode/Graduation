<template>
    <div class="panel panel-default">

        <div class="panel-heading">
            宿舍管理
        </div>

        <!-- 搜索控件开始 -->
        <div class="search-options">


            <table cellspacing="0" width="100%">
                <tbody>

                <tr>
                    <td>

                        宿舍楼:<el-select v-model="ldid">
                            <el-option :value="-1" label="全部"></el-option>
                            <el-option v-for="item in listLoudong" :value="item.id" :label="item.name"></el-option>
                        </el-select>

                        宿舍类型:<el-select v-model="typeid">
                        <el-option :value="-1" label="全部"></el-option>
                        <el-option v-for="item in listSstype" :value="item.id" :label="item.name"></el-option>
                    </el-select>
                        寝室名<input name="name" @keyup.enter="search" v-model="name" class="input-txt" type="text"/>


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
                <el-button type="danger" icon="el-icon-delete" @click="deleteRec">删除</el-button>
                <route-button icon="el-icon-plus" url="/admin/bunitadd">新增</route-button>

            </el-button-group>
        </div>

        <table id="bunit" width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
            <thead>
            <tr>
                <th>
                    <input type="checkbox" @click="selectedAllHandler" v-model="selectedAll"/>
                </th>


                <th><b>寝室号</b></th>



                <th><b>班级</b></th>



                <th><b>已入住人数</b></th>


                <th><b>宿舍类型</b></th>


                <th><b>洗衣机</b></th>


                <th><b>空调</b></th>



                <th>
                    操作
                </th>

            </tr>

            </thead>
            <tbody>


            <tr v-for="(bunit,index) in  listBunit" :key="bunit.id">
                <td>
                    <input class="check" name="ids" type="checkbox" v-model="bunit.rowSelected"/>


                </td>


                <td>
                  {{bunit.ldname}}  {{ bunit.name }}
                </td>



                <td>
                    {{ bunit.bjname }}
                </td>


                <td>
                    {{ bunit.cicount }}
                </td>


                <td>
                    {{ bunit.typename }}/{{bunit.maxcount}}人
                </td>


                <td>
                    <div v-if="bunit.haswm==1">
                        <i class="el-icon-check"></i>
                    </div>
                    <div v-else>
                        <i class="el-icon-close"></i>
                    </div>
                </td>


                <td>
                    <div v-if="bunit.hasac==1">
                        <i class="el-icon-check"></i>
                    </div>
                    <div v-else>
                        <i class="el-icon-close"></i>
                    </div>
                </td>



                <td>
                    <el-button-group>
                        <router-link class="el-button el-button--primary el-button--small"
                                     :to="`/admin/bunitadd?id=${bunit.id}`"><i class="el-icon-edit"> </i>编辑
                        </router-link>

                        <el-button size="small" type="warning" @click="showInfodlg(bunit)"><i class="el-icon-info"></i>
                            详细
                        </el-button>
                    </el-button-group>
                </td>
            </tr>

            <tr v-if="listBunit.length==0">
                <td colspan="20">
                    没有相关宿舍信息
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


        <el-dialog :visible.sync="infodlg" title="宿舍详细">

            <table class="smart-table" border="1" cellspacing="1" width="100%">


                <tr>
                    <td class="tlabel" width="20%" align="right">单元号:</td>
                    <td width="*">
                        {{ bunit.name }}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel"  align="right">所属楼栋:</td>
                    <td width="*">
                        {{ bunit.ldname }}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel"  align="right">班级:</td>
                    <td width="*">
                        {{ bunit.bjname }}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">已入住人数:</td>
                    <td width="*">
                        {{ bunit.cicount }}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="10%" align="right">宿舍类型:</td>
                    <td width="*">
                        {{ bunit.typename }}/{{bunit.maxcount}}人
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="10%" align="right">洗衣机:</td>
                    <td width="*">
                        <div v-if="bunit.haswm==1">
                            <i class="el-icon-check"></i>
                        </div>
                        <div v-else>
                            <i class="el-icon-close"></i>
                        </div>
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="10%" align="right">空调:</td>
                    <td width="*">
                        <div v-if="bunit.hasac==1">
                            <i class="el-icon-check"></i>
                        </div>
                        <div v-else>
                            <i class="el-icon-close"></i>
                        </div>
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" width="10%" align="right">说明:</td>
                    <td width="*">
                        {{ bunit.des }}
                    </td>
                </tr>


            </table>


        </el-dialog>
    </div>
</template>


<script type="text/javascript">

import {VueUtil} from "../util/vueutil";

export default {

    name: "bunitmanager",
    data() {
        return {


            "name": "",
            ldid:-1,
            typeid:-1,
            listSstype:[],
            listLoudong:[],
            hostHead: this.$app.contextPath,
            selectedAll: false,
            listBunit: [],
            pageindex: 1, //初始页
            pagesize: 10,
            total: 10,
            infodlg: false,
            bunit: {}

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
        async getSstype() {

            let url = "admin/sstype/list";
            let {data: res} = await this.$http.post(url, {"ispaged": "-1"});
            if (res != null && res.stataCode<0) {
                this.$message.error(res.des);
                return;
            }
            this.listSstype=res.data;
            if(this.listSstype!=null&&this.listSstype.length>0){
                this.bunit.typeid=this.listSstype[0].id;
            }

        },//end load
        async getLoudongs() {
            let url = "admin/loudong/list";
            let {data: res} = await this.$http.post(url,{"ispaged":'-1'});
            if (res.stateCode < 0) {
                this.$message.error(res.des);
                return;
            }
            this.listLoudong = res.data;
            if(this.listLoudong!=null&&this.listLoudong.length>0){
                this.bunit.ldid=this.listLoudong[0].id;
            }
        },
        async search() {
            let url = "admin/bunit/list";
            let param = {
                currentpageindex: this.pageindex,
                pagesize: this.pagesize,
                "name": this.name,
            };
            if(this.typeid!=-1)
                param.typeid=this.typeid;
            if(this.ldid!=-1)
                param.ldid=this.ldid;
            let util = new VueUtil(this);
            console.log("this.pageindex=" + this.pageindex);
            let res = await util.http.post(url, param);
            if (res.data != null) {
                let pageInfo = res.data.data;
                this.total = pageInfo.total;
                this.listBunit = pageInfo.list;
                console.log(pageInfo);
            }
        },
        async deleteRec() {
            let url = "admin/bunit/delete";
            let util = new VueUtil(this);
            let hasChecked = this.listBunit.some(c => {
                return c.rowSelected == true;
            });
            if (!hasChecked) {
                util.alert('请选择需要删除的记录', '系统提示', {
                    confirmButtonText: '确定'
                });
                return;
            }

            let ids = this.listBunit.filter(c => c.rowSelected).map(c => c.id);
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
            if (this.listBunit != null) {
                this.listBunit.forEach(c => {
                    c.rowSelected = !this.selectedAll;
                });
            }
        },
        showInfodlg(bunit) {
            this.bunit = bunit;
            this.infodlg = true;
        },
    },
    async created() {
        await this.getLoudongs();
        await this.getSstype();
        await this.search();
    }
};

</script>
