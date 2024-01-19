<template>
    <div>
        <h2 id="alarm-title">알림 설정</h2>
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
#alarm-title{
    margin: 10px;
    padding: 10px;
}
#item{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    padding: 20px;
    margin: 20px;
    border-bottom: 1px solid rgb(169, 169, 169);
}
#item-icon{
    cursor: pointer;
}
</style>