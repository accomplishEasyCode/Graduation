<template>
    <li>
        <span  @click="selectedHandler()" class="itext" >
            <slot></slot>
        </span>
    </li>
</template>

<script>
    import Vue from 'vue'
    import VueRouter from 'vue-router'
    Vue.use(VueRouter)
    export default {
        name: "MySubmenItem",
        methods:{
            selectedHandler(){
                if(this.url!=null&&this.$route.path!=this.url)
                    this.$router.push(this.url);
            },

        },
        props:{

           url:{
               type:String
           }
        }
    }
</script>

<style scoped>

</style>
