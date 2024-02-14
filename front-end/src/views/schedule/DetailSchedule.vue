<template>
  <v-card class="pa-5">
    <v-card-title class="d-flex align-center pa-2">
      <p class="text-h5 font-weight-black">일정 상세</p>
      <v-spacer></v-spacer>
      <v-btn class="mx-1" @click="modify">일정 수정하기</v-btn>
      <v-btn class="mx-1" @click="deletes">일정 삭제하기</v-btn>
    </v-card-title>
    <v-card-item class="pa-3">
      <v-row>
        <v-col cols="6">
          <p>일정 종류</p>
        </v-col>
        <v-col cols="3">
          <p>시작 시간</p>
        </v-col>
        <v-col cols="3">
          <p>종료 시간</p>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="6">
          <v-select
            label="Choice Type"
            :items="typeschedule"
            variant="outlined"
            density="compact"
            hide-details="true"
            v-model="selecttype"
          ></v-select>
        </v-col>
        <v-col cols="6">
          <v-row>
            <v-col cols="3">
              <v-select v-model="selectSh" :items="starthours" variant="outlined" density="compact" hide-details="true"></v-select>
            </v-col>
            <v-col cols="3">
              <v-select v-model="selectSm" :items="startmins" variant="outlined" density="compact" hide-details="true"></v-select>
            </v-col>
            <v-col cols="3">
              <v-select v-model="selectEh" :items="endhours" variant="outlined" density="compact" hide-details="true"></v-select>
            </v-col>
            <v-col cols="3">
              <v-select v-model="selectEm" :items="endmins" variant="outlined" density="compact" hide-details="true"></v-select>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="6">
          <p>요약</p>
        </v-col>
        <v-col cols="6">
          <p>설명</p>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="6">
          <v-text-field v-model="summary" label="Summary" variant="outlined" required></v-text-field>
        </v-col>
        <v-col cols="6">
          <v-text-field v-model="description" label="Description" variant="outlined" required></v-text-field>
        </v-col>
      </v-row>
    </v-card-item>
  </v-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { deleteSchedule, modifySchedule, selectScheduleDetail } from '@/api/schedule'

const router = useRouter()

const starthours = ref(['08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21'])
const startmins = ref(['00', '05', '10', '15', '20', '25', '30', '35', '40', '45', '50', '55'])
const endhours = ref(['09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22'])
const endmins = ref(['00', '05', '10', '15', '20', '25', '30', '35', '40', '45', '50', '55'])

const typeschedule = ref(['회의', '출장'])
const selecttype = ref('')
const selectSh = ref('08')
const selectSm = ref('00')
const selectEh = ref('22')
const selectEm = ref('00')

const props = defineProps({
  scheduleid: Number,
  date: String
})

const summary = ref('')
const description = ref('')

const detail = async () => {
  await selectScheduleDetail(
    props.scheduleid,
    (response) => {
      console.log(response.data.dataBody)
      const data = response.data.dataBody
      selectSh.value = data.startedTime.slice(11, 13)
      selectSm.value = data.startedTime.slice(14, 16)
      selectEh.value = data.endTime.slice(11, 13)
      selectEm.value = data.endTime.slice(14, 16)
      selecttype.value = data.type === 'BUSINESSTRIP' ? '출장' : '회의'
      summary.value = data.summary
      description.value = data.description
    },
    (error) => {
      console.log(error)
    }
  )
}

const modify = async () => {
  const data = {
    startedTime: props.date + 'T' + selectSh.value + ':' + selectSm.value + ':00.000Z',
    endTime: props.date + 'T' + selectEh.value + ':' + selectEm.value + ':00.000Z',
    type: typeschedule.value == '회의' ? 'CONFERENCE' : 'BUSINESSTRIP',
    summary: summary.value,
    description: description.value
  }
  const param = {
    id: props.scheduleid,
    data: data
  }
  console.log(data)
  await modifySchedule(
    param,
    (response) => {
      console.log(response)
      router.back()
    },
    (error) => {
      console.log(error)
    }
  )
}

const deletes = async () => {
  await deleteSchedule(
    props.scheduleid,
    (response) => {
      console.log(response)
      router.back()
    },
    (error) => {
      console.log(error)
    }
  )
}

onMounted(() => {
  detail()
})
</script>

<style lang="scss" scoped></style>
