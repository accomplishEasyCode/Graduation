<template>
  <div>
      <select :name="name" @change="selected($event)"  :class="cssclass">
          <option v-for="(item,index) in datasource"  :selected="item[valuefield].toString()==selectedItem.value?'selected':''"   :key="index" :value="item[valuefield]">{{item[textfield]}}</option>
      </select>
  </div>
</template>

<script>
import axios  from "axios";

export default {
   name:"dw-dropdownlist",
   data(){
       return {
           datasource:[],
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
       type:String,
       required:true
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
             this.$emit("selected",this.selectedItem.value);
         }
       this.$emit("loaded",this.selectedItem.value,this.selectedItem);
     },
     selected(e){
         let index=e.target.selectedIndex;
         let value=e.target.value;
         if(this.datasource!=null&&this.datasource.length>0) {
             this.selectedItem.value = this.datasource[index][this.valuefield];
             this.selectedItem.text = this.datasource[index][this.textfield];
             eval(`this.$parent.${this.textmodelname}=this.selectedItem.text;`);
         }
         this.$emit("selected",value,this.selectedItem);
     }
   }
}
</script>

<style >

   .mydropdown{
       display: block;
       width: 200px;
       height: 34px;
       padding: 6px 12px;
       font-size: 14px;
       line-height: 1.42857143;
       color: #555;
       background-color: #fff;
       background-image: none;
       border: 1px solid #ccc;
       border-radius: 4px;
       -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
       box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
       -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
       -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
       transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
   }

</style>




