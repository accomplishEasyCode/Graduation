<template>
    <div>
        <phead></phead>
        <div style="min-height:884px;" class="pbody">
            <div  class="sidebar">
                <div class="addmenu">系统功能</div>
                <my-menu >


                    <my-submenu  v-if="accountInfo.roleid==2"  icon="flaticon-381-settings-2" text="订水管理">

                        <my-submen-item  url="/admin/shangpinmanager">宿舍服务</my-submen-item>
                        <my-submen-item  url="/admin/shangpinadd">新增服务</my-submen-item>

                        <my-submen-item  url="/admin/spcategorymanager">服务类型管理</my-submen-item>

                        <my-submen-item url="/admin/appointordermanager">订单管理</my-submen-item>


                    </my-submenu>

                    <my-submenu  v-if="accountInfo.roleid==2"  icon="flaticon-381-settings-2" text="宿舍管理">

                        <my-submen-item  url="/admin/huiyuanmanager">学生管理</my-submen-item>

                        <my-submen-item url="/admin/loudongmanager">宿舍楼管理</my-submen-item>
                        <my-submen-item  url="/admin/bunitmanager">宿舍管理</my-submen-item>
                        <my-submen-item  url="/admin/sstypemanager">宿舍类别管理</my-submen-item>
                        <my-submen-item  url="/admin/tiaosumanager">宿舍申请管理</my-submen-item>
                        <my-submen-item   url="/admin/baoxiumanager">报修管理</my-submen-item>
                        <my-submen-item  url="/admin/pingfenguide">宿舍评分</my-submen-item>
                        <my-submen-item  url="/admin/pfstat">评分统计</my-submen-item>
                        <!-- <my-submen-item  url="/admin/chafangmanager">查房管理</my-submen-item>
                        <my-submen-item  url="/admin/chafangadd">查房登记</my-submen-item> -->

                    </my-submenu>


                    <my-submenu   icon="flaticon-381-layer-1" text="基础信息">
                        <my-submen-item  url="/admin/ssstat">入住统计</my-submen-item>

                        <my-submen-item v-if="accountInfo.roleid==2" url="/admin/lanmumanager">栏目管理</my-submen-item>
                        <my-submen-item v-if="accountInfo.roleid==2" url="/admin/xinximanager">资讯管理</my-submen-item>
                        <my-submen-item v-if="accountInfo.roleid==2" url="/admin/noticemanager">系统公告</my-submen-item>
                        <my-submen-item v-if="accountInfo.roleid==2" url="/admin/xinxiadd">发布资讯</my-submen-item>
                        <my-submen-item v-if="accountInfo.roleid==2" url="/admin/jiaodiantumanager">轮播图设置</my-submen-item>
                        <my-submen-item v-if="accountInfo.roleid==2" url="/admin/leavewordmanager">留言管理</my-submen-item>

                    </my-submenu>

                    <my-submenu v-if="accountInfo.roleid==1" icon="flaticon-381-layer-1" text="用户管理">
                        <my-submen-item url="/admin/huiyuanmanager">学生管理</my-submen-item>
                        <my-submen-item url="/admin/banjimanager">班级管理</my-submen-item>
                        <my-submen-item url="/admin/usersmanager">系统用户管理</my-submen-item>
                        <my-submen-item url="/admin/sysrolemanager">角色管理</my-submen-item>
                        <my-submen-item url="/admin/usersadd">新增管理用户</my-submen-item>

                    </my-submenu>

                    <my-submenu icon="flaticon-381-television" text="账户设置">
                        <my-submen-item url="/admin/accountinfo">账户信息</my-submen-item>
                        <my-submen-item url="/admin/modifypw">密码设置</my-submen-item>
                        <my-submen-item url="/admin/modifyinfo">编辑账户</my-submen-item>

                    </my-submenu>



                </my-menu>

            </div>
            <div class="page-wrapper">
                <router-view :accountInfo="accountInfo" />
            </div>
        </div>
    </div>
</template>

<script>
    import Phead from "./phead";
    import MyMenu from "../common/mymenu/MyMenu";
    import MySubmenu from "../common/mymenu/MySubmenu";
    import MySubmenItem from "../common/mymenu/MySubmenItem";

    export default {
        name: "index",
        data(){
           return {
              accountInfo:{}
           }
        },
        methods:{

        },
        created() {
            this.accountInfo=this.$app.mycas.getAccount(this.$app.mycas.users_key);
        },

        components: {MySubmenItem, MySubmenu, MyMenu, Phead}
    }
</script>

<style scoped>

</style>
