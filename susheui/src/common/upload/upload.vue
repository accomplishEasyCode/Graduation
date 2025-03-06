<template>
<div>
     <div v-if="!mutiple"  :class="className">
          <img  class="image" :src="hostHead+value" alt=""/>
          <div class="upload">
           <div class="plus-circle"></div>
               <input name="$$fuploads" @change="singleuploadHandler($event)" type="file" :accept="accept" />
           </div>
      </div>

     <div v-else class="uploadimages-wapper">

         <div class="uploadimages-items">

             <div v-for="item in imgs" :key="item" class="image-item">
                 <div @click="removeHandler(item)" class="op-close">×</div>
                 <img :src="hostHead+item" width="200" height="200"/>
             </div>
         </div>
         <div  :class="className">

             <div class="upload">
                 <div class="plus-circle"></div>
                 <input name="$$fuploads" @change="mutiuploadHandler($event)" type="file" :accept="accept" />
             </div>
         </div>
     </div>
</div>
</template>

<script>
import axios  from "axios";
export default {
    tagname: "dw-upload",
    data(){
       return{

       }
    },

    computed:{
      imgs(){
          if(this.value=="")
              return [];
         return this.value.split("$;");
      }
    },
    methods:{
       async singleuploadHandler(e){

            let formData = new FormData();
            let file=e.target.files[0];
            formData.append('file', file);
            let src=window.URL.createObjectURL(file);
            let url=this.hostHead+this.actionUrl;
            let {data:res}=await axios.post(url,formData);
            console.log("upload组件返回:",res);
            if(res.data!=null&&res.data.length>0){
                let val=res.data[0].relativeUrl;
                //console.log("val=",val);
                this.$emit("update",val);
            }

        },
       async mutiuploadHandler(e){
           let formData = new FormData();
           let file=e.target.files[0];
           formData.append('file', file);
           //formData.append('__mySelf', "1024");
           let src=window.URL.createObjectURL(file);
           let url=this.hostHead+this.actionUrl;
           let {data:res}=await axios.post(url,
               formData,{headers:{"Content-Type":"multipart/form-data"}});
           console.log("upload组件返回:",res);
           if(res.data!=null&&res.data.length>0) {
               let val="";
               if(this.value=="")
                   val=res.data[0].relativeUrl;
               else
                   val=this.value+"$;"+res.data[0].relativeUrl;
               this.$emit("update", val);
               this.$emit("change",this.imgs);
           }
       },
       removeHandler(item){
           let imgs=this.value.split("$;");
           let index=imgs.findIndex(c=> c==item);
           if(index!=-1)
               imgs.splice(index,1);
           if(imgs!=null&&imgs.length>0) {
               let val = imgs.join("$;");
               this.$emit("update", val);
           }else
               this.$emit("update", "");

       }
    },
    model:{
      prop:"value",
      event:'update'
    },
    props:{

      actionUrl:{
          require:true,
          type:String,
          default:"/admin/upload"
      },
      hostHead:{
          type:String,
          default:"/"
      },

      value:{
          type:String,
          default:"/upload/nopic.jpg"
      },
      className:{
          type:String,
          default:'upload-component'
      },
      accept:{
          type:String,
          default:".jpg,.jpeg,.png,.gif",
      },
      mutiple:{
          type:Boolean,
          default:false
      }


    }

}
</script>

<style >
.upload-component {
    border: 2px dashed #ccc;
    display: flex;
    align-items: center;
    justify-content: center;
    list-style: none;
    margin: 0 5px 10px;
    cursor: pointer;
    height: 200px;
    width: 200px;
    position: relative;
}

.upload-component .image {
    width: 196px;
    height: 196px;
    border-radius: 6px;
    z-index: 1;
}

.upload-component .upload {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 100px;
    height: 100px;
    z-index: 100;
    display: flex;
    justify-content: center;
}

.upload-component .upload > input[type="file"] {
    cursor: pointer;
    width: 128px;
    height: 128px;
    opacity: 0;
    top: 0px;
    left: 0px;
    position: absolute;
}

.uploadimages-wapper {
    border: 2px dashed #ccc;
    display: flex;
    align-items: center;
    list-style: none;
}

.uploadimages-wapper > .uploadimages-items {
    display: flex;
    align-items: center;
    list-style: none;
    flex-wrap: wrap;
}

.uploadimages-wapper > .uploadimages-items > .image-item {
    position: relative;
    margin-left: 8px;
    border: 1px solid #ddd;
    cursor: pointer;
}

.uploadimages-wapper > .uploadimages-items > .image-item .op-close {
    position: absolute;
    top: 2px;
    right: 2px;
    width: 20px;
    height: 20px;
    background-color: #00a7d0;
    cursor: pointer;
    border-radius: 2px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: nowrap;
    color: red;
    font-size: 30px;
    font-weight: 600;
}

.uploadimages-wapper > .uploadimages-items > .image-item .op-close:hover {
    background-color: red;
    color: #fff;
}

.uploadimages-wapper > .uploadimages-uc {
    border: 2px dashed #ccc;
    display: flex;
    align-items: center;
    justify-content: center;
    list-style: none;
    margin: 0 5px 10px;
    cursor: pointer;
    height: 200px;
    width: 200px;
    position: relative;
}

.vertical-banner {
    border: 2px dashed #ccc;
    display: flex;
    align-items: center;
    justify-content: center;
    list-style: none;
    margin: 0 5px 10px;
    cursor: pointer;
    height: 486px;
    width: 200px;
    position: relative;
}

.vertical-banner .image {
    width: 196px;
    height: 480px;
    border-radius: 6px;
    z-index: 1;
}

.vertical-banner .upload {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 100px;
    height: 100px;
    z-index: 100;
    display: flex;
    justify-content: center;
}

.vertical-banner .upload > input[type="file"] {
    cursor: pointer;
    width: 128px;
    height: 128px;
    opacity: 0;
    top: 0px;
    left: 0px;
    position: absolute;
}

.horizontal-banner {
    border: 2px dashed #ccc;
    display: flex;
    align-items: center;
    justify-content: center;
    list-style: none;
    margin: 0 5px 10px;
    cursor: pointer;
    height: 200px;
    width: 800px;
    position: relative;
}

.horizontal-banner .image {
    width: 796px;
    height: 196px;
    border-radius: 6px;
    z-index: 1;
}

.horizontal-banner .upload {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 100px;
    height: 100px;
    z-index: 100;
    display: flex;
    justify-content: center;
}

.horizontal-banner .upload > input[type="file"] {
    cursor: pointer;
    width: 128px;
    height: 128px;
    opacity: 0;
    top: 0px;
    left: 0px;
    position: absolute;
}

.plus-circle {
    position: relative;
    margin-right: 8px;
    margin-bottom: 8px;
    width: 46px;
    height: 46px;
    box-sizing: border-box;
    background-color: red;
    border-radius: 50%;
    opacity: 1;
}

.plus-circle::before {
    content: " ";
    position: absolute;
    top: 50%;
    left: 50%;
    -webkit-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    background-color: #fff;
    width: 2px;
    height: 32px;
}

.plus-circle:after {
    content: " ";
    position: absolute;
    top: 50%;
    left: 50%;
    -webkit-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    background-color: #fff;
    width: 32px;
    height: 2px;
}

.vertical-banner:hover, .upload-component:hover, .horizontal-banner:hover {
    border: 2px dashed #ff847f;
}


</style>
