<template>
    <v-sheet class="w-30 ma-auto pa-12 d-flex flex-column justify-center" style="border-radius: 10px;">
        <!-- 로그 -->
        <div class="d-flex justify-center">
            <span style="font-size: xx-large; font-weight: bolder;">MEFI</span>
        </div>
        <!-- 입력창 -->
        <v-form @submit.prevent="login">
            <v-text-field
                label="이메일"
                v-model="email"
                hide-details="auto"
                type="email"
                class="ma-3"
            ></v-text-field>
            <v-text-field
                label="비밀번호"
                v-model="password"
                hide-details="auto"
                type="password"
                class="ma-3"
            ></v-text-field>
            <!-- 로그인 버튼 -->
            <v-spacer></v-spacer>
            <v-btn type="submit" class="w-100">로그인</v-btn>
        </v-form>

        <!-- 그 외 비밀번호 찾기 / 회원 가입 기능 -->
        <div class="d-flex justify-center">
            <a @click="goSearchPassword">비밀번호 찾기 </a>  |  
            <a @click="goSignup"> 회원가입</a>
        </div>
    </v-sheet>
</template>

<script setup>
import router from "@/router/index.js"
import { useUserStore } from "@/stores/user"
import { ref } from "vue"

// 입력 정보를 담을 변수
const email = ref('')
const password = ref('')
const store = useUserStore()

// 로그인 함수
// 유효성 검사 후 로그인 정보를 넘겨줌
const login = function(){
    const user = {
        email:email.value,
        password:password.value,
    }
    console.log('component', user)
    store.login(user)
}

// 기능 : 회원 가입 페이지로 이동
const goSignup = function(){
    router.push({name:'signup'})
}

// 기능 : 비밀 번호 찾기 페이지로 이동
const goSearchPassword = () => {
    router.push({name:'search-password1'})
}
</script>

<style scoped>
</style>