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
  width: 100%;
  height: 100%;
}
</style>