<template>
    <div>
        <!-- 알림 설정 -->
        <h2 id="alarm-title">알림 설정</h2>
        <div>
            <div id="item">
                <!-- 알림 설정 -->
                <div>알림 설정</div>
                <div id="item-icon">
                    <!-- 알림 on -->
                    <font-awesome-icon :icon="['fas', 'toggle-on']" v-if="alarm" @click="toggleAlarm"/>
                    <!-- 알림 off -->
                    <font-awesome-icon :icon="['fas', 'toggle-off']" v-else @click="toggleAlarm"/>
                </div>
            </div>
            <div id="item">
                <!-- 알림 소리 설정 -->
                <div>알림 소리</div>
                <div id="item-icon">
                    <!-- 소리 on -->
                    <font-awesome-icon :icon="['fas', 'toggle-on']" v-if="sound" @click="toggleSound"/>
                    <!-- 소리 off -->
                    <font-awesome-icon :icon="['fas', 'toggle-off']" v-else @click="toggleSound"/>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue"

// 알림 on off를 담을 변수
const alarm = ref(true)
// 소리 on off를 담을 변수
const sound = ref(false)

// 작동 : 해당 아이콘 클릭 시 toggle됨
// 기능 : alarm 설정 on off
const toggleAlarm = () => {
    alarm.value = !alarm.value
}

// 작동 : 해당 아이콘 클릭 시 toggle됨
// 기능 : sound 설정 on off
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