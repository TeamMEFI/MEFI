<template>
  <v-app>
    <RouterView/>
  </v-app>
</template>

<script setup>
import { RouterView, useRouter } from "vue-router";
import {useUserStore} from "./stores/user.js"
import { updateToken } from "./api/user";

const router = useRouter()
const store = useUserStore()

// login
// accessToken 재발급
// accessToken이 있으면 home page
// accessToken이 없으면 login page
router.beforeEach((to, from)=>{
  if (localStorage.getItem('refreshToken') !== null){
    updateToken(
      localStorage.getItem('refreshToken'), (response)=>{console.log(response)},
      (err) => {console.log(err)}
    ).then((res)=>{console.log(res)})
      .catch((err)=>{console.log(err)})
  }
  if(localStorage.getItem('accessToken')==null
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