<template>

    <div class="mypanel1">

        <div class="hd">
            宿舍统计
        </div>
        <div class="search-options">
            <table cellspacing="0" width="100%">
                <tbody>
                <tr>
                    <td>

                        楼栋:
                        <el-select v-model="ldid">
                            <el-option label="全部" :value="-1"></el-option>
                            <el-option v-for="item in listLoudong" :label="item.name" :value="item.id"></el-option>

                        </el-select>


                        <el-button @click="search" icon="el-icon-search" type="danger">统计</el-button>


                    </td>
                </tr>
                </tbody>
            </table>

        </div>

        <table id="dingdan" width="100%" border="0" cellspacing="0"
               cellpadding="0" class="ui-record-table">
            <thead>
            <tr>
                <th><b>编号</b></th>
                <th><b>宿舍楼</b></th>
                <th><b>寝室数量</b></th>
                <th><b>已入住数</b></th>
                <th><b>未入住数</b></th>
                <th><b>入住率</b></th>
            </tr>
            </thead>
            <tbody>

            <tr v-if="listMap!=null&&listMap.length==0">
                <td colspan="10">没有相关信息</td>
            </tr>


            <tr v-for="(map,index) in listMap ">
                <td>
                    <div class="sequence">{{index+1}}</div>
                </td>


                <td>{{map.ldname}}</td>
                <td>{{map.totalcount}}</td>
                <td>
                    {{map.checkincount}}间
                </td>
                <td>
                    {{map.notcheckincount}}间
                </td>
                <td>
                    {{map.checkincount/map.totalcount}}
                </td>

            </tr>


            </tbody>
        </table>


        <div id="divLbArea" style="height:250px;width: 100%"></div>

        <div id="divLineArea" style="height:350px;width: 100%"></div>


    </div>

</template>

<script>
    import  {VueUtil} from "../util/vueutil";
    import echartPlus from "../util/echarts/echart-plus"

    export default {
        name: "ssstat",
        data() {
            return {

                ldid:-1,
                hostHead: this.$app.contextPath,
                lbData:[],
                pieData:[],
                listMap: [],
                listLoudong:[]
            }

        },
        methods: {

            async search() {
                let url = "admin/loudong/ssstat";
                let param = {

                };
                if(this.ldid!=-1)
                    param.ldid=this.ldid;

                let {data: res} = await this.$http.post(url, param);
                if (res != null && res.stateCode < 0) {
                    this.$message.error(res.des);
                    return;
                }
                this.listMap = res.data;
                if(this.ldid==-1){
                    if(this.listMap!=null){
                        let temdata=[];
                        let  piedata=[];
                        this.listMap.forEach(c=>{
                            temdata.push({name:c.ldname,value:c.checkincount});
                            piedata.push({name:c.ldname,value:c.checkincount/c.totalcount})
                        });

                        echartPlus.echartBar({
                                data:temdata,
                                dom:document.querySelector("#divLbArea"),
                                title:"入住统计",
                                nameField:"name",
                                valueField:"value"
                        });
                        echartPlus.echartPie({
                                data:piedata,
                                dom:document.querySelector("#divLineArea"),
                                title:"入住统计",
                                nameField:"name",
                                valueField:"value"
                        });

                    }
                }
                else{
                    if(this.listMap!=null){
                        let temdata=[];
                        let  piedata=[];
                        this.listMap.forEach(c=>{
                            temdata.push({name:"已入住数量",value:c.checkincount});
                            temdata.push({name:"未入住数量",value:c.notcheckincount});
                            piedata.push({name:"已入住数量",value:c.checkincount})
                            piedata.push({name:"未入住数量",value:c.notcheckincount})
                        });

                        echartPlus.echartBar({
                            data:temdata,
                            dom:document.querySelector("#divLbArea"),
                            title:"入住统计",
                            nameField:"name",
                            valueField:"value"
                        });
                        echartPlus.echartPie({
                            data:piedata,
                            dom:document.querySelector("#divLineArea"),
                            title:"入住统计",
                            nameField:"name",
                            valueField:"value"
                        });

                    }
                }




            },
            async getLoudongs() {
                let url = "admin/loudong/list";
                let param = {
                  "ispaged":"-1"
                };
                let {data: res} = await this.$http.post(url, param);
                if (res != null && res.stateCode < 0) {
                    this.$message.error(res.des);
                    return;
                }
                this.listLoudong = res.data;
            }

        },
        created() {
             this.getLoudongs();
        }
    }
</script>

<style scoped>

</style>
