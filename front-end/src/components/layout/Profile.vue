<template>
  <v-infinite-scroll class="sidebar w-100 h-100 d-flex flex-column aling-center pa-3">
    <div class="d-flex mb-4 align-center">
      <div class="w-25">
        <img
          class="rounded-circle"
          width="50px"
          height="50px"
          src="@/assets/sampleImg.PNG"
          alt="profile-image"
        />
      </div>
      <div class="w-75 pl-2">
        <p>박병조</p>
        <p>구미 2반 204조</p>
      </div>
    </div>

    <v-btn class="my-4 border elevation-0" height="60" @click="router.push({ name: 'main' })"
      >개인 일정 조회
    </v-btn>
    <div class="d-flex mx-2 my-4 justify-space-between align-center">
      <p style="font-weight: bold">팀 목록</p>
      <font-awesome-icon :icon="['fas', 'plus']" />
      <v-dialog activator="parent" v-model="dialog" persistent width="70%" height="70%">
        <TeamCreateDialog @close-dialog="dialog = false" />
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
import { ref, onMounted } from 'vue'
import TeamCreateDialog from '@/components/dialog/TeamCreateDialog.vue'
import { useRouter } from 'vue-router'
import { teamSelect } from '@/api/team.js'

const dialog = ref(false)
const router = useRouter()
const teams = ref([])

const select = async () => {
    await teamSelect(
        '',(response) => {
            teams.value = response.data.dataBody
        },
        (error)=>{
            console.log(error)
        }
    )
}

onMounted(() => {
  select()
});

const goTeamPage = (id) => {
  router.push({name: 'team', params: { id }})
}
</script>

<style scoped>
::-webkit-scrollbar {
  display: none;
}
</style>
