<template>

    <div class="panel panel-default">
        <div class="panel-heading">

            <div v-if="actiontype=='save'"><i class="el-icon-plus"></i>新建宿舍</div>
            <div v-if="actiontype=='update'"><i class="el-icon-edit"></i>编辑宿舍</div>

        </div>


        <table class="smart-table" border="1" cellspacing="1" width="100%">


            <tr>
                <td width="10%" class="tlabel" align="right">宿舍编号:</td>
                <td width="*">
                    <input name="name" v-model="bunit.name" placeholder="宿舍编号"
                           v-validate="{required:true,messages:{required:'请输入宿舍编号'}}" class="input-txt"
                           type="text"/>


                </td>
            </tr>


            <tr>
                <td width="10%" class="tlabel" align="right">宿舍楼:</td>
                <td width="*">
                    <el-select v-model="bunit.ldid">
                        <el-option v-for="item in listLoudong" :value="item.id" :label="item.name"></el-option>
                    </el-select>


                </td>
            </tr>


            <tr>
                <td  class="tlabel" align="right" >所属班级:</td>
                <td width="*">
                    <el-select  v-model="bunit.bjid">
                        <el-option v-for="item in listBanji" :value="item.id" :label="item.name"></el-option>
                    </el-select>

                </td>
            </tr>

            <tr>
                <td width="10%" class="tlabel" align="right">宿舍类型:</td>
                <td width="*">
                    <el-select v-model="bunit.typeid">
                        <el-option v-for="item in listSstype" :value="item.id" :label="item.name"></el-option>
                    </el-select>


                </td>
            </tr>


            <tr>
                <td width="10%" class="tlabel" align="right">洗衣机:</td>
                <td width="*">

                    <el-radio-group v-model="bunit.haswm">
                        <el-radio-button :label="1">有</el-radio-button>
                        <el-radio-button :label="0">无</el-radio-button>
                    </el-radio-group>


                </td>
            </tr>


            <tr>
                <td width="10%" class="tlabel" align="right">空调:</td>
                <td width="*">
                    <el-radio-group v-model="bunit.hasac">
                        <el-radio-button :label="1">有</el-radio-button>
                        <el-radio-button :label="0">无</el-radio-button>
                    </el-radio-group>



                </td>
            </tr>


            <tr>
                <td align="right">说明:</td>
                <td colspan="3">
                    <textarea name="des" rows="4" v-model="bunit.des" style="width:48%;height:100px;"
                              v-validate="{required:true}"></textarea>

                </td>
            </tr>


            <tr>
                <td colspan="4">

                    <el-button type="primary" icon="el-icon-check" @click="submitHandler">提交</el-button>

                </td>
            </tr>


        </table>


    </div>

</template>

<script type="text/javascript">

import {VueUtil} from "../util/vueutil";


export default {
    name: "bunitadd",
    data() {
        return {
            actiontype: 'save',
            hostHead: this.$app.contextPath,
            fileList: [],
            errors: {},
            listSstype:[],
            listLoudong:[],
            listBanji:[],
            "bunit": {
                "name": "",
                "ldid": "",
                "bjid": "",
                "cicount": "",
                "typeid": 1,
                "haswm": 1,
                "hasac": 1,
                "des": "",
            },
        }
    },
    methods: {
        //begin load
        async load() {
            let id = this.$route.query.id;
            if (id != null) {
                this.actiontype = "update";
                this.pageTitle = "编辑宿舍";
                let url = "admin/bunit/load";
                let util = new VueUtil(this);
                let {data: res} = await util.http.post(url, {id: id});
                console.log("res", res);
                if (res != null && res.data != null) {
                    this.bunit = res.data;
                }
            }
        },//end load
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
        async getBanjis() {
            let url = "admin/banji/list";
            let {data: res} = await this.$http.post(url,{"ispaged":'-1'});
            if (res.stateCode < 0) {
                this.$message.error(res.des);
                return;
            }
            this.listBanji = res.data;
            if(this.listBanji!=null&&this.listBanji.length>0){
                this.bunit.bjid=this.listBanji[0].id;
            }
        },
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
        async submitHandler() {

            let defaultOptions = {
                url: "admin/bunit/save",
                actionTip: "宿舍新增成功"
            };
            if (this.actiontype == "update") {
                defaultOptions.url = "admin/bunit/update";
                defaultOptions.actionTip = "宿舍更新成功";
            }
            const validRes = this.myValidator.valid(this);
            console.log("valRes", validRes);
            if (!validRes)
                return;


            let util = new VueUtil(this);
            let params = {...this.bunit};
            let {data: res} = await util.http.post(defaultOptions.url, params);
            if (res.stateCode <= 0) {
                util.alert(res.des, '系统提示', {
                    confirmButtonText: '确定'
                });
                return;
            }
            util.message({
                message: defaultOptions.actionTip,
                type: 'success',
                duration: 2000
            });
            this.$router.push("/admin/bunitmanager");

        },


    },
    async created() {
       await this.getSstype();
       await this.getLoudongs();
       await this.getBanjis();
       await this.load();
    }

}

</script>
