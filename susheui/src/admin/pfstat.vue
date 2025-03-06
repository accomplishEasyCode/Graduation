<template>

    <div class="panel panel-default">
        <div class="panel-heading">
            卫生统计

        </div>
        <div class="panel-body">

        <div class="search-options">


            <table cellspacing="0" width="100%">
                <tbody>

                <tr>
                    <td style="text-align:left; ">

                        学期周:<div style="width: 120px;display: inline-block;">
                        <el-select v-model="weekid">
                            <el-option :value="-1" label="所有"></el-option>
                            <el-option v-for="item in listTermweek" :value="item.id" :label="item.name" :key="item.id">

                            </el-option>
                        </el-select>
                    </div>
                        <el-button @click="search" type="success">
                            <i class="el-icon-search"></i>搜索
                        </el-button>
                    </td>
                </tr>
                </tbody>
            </table>

        </div>


        <div style="width:100%;">

            <div class="panel panel-default">
                <div class="panel-heading">卫生平均成绩</div>
                <div id="divLbAvgscore" style="height:250px;width: 100%;"></div>
                <table class="ui-record-table" width="100%" border="0" cellspacing="0"
                       cellpadding="0">
                    <thead>
                      <tr>
                          <th>楼栋</th>
                          <th>平均得分</th>

                      </tr>

                    </thead>
                    <tbody>
                        <tr v-for="item in listAvgscore ">
                            <td class="first_td">{{item.name}}</td>
                            <td>{{item.value.toFixed(2)}}分</td>
                        </tr>
                        <tr v-if="listAvgscore.length<=0">
                            <td colspan="2">暂无相关信息</td>
                        </tr>
                    </tbody>
                </table>

            </div>

            <div class="panel panel-default">
                <div class="panel panel-heading">卫生评分分布</div>
                <div id="divPieScorescope" style="height:250px;width: 100%;"></div>
                <table  class="ui-record-table" width="100%" border="0" cellspacing="0"
                        cellpadding="0">
                    <thead>
                    <tr>
                        <th >名称</th>
                        <th>说明</th>

                    </tr>
                    </thead>
                    <tbody>

                        <tr v-for="item in listScorescope ">
                            <td class="first_td">{{item.name}}</td>
                            <td>{{item.value}}个</td>
                        </tr>
                        <tr v-if="listScorescope.length<=0">
                            <td colspan="2">暂无相关信息</td>
                        </tr>
                    </tbody>



                </table>

            </div>
        </div>

    </div>
    </div>
</template>

<script>
    import echartPlus from "../util/echarts/echart-plus"
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "pftongji",
        data(){
            return {
                listTermweek: [],
                listAvgscore: [],
                listScorescope: [],
                weekid: 1
            }
        },
        async created(){
            await this.getWeeks();
            await this.search();
        },
        methods:{

            async getWeeks() {
                let url = "admin/termweek/list";
                let util = new VueUtil(this);
                console.log("this.pageindex=" + this.pageindex);
                let {data:res} = await util.http.post(url, {
                    ispaged:"-1"
                });
                if (res.data != null) {
                    this.listTermweek = res.data;
                    if (this.listTermweek!=null&&this.listTermweek.length>0)
                        this.weekid=this.listTermweek[0].id;
                }
            },
            search(){
                this.getAvgscore({weekid:this.weekid});
                this.getScorescope({weekid:this.weekid})
            },
            getAvgscore(options){

                let params={};
                Object.assign(params,options);
                let url="admin/chengji/avgscore";
                this.$http.post(url,params).then(res=>{
                    console.log(res.data);
                    if(res.data!=null) {
                        this.listAvgscore = res.data.data;
                        echartPlus.echartBar({
                            data: this.listAvgscore,
                            dom: document.querySelector("#divLbAvgscore"),
                            title: "平均成绩分布"
                        });
                    }
                });

            },
            getScorescope(options){
                let params={};
                Object.assign(params,options);
                let url="admin/chengji/scorescope";
                this.$http.post(url,params).then(res=>{
                    console.log(res.data);
                    if(res.data!=null) {
                        this.listScorescope = res.data.data;
                        echartPlus.echartPie({
                            data: this.listScorescope,
                            dom: document.querySelector("#divPieScorescope"),
                            title: "寝室评分分布"
                        });
                    }
                });

            }
        }//end methods
    }
</script>

<style scoped>

</style>
