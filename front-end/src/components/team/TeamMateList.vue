<template>
  <div class="bg-white h-100 w-100 elevation-3 pa-0 rounded-lg">
    <v-toolbar class="elevation-1 rounded-t-lg" height="50">
      <v-toolbar-title class="font-weight-bold text-h5">팀원 목록</v-toolbar-title>
      <v-btn icon @click="dialog=true">
          <v-icon>mdi-account-plus-outline</v-icon>
          <v-dialog v-model="dialog" persistent width="50%" height="70%">
            <TeamMateDialog @close-dialog="close" :team-id="props.teamId" />
          </v-dialog>
      </v-btn>
    </v-toolbar>
    <div>
      <v-list v-if="members">
        <v-list-item
            v-for="member in members"
            :key="member.id"
            :title="member.name"
          ></v-list-item>
      </v-list>
    </div>
    <!-- <template v-slot:loading></template> -->
  </div>
</template>

<script setup>
import { ref, onMounted, watchEffect } from 'vue';
import { selectTeamMate } from '@/api/team.js';
import TeamMateDialog from './TeamMateDialog.vue';
const dialog = ref(false)
const props = defineProps({
  teamId: Number
});

const members = ref([])

const selectmember = async () => {
    await selectTeamMate(
        props.teamId,(response) => {
            members.value = response.data.dataBody
            console.log(response.data.dataBody)
        },
        (error)=>{
            console.log(error)
        }
    )
  }

// 최초 생성시 팀원 조회
onMounted(() => {
  selectmember();
});

// 팀 전환시 팀원 조회
watchEffect((props, (newValue) => {
  selectmember();
}))

const close = () => {
  selectmember();
  dialog.value = false;
}

</script>

<style scoped>

</style>