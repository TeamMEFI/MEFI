<template>
  <v-container class="pa-0">
    <v-row class="d-flex align-center justify-start ma-2">
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
      <v-btn @click="router.push({ name: 'insertschedule', params: {date: choicedate}})">
        <p class="font-weight-black text-h6">개인 일정 생성</p>
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
      <v-col v-for="i in weekday" class="day"  style="flex-grow: 0;" :class="week[i]['type']" @click="choicedate = week[i]['fulldate']">
          <div >
              {{ week[i]['date'] }}
          </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter();


// 기준 일자 (Today)
const nowdate = ref(new Date())
const year  = ref(nowdate.value.getFullYear())
const month = ref(nowdate.value.getMonth())
const choicedate = ref(String(year.value) +'-'+ String(month.value+1).padStart(2,'0') +'-' + String(nowdate.value.getDate()).padStart(2,'0'))
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
  const startdate = new Date(year, month, 1).getDay();
  const enddate = new Date(year, month + 1, 0).getDate();
  const new_list = Array(42).fill(null).map(() => ({ type: 'current', date: 0, fulldate: '' }));

  // 달력 표시 데이터
  const result = [];

  // 이전, 다음 년도 및 월 관리
  const fullprevyear   = month == 0 ?  year - 1 : year
  const fullprevmonth  = month == 0 ?  12 : month
  const fullmonth      = month + 1
  const fullnextyear   = month == 11 ? year + 1 : year
  const fullnextmonth  = month == 11 ? 1 : month + 2

  // 이전 달 
  for (let i = startdate - 1; i >= 0 ; i--) {
    new_list[i]['type'] = 'not_current'
    new_list[i]['fulldate'] = String(fullprevyear) +'-'+ String(fullprevmonth).padStart(2,'0') +'-'+ String(prevenddate).padStart(2,'0')
    new_list[i]['date'] = String(prevenddate--).padStart(2,'0')
  }

  // 현재 달
  for (let j = startdate; j < enddate + startdate; j++) {
    new_list[j]['fulldate'] = String(year) +'-'+ String(fullmonth).padStart(2,'0') +'-'+ String(current).padStart(2,'0')
    new_list[j]['date'] = String(current++).padStart(2,'0')
  }

  // 다음달
  for (let k = enddate + startdate; k < 42; k++) {
    new_list[k]['type'] = 'not_current' 
    new_list[k]['fulldate'] = String(fullnextyear) +'-'+ String(fullnextmonth).padStart(2,'0') +'-'+ String(next).padStart(2,'0')
    new_list[k]['date'] = String(next++).padStart(2,'0')
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



// 달력 날짜 계산
const cal = ref(makecalendar(year.value, month.value))

// 일정 생성 관련


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