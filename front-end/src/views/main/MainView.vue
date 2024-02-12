<template>
  <v-app class="w-100 h-cal d-flex justify-center">
    <!-- v-row를 사용하여 왼쪽(6), 오른쪽(4)으로 나눈 레이아웃 -->
    <v-row class="h-cal">
      <!-- 왼쪽(6) -->
      <v-col cols="9" class="w-100 h-100 d-flex justify-center">
        <!-- 왼쪽 컴포넌트를 여기에 추가하세요 -->
        <v-container class="w-100 h-100">
          <CalendarVue @click-day="(date, data) => dataCheck(date, data)"/>
        </v-container>
      </v-col>

      <!-- 오른쪽(4) -->
      <v-col cols="3" class="w-100 h-100">
        <!-- 오른쪽 컴포넌트를 여기에 추가하세요 -->
        <v-container class="w-100 h-100 d-flex flex-column">
          <Schedule :schedule-data="schedule" :schedule-date="catchdate"/>
        </v-container>
      </v-col>
    </v-row>
  </v-app>
</template>

<script setup>
import { ref } from 'vue'

import CalendarVue from '@/components/calendar/Calendar.vue'
import Schedule from '@/components/schedule/InvSchedule.vue'


const windowWidth = ref(window.innerWidth)
const windowHeight = ref(window.innerHeight)
const schedule = ref([])
const catchdate = ref('')

const dataCheck = (date, data) => {
  catchdate.value = date
  schedule.value = data
  console.log(date, data)
}

window.addEventListener('resize', function () {
  windowWidth.value = window.innerWidth
})
</script>
<style scoped></style>
