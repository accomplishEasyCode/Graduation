<template>
    <div   class="wrap  round-block">


        <div  class="white-paper">
            <div class="show-details">
                <div class="picture-box">
                    <img v-if="xinxi.images!=null" :src="hostHead+xinxi.images[0]" />

                </div>
                <div class="text-box">
                    <div class="title">{{xinxi.title}}</div>
                    <div class="sub-title"></div>
                    <div>
                        <ul>


                            <li><strong>发布时间:</strong>{{xinxi.pubtime}}</li>
                            <li><strong>点击次数:</strong> {{xinxi.clickcount}}
                            </li>
                            <li><strong>所属分类:</strong> {{xinxi.lmname}}</li>



                        </ul>
                    </div>

                </div>
            </div>

            <div v-html="xinxi.dcontent" class="brief-content">

            </div>

        </div>



    </div>
</template>

<script>
    import  {VueUtil} from "../util/vueutil";

    export default {
        name: "xinxiinfo",
        data(){
            return {
                hostHead:this.$app.contextPath,
                listHotXinxi:[],
                id:0,
                openLogin:false,
                hyid:this.accountInfo.id,
                xinxi:{},
                swiperOptions: {
                    pagination: {
                        el: '.swiper-pagination',    //与slot="pagination"处 class 一致
                        clickable: true            //轮播按钮支持点击
                    },
                    navigation: { // 左右箭头
                        nextEl:'.swiper-button-next',
                        prevEl:'.swiper-button-prev'
                    },
                    loop: true,		// 循环回路
                    slidesPerView: "auto",	// 设置slider容器能够同时显示的slides数量; 设置为auto则自动根据slides的宽度来设定数量
                    centeredSlides: true,	// 设定为true时，active slide会居中，而不是默认状态下的居左
                    spaceBetween: 30

                },

            }
        },
        methods:{

            async getInfo(){
                this.id=this.$route.query.id;
                let {data:res}=await this.$http.post("admin/xinxi/info",{id:this.id});
                if(res!=null&&res.stateCode>0) {
                    this.xinxi = res.data;
                    console.log(this.xinxi);

                }
            },


        },
        created () {

            this.getInfo();
        },
        props:{
            accountInfo:{
                type:Object
            }
        }
    }
</script>

<style scoped>

</style>
