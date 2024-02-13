<template>
  <div class="bg-white h-100 w-100 elevation-3 pa-0 rounded-lg" @click="clickday">
      <div class="w-100 h-100 d-flex flex-column">
  
      </div>
  </div>
</template>

<script setup>
import { selectTeamSchedule } from '@/api/schedule';
import { ref, watchEffect } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter()
const emit = defineEmits(['clickDay'])
const props = defineProps({
  teamId: Number,
  scheduleDate: String,
})
console.log(props)
const final = ref([])

// 일자 선택
const clickday = () => {
  emit('clickDay', props.scheduleDate)
}

const schedule = async () => {
  const date = props.scheduleDate.replaceAll('-','')
  await selectTeamSchedule(
      props.teamId, date, (response) => {
        console.log(response.data.dataBody)

      },
      (error)=>{
          console.log(error)
      }
  )
}

watchEffect((props, (newvalue) => {
  console.log('check')
  schedule()
}))



</script>

<style scoped>

.can {
background-color: burlywood;
}

.cannot {
background-color: bisque;
}
</style>