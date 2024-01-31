<template>
  <v-app>
    <!-- 헤더 -->
    <v-app-bar
      height="40">
      <HeaderVue/>
    </v-app-bar>

    <!-- 사이드바 -->
    <v-navigation-drawer permanent app class="w-15">
      <ProfileVue/>
    </v-navigation-drawer>

    <!-- 메인 컨텐츠 -->
    <v-main>
      <div class="w-100 h-100 wrapper pa-2">
        <RouterView class="bg-white rounded" />
      </div>
    </v-main>

    <!-- 풋터 -->
    <!-- <v-footer app class="w-100" color="#f0f0f0">
      <FooterVue/>
    </v-footer> -->
  </v-app>
  
</template>

<script setup>
import HeaderVue from '@/components/layout/Header.vue';
import FooterVue from '@/components/layout/Footer.vue';
import ProfileVue from '@/components/layout/Profile.vue';
import { useRouter, RouterView } from "vue-router";
const router = useRouter()
router.push({name:'main'})
</script>

<style>
.v-footer {
  justify-content: center;
}

.wrapper {
  background-color: #F7F9FF;
}
</style>
