<template>
  <div class="bg-white h-100 w-100" @click="clickday">
      <div class="w-100 h-100 d-flex flex-column">
        <p class="font-weight-bold ma-4">{{ props.scheduleDate }}</p>
        <div v-for="conf in data" @click="router.push({name:'detailconference', params:{ teamid : props.teamId, conferenceid: conf.id}})" class="text-start CONFERENCE ma-1 rounded-lg">
          <p class="font-weight-bold ma-3">{{ conf.title }}</p>
          <p class="font-weight-bold ma-3">{{ conf.callStart.slice(11,16) }} ~ {{ conf.callEnd.slice(11,16) }}</p>
        </div>
      </div>
  </div>
</template>
<script setup>
import { ref, watchEffect } from 'vue'
import { useRouter } from 'vue-router'
import { getConferenceHistory } from '@/api/conference'
const emit = defineEmits(['clickDay'])
const props = defineProps({
  teamId: Number,
  scheduleDate: String
})
const router = useRouter();
const data = ref([])

// 일자 선택
const clickday = () => {
  emit('clickDay', props.scheduleDate)
}

const schedule = async () => {
  const date = props.scheduleDate.replaceAll('-','')
  await getConferenceHistory(
      props.teamId, date, (response) => {
          data.value = response.data.dataBody
          console.log(response.data.dataBody)
      },
      (error)=>{
          console.log(error)
      }
  )
}

watchEffect((props, (newvalue) => {
  schedule()
}))

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

.CONFERENCE {
  background-color: rgba(189, 255, 151, 0.5);
}
</style>
