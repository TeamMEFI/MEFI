<template>
  <v-container class="pa-0">
    <v-row class="d-flex align-center justify-start ma-3">
      <v-col cols="4">
        <v-row>
          <v-col cols="3" class="d-flex justify-center align-center">
            <v-btn icon="mdi-chevron-left" @click="clickprev"></v-btn>
          </v-col>
          <v-col cols="6" class="d-flex justify-center align-center">
            <p> {{ year }} {{ listofmonthword[month] }}</p>
          </v-col>
          <v-col cols="3" class="d-flex justify-center align-center">
            <v-btn icon="mdi-chevron-right" @click="clicknext"></v-btn>
          </v-col>
        </v-row>
      </v-col>
      <v-spacer></v-spacer>
      <v-btn :disabled="role === 'MEMBER'" class="me-3" @click="dialog=true">
        <p class="font-weight-black text-h6">팀 관리</p>
      </v-btn>
      <v-dialog v-model="dialog" persistent width="70%" height="70%">
        <TeamModifyDialog @close-dialog="dialog = false" :team-id="props.teamId"/>
      </v-dialog>  
      <v-btn @click="router.push({ name: 'insertconference', params: { teamid : props.teamId } })">
        <p class="font-weight-black text-h6">회의 예약</p>
      </v-btn>
    </v-row>
    <v-row class="d-flex align-center justify-center">
      <v-col v-for="i in weekday" class="day-header" style="flex-grow: 0;">
        <div>
          {{ dayofweek[i] }}
        </div>
      </v-col>
    </v-row>
    <v-row v-for="week in cal" class="d-flex align-center justify-center">
      <v-col v-for="i in weekday" class="day"  style="flex-grow: 0;" :class="week[i]['type']" >
          <div >
              {{ week[i]['date'] }}
              <v-btn @click="clicksomething(week[i]['date'])" class="ma-0 pa-0">
              일정잇음?
              </v-btn>
          </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted, watchEffect } from 'vue';
import { useRouter } from 'vue-router';
import { selectTeam } from '@/api/team.js';
import TeamModifyDialog from '../team/TeamModifyDialog.vue';

const router = useRouter()

const props = defineProps({
  teamId: Number,
});
const dialog = ref(false);
const role = ref('');

const select = async () => {
    const dummy = props.teamId
    await selectTeam(
        (response) => {
            role.value = response.data.dataBody.find(data => data.teamId == props.teamId).role
        },
        (error)=>{
            console.log(error)
        }
    )
}

onMounted(() => {
  select();
})

// 팀 전환시 팀원 조회
watchEffect((props, (newValue) => {
  select();
}))


// 셀렉터 옵션 및 캘린더 옵션들
const weekday = ref([0, 1, 2, 3, 4, 5, 6])
const dayofweek = ref(['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'])
const listofmonthword = ['January','February','March','April','May','June','July','August','September','October','November', 'December']

/*-------------------------
// 해당 월 달력 채우기
//-----------------------*/
const makecalendar = (year, month) => {
  // 이전 달, 현재 달, 다음 달 구분 및 계산용 변수
  let prevenddate = new Date(year, month, 0).getDate();
  let current = 1
  let next = 1

  // 현재 달력에 표시하기 위한 일자수 계산용 변수
  const startday = new Date(year, month, 1).getDay();
  const enddate = new Date(year, month + 1, 0).getDate();
  const new_list = Array(42).fill(null).map(() => ({ type: 'current', date: 0 }));

  // 달력 표시 데이터
  const result = [];
  
  // 이전 달 
  for (let i = startday - 1; i >= 0 ; i--) {
    new_list[i]['type'] = 'not_current' 
    new_list[i]['date'] = String(year) + String(month ? 0 : 12).padStart(2,'0') + String(prevenddate--).padStart(2,'0')
  }

  // 현재 달
  for (let j = startday; j < enddate + startday; j++) {
    new_list[j]['date'] = String(year) + String(month).padStart(2,'0') + String(current++).padStart(2,'0')
  }

  // 다음달
  for (let k = enddate + startday; k < 42; k++) {
    new_list[k]['type'] = 'not_current' 
    new_list[k]['date'] = String(year) + String(month).padStart(2,'0') + String(next++).padStart(2,'0')
  }

  // 일자 데이터 주차별 나누기
  for (let i = 0; i < 42; i += 7) {
      result.push(new_list.slice(i, i + 7));
  }

  return result;
};

// 이전달 이동
const clickprev = () => {
  if (month.value === 0) {
    year.value -= 1
    month.value = 11
  } else {
    month.value -= 1
  }
  cal.value = makecalendar(year.value, month.value)
}

// 다음달 이동
const clicknext = () => {
  if (month.value === 11) {
    year.value += 1
    month.value = 0
  } else {
    month.value += 1
  }
  cal.value = makecalendar(year.value, month.value)
}

// 추후 개발 예정. 이벤트
const clicksomething = (te) => {
  alert(te)
}

// 기준 일자 (Today)
const nowdate = ref(new Date())
const year  = ref(nowdate.value.getFullYear())
const month = ref(nowdate.value.getMonth())
const date  = ref(nowdate.value.getDate())
const day   = ref(nowdate.value.getDay())

// 달력 날짜 계산
const cal = ref(makecalendar(year.value, month.value))

// 일정 생성 관련

const type = ref(11)
const data = ref(22)

</script>



<style scoped>
.day {
    max-width: calc(95%/7);
    min-width: calc(95%/7);
    min-height: 110px;
    max-height: 110px;
    border: 1px solid black;
    text-align: center;
    flex-grow: initial !important;
}
.day-header {
  max-width: calc(95%/7);
  min-width: calc(95%/7);
  background-color: #f2f2f2;
  border: 0.2px solid #495464;
}
.not_current {
  background-color: #e0e0e0;
}
</style>