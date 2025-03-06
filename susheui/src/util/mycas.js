
let mycas={
    huiyuan_key:"huiyuan",
    zhiyuan_key:"zhiyuan",
    student_key:"student",
    users_key:"users",
    login(key,data){
        let info=data;
        if(typeof data === 'object') {
            console.log("object");
            info = JSON.stringify(data);
        }
       window.sessionStorage.setItem(key,info);

    },
    getAccount(key){
        let strData=window.sessionStorage.getItem(key);
        if(strData!=null)
            return JSON.parse(strData);
        return null;
    },

    isLogin(key){
        return this.getAccount(key)!=null;
    },

    logout(key){
        if(window.sessionStorage.getItem(key)!=null)
            window.sessionStorage.removeItem(key);
    }


}


export  default  mycas;
