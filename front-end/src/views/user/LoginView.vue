<template>
    <div id="login-container">
        <v-form id="login">
            <div id="input-form">
                <v-text-field
                    class="id"
                    label="아이디"
                    hide-details="auto"
                    v-model="id"
                ></v-text-field>
                <v-text-field
                    class="pwd"
                    label="비밀번호"
                    hide-details="auto"
                    type="password"
                    v-model="password"
                ></v-text-field>
            </div>
            <div id="submit-button">
                <v-btn @click="login">로그인</v-btn>
                <v-btn @click="goSignup">회원 가입</v-btn>
            </div>
        </v-form>
    </div>
</template>

<script setup>
import router from "@/router"
import { useUserStore } from "@/stores/user"
import { ref } from "vue"
import { RouterLink } from "vue-router"

const id = ref('')
const password = ref('')

const store = useUserStore()
const login = function(){
    const user = {
        id:id.value,
        password:password.value,
    }
    store.login(user)
}

const goSignup = function(){
    router.push({name:'signup'})
}
</script>

<style scoped>
#login{
    margin: 50px auto;
    width: 500px;
    display: flex;
    flex-direction: column;
    justify-content: center;
}
.submit-button{
    display: flex;
    /* 안먹힘 이유는 뭘까 */
    flex-direction: column;
    justify-content: space-around;
    align-content: center;
}
</style>