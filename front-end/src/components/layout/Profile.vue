<template>
  <v-container class="bg-main w-100 h-100 d-flex flex-column aling-center justify-space-between">
      <div class="d-flex justify-center mb-10"><img class="body-img-img elevation-20" src="../../assets/sampleImg.PNG" alt=""></div>
      <div class="h-15">팀캘린더</div>
      <div class="h-85">
          <div class="bg-white h-100 elevation-20">
              <v-toolbar color="#2A4770" >
                  <v-toolbar-title class="font-weight-bold text-h5">TeamList</v-toolbar-title>
                  <v-spacer></v-spacer>
                  <v-btn icon>
                      <v-icon>mdi-dots-vertical</v-icon>
                      <v-dialog activator="parent" v-model="dialog" persistent width="1000" height="700">
                        <TeamCreateDialog @close-dialog="dialog=false"/>
                      </v-dialog>
                  </v-btn>
              </v-toolbar>
              <v-list :items="items"></v-list>
          </div>
      </div>
  </v-container>
</template>

<script setup>
import { ref } from 'vue';
import TeamCreateDialog from '@/components/dialog/TeamCreateDialog.vue';
const dialog = ref(false)

const items = ref([
        {
          title: 'Item #1',
          value: 1,
        },
        {
          title: 'Item #2',
          value: 2,
        },
      ])
</script>

<style scoped>
.body-img-img{
    width: 250px;
    height: 250px;
    border-radius: 50%;
    margin: 10px;
}
</style>