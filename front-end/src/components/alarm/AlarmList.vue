<template>
    <v-card min-width="400">
        <AlarmHeader @close="close" @read-all="readAll()"></AlarmHeader>
        <!-- 스크롤 : 안보이게 하고 싶음 --> 
        <v-infinite-scroll :height="300" class="pa-0 ma-0">
            <!-- alarms 있을때 -->
            <div v-if="alarms != null" class="ma-0 pa-0">
                <v-list class="pa-0" v-for="alarm in alarms" :key="alarm.id">
                    <AlarmOne :alarm="alarm" class="cursor-pointer" @click="openAlarm(alarm)"></AlarmOne>
                </v-list>
                <v-dialog :value="modalAlarm" activator="parent" width="auto">
                    <AlarmMain :alarm="propsAlarm" @close="modalAlarm = !modalAlarm"></AlarmMain>
                </v-dialog>
            </div>

            <!-- alarms 없을때 -->
            <div v-else>
                <span>알림이 없습니다</span>
            </div>
        </v-infinite-scroll>
    </v-card>
</template>

<script setup>
import AlarmOne from './AlarmOne.vue';
import AlarmHeader from './AlarmHeader.vue';
import AlarmMain from './alarmDetail/AlarmMain.vue';
import { ref } from 'vue';

// 알림 모달 제어
const modalAlarm = ref(false)
const propsAlarm = ref({
    id:0,
    title:'asdf',
    writer:'asdf',
    day:'00-00-00'
})
const openAlarm = (alarm) => {
    modalAlarm.value = true
    propsAlarm.value = alarm
}

// 더미데이터
const alarms = ref([
    {
        id:1,
        title:'title1',
        writer:'writer1',
        day:'00-00-00',
    },
    {
        id:2,
        title:'title2',
        writer:'writer2',
        day:'00-00-00',
    },
    {
        id:3,
        title:'title3',
        writer:'writer3',
        day:'00-00-00',
    },
    {
        id:4,
        title:'title4',
        writer:'writer4',
        day:'00-00-00',
    },
    {
        id:5,
        title:'title5',
        writer:'writer5',
        day:'00-00-00',
    },
    {
        id:6,
        title:'title6',
        writer:'writer6',
        day:'00-00-00',
    },
])
const emit = defineEmits(['close'])
const close = () => {
    emit('close')
}

const readAll = () =>{
    console.log('전체 읽음')
    // api alarm all
}
</script>

<style scoped>

</style>