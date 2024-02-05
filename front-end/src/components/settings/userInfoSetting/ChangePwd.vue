<template>
    <v-sheet class="pa-10">
        <v-form @submit.prevent="changePWD()">
            <!-- 현재 비밀번호 입력 -->
            <v-text-field
                label="현재 비밀번호"
                variant="outlined"
                type="password"
                v-model="password"
            ></v-text-field>
            <!-- 새로운 비밀 번호 입력  -->
            <v-text-field
                label="새로운 비밀번호"
                variant="outlined"
                type="password"
                v-model="new_password"
                :rules="rule_pass"
            ></v-text-field>
            <!-- 새로운 비밀 번호 확인 -->
            <v-text-field
                label="새로운 비밀번호 확인"
                variant="outlined"
                type="password"
                v-model="new_password_check"
                :rules="rule_pass_check"
            ></v-text-field>
            <!-- 저장 : 유효성 검사와 현재 비밀 번호가 일치한 경우, alert창에서 '예'를 누를 경우, 비밀 번호 변경 -->
            <v-btn type="submit" class="w-100" variant="flat" color="#45566F">저장</v-btn>
        </v-form>
    </v-sheet>
</template>

<script setup>
import { ref } from "vue"

const password = ref('')
const new_password = ref('')
const new_password_check = ref('')

// 유효성 검사
// 비밀번호 : 필수항목 / 영문숫자특수문자(8-16) 
const rule_pass = [
    value => !!value || '필수 항목 입니다.',
    value => (value && regex_pass.test(value)) || '영문, 숫자, 특수문자가 조합하여 입력해주세요(8-16자)',
]
// 비밀번호확인 : 필수항목 / 비밀번호 일치 여부
const rule_pass_check = [
    value => !!value || '필수 항목 입니다.',
    value => (value && value==new_password.value) || '비밀번호가 일치하지 않습니다'
]

// 정규식
// 비밀번호 : 영문숫자특수문자(8-16) 
const regex_pass =  /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/

// 비밀 번호 변경 함수
// 유효성 검사 후 rest api 보내기
const changePWD = () => {
    if (window.confirm('비밀 번호를 변경하시겠습니까?')){
        // api user password update
    }
}
</script>

<style scoped>
</style>