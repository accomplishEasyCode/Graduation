import {app}  from "./app.js"
import axios  from "axios";
//console.log("native axios=",axios);
axios.defaults.baseURL = app.contextPath;
axios.interceptors.request.use(config=>{
    //console.log(config);
    let adminUserStr=window.sessionStorage.getItem("adminUser");
    if(adminUserStr!=null) {
        let adminUser = JSON.parse(adminUserStr);
        if (adminUser!=null&&adminUser.token != null)
            config.headers.token = adminUser.token;
    }
    //config.data["__mySelf"]="1024";
    // console.log("config",config);
    //console.log("data=",config.data);
    //  if(config.url.indexOf("admin/upload")==-1)
    //     config.data = {
    //         ...config.data,
    //         "__mySelf":"1024",
    //     }
    return config;
});

// module.exports=axios;
export  {axios}







