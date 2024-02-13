<template>
  <div class="bg-white w-100 h-100 elevation-3 ma-0 pa-0">
    <v-toolbar color="#2A4770" class="elevation-1">
      <v-toolbar-title class="font-weight-bold text-h5">Team's Schedule</v-toolbar-title>
    </v-toolbar>
    <v-list>
      <v-list-item
        v-for="schedule in scheduleList"
        :key="schedule"
        :title="schedule.title === '' ? schedule.callStart : schedule.title"
        @click="
          router.push({
            name: 'detailconference',
            params: { teamid: props.teamId, conferenceid: schedule['id'] }
          })
        "
      ></v-list-item>
    </v-list>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getConferenceHistory } from '@/api/conference'
const route = useRoute()
const router = useRouter()
const props = defineProps({
  teamId: Number
})
const teamId = ref(route.params.id)
// 검색 API 호출 함수
// 검색 결과 값 List
const scheduleList = ref([])
const getHistory = () => {
  getConferenceHistory(
    { start: '20230102', end: '20240211' },
    teamId.value,
    (response) => {
      const responseData = response.data?.dataBody
      console.log(teamId.value)
      console.log(responseData)
      scheduleList.value = responseData.map((conference) => {
        if (conference.title === '') {
          const date = conference.callStart.slice(0, 10)
          const time = conference.callStart.slice(11, 16)
          conference.title = `${date} ${time}`
        }
        return conference
      })
    },
    (error) => {
      const errorCode = error.response.data.dataHeader?.resultCode
      const errorMessage = error.response.data.dataHeader?.resultMessage
      
      if (errorCode === 'C-001' || errorCode === 'G-001') {
        alert(errorMessage)
        router.replace({name: "notFound"})
      }
    }
  )
}
onMounted(() => {
  getHistory()
})
</script>
<style scoped></style>