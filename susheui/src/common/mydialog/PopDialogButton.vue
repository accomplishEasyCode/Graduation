<template>
    <div :class="{inline:inline}">
        <span  @click="popHandler"  :class="cssClass">
            <slot></slot>
        </span>

        <mydialog :width="width" :show.sync="showDlg" @close="closeHandler"  :url="url"  :xdata="xdata" :title="title" ></mydialog>
    </div>
</template>

<script>
    import mydialog from "./mydialog";
    export default {
        name: "PopDialogButton",
        data(){
            return {
               showDlg:false
            }
        },
        methods:{
           popHandler(){
               this.showDlg=this.visible;
           },
           closeHandler(val) {
               this.$emit("refresh", true);
           }
        },
        props:{
           url:{
              type:String,
              require:true
           },
           visible:{
               type:Boolean,
               default:true
           },
           inline:{
              type:Boolean,
              default:true,
            },
            xdata:{
                type:Object
            },
            title: {
                type: String,
                default: "标题"
            },
            cssClass:{
               type:String
            },
            width:{
                type:String,
                default: "80%"
            },
        },
        components:{
            mydialog
        },
        watch:{
            visible(newVal,oldVal){
                this.showDlg=newVal;
            }
        }
    }
</script>

<style scoped>
   .inline{
       display: inline-block;
   }
</style>
