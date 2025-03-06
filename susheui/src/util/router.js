import Vue from 'vue'
import VueRouter from 'vue-router'
import fa from "element-ui/src/locale/lang/fa";

Vue.use(VueRouter)


/**
 *
 * @param page 必选
 * @param path 可选
 * @returns {{path: string, component: (function(): *), name: string}}
 */
function routeItem(page,path){
    if(path==null){
        path=page;
    }
    let startPos=page.toLowerCase().lastIndexOf("/");
    let endPos=page.toLowerCase().indexOf(".vue");
    if(startPos==-1)
        return{
            path:`${path}`,
            name: `${page}`,
            meta:{
                noCache:true
            },
            component: ()=>import(`@/${page}`)
        };
    let name=page.toLowerCase();
    if(endPos!=-1)
          name=page.toLowerCase().substring(0,endPos);
    let url=name.substring(1);
    //console.log("url=",url);
    let t="@/admin/login";
    return {
        path:`${path}`,
        name: `${path}`,
        component: ()=>import(`@/${url}.vue`),
        meta: {
            noCache:true
        }

    };

}




const adminRoutes = [
    {
        path: "/",
        name: "Index",
        redirect: "/admin/login"
    },
    {
        path: "/register",
        name: "register",
        component: ()=>import("@/register")
    },
    routeItem("/admin/login"),
    {
        path: "/admin",
        name: "AdminIndex",
        component: () => import('@/admin/index.vue'),
        redirect: "/admin/index",
        children: [
            routeItem("/admin/accountinfo","index"),
            routeItem("/admin/usersmanager","usersmanager"),
            routeItem("/admin/usersadd","usersadd"),
            routeItem("/admin/accountinfo","accountinfo"),
            routeItem("/admin/modifypw","modifypw"),
            routeItem("/admin/modifyinfo","modifyinfo"),
            routeItem("/admin/sysrolemanager","sysrolemanager"),
            routeItem("/admin/sysroleadd","sysroleadd"),
            routeItem("/admin/huiyuanmanager","huiyuanmanager"),
            routeItem("/admin/huiyuanadd","huiyuanadd"),
            routeItem("/admin/noticemanager","noticemanager"),
            routeItem("/admin/noticeadd","noticeadd"),
            routeItem("/admin/leavewordmanager","leavewordmanager"),
            routeItem("/admin/xinximanager","xinximanager"),
            routeItem("/admin/xinxiadd","xinxiadd"),
            routeItem("/admin/lanmumanager","lanmumanager"),
            routeItem("/admin/jiaodiantumanager","jiaodiantumanager"),
            routeItem("/admin/jiaodiantuadd","jiaodiantuadd"),
            routeItem("/admin/loudongmanager","loudongmanager"),
            routeItem("/admin/loudongadd","loudongadd"),
            routeItem("/admin/banjimanager","banjimanager"),
            routeItem("/admin/termweekmanager","termweekmanager"),
            routeItem("/admin/pingfenguide","pingfenguide"),
            routeItem("/admin/baoxiumanager","baoxiumanager"),
            routeItem("/admin/pfstat","pfstat"),
            routeItem("/admin/tiaosumanager","tiaosumanager"),
            routeItem("/admin/ssstat","ssstat"),
            routeItem("/admin/banjimanager","banjimanager"),
            routeItem("/admin/bunitmanager","bunitmanager"),
            routeItem("/admin/bunitadd","bunitadd"),
            routeItem("/admin/sstypemanager","sstypemanager"),
            routeItem("/admin/sstypeadd","sstypeadd"),
            routeItem("/admin/chafangmanager","chafangmanager"),
            routeItem("/admin/chafangadd","chafangadd"),
            routeItem("/admin/shangpinmanager","shangpinmanager"),
            routeItem("/admin/shangpinadd","shangpinadd"),
            routeItem("/admin/supplyormanager","supplyormanager"),
            routeItem("/admin/supplyoradd","supplyoradd"),
            routeItem("/admin/spcategorymanager","spcategorymanager"),
            routeItem("/admin/caigoumanager","caigoumanager"),
            routeItem("/admin/spcategoryadd","spcategoryadd"),
            routeItem("/admin/appointordermanager","appointordermanager"),

        ],

    }
];

const huiyuanRoutes=[
    {
        path: "/huiyuan",
        name: "huiyuanIndex",
        redirect: "/huiyuan/index",
        component:()=>import("@/huiyuan/index"),
        children:[
            routeItem("/huiyuan/accountinfo","index"),
            routeItem("/huiyuan/accountinfo","accountinfo"),
            routeItem("/huiyuan/modifypw","modifypw"),
            routeItem("/huiyuan/modifypaypw","modifypaypw"),
            routeItem("/huiyuan/yue","yue"),
            routeItem("/huiyuan/modifyinfo","modifyinfo"),

            routeItem("/huiyuan/mydes","mydes"),

            routeItem("/huiyuan/xinxilist","xinxilist"),
            routeItem("/huiyuan/xinxiinfo","xinxiinfo"),
            routeItem("/huiyuan/baoxiumanager","baoxiumanager"),
            routeItem("/huiyuan/baoxiuadd","baoxiuadd"),
            routeItem("/huiyuan/qspingfenmanager","qspingfenmanager"),
            routeItem("/huiyuan/tiaosuadd","tiaosuadd"),
            routeItem("/huiyuan/tiaosumanager","tiaosumanager"),
            routeItem("/huiyuan/propertycostmanager","propertycostmanager"),
            routeItem("/huiyuan/chat","chat"),
            routeItem("/huiyuan/laifangmanager","laifangmanager"),
            routeItem("/huiyuan/healdatamanager","healdatamanager"),
            routeItem("/huiyuan/healdataadd","healdataadd"),
            routeItem("/huiyuan/chafangmanager","chafangmanager"),
            routeItem("/huiyuan/chafangmanager","chafangmanager"),
            routeItem("/huiyuan/noticelist","noticelist"),
            routeItem("/huiyuan/noticeinfo","noticeinfo"),
            routeItem("/huiyuan/shangpinlist","shangpinlist"),
            routeItem("/huiyuan/shangpininfo","shangpininfo"),
            routeItem("/huiyuan/appointordermanager","appointordermanager"),

        ]
    }
];


const router = new VueRouter({
    mode: 'hash',//history
    base: process.env.BASE_URL

})

router.addRoutes(adminRoutes);
router.addRoutes(huiyuanRoutes);



router.beforeEach((to, from, next) => {
    //console.log('to.path=' + to.path)
    document.title="宿舍管理系统";
    if (to.path === '/admin/login') {
        return next()
    }
    let path=to.path;
    let $this=router.app;
    if(path.startsWith("/admin")){
        let strUsers = window.sessionStorage.getItem($this.$app.mycas.users_key);
        if (strUsers == null) {
            return next('/admin/login')
        }
    }
    if(path.startsWith("/huiyuan")){
        let strStudent = window.sessionStorage.getItem($this.$app.mycas.huiyuan_key);
        if (strStudent == null) {
            return next('/admin/login')
        }
    }


    // let adminUser=JSON.parse(adminUserStr);
    // if(!adminUser.token)
    //     return next("/admin/login");
    next();
});

export default router
