<template>
  <div>
    <RouterView></RouterView>
  </div>
</template>

<script setup>
import { RouterView, useRouter } from "vue-router";
import {useUserStore} from "./stores/user.js"

const router = useRouter()
const store = useUserStore()
router.beforeEach((to,from)=>{
  if(!store.isLogin && to.name!='login' && to.name!='signup'){
    return {name:'login'}
  }
})
</script>

<style scoped>
.container{
  margin-top: 80px;
  min-height: 600px;
  border: 1px solid black;
  border-radius: 10px;
}
</style>