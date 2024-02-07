<template>
    <v-card min-width="400">
        <AlarmHeader @close="close" @read-all="readAll()"></AlarmHeader>
        <div v-if="alarms != null">
            <v-list class="pa-0" v-for="alarm in alarms" :key="alarm.id">
                <v-menu>
                    <AlarmOne :alarm="alarm" class="cursor-pointer"></AlarmOne>
                    <AlarmDetailVue></AlarmDetailVue>
                </v-menu>
            </v-list>
        </div>
        <div v-else>
            <span>알림이 없습니다</span>
        </div>
    </v-card>
</template>

<script setup>
import AlarmOne from './AlarmOne.vue';
import AlarmHeader from './AlarmHeader.vue';
import AlarmDetailVue from './AlarmDetail.vue';
import { ref } from 'vue';

const alarms = ref([
    {
        id:1,
        title:'title1',
        day:'00-00-00',
    },
    {
        id:2,
        title:'title2',
        day:'00-00-00',
    },
    {
        id:3,
        title:'title3',
        day:'00-00-00',
    },
    {
        id:4,
        title:'title4',
        day:'00-00-00',
    },
    {
        id:5,
        title:'title5',
        day:'00-00-00',
    },
    {
        id:6,
        title:'title6',
        day:'00-00-00',
    },
])
const emit = defineEmits(['close'])
const close = () => {
    emit('close')
}

const readAll = () =>{
    // router.go
    // 전체 읽음 반영
}
</script>

<style scoped>
</style>