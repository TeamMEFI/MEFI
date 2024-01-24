<template>
    <div id="background">
        <div id="login-container">
            <!-- 로그 -->
            <div id="log">
                MEFI
            </div>
            <!-- 입력창 -->
            <v-form id="input-form">
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
            </v-form>
            <!-- 로그인 버튼 -->
            <v-btn id="submit" @click="login">로그인</v-btn>
            <!-- 그 외 비밀번호 찾기 / 회원 가입 기능 -->
            <div id="side-function">
                <span tytpe="button" id="password-find">비밀번호 찾기 </span>  |  
                <span @click="goSignup" id="signup"> 회원가입</span>
            </div>
        </div>
    </div>
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
</script>



<style scoped>
#background{
    color: rgb(73, 84, 100);
}
#login-container{
    width: 700px;
    height: 400px;
    border: 1px solid black;
    border-radius: 15px;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
}
#log{
    text-align: center;
    font-size:xx-large;
    font-weight: bolder;
    margin: 30px;
}
#input-form{
    width: 500px;
    margin: 0px auto
}
#submit{
    width: 300px;
    align-content: center;
    margin: 0px auto;
}
#side-function{
    margin-top: 10px;
    display: flex;
    flex-direction: row;
    justify-content: center;
}
#password-find{
    cursor: pointer;
}
#signup{
    cursor: pointer;
}
</style>