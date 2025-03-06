<template>
  <div id="app">

<!--    <router-view :key="$route.fullPath" />-->
      <router-view  :key="$app.key" />
  </div>
</template>
<script>
     export  default {
         name:"app",
         computed:{

         }
     }
</script>
<style>
</style>
