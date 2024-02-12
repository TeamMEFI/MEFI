<template>
  <div class="bg-white h-100 w-100 elevation-3 pa-0 rounded-lg">
      <div class="w-100 h-100 d-flex flex-column">
        <div  v-for="hour in final" :key="hour" :style="{height : getheight(hour['duration'])}" :class="hour['type']">
          <div v-if="hour.duration > 0">
            {{ hour.type }}
          </div>
        </div>
      </div>
  </div>
</template>

<script setup>
import { selectTeamSchedule } from '@/api/schedule';
import { ref, watchEffect } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter()

const props = defineProps({
  teamId: Number,
  scheduleDate: String,
})
const data = ref([])
// 스케줄 필요코드
const hours = ref(['08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00'])
const timerhours = ['08:', '09:', '10:', '11:', '12:', '13:', '14:', '15:', '16:', '17:', '18:', '19:', '20:', '21:']
const mins = ['00', '05', '10', '15', '20', '25', '30', '35', '40', '45', '50', '55']

const times = () => {
const newtimes = []
for (const hour of timerhours) {
  for (const minute of mins) {
    const time = hour + minute;
    if (time >= '08:00' && time <= '21:55') {
      newtimes.push(time);
    }
  }
}
newtimes.push('22:00')
return newtimes;
}

const tabletimes = times()

// 시간분리
const drowingtime = (table, schedule) => {
  if (schedule.length === 0) return [];

  const tabledata = [];
  let previousEnd = '08:00';

  for (const time of schedule) {
    const startTime = time.startedTime.slice(11, 16);
    const endTime = time.endTime.slice(11, 16);
    const startIdx = table.indexOf(previousEnd);
    const endIdx = table.indexOf(startTime);

    if (startIdx !== -1 && endIdx !== -1) {
      const duration = endIdx - startIdx;
      tabledata.push({ duration, type: 'can', startedTime: previousEnd, endTime: startTime });
    }

    const startIdxTime = table.indexOf(startTime);
    const endIdxTime = table.indexOf(endTime);

    tabledata.push({
      type: 'cannot',
      duration: endIdxTime - startIdxTime,
      ...time
    });

    previousEnd = endTime;
  }

  const lastEndIdx = table.indexOf(previousEnd);
  const finalEndIdx = table.indexOf('22:00');

  if (lastEndIdx !== -1 && finalEndIdx !== -1 && lastEndIdx !== finalEndIdx) {
    const duration = finalEndIdx - lastEndIdx;
    tabledata.push({ duration, type: 'can', startedTime: previousEnd, endTime: '22:00' });
  }

  // 겹치는 시간 계산
  for (let i = 0; i < tabledata.length - 1; i++) {
    if (tabledata[i + 1].duration < 0) {
      tabledata[i].duration += tabledata[i + 1].duration;
    }
  }

  console.log(tabledata);
  return tabledata;
};

const getheight = (i) => {
return i / 168 * 100 + '%';
}

const final = ref([])

const schedule = async () => {
  await selectTeamSchedule(
      props.teamId, props.scheduleDate, (response) => {
        data.value = response.data.dataBody
        final.value = drowingtime(tabletimes, data.value)
      },
      (error)=>{
          console.log(error)
      }
  )
}
// 스케줄 상세정보 이동
// const detailSchedule = (data) => {
// console.log(props.scheduleDate)
// router.push({name: 'detailschedule', params: { scheduleid : data, date: props.scheduleDate}})
// }

watchEffect((props, (newvalue) => {
  console.log('check')
  schedule()
}))
watchEffect((data, () => {
  
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