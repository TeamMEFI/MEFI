<template>
  <v-container class="bg-main w-100 h-100 d-flex flex-column aling-center justify-space-between pa-3">
      <div class="d-flex justify-center mb-10">
        <img class="body-img-img w-100 h-100 elevation-20" src="../../assets/sampleImg.PNG" alt="" @click="router.push({name: 'main'})">
      </div>
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
              <v-list>
                  <v-list-item  v-for="item in items"
                  :key="item.value"
                  :title="item.title"
                  @click="router.push({name: 'team'})">
                  </v-list-item>
              </v-list>
          </div>
      </div>
  </v-container>
</template>

<script setup>
import { ref } from 'vue';
import TeamCreateDialog from '@/components/dialog/TeamCreateDialog.vue';
import { useRouter } from 'vue-router';
const dialog = ref(false)
const router = useRouter()
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
    height: calc(width);
    border-radius: 50%;
    margin: 10px;
}
</style>