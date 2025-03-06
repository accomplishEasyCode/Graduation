<template>
    <div class="wrapper" >
        <div  class="carousel-con">
            <el-carousel  height="720px" :interval="5000" arrow="always">
                <el-carousel-item v-for="(jdt,index) in listJiaodiantu" :key="index">

                    <img class="image" :src="hostHead+jdt.tupian " />

                </el-carousel-item>
            </el-carousel>
        </div>
        <div class="login-content">
            <div style="margin-top:120px;">
                <div class="row align-items-center justify-content-center h-100">
                    <div class="col-md-10">
                        <div class="card p-3">
                            <div class="card-body">

                                <h3 class="mb-3 font-weight-bold text-center">宿舍管理系统</h3>
                                <p class="text-center text-secondary mb-4"></p>

                                <div class="mb-5">
                                    <p class="line-around text-secondary mb-0"><span class="line-around-1">用户登录</span></p>
                                </div>

                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="form-group">
                                                <label class="text-secondary">用户名</label>
                                                <input class="form-control" v-model="username" tabindex="1" type="text" placeholder="请输入账户">
                                            </div>
                                        </div>
                                        <div class="col-lg-12 mt-2">
                                            <div class="form-group">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <label class="text-secondary">密码</label>

                                                </div>
                                                <input class="form-control" v-model="password" tabindex="2" type="password" placeholder="输入密码">
                                            </div>
                                        </div>

                                        <div class="col-lg-12 mt-2">
                                            <div class="form-group">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <label class="text-secondary">用户类型</label>
                                                </div>
                                                <el-radio-group tabindex="3" v-model="usertype">
                                                    <el-radio-button :label="4">学生</el-radio-button>
                                                    <el-radio-button :label="2">宿管员</el-radio-button>
                                                    <el-radio-button :label="1">管理员</el-radio-button>

                                                </el-radio-group>
                                            </div>
                                        </div>
                                    </div>
                                    <el-button @click="login" type="primary">登录</el-button>
                                    <div class="col-lg-12 mt-3">
                                        <p class="mb-0 text-center">
                                             <router-link to="/register">注册</router-link>
                                        </p>
                                    </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>


        </div>
    </div>
</template>
<script>
    export default {
        data () {
            return {

                hostHead:this.$app.contextPath,
                 username: '',
                 password: '',
                 usertype:1,
                listJiaodiantu:[],

            }
        },
        methods: {
           async login () {

                     console.log("登录");
                     let {data:res} = await this.$http.post('/admin/login', {
                            username: this.username,
                            password: this.password,
                            usertype: this.usertype
                     });
                    if(res!=null&&res.stateCode<=0) {
                        this.$alert(res.des, "系统提示", {
                            confirmButtonText: "确定"
                        });
                        return ;
                    }
                    console.log("服务器返回",res);
                    if(this.usertype==1||this.usertype==2||this.usertype==3) {
                        let key=this.$app.mycas.users_key;
                        console.log("服务端返回数据",res.data);
                        this.$app.mycas.login(key, res.data);
                         window.sessionStorage.removeItem(this.$app.mycas.huiyuan_key);

                        this.$router.push("/admin/index");
                    }
                    if(this.usertype==4){
                        let key=this.$app.mycas.huiyuan_key;
                        this.$app.mycas.login(key, res.data);
                        window.sessionStorage.removeItem(this.$app.mycas.users_key);
                        this.$router.push("/huiyuan/index");
                    }



            },
            getJiaodiantu() {
                let url = "/admin/jiaodiantu/list";
                this.$http.post(url,{"ispaged":"-1"}).then(res => {
                    //console.log(res.data);
                    this.listJiaodiantu = res.data.data;

                });
            },

        },
        created() {
            this.getJiaodiantu();
        }

    }
</script>
<style scoped>
     @import "css/login.css";



     .carousel-con{
         position: absolute;
         left: 0;
         top:0;
         right: 0;
         width: 100%;
         height: 100%;
         background: #0D8BBD;

     }
     .carousel-inner {
         width:100%;
         height: 100%;
         /*height:580px;*/
     }
     .carousel-inner img{
         width:100%;
         height:100% !important;
     }


     .el-carousel__item{
         width: 100%;
         height: 100%;

     }
     .el-carousel__item a{
         width: 100%;
         height: 100%;
         display: inline-block;
     }
     .el-carousel__item .image {
         width: 100%;
         height: 100%;
         /*min-height: 700px;*/
     }
     .el-carousel__item:nth-child(2n) {
         background-color: #99a9bf;
     }
     .el-carousel__item:nth-child(2n+1) {
         background-color: #d3dce6;
     }
</style>
