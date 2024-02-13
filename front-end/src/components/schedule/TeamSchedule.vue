<template>
  <div class="w-100 h-100 ma-0 pa-0">
    <template v-for="schedule in scheduleList" :key="schedule">
      <div
        class="conference-title"
        type="button"
        @click="
          router.push({
            name: 'detailconference',
            params: { teamid: props.teamId, conferenceid: schedule['id'] }
          })
        "
      >
        {{ schedule.title }}
      </div>
    </template>
  </div>
</template>
<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getConferenceHistory } from '@/api/conference'

const route = useRoute()
const router = useRouter()
const props = defineProps({
  teamId: Number,
  scheduleDate: String
})
const teamId = ref(route.params.id)
// 검색 API 호출 함수
// 검색 결과 값 List
const scheduleList = ref([])
const getHistory = () => {
  getConferenceHistory(
    { start: props.scheduleDate, end: props.scheduleDate },
    teamId.value,
    (response) => {
      const responseData = response.data?.dataBody
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
        router.replace({ name: 'notFound' })
      }
    }
  )
}

watch(() => props.scheduleDate, () => {
  getHistory()
})

onMounted(() => {
  getHistory()
})
</script>
<style scoped>
.conference-title {
  width: 100%;
  font-size: 12px;
  margin-bottom: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  text-align: left;
}
</style>
