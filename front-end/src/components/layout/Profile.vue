<template>
  <v-infinite-scroll class="sidebar w-100 h-100 d-flex flex-column aling-center pa-3">
    <div class="d-flex mb-4">
      <div class="w-25">
        <img
          class="body-img-img w-100 h-100 rounded-circle"
          width="50px"
          src="@/assets/sampleImg.PNG"
          alt="profile-image"
        />
      </div>
      <div class="w-80">
        <p>박병조</p>
        <p>구미 2반 204조</p>
      </div>
    </div>

    <v-btn class="my-4 border" height="60" @click="router.push({ name: 'main' })"
      >개인 일정 조회
    </v-btn>
    <div class="d-flex mx-2 my-4 justify-space-between align-center">
      <p>팀 목록</p>
      <font-awesome-icon :icon="['fas', 'plus']" />
      <v-dialog activator="parent" v-model="dialog" persistent width="70%" height="70%">
        <TeamCreateDialog @close-dialog="dialog = false" />
      </v-dialog>
    </div>
    <div v-for="team in teams">
      <v-list v-model:opened="teamOpen">
        <v-list-group :value="team.teamName">
          <template v-slot:activator="{ props }">
            <v-list-item v-bind="props" :title="team.teamName"></v-list-item>
          </template>

          <v-list-item
            v-for="teamMember in team.teamMembers"
            :key="teamMember"
            :title="teamMember"
            :value="teamMember"
          ></v-list-item>
        </v-list-group>
      </v-list>
    </div>
    <template v-slot:loading></template>
  </v-infinite-scroll>
</template>

<script setup>
import { ref } from 'vue'
import TeamCreateDialog from '@/components/dialog/TeamCreateDialog.vue'
import { useRouter } from 'vue-router'

const dialog = ref(false)
const router = useRouter()

const teamOpen = ref(['Teams'])
const teams = ref([
  {
    teamName: 'Team1',
    teamMembers: ['Member1', 'Member2', 'Member3']
  },
  {
    teamName: 'Team2',
    teamMembers: [
      'Member4',
      'Member5',
      'Member6',
      'Member7',
      'Member8',
      'Member9',
      'Member10',
      'Member11'
    ]
  },
  {
    teamName: 'Team3',
    teamMembers: ['Member12', 'Member13', 'Member14', 'Member15', 'Member16']
  }
])
</script>

<style scoped>
::-webkit-scrollbar {
  display: none;
}
</style>
