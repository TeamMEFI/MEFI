<template>
  <v-app>
    <!-- 헤더 -->
    <v-app-bar app color="#494550" height="40" class="elevation-0" >
      <HeaderVue />
    </v-app-bar>
    
    <div class="d-flex w-100 wrapper pa-2">
      <!-- 사이드바 -->
      <div class="bg-white border-right mt-5 pt-5" style="width: 240px;">
        <ProfileVue />
      </div>
      <!-- 메인 컨텐츠 -->
      <RouterView class="bg-white ml-2 mt-10 rounded" />
    </div>

    <!-- 풋터 -->
    <!-- <v-footer app class="w-100" color="#f0f0f0">
      <FooterVue/>
    </v-footer> -->
  </v-app>
</template>

<script setup>
import HeaderVue from '@/components/layout/Header.vue'
import ProfileVue from '@/components/layout/Profile.vue'
</script>

<style> 
.wrapper {
  background-color: #f7f9ff;
}
</style>
