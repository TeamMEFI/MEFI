<template>
  <v-layout>
    <!-- 헤더 -->
    <v-app-bar app color="#494550" height="40" class="elevation-0" >
      <HeaderVue :alarms="alarms" @remove-alarm="removeAlarm" @remove-alarms="removeAlarms"/>
    </v-app-bar>
    
    <div class="d-flex w-100 wrapper pa-0 h-cal h-cal-position">
      <!-- 사이드바 -->
      <div class="bg-white border-right h-100 sidebar">
        <ProfileVue />
      </div>
      <!-- 메인 컨텐츠 -->
      <RouterView class="bg-white w-100 h-100 rounded" />
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
import { useSettingStore } from '@/stores/setting';
import { watch } from 'vue';
import { ref } from 'vue';
import { onMounted } from 'vue';


// 변화 감지하여 알림 추가
const store = useSettingStore()
watch(()=> store.alarmFlag, () => {
  if(store.alarmFlag === true){
    alarms.value.push(store.alarmContent)
    store.alarmFlag = false
    console.log(alarms.value, store.alarmFlag, store.alarmContent)
  }
})

// 읽은 알림 삭제 처리
const removeAlarm = async (alarmId) => {
  console.log(alarmId)
  console.log(alarms.value)
  for(let i=0;i<alarms.value.length;i++){
    if(alarms.value[i].id===alarmId){
      alarms.value.splice(i,1);
    }
  }
  console.log(alarms.value)
}

// 알림 전체 읽음 및 배열 삭제
const removeAlarms = () => {
  alarms.value = []
}

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
