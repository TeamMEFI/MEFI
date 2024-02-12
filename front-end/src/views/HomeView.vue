<template>
  <v-layout>
    <!-- 헤더 -->
    <v-app-bar app color="#494550" height="40" class="elevation-0" >
      <HeaderVue :alarms="alarms"/>
    </v-app-bar>
    
    <div class="d-flex w-100 wrapper pa-0 h-100">
      <!-- 사이드바 -->
      <div class="bg-white border-right h-cal mt-5 pt-5" style="width: 240px;">
        <ProfileVue />
      </div>
      <!-- 메인 컨텐츠 -->
      <RouterView class="bg-white w-100 h-cal ml-2 mt-10 rounded" />
    </div>

    <!-- 풋터 -->
    <!-- <v-footer app class="w-100" color="#f0f0f0">
      <FooterVue/>
    </v-footer> -->
  </v-layout>
</template>

<script setup>
import { alarmAll } from '@/api/alarm';
import HeaderVue from '@/components/layout/Header.vue'
import ProfileVue from '@/components/layout/Profile.vue'
import { watch } from 'vue';
import { ref } from 'vue';
import { onMounted } from 'vue';

// 안읽은 알림
const alarms = ref([])

// 알림 조회
onMounted(async()=>{
  try{
    const response = await alarmAll()
    alarms.value = response.data.dataBody;
    console.log(alarms.value)
  }catch(error){
    console.log(error)
  }
})


</script>

<style> 
.wrapper {
  background-color: #f7f9ff;
}
</style>
