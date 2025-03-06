
<template>

        <div  class="hand"   @click="routeHandler">
            <slot></slot>
        </div>

</template>

<script>
    // const toTypes: Array<Function> = [String, Object]
    // const eventTypes: Array<Function> = [String, Array]
    export default {
        name: "route-template",
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
   .hand{
       cursor: pointer;
       display: inline-block;
   }
</style>
