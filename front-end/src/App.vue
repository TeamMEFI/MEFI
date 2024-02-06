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
      localStorage.getItem('refreshToken'), 
      (res)=>{
        // token 재발급 과정에서 refresh token 만료되었을 시 에러 처리
        if( to.name!='login' 
          && to.name!='signup' 
          && to.name!='email' 
          && to.name!='search-password1' 
          && to.name!='search-password2'
          && to.name!='search-password3' ){
          return {name:'login'}
        }
      },
      (err) => {
        console.log('token err ', err.response.status)
        if (err.response.status==403){
          localstorage.clear();
        }
        router.push({name:'home'})
      }
    )
  }
  else{
    if( to.name!='login' 
      && to.name!='signup' 
      && to.name!='email' 
      && to.name!='search-password1' 
      && to.name!='search-password2'
      && to.name!='search-password3' ){
      return {name:'login'}
    }
  }
})
</script>

<style scoped>
</style>