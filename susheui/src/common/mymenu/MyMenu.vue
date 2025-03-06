<template>
    <ul class="mymenu1">
       <slot></slot>
    </ul>
</template>

<script>
    export default {
        name: "MyMenu",
        data(){
            return {
                items:[]
            }
        },
        watch:{
           items:{
               handler(oldVal,newVal){
                  let submenus= this.getSubmenus();
                  console.log("newVal=",newVal);
                  if(newVal!=null){
                      for (let i=0;i<newVal.length;i++){
                          submenus[i].show=newVal[i].show;
                      }
                  }

               },
               immediate: true,
               deep: true
           }
        },
        methods:{
            getSubmenus(){
                let children = [];

                if(children!=null&&this.$children.length>0) {
                    for(let i=0;i<this.$children.length;i++) {
                        let childName = this.$children[i].$options.name;
                        if(childName=='MySubmenu')
                            children.push(this.$children[i]);
                    }
                }
                return children
            }
        }
    }
</script>

<style scoped>

</style>
