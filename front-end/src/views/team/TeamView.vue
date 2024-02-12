<template>
  <v-app class="w-100 h-100 d-flex justify-center">
    <!-- v-row를 사용하여 왼쪽(6), 오른쪽(4)으로 나눈 레이아웃 -->
    <v-row>
      <!-- 왼쪽(6) -->
      <v-col cols="9" class="w-100 h-100 d-flex justify-center">
        <!-- 왼쪽 컴포넌트를 여기에 추가하세요 -->
        <v-container class="w-100 h-100">
          <TeamCalendarVue :team-id="Number(props.id)" />
        </v-container>
      </v-col>

      <!-- 오른쪽(4) -->
      <v-col cols="3" class="w-100 h-100 pa-0">
        <!-- 오른쪽 컴포넌트를 여기에 추가하세요 -->
        <v-container class="w-100 h-100 d-flex flex-column">
          <TeamMateList :team-id="Number(props.id)" />
        </v-container>
      </v-col>
    </v-row>
  </v-app>
</template>

<script setup>
import TeamCalendarVue from '@/components/calendar/TeamCalendar.vue'
import Schedule from '@/components/schedule/TeamSchedule.vue'
import TeamMateList from '@/components/team/TeamMateList.vue'

const props = defineProps({
  id: Number
})
</script>

<style scoped></style>
