<script setup>
import { ref } from 'vue';
// 셀렉터 옵션 및 캘린더 옵션들
const type = ref('month')
const types = ref(['month', 'week'])
const weekday = ref([0, 1, 2, 3, 4, 5, 6])
const weekdays = ref([
          { title: 'Sun - Sat', value: [0, 1, 2, 3, 4, 5, 6] },
          { title: 'Mon - Sun', value: [1, 2, 3, 4, 5, 6, 0] },
          { title: 'Mon - Fri', value: [1, 2, 3, 4, 5] },
        ])
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
    new_list[i]['date'] = prevenddate--
  }

  // 현재 달
  for (let j = startday; j < enddate + startday; j++) {
    new_list[j]['date'] = current++
  }

  // 다음달
  for (let k = enddate + startday; k < 42; k++) {
    new_list[k]['type'] = 'not_current' 
    new_list[k]['date'] = next++
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

</script>

<template>
<v-sheet>
  <v-row class="d-flex align-center">
    <v-col cols="4">
      <v-select
        v-model="type"
        :items="types"
        dense
        variant="outlined"
        hide-details
        class="ma-2"
        label="View Mode"
      ></v-select>
    </v-col>
    <v-col cols="4" class="text-button d-flex align-center">
      <v-btn icon="mdi-chevron-left" @click="clickprev"></v-btn>
      <p class="ma-2"> {{ year }} {{ listofmonthword[month] }}</p>
      <v-btn icon="mdi-chevron-right" @click="clicknext"></v-btn>
    </v-col>
  </v-row>
  <v-row>
    <v-col v-for="i in weekday" class="day-header">
      <div>
        {{ dayofweek[i] }}
      </div>
    </v-col>
  </v-row>
  <v-row v-for="week in cal">
    <v-col v-for="i in weekday" class="pa-0 ma-0">
      <div class="day pa-0 ma-0" :class="week[i]['type']">
        {{ week[i]['date'] }}
        <v-btn @click="clicksomething(week[i]['date'])">
          일정잇음?
        </v-btn>
      </div>
    </v-col>
  </v-row>
</v-sheet>
</template>

<style scoped>
.day {
  height: 100px;
  border: 1px solid black;
  text-align: center;
}
.day-header {
  text-align: center;
  background-color: #f2f2f2;
  border: 0.2px solid #495464;
}
.not_current {
  background-color: #495464;
}
</style>