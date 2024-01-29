<template>
    <div>
        <!-- setting header -->
        <Header id="setting-header" @close="close"></Header>

        <main id="setting-body">
            <!-- 선택지 -->
            <OptionsVue id="body-option" @change-option="optionChange"></OptionsVue>
            <div id="body-content">
                <!-- 알림 설정 -->
                <AlarmSettingVue v-show="option===1"></AlarmSettingVue>
                <!-- 입출력 설정 -->
                <IOSettingVue v-show="option===2"></IOSettingVue>
            </div>
        </main>
    </div>
</template>

<script setup>
import Header from "./Header.vue";
import OptionsVue from "./Options.vue";
import AlarmSettingVue from "./AlarmSetting.vue";
import IOSettingVue from "./IOSetting.vue";
import { ref } from "vue";

// 옵션 선택지 변수
const option = ref(1)
// 옵션 변경
const optionChange = (num)=>{
    option.value = num
}
// 상위 컴포넌트에 전달
// 기능 : 해당 모달창 닫기
const emit = defineEmits(['close'])
const close = () => {
    emit('close')
}
</script>

<style scoped>
#setting-header{
    width: 600px;
    height: 30px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    padding: 10px;
}
#setting-body{
    display: grid;
    grid-template-columns: 2fr 8fr
}
#body-option{
    background-color: rgb(96, 106, 121);
    color: white;
}
#body-content{
    height: 400px;
}
</style>
