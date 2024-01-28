<template>
    <div class="bg-white w-100 h-100 elevation-3 ma-0 pa-0">
        <v-toolbar color="#2A4770" class="elevation-1" >
            <v-toolbar-title class="font-weight-bold text-h5">Schedule</v-toolbar-title>
        </v-toolbar>
        <v-list :items="scheduleList"></v-list>
    </div>
</template>

<script setup>
import { ref } from 'vue';
// 검색어

// 검색 API 호출 함수
const searchdocs = () => {
    console.log(1)
}
// 검색 결과 값 List
const scheduleList = ref([
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

</style>