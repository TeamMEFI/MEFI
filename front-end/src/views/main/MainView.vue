<template>
    <v-container fluid>
      <!-- v-row를 사용하여 왼쪽(6), 오른쪽(4)으로 나눈 레이아웃 -->
      <v-row>
        <!-- 왼쪽(6) -->
        <v-col cols="7" class="ma-0 d-flex justify-center">
          <!-- 왼쪽 컴포넌트를 여기에 추가하세요 -->
          <CalendarVue/>
        </v-col>
  
        <!-- 오른쪽(4) -->
        <v-col cols="4">
          <!-- 오른쪽 컴포넌트를 여기에 추가하세요 -->
          <v-card>
            <v-card-title>오른쪽 컴포넌트</v-card-title>
            <!-- 내용 추가 -->
          </v-card>
        </v-col>
      </v-row>
    </v-container>
</template>
  
<script setup>
import CalendarVue from '@/components/calendar/Calendar.vue';
</script>

<style>

</style>