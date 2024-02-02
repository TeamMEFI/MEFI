<template>
    <v-sheet class="w-30 ma-auto pa-12 d-flex flex-column justify-center" min-width="500" style="border-radius: 10px;">
        <v-form @submit.prevent="chagnePassword" class="d-flex flex-column justify-center">
            <!-- 새 비밀번호 입력창 -->
            <v-text-field
                label="새로운 비밀번호"
                v-model="password"
                :rules="rule_pass"
                variant="outlined"
                type="password"
            ></v-text-field>

            <!-- 새 비밀번호 확인 입력창 -->
            <v-text-field
                label="새로운 비밀번호 확인"
                v-model="password_check"
                :rules="rule_pass_check"
                variant="outlined"
                type="password"
            ></v-text-field>

            <!-- submit 버튼 -->
            <v-btn type="submit" class="w-100" variant="flat" color="#45566F">비밀 번호 변경</v-btn>
        </v-form>
    </v-sheet>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
const router = useRouter()

// 입력받은 정보
const password = ref('')
const password_check = ref('')

// 유효성
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

// 정규식
// 비밀번호 정규식
const regex_pass =  /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/

// 비밀 번호 변경 api
const chagnePassword = () => {
    // api 연결
    router.push({name:'login'})
}
</script>

<style scoped>
</style>