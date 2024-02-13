<template>
  <v-infinite-scroll v-if="store.userInfo !== null" class="w-100 h-100 d-flex flex-column aling-center pa-3">
    <div class="d-flex mb-4 align-center">
      <div class="">
        <img
          class="rounded-circle"
          width="50px"
          height="50px"
          :src="userphoto"
          alt="profile-image"
        />
      </div>
      <div class="w-75 pl-2">
        <p class="font-weight-black ma-1">{{ username }}</p>
        <p class="font-gray ma-1">{{ userdept }} | {{ userposition }}</p>
      </div>
    </div>

    <v-btn class="my-4 border elevation-0" height="60" @click="router.push({ name: 'main' })"
      >개인 일정 조회
    </v-btn>
    <div class="d-flex mx-2 my-4 justify-space-between align-center">
      <p style="font-weight: bold">팀 목록</p>
      <font-awesome-icon :icon="['fas', 'plus']" @click="dialog=true"/>
      <v-dialog v-model="dialog" persistent width="70%" height="70%">
        <TeamCreateDialog @close-dialog="dialog = false"/>
      </v-dialog>
    </div>
    <div>
      <v-list v-if="teams">
        <v-list-item
            v-for="team in teams"
            :key="team"
            :title="team.teamName"
            :value="team.teamId"
            @click="goTeamPage(team.teamId)"
          ></v-list-item>
      </v-list>
    </div>
    <template v-slot:loading></template>
  </v-infinite-scroll>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import TeamCreateDialog from '@/components/team/TeamCreateDialog.vue'
import { useRouter } from 'vue-router'
import { selectTeam } from '@/api/team.js'
import { useUserStore } from "@/stores/user"
const store = useUserStore()
const username = ref(store.userInfo?.name)
const userdept = ref(store.userInfo?.dept)
const userposition = ref(store.userInfo?.position)
const userphoto = ref(`data:image/jpeg;base64, ${store.userInfo?.profile}`)
const dialog = ref(false)
const router = useRouter()
const teams = ref([])

const select = async () => {
    await selectTeam(
        (response) => {
            teams.value = response?.data.dataBody
        },
        (error)=>{
            console.log(error)
        }
    )
}

watch(() => dialog.value, () => {
  select()
})

onMounted(() => {
  if (store.userInfo !== null) {
    select()
  }
});

const goTeamPage = (id) => {
  console.log('check')
  router.push({name: 'team', params: { id }})
}
</script>

<style scoped>
::-webkit-scrollbar {
  display: none;
}
</style>
