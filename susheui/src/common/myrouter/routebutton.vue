
<template>

        <span  :class="'el-button el-button--'+type" @click="routeHandler" >
            <i :class="icon"></i>
            <span v-if="!$slots.default">{{text}}</span>
            <slot></slot>
        </span>

</template>

<script>
    export default {
        name: "route-button",
        data(){
            return {
                show:true
            }
        },
        methods:{
            routeHandler(){
               if(this.$route.path!=this.url)
                   this.$router.push(this.url);
            }
        },
        props:{
           url:{
              required:true,
              type:String
           },
           icon:{
               required: false,
               type:String,
               default:"el-icon-plus"
           },
           text:{
               required:false,
               type:String
           },
           type:{
               required: false,
               type:String,
               default:"primary"
           },
           openType:{
               required: false,
               type:String,
               default:"link"
           }
        }
    }
</script>

<style scoped>

</style>
