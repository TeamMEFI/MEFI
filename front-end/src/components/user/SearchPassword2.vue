<template>
    <v-sheet class="w-30 ma-auto pa-12 d-flex flex-column justify-center" style="border-radius: 10px;">
        <v-form @submit.prevent="chagnePassword">
            <v-text-field
                label="새로운 비밀번호"
                type="password"
                v-model="password"
                :rules="rule_pass"
            ></v-text-field>
            <v-text-field
                label="새로운 비밀번호 확인"
                type="password"
                v-model="password_check"
                :rules="rule_pass_check"
            ></v-text-field>
            <div class="d-flex align-center justify-center">
                <v-btn type="submit">비밀 번호 변경</v-btn>
            </div>
        </v-form>
    </v-sheet>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
const router = useRouter()

const password = ref('')
const password_check = ref('')
// 비밀번호 (필수항목 / 영문숫자특수문자(8-16))
const rule_pass = [
    value => !!value || '필수 항목 입니다.',
    value => (value && regex_pass.test(value)) || '영문, 숫자, 특수문자가 조합하여 입력해주세요(8-16자)',
]
// 비밀번호확인 (필수항목 / 비밀번호 일치 여부)
const rule_pass_check = [
    value => !!value || '필수 항목 입니다.',
    value => (value&&value==password.value) || '비밀번호가 일치하지 않습니다'
]
// 비밀번호
const regex_pass =  /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/

const chagnePassword = () => {
    console.log('change password')
    // api 연결
    router.push({name:'login'})
}
</script>

<style scoped>
</style>