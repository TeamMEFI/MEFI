<template>
    <v-sheet class="w-30 mx-auto pa-12 ma-10" style="border-radius: 10px;">
        <!-- 로그 -->
        <div class="">MEFI</div>
        <!-- 입력창 -->
        <v-form @submit.prevent="login">
            <v-text-field
                label="아이디"
                v-model="email"
                hide-details="auto"
                type="email"
            ></v-text-field>
            <v-text-field
                label="비밀번호"
                type="password"
                v-model="password"
                hide-details="auto"
            ></v-text-field>
            <!-- 로그인 버튼 -->
            <v-row class="align-center justify-center">
                <v-btn type="submit">로그인</v-btn>
            </v-row>
        </v-form>

        <!-- 그 외 비밀번호 찾기 / 회원 가입 기능 -->
        <v-row class="align-center justify-center">
            <v-btn @click="goSearchPassword">비밀번호 찾기 </v-btn>  |  
            <v-btn @click="goSignup"> 회원가입</v-btn>
        </v-row>
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
    store.login(user)
}

// 기능 : 회원 가입 페이지로 이동
const goSignup = function(){
    router.push({name:'signup'})
}

// 기능 : 비밀 번호 찾기 페이지로 이동
const goSearchPassword = () => {
    router.push({name:'search-password'})
}
</script>

<style scoped>
</style>