<template>
    <div class="mypanel">
        <div class="hd">

            账户信息

        </div>
        <div class="bd">
            <table cellpadding="0" cellspacing="1" border="1" class="smart-table" width="100%">


                <tr>
                    <td class="tlabel" align="right">用户名:</td>
                    <td>
                        {{users.username}}
                    </td>
                    <td colspan="2" rowspan="6">
                        <img width="200" height="200" :src="hostHead+users.xiangpian"/>
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">姓名:</td>
                    <td>
                        {{users.realname}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">性别:</td>
                    <td>
                        {{users.sex}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">电话:</td>
                    <td>
                        {{users.tel}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">昵称:</td>
                    <td>
                        {{users.nickname}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">创建时间:</td>
                    <td>
                        {{users.createtime}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">登录次数:</td>
                    <td>
                        {{users.logtimes}}
                    </td>

                    <td class="tlabel" align="right">邮箱:</td>
                    <td>
                        {{users.email}}
                    </td>
                </tr>


                <tr>
                    <td class="tlabel" align="right">创建人:</td>
                    <td>
                        {{users.creator}}
                    </td>

                    <td class="tlabel" align="right">角色:</td>
                    <td>
                        {{users.rolename}}
                    </td>
                </tr>


            </table>
        </div>
    </div>
</template>

<script>
    import {VueUtil} from "../util/vueutil";

    export default {
        name: "accountinfo",
        data() {
            return {
                actiontype: 'save',
                hostHead: this.$app.contextPath,
                "users": {},
            }
        },
        props:{
            accountInfo: {type:Object}
        },
        methods: {
            //begin load
            async load() {
                let id = this.accountInfo.id;
                let util = new VueUtil(this);
                if (id != "") {
                    let url = "admin/users/load";
                    let {data: res} = await util.http.post(url, {id: id});
                    console.log("res", res);
                    if (res != null && res.data != null)
                        this.users = res.data;
                }
            },//end load

        },
        created() {
            this.load();
        }
    }
</script>

<style scoped>

</style>
