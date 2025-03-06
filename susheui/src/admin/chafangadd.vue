<template>

    <div class="mypanel">
        <div class="hd">

            <div v-if="actiontype=='save'">新建查房</div>
            <div v-if="actiontype=='update'">编辑查房</div>

        </div>


        <table class="smart-table" border="1" cellspacing="1" width="100%">


            <tr>
                <td class="tlabel" align="right">学号:</td>
                <td>
                    <el-select v-model="chafang.targetid">
                        <el-option v-for="item in listHuiyuan" :value="item.id" :label="item.accountname+'--'+item.name"></el-option>
                    </el-select>

                </td>
            </tr>


            <tr>
                <td width="10%" class="tlabel" align="right">查房日期:</td>
                <td width="*">
                    <el-date-picker v-model="chafang.cfdate" format="yyyy-MM-dd" value-format="yyyy-MM-dd" type="date"
                                    placeholder="选择日期">
                    </el-date-picker>
                </td>
            </tr>


            <tr>
                <td width="10%" class="tlabel" align="right">结果:</td>
                <td width="*">

					<el-radio-group  v-model="chafang.result">
						<el-radio label="正常"></el-radio>
						<el-radio label="晚归"></el-radio>
						<el-radio label="外宿"></el-radio>
					</el-radio-group>


                </td>
            </tr>


            <tr>
                <td align="right">说明:</td>
                <td colspan="3">
                    <quill-editor v-model="chafang.des" :options="{placeholder: '说明'}"></quill-editor>
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
        name: "chafangadd",
        data() {
            return {
                actiontype: 'save',
                hostHead: this.$app.contextPath,
                fileList: [],
                listHuiyuan:[],
                errors: {},
                "chafang": {
                    "targetid": "",
                    "cfdate": new Date().format("yyyy-MM-dd"),
                    "result": "正常",
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
                    this.pageTitle = "编辑查房";
                    let url = "admin/chafang/load";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null) {
                        this.chafang = res.data;


                    }
                }
            },//end load

            async getHuiyuans() {

                    let url = "admin/huiyuan/list";
                    let util = new VueUtil(this);
                    let {data: res} = await util.http.post(url, {"ispaged": "-1"});
                    console.log("res", res);
                    if(res!=null&&res.stateCode<0){
                        this.$mesages.error(res.des);
                        return;
                    }
                    this.listHuiyuan=res.data;
                    if(this.listHuiyuan!=null&&this.listHuiyuan.length>0){
                        this.chafang.targetid=this.listHuiyuan[0].id;
                    }

            },
            async submitHandler() {

                let defaultOptions = {
                    url: "admin/chafang/save",
                    actionTip: "查房新增成功"
                };
                if (this.actiontype == "update") {
                    defaultOptions.url = "admin/chafang/update";
                    defaultOptions.actionTip = "查房更新成功";
                }
                const validRes = this.myValidator.valid(this);
                console.log("valRes", validRes);
                if (!validRes)
                    return;


                let util = new VueUtil(this);
                let params = {...this.chafang};
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
                this.$router.push("/admin/chafangmanager");

            },


        },
        async created() {
           await this.getHuiyuans();
           await this.load();
        }

    }

</script>
