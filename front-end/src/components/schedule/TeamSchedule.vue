<template>
    <div class="bg-white w-100 h-100 elevation-3 ma-0 pa-0">
        <v-toolbar color="#2A4770" class="elevation-1" >
            <v-toolbar-title class="font-weight-bold text-h5">Team's Schedule</v-toolbar-title>
            <v-btn icon>
                <v-icon>mdi-dots-vertical</v-icon>
                <v-dialog activator="parent" v-model="dialog" persistent width="600" height="700">
                  <ScheduleDialog @close-dialog="dialog=false"/>
                </v-dialog>
            </v-btn>
        </v-toolbar>
        <v-list :items="scheduleList"></v-list>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import ScheduleDialog from '@/components/dialog/ScheduleDialog.vue';
const dialog = ref(false)
// 검색어

// 검색 API 호출 함수
const searchdocs = () => {
    console.log(1)
}
// 검색 결과 값 List
const scheduleList = ref([
        {
          title: 'Item #1',
          value: 2,
        },
        {
          title: 'Item #2',
          value: 2,
        },
        {
          title: 'Item #3',
          value: 3,
        },
])


</script>

<style scoped>

</style>