<template>
  <div class="wrap">
    <RouterView/>
  </div>
</template>

<script setup>
import { RouterView, useRouter } from "vue-router";
import {useUserStore} from "./stores/user.js"

const router = useRouter()
const store = useUserStore()

router.beforeEach((to, from)=>{
  if(!store.isLogin && to.name!='login' && to.name!='signup'){
    return {name:'login'}
  }
})
</script>

<style scoped>
</style>