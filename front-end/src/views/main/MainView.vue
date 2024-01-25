<template>
    <v-container fluid class="w-100 h-100 ma-0 d-flex justify-center">
      <!-- v-row를 사용하여 왼쪽(6), 오른쪽(4)으로 나눈 레이아웃 -->
      <v-row>
        <!-- 왼쪽(6) -->
        <v-col cols="8" class="w-100 h-100 ma-0 d-flex justify-center">
          <!-- 왼쪽 컴포넌트를 여기에 추가하세요 -->
          <v-container class="w-100 h-100">
            <CalendarVue/>
          </v-container>
        </v-col>
  
        <!-- 오른쪽(4) -->
        <v-col cols="4" class="w-100 h-100 ma-0">
          <!-- 오른쪽 컴포넌트를 여기에 추가하세요 -->
          <v-container class="w-100 h-100 d-flex flex-column">
            <v-row class="w-100 h-70">
              <v-col cols="12" class="w-100 ma-0">
                <Schedule/>
              </v-col>
            </v-row>
            <v-row class="w-100 h-30">
              <v-col cols="12" class="w-100 ma-0">
                <SearchDoc/>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </v-container>
</template>
  
<script setup>
import CalendarVue from '@/components/main/Calendar.vue';
import SearchDoc from '@/components/main/SearchDoc.vue';
import Schedule from '@/components/main/Schedule.vue';
</script>
<style>

</style>