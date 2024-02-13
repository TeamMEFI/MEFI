<template>
  <div class="bg-white h-100 w-100" @click="clickday">
      <div class="w-100 h-100 d-flex flex-column">
        <div v-for="conf in data" @click="router.push({name:'detailconference', params:{ teamid : props.teamId, conferenceid: conf.id}})">
          <p>{{ conf.title }}</p>
          <p>{{ conf.callStart.slice(11,16) }} ~ {{ conf.callEnd.slice(11,16) }}</p>
        </div>
      </div>
  </div>
</template>
<script setup>
import { ref, onMounted, watchEffect } from 'vue'
import { useRoute, useRouter } from 'vue-router'
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
</style>
