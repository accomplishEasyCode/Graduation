<template>

    <div class="panel panel-default">
        <div class="panel-heading">
           寝室评分登记向导

        </div>

        <!-- 搜索控件开始 -->
        <div class="search-options">


            <table cellspacing="0" width="100%">
                <tbody>

                <tr>
                    <td>

                        学期周:<div style="width: 120px;display: inline-block;">
                        <el-select v-model="weekid">

                            <el-option v-for="item in listTermweek" :value="item.id" :label="item.name" :key="item.id">

                            </el-option>
                        </el-select>
                    </div>


                        <el-button @click="searchHandler" type="danger">
                            <i class="el-icon-search"></i>搜索
                        </el-button>
                    </td>
                </tr>
                </tbody>
            </table>

        </div>


        <table width="100%" border="0" cellspacing="0"
               cellpadding="0" class="ui-record-table">
            <thead>
            <tr>

                <th><b>宿舍楼</b></th>

                <th><b>寝室</b></th>

            </tr>
            </thead>
            <tbody>

            <tr v-if="listHygiene.length==0">
                <td colspan="20">没有宿舍楼信息</td>
            </tr>


            <tr v-for="sushelou in listHygiene">

                <td>
                    <div class="cst-group-btn">
                        <div class="it">
                            {{sushelou.name}}
                        </div>

                    </div>


                </td>

                <td>
                    <div class="subtype-list">

                        <div v-for="qs in sushelou.hygienes" class="lm-item">
                            <div class="text ">{{qs.name}}</div>
                            <div>
                                <span style="cursor:pointer;" @click="showPingfen(qs)" v-if="qs.weekname==null"><i class="fa fa-plus"></i>评分</span>
                                <span style="color:red;font-weight:600;font-size: 16px; " v-else>{{qs.score}}分</span>
                            </div>
                        </div>

                    </div>

                    <div v-if="sushelou.hygienes!=null&&sushelou.hygienes.length==0">
                        暂无宿舍信息
                    </div>

                </td>

            </tr>

            </tbody>
        </table>


        <el-dialog title="寝室卫生评分" :visible.sync="openDlg">

            <table class="smart-table" border="1">
                <tr>
                    <td width="10%" align="right">寝室</td>
                    <td>{{qs.ldname}}{{qs.name}}</td>

                </tr>

                <tr>
                    <td width="10%" align="right" >得分:</td>
                    <td width="*">
                        <input name="score" v-model="score"  placeholder="得分" v-validate="{required:true,number:true,messages:{required:'请输入得分',number:'请输入正确的分数'}}"  class="input-txt" type="text"   />

                    </td>
                </tr>
            </table>

            <div slot="footer" class="dialog-footer">
                <el-button @click="openDlg = false">取 消</el-button>
                <el-button @click="submitHandler" type="primary" >确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    import  {VueUtil} from "../util/vueutil";

    export default {
        name: "pingfenguide",
        data() {
            return {
                "name": "",
                weekid:1,
                qs:{},
                score:80,
                openDlg:false,
                pfren:this.accountInfo.username,
                hostHead: this.$app.contextPath,
                listTermweek: [],
                listHygiene:[]
            }

        },
        props:{
            accountInfo:{
                type:Object
            }
        },
        methods: {

            async getWeeks() {
                let url = "admin/termweek/list";
                let util = new VueUtil(this);

                let {data:res} = await util.http.post(url, {
                    ispaged:"-1"
                });
                if (res.data != null) {
                    this.listTermweek = res.data;
                }
            },
            async searchHandler(){
                let url = "admin/sushelou/pingfens";
                let util = new VueUtil(this);

                let {data:res} = await util.http.post(url, {
                    weekid:this.weekid
                });
                if (res.data != null) {
                    this.listHygiene = res.data;
                }
            },
            async showPingfen(curQs){
                this.qs=curQs;
                console.log("this.qs=",this.qs);
                this.openDlg=true;
            },
            async submitHandler(){
                let util = new VueUtil(this);
                const  validRes=this.myValidator.valid(this);
                console.log("valRes",validRes);
                if (!validRes)
                    return;
                let url="admin/qspingfen/save";
                let {data:res} = await util.http.post(url, {
                    weekid:this.weekid,
                    score:this.score,
                    qsid:this.qs.id,
                    pfren:this.pfren
                });
                if (res!=null&&res.stateCode<0){
                    util.message.error(res.des);
                    return ;
                }
                this.openDlg = false;
                this.score = 70;
                util.message.success("评分完成");
                this.searchHandler();

            }

        },
        async created() {
           await this.getWeeks();
           await this.searchHandler();
        }
    }
</script>

<style scoped>

</style>
