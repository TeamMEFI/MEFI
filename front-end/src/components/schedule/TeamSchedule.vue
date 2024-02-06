<template>
    <div class="bg-white w-100 h-100 elevation-3 ma-0 pa-0">
        <v-toolbar color="#2A4770" class="elevation-1" >
            <v-toolbar-title class="font-weight-bold text-h5">Team's Schedule</v-toolbar-title>
        </v-toolbar>
        <v-list>
          <v-list-item
            v-for="schedule in scheduleList"
            :key="schedule"
            :title="schedule.title"
            @click="router.push({name: 'detailconference', params: { teamid : props.teamId, conferenceid : schedule['id']}})"
          ></v-list-item>
        </v-list>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter()
const props = defineProps({
  teamId: Number,
});
// 검색어

// 검색 API 호출 함수
// 검색 결과 값 List
const scheduleList = ref([
        {
          title: 'Item #1',
          value: 2,
          id: 1
        },
        {
          title: 'Item #2',
          value: 2,
          id: 2
        },
        {
          title: 'Item #3',
          value: 3,
          id: 3
        },
])


</script>

<style scoped>

</style>