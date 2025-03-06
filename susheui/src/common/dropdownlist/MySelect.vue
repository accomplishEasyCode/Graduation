<template>
    <div class="inline">
        <el-select v-model="val"  @change="selected">
            <el-option v-for="(item,index) in datasource"    :key="index" :label="item[textfield]" :value="item[valuefield]"></el-option>
        </el-select>
    </div>
</template>

<script>

    import axios  from "axios";
    export default {
        name:"MySelect",
        data(){
            return {
                datasource:[],
                val:"",
                selectedItem:{
                    text:"",
                    value:""
                },
            }
        },
        model:{
            prop:"value",
            event:"selected"
        },
        props:{
            name:{
                type:String

            },
            cssclass:{
                type:String,
                default:"mydropdown"
            },
            url:{
                type:String,
                default: null
            },
            textmodelname:{
                type:String
            },
            textfield:{
                type:String,
                default: "name"
            },
            valuefield:{
                type: String,
                default: "id"
            },
            ds:{
                type:Array
            },
            value:{

                required:true
            },
            http:{
                type:Object
            },
            data:{
                type:Object
            }
        },
        created(){
            this.preload();
            this.load();
            console.log("创建"+this.name);
        },
        watch:{
            ds(newVal,oldVal){
                //this.datasource=newVal;
                this.load();
            }
        },
        methods:{
            preload(){
                this.$emit("preload");
            },
            async load(){
                if(this.http!=null)
                    this.$http=this.http;
                if(this.url!=null){
                    let {data:res}=await axios.post(this.url,this.data);
                    if(res!=null){
                        if(res.data!=null)
                            this.datasource=res.data;
                        else
                            this.datasource=res;
                    }
                }
                if(this.ds!=null)
                    this.datasource=this.ds;
                if(this.datasource!=null&&this.datasource.length>0){
                    let index= this.datasource.findIndex(item=>item[this.valuefield].toString()==this.value.toString());
                    if(index!=-1) {
                        this.selectedItem.value = this.datasource[index][this.valuefield];
                        this.selectedItem.text = this.datasource[index][this.textfield];
                    }else{
                        this.selectedItem.value = this.datasource[0][this.valuefield];
                        this.selectedItem.text = this.datasource[0][this.textfield];
                    }
                    this.$parent[this.textmodelname] = this.selectedItem.text;
                    this.val=this.selectedItem.value;
                    this.$emit("selected",this.selectedItem.value);
                }
                this.$emit("loaded",this.selectedItem.value,this.selectedItem);
            },
            selected(val){

                this.$emit("selected",val,this.selectedItem);
            }
        }
    }
</script>

<style scoped>

     .inline{
         display: inline-block;
     }


</style>




