<template>
    <el-dialog :width="width" :title="title" :close-on-click-modal="false" :visible="show" @update:visible="closeHandler">

        <component :is="view" v-if="reload" :params="params" @close="closeHandler" ></component>

    </el-dialog>
</template>

<script>


    export default {
        name: "mydialog",
        data() {
            return {
                view: null,
                reload: true,
                params:{}
            }
        },

        props: {
            url: {
                type: String,
                require: true
            },
            width:{
               type:String,
               default: "80%"
            },
            xdata:{
                type:Object
            },
            title: {
                type: String,
                default: "详情"
            },
            show: {
                type: Boolean,
                require: true
            }
        },
        methods: {
            loadComponent(val) {
                return import(`@/${val}`);

            },
            getParam() {
                if (this.url.indexOf("?") == -1) {
                    return {};
                }
                let url = this.url.split("?")[1];
                let params = new Object();

                let strs = url.split("&");
                for (var i = 0; i < strs.length; i++) {
                    params[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
                }
                params= Object.assign(params,this.xdata);
                console.log("params",params);
                this.params=params;
            },
            closeHandler(val) {
                console.log("执行关闭");
                this.$emit("update:show", val);
                this.$emit("close", true);

            }

        },

        watch: {

            url: {
                handler(newVal) {

                    if (newVal== null)
                        return ;
                    console.log("newVal", newVal);
                    let path=newVal;
                    if(newVal.indexOf("?")!=-1)
                        path=newVal.split("?")[0];
                    this.getParam();
                    console.log("this.params",this.params);
                    if (path.startsWith("/"))
                        path = path.substring(1);
                    this.loadComponent(path).then(component => {
                        this.view = component.default;
                    });
                    this.reload = false;
                    this.$nextTick(() => {
                        this.reload = true;
                    });

                },
                immediate: true
            }

        }

    }
</script>

<style scoped>

</style>
