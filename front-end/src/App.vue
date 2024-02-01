<template>
  <v-app class="wrap">
    <RouterView/>
  </v-app>
</template>

<script setup>
import { RouterView, useRouter } from "vue-router";
import {useUserStore} from "./stores/user.js"

const router = useRouter()
const store = useUserStore()

router.beforeEach((to, from)=>{
  if(!store.isLogin 
  && to.name!='login' 
  && to.name!='signup' 
  && to.name!='email' 
  && to.name!='search-password1' 
  && to.name!='search-password2' ){
    return {name:'login'}
  }
})
</script>

<style scoped>
</style>