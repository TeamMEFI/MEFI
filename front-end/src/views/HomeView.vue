<template>
  <v-app>
    <!-- 헤더 -->
    <v-app-bar app>
      <HeaderVue/>
    </v-app-bar>

    <!-- 사이드바 -->
    <v-navigation-drawer app class="w-15">
      <ProfileVue/>
    </v-navigation-drawer>

    <!-- 메인 컨텐츠 -->
    <v-main class="w-100 h-100">
      <RouterView/>
    </v-main>

    <!-- 풋터 -->
    <v-footer app class="w-100">
      <FooterVue/>
    </v-footer>
  </v-app>
  
</template>

<script setup>
import HeaderVue from '@/components/layout/Header.vue';
import FooterVue from '@/components/layout/Footer.vue';
import ProfileVue from '@/components/layout/Profile.vue';
import { RouterView, useRouter } from "vue-router";
const router = useRouter()
router.push({name:'main'})
</script>

<style>
.v-footer {
  justify-content: center;
}


</style>
