<template>
    <div class="panel panel-default">

        <div class="panel-heading">
           学生管理
        </div>

        <div class="panel-body">
            <div class="search-options">


                <table cellspacing="0" width="100%">
                    <tbody>

                    <tr>
                        <td>

                            班级:
                            <el-select v-model="bjid">
                                <el-option label="全部" :value="-1"></el-option>
                                <el-option v-for="item in listBanji" :label="item.name" :value="item.id"></el-option>

                            </el-select>

                            学号: <input name="accountname" @keyup.enter="search" v-model="accountname" class="input-txt"
                                       type="text"/>


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

            <table id="huiyuan" width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-record-table">
                <thead>
                <tr>
                    <th>
                        <input type="checkbox" @click="selectedAllHandler" v-model="selectedAll"/>
                    </th>
                    <th><b>学号</b></th>
                    <th><b>姓名</b></th>
                    <th><b>性别</b></th>

                    <th><b>班级</b></th>
                    <th><b>寝室</b></th>

                    <th><b>注册时间</b></th>


                    <th>
                        操作
                    </th>
                </tr>

                </thead>
                <tbody>
                <tr v-for="(huiyuan,index) in  listHuiyuan" :key="huiyuan.id">
                    <td>
                        <input class="check" name="ids" type="checkbox" v-model="huiyuan.rowSelected"/>
                    </td>
                    <td>
                        {{huiyuan.accountname}}
                    </td>
                    <td>
                        {{huiyuan.name}}
                    </td>
                    <td>
                        {{huiyuan.sex}}
                    </td>

                    <td>
                        {{huiyuan.bjname}}
                    </td>
                    <td>
                        <el-tag v-if="huiyuan.state==1" type="danger">待分宿舍</el-tag>
                        <el-tag v-if="huiyuan.state==2" type="success">{{huiyuan.ldname}}--{{huiyuan.unitname}}</el-tag>

                    </td>
                    <td>
                        {{huiyuan.createtime}}
                    </td>

                    <td>
                        <div class="el-button-group">
                            <route-button :url="'/admin/huiyuanadd?id='+huiyuan.id" type="primay" icon="el-icon-edit">编辑</route-button>

                            <span class="el-button el-button--warning" @click="showDlg(huiyuan,'infoDlg')" ><i class="el-icon-info"></i>查看</span>
                        </div>
                    </td>
                </tr>

                <tr v-if="listHuiyuan.length==0">
                    <td colspan="20">
                        没有相关学生信息
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

            <huiyuandetails ref="infoDlg"></huiyuandetails>




        </div>
    </div>
</template>

<script>
    import {VueUtil,ModalDialogUtils} from "../util/vueutil";
    import Huiyuandetails from "@/admin/huiyuandetails";

    export default {
        name: "huiyuanmanager",
        mixins:[ModalDialogUtils],
        components: {Huiyuandetails},
        data(){
            return {
                "accountname":"",
                hostHead:this.$app.contextPath,
                selectedAll:false,
                listHuiyuan:[],
                pageindex:1, //初始页
                pagesize:10,
                total:10,
                spdlg:false,
                infodlg:false,
                "status":2,
                "spdes":"",
                listBanji:[],
                bjid:-1,
                huiyuan:{}
            }

        },
        methods:{
            pagesizeChange: function (pagesize) {
                this.pagesize = pagesize;
            },
            pageindexChange: function(pageindex){
                this.pageindex = pageindex;
                console.log(this.pageindex);
                this.search();
            },
            async search(){
                let url="admin/huiyuan/list";
                let param={
                    currentpageindex:this.pageindex,
                    pagesize:this.pagesize,
                    "accountname":this.accountname,
                };
                if(this.bjid!=-1)
                    param.bjid=this.bjid;

                let util=new VueUtil(this);

                let {data:res}=await util.http.post(url,param);
                console.log("res=",res);
                if(res!=null){
                    let pageInfo=res.data;
                    this.total=pageInfo.total;
                    this.listHuiyuan=pageInfo.list;
                    console.log(pageInfo);
                }
            },
            async deleteRec(){
                let url="admin/huiyuan/delete";
                let util=new VueUtil(this);
                let hasChecked=this.listHuiyuan.some(c=>{
                    return c.rowSelected==true;
                });
                if(!hasChecked){
                    util.alert('请选择需要删除的记录', '系统提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }

                let ids=this.listHuiyuan.filter(c=>c.rowSelected).map(c=>c.id);
                let res= util.confirm('是否要删除数据?', '系统提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(()=>{
                    util.http.post(url, {ids},{emulateJSON:false}).then(res=>{
                        if (res.data != null && res.data.stateCode > 0) {
                            this.search();
                        }
                    });
                }).catch(()=>{});
            },
            selectedAllHandler(){
                console.log(this.selectedAll);
                if(this.listHuiyuan!=null){
                    this.listHuiyuan.forEach(c=>{
                        c.rowSelected=!this.selectedAll;
                    });
                }
            },
            showSpdlg(huiyuan){
                this.huiyuan=huiyuan;
                this.spdlg=true;
            },

            async submitHandler(){

                let util = new VueUtil(this);
                let url="admin/huiyuan/shenhe";
                let {data: res} = await util.http.post(url, {
                    id: this.huiyuan.id,
                    status:this.status,
                    spdes:this.spdes
                });
                if (res != null && res.stateCode<0) {
                    util.message.error(res.des);
                    return;
                }
                this.spdlg=false;
                this.$message.success("成功");
                this.search();
            },
            async getBanjis() {
                let url = "admin/banji/list";
                let param = {
                    "ispaged":"-1"
                };
                let {data: res} = await this.$http.post(url, param);
                if (res != null && res.stateCode < 0) {
                    this.$message.error(res.des);
                    return;
                }
                this.listBanji = res.data;
            }
        },
       async created(){
           await this.getBanjis();
           await this.search();
        }
    }
</script>

<style scoped>

</style>
