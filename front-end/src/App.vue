<template>
  <div>
    <header><Header></Header></header>    
    <main class="container"><RouterView /></main>
    <!-- LoginView -->
    <footer><Footer></Footer></footer>
  </div>
</template>

<script setup>
import Header from "./components/Header.vue"
import Footer from "./components/Footer.vue";
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