<template>
    <div>
        <h1>알림 설정</h1>
        <div>
            <div id="item">
                <div>알림 설정</div>
                <div id="item-icon">
                    <font-awesome-icon :icon="['fas', 'toggle-on']" v-if="alarm" @click="toggleAlarm"/>
                    <font-awesome-icon :icon="['fas', 'toggle-off']" v-else @click="toggleAlarm"/>
                </div>
            </div>
            <div id="item">
                <div>알림 소리</div>
                <div id="item-icon">
                    <font-awesome-icon :icon="['fas', 'toggle-on']" v-if="sound" @click="toggleSound"/>
                    <font-awesome-icon :icon="['fas', 'toggle-off']" v-else @click="toggleSound"/>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue"

const alarm = ref(true)
const sound = ref(false)
const toggleAlarm = () => {
    alarm.value = !alarm.value
}
const toggleSound = () =>{
    sound.value = !sound.value
}
</script>

<style scoped>
#item{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    padding: 10px;
    border: 1px solid black;
}
#item-icon{
    cursor: pointer;
}
</style>