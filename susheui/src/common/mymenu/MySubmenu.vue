<template>

    <li >
                <span @click="collapseHandler()" :class="{itext:true,'has-arrow':true, active:show}">
                    <i :class="icon"></i>{{text}}
                </span>
        <ul class="mymenu1">
            <slot></slot>
        </ul>
    </li>
</template>

<script>
    export default {
        name: "MySubmenu",
        data(){
          return {
              key:"",
              show:false,
          }
        },
        props:{

            icon:{
               type:String,
                default: "flaticon-381-networking"
            },
            text:{
               type:String,
                default:"菜单标题"
            }
        },
        created() {
            let menu=this.getMenu();
            this.key=0;
            if(menu!=null) {
                this.key=menu.items.length+1;
                if(this.key==1)
                    this.show=true;
                menu.items.push({text: this.text, icon: this.icon, show: this.show, key:this.key});
            }
        },
        methods:{

            collapseHandler(){
                let menu=this.getMenu();
                if(menu!=null&&menu.items!=null){
                    menu.items.forEach(c=>{
                       //console.log("key=",c.key);
                      if(c.key==this.key) {
                          this.show = !this.show;
                          c.show=this.show;
                      }
                      else {
                          c.show = false;
                      }
                    });
                }

            },
            getMenu(name = 'MyMenu') {
                let parent = this.$parent;
                let parentName = parent.$options.name
                while (parentName !== name) {
                    parent = parent.$parent
                    if (!parent) return false
                    parentName = parent.$options.name
                }
                return parent
            }
        }
    }
</script>

<style scoped>

</style>
