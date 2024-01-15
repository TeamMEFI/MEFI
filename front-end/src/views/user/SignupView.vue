<template>
    <div id="signup-container">
        <v-form id="input-form">
            <v-text-field
                label="아이디"
            ></v-text-field>
            
            <v-text-field
                label="이름"
            ></v-text-field>

            <v-text-field
                label="회사"
            ></v-text-field>

            <v-text-field
                label="이메일"
            ></v-text-field>

            <v-text-field
                label="비밀 번호"
                type="password"
            ></v-text-field>

            <v-text-field
                label="비밀 번호 확인"
                type="password"
            ></v-text-field>

            <v-btn @click="signup">회원 가입</v-btn>
        </v-form>
    </div>
</template>

<script setup>
import { useUserStore } from "@/stores/user"
import { ref } from "vue"

const store = useUserStore();

const id = ref('')
const name = ref('')
const company = ref('')
const email = ref('')
const password = ref('')
const password_check = ref('')
const signup = function(){
    const userInfo = {
        id:id.value,
        name:name.value,
        company:company.value,
        email:email.value,
        password:password.value,
    }
    store.signup(userInfo)
}
</script>

<style scoped>
#signup-container{
    display: flex;
    flex-direction: row;
    justify-content: center;
}
#input-form{
    width: 400px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
}

</style>