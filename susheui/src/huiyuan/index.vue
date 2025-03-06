<template>
    <div>
        <phead></phead>
        <div style="min-height:884px;" class="pbody">
            <div  class="sidebar">
                <div class="addmenu">系统功能</div>
                <my-menu>
                    <my-submenu icon="flaticon-381-networking" text="与我相关">


                        <my-submen-item url="/huiyuan/tiaosuadd">宿舍申请申请</my-submen-item>
                        <my-submen-item url="/huiyuan/tiaosumanager">宿舍申请记录</my-submen-item>
                        <my-submen-item url="/huiyuan/qspingfenmanager">寝室评分</my-submen-item>
                        <my-submen-item url="/huiyuan/baoxiuadd">报修申请</my-submen-item>
                        <my-submen-item url="/huiyuan/baoxiumanager">我的报修</my-submen-item>
                        <!-- <my-submen-item url="/huiyuan/chafangmanager">查房记录</my-submen-item> -->
                        <my-submen-item url="/huiyuan/shangpinlist">预定服务</my-submen-item>
                        <my-submen-item url="/huiyuan/appointordermanager">我的订单</my-submen-item>

                    </my-submenu>


                    <my-submenu  icon="flaticon-381-layer-1" text="系统资讯">


                        <my-submen-item url="/huiyuan/chat">在线咨询</my-submen-item>
                        <my-submen-item url="/huiyuan/xinxilist">系统资讯</my-submen-item>
                        <my-submen-item url="/huiyuan/noticelist">系统公告</my-submen-item>

                    </my-submenu>




                    <my-submenu icon="flaticon-381-television" text="账户设置">

                        <my-submen-item url="/huiyuan/accountinfo">账户信息</my-submen-item>

                        <my-submen-item url="/huiyuan/modifypw">密码设置</my-submen-item>
                        <my-submen-item url="/huiyuan/modifyinfo">编辑账户</my-submen-item>

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
            this.accountInfo=this.$app.mycas.getAccount(this.$app.mycas.huiyuan_key);
        },

        components: {MySubmenItem, MySubmenu, MyMenu,Phead}
    }
</script>

<style scoped>

</style>
