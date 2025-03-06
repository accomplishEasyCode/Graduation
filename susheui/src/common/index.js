
import Dropdownlist from './dropdownlist'
import Upload from './upload'
import RouteItems from "./myrouter"
import Validate from './myvalidate'

const Controls=[
    Dropdownlist,
    Upload
];
const install=function (Vue,options){
    Controls.forEach(c=>{
        c.install(Vue,options);
        //console.log("注册插件"+c.tagname);
        //Vue.component(c.tagname,c);
    });
    //验证器
    Validate.install(Vue,options);
    //路由器
    RouteItems.install(Vue,options);
}
if(typeof window!='undefined' &&window.Vue){
   install(window.Vue);
}
export  default {
    install,
    Dropdownlist,
    RouteItems,
    Upload,
    Validate
}
