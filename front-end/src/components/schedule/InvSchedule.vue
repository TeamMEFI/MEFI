<template>
    <div class="bg-white h-100 w-100 elevation-3 pa-0">
        <v-toolbar color="#2A4770" class="elevation-1" >
            <v-toolbar-title class="font-weight-bold text-h5">Schedule</v-toolbar-title>
            <v-spacer/>
            <v-btn icon>
                <v-icon>mdi-dots-vertical</v-icon>
                <v-dialog activator="parent" v-model="dialog" persistent width="600" height="700">
                  <ScheduleDialog @close-dialog="dialog=false"/>
                </v-dialog>
            </v-btn>
        </v-toolbar>
        <div class="w-100 h-cal d-flex">
          <div class="w-25 d-flex flex-column">
            <div v-for="hour in hours" :key="hour" :style="{height : (100/14) + '%'}">
              <div class="h-100 w-100 d-flex justify-center align-center">
                {{ hour }}
              </div>
              <v-divider></v-divider>
            </div>
          </div>
          <div class="w-75 d-flex flex-column">
            <div v-for="hour in final" :key="hour" :style="{height : getheight(hour['duration'])}" :class="hour['scheduletype']">
                <div v-if="hour['scheduletype'] != 'none'" @click="s()" class="w-100 h-100">
                  <p>시간 {{ hour['start'] }} ~ {{ hour['end'] }}</p>
                  <p>요약 : {{ hour['summary'] }}</p>
                </div>
            </div>
          </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import ScheduleDialog from '@/components/dialog/ScheduleDialog.vue';
import { useRouter } from 'vue-router';
const router = useRouter()
const dialog = ref(false)
const hours = ref(['08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00'])
const timerhours = ['08:', '09:', '10:', '11:', '12:', '13:', '14:', '15:', '16:', '17:', '18:', '19:', '20:', '21:']
const mins = ['00', '05', '10', '15', '20', '25', '30', '35', '40', '45', '50', '55']
const scheduleList = ref([
    { summary: '오전일정', description:'회의', scheduletype:'businesstrip', start: '09:35', end: '12:00'},
    { summary: '오후일정', description:'회의', scheduletype:'dispatch', start: '14:00', end: '16:00'},
])

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
    const startIdx = table.indexOf(previousEnd);
    const endIdx = table.indexOf(time.start);

    if (startIdx !== -1 && endIdx !== -1) {
      const duration = endIdx - startIdx;
      tabledata.push({ duration, summary: '', description:'', scheduletype:'none', start: '', end: '' });
    }

    const startIdxTime = table.indexOf(time.start);
    const endIdxTime = table.indexOf(time.end);

    tabledata.push({
      duration: endIdxTime - startIdxTime,
      ...time
    });

    previousEnd = time.end;
  }

  const lastEndIdx = table.indexOf(previousEnd);
  const finalEndIdx = table.indexOf('22:00');

  if (lastEndIdx !== -1 && finalEndIdx !== -1 && lastEndIdx !== finalEndIdx) {
    const duration = finalEndIdx - lastEndIdx;
    tabledata.push({ duration, summary: '', description:'', scheduletype:'none', start: '', end: '' });
  }

  return tabledata;
};

const getheight = (i) => {
  return i / 168 * 100 + '%';
}

const final = drowingtime(tabletimes, scheduleList.value)
console.log(final);
 
const s = () => {
  const type = 'schedule'
  router.push({ name: 'detail', params: { type } });
}
// 검색 결과 값 List


</script>

<style scoped>
.h-cal {
  height: calc(100% - 64px) !important;
}

.businesstrip {
  background-color: burlywood;
}

.dispatch {
  background-color: bisque;
}
</style>