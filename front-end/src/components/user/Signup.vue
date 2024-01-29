<template>
    <!-- 회원 가입 -->
    <v-sheet class="w-30 mx-auto pa-12 ma-10" style="border-radius: 10px;">
        <!-- 회원 가입 입력창 -->
        <v-form @submit.prevent="signup">
            <v-text-field
                label="아이디"
                v-model="email"
                :rules="rule_email"
            ></v-text-field>

            <v-row class="align-center justify-center">
                <v-btn :disabled="email_check" @click="goEmail">이메일 인증</v-btn>
            </v-row>

            <v-text-field
                label="비밀 번호"
                type="password"
                v-model="password"
                :rules="rule_pass"
            ></v-text-field>

            <v-text-field
                label="비밀 번호 확인"
                type="password"
                v-model="password_check"
                :rules="rule_pass_check"
            ></v-text-field>

            <v-text-field
                label="이름"
                v-model="name"
                :rules="rule_name"
            ></v-text-field>

            <v-text-field
                label="부서"
                v-model="dept"
            ></v-text-field>

            <v-text-field
                label="직급"
                v-model="position"
            ></v-text-field>

            <!-- 회원 가입 버튼 -->
            <v-row class="align-center justify-center">
                <v-btn type="submit">회원 가입</v-btn>
            </v-row>
        </v-form>
    </v-sheet>
</template>

<script setup>
import router from "@/router";
import { useUserStore } from "@/stores/user"
import { ref, watch } from "vue"

const store = useUserStore();

// 입력받을 정보
const email = ref('')
const password = ref('')
const password_check = ref('')
const name = ref('')
const dept = ref('')
const position = ref('')

// 기능 : 이메일 인증 버튼
// 작동 원리 : 입력 형식 맞으면 true, 아니면 false
const email_check = ref(true)
watch(
    ()=>email.value,
    ()=>{
        if (regex_email.test(email.value)) {
            email_check.value = false
        }
    }
)

// 유효성 검사
// 이메일 (필수항목 / 이메일 형식)
const rule_email = [
    value => !!value || '필수 항목 입니다.',
    value => (value && regex_email.test(value)) || '이메일 주소를 정확히 입력해주세요',
]
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
// 이름 (필수)
const rule_name = [
    value => !!value || '필수 항목 입니다.',
]

// 정규식
// 이메일
const regex_email = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
// 비밀번호
const regex_pass =  /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/

// 회원 가입
const signup = function(){
    // 회원 정보 유효성 검사를 통과했을 시, signup
    // 안했을 시, 입력 형식에 대한 경고문 띄우기
    if ( !email.value || !regex_email.test(email.value) || 
    !password.value || !regex_pass.test(password.value) ||
    !password_check.value || password_check.value!=password.value ||
    !name.value){
        alert("모두 입력해 주세요")
    }else{
        // 회원 정보
        const userInfo = {
            email:email.value,
            password:password.value,
            name:name.value,
            dept:dept.value,
            position:position.value,
        }
        console.log('singup view')
        store.signup(userInfo)
    }
}

// 이메일 인증
const goEmail = () => {
    router.push({name:'email'})
}
</script>

<style scoped>
</style>