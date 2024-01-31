<template>
  <v-sheet height="100%" width="100%" rounded="xl" elevation="12" class="pa-5">
    <v-list :items="docsList"></v-list>
  </v-sheet>
</template>

<script setup>
import { ref } from 'vue';
// 검색어
const keyword = ref('hello')
// 검색 API 호출 함수
const searchdocs = () => {
    console.log(1)
}
// 검색 결과 값 List
const docsList = ref([
        {
          title: 'Item #1',
          value: 2,
        },
        {
          title: 'Item #2',
          value: 2,
        },
        {
          title: 'Item #3',
          value: 3,
        },
])
</script>

<style scoped>
.bg {
  background-color: #f0f0f0;
}
</style>