<template>
    <div>
        <!-- 헤더 -->
        <HeaderVue id="setting-header" :option="option" @close="close"></HeaderVue>
        <div id="setting-body">
            <!-- 선택지 -->
            <OptionsVue id="body-option" @change-option="optionChange"></OptionsVue>
            <!-- 선택지에 따른 컴포넌트 -->
            <div id="body-content">
                <!-- 회원 정보 조회 및 수정 -->
                <UserInfoVue v-if="option===1"></UserInfoVue>
                <!-- 비밀번호 변경 -->
                <ChangePwdVue v-if="option===2"></ChangePwdVue>
                <!-- 회원 탈퇴 -->
                <UserDelete v-if="option===3"></UserDelete>
            </div>
        </div>
    </div>
</template>

<script setup>
import HeaderVue from "./Header.vue";
import OptionsVue from "./Options.vue";
import UserInfoVue from "./UserInfo.vue";
import ChangePwdVue from "./ChangePwd.vue";
import UserDelete from "./UserDelete.vue";
import { ref } from "vue";

const option = ref(1)
const optionChange = (num)=>{
    option.value = num
}
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