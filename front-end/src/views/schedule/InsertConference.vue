<template>
  <v-card class="h-100 pa-5">
    <v-card-title class="d-flex h-5 align-center pa-2">
      <p class="text-h5 font-weight-black">회의 예약</p>
      <v-spacer></v-spacer>
      <v-btn @click="conferenceReservation">회의 예약하기</v-btn>
    </v-card-title>
    <v-card-item class="px-0">
      <v-container>
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
              v-model="selectvalue"
              :items="typeschedule"
              hide-selected="1"
              variant="outlined"
              density="compact"
              hide-details="true"
              disabled
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
            <p>제목</p>
          </v-col>
          <v-col cols="6">
            <p>설명</p>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="6">
            <v-text-field v-model="title" label="title" variant="outlined" density="compact" required></v-text-field>
          </v-col>
          <v-col cols="6">
            <v-text-field v-model="description" label="description" variant="outlined" density="compact" required></v-text-field>
          </v-col>
        </v-row>
      </v-container>
    </v-card-item>
    <v-card-title class="d-flex h-5 align-center pa-2">
      <p class="text-h5 font-weight-black">회의 관련 문서</p>
    </v-card-title>
    <div class="h-50 pa-2 my-2">
      <SearchDoc :documentState="documentState" />
    </div>
  </v-card>
</template>
<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import SearchDoc from '@/components/docs/SearchDoc.vue'
import { createConference } from '@/api/conference'
<<<<<<< HEAD

const route = useRoute()
=======
>>>>>>> fe/MainPage/CSS-refactoring
const router = useRouter()
const starthours = ref(['08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21'])
const startmins = ref(['00', '05', '10', '15', '20', '25', '30', '35', '40', '45', '50', '55'])
const endhours = ref(['09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22'])
const endmins = ref(['00', '05', '10', '15', '20', '25', '30', '35', '40', '45', '50', '55'])
const typeschedule = ref(['회의'])
const selectvalue = ref('회의')

const props = defineProps({
  teamid: Number,
  date: String
})
const documentState = ref({
  state: 'create',
  conferenceId: undefined
})
const title = ref('')
const description = ref('')
const selectSh = ref('')
const selectSm = ref('')
const selectEh = ref('')
const selectEm = ref('')

const conferenceReservation = async () => {
  if (!selectSh.value || !selectSh.value || !selectEh.value || !selectEh.value) {
    alert('회의 시간을 설정해주세요.')
    return false
  }

  const conferenceCreateReqDto = {
    title: title.value,
    description: description.value,
    thumbnailUrl: '',
    callStart: props.date + 'T' + selectSh.value + ':' + selectSm.value + ':00.000Z',
    callEnd: props.date + 'T' + selectEh.value + ':' + selectEm.value + ':00.000Z',
    teamId: props.teamid
  }

  await createConference(
    conferenceCreateReqDto,
    (response) => {
      const conferenceId = response.data.dataBody
      documentState.value.state = 'done'
      documentState.value.conferenceId = conferenceId
    },
    (error) => {
      console.log(error)
    }
  )
  
  router.push({ name: 'team', params: { id: route.params?.teamid } })
}
</script>
<style scoped></style>
