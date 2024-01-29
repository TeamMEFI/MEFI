<template>
    <div>
        <v-form :disabled="disable">
            <!-- 정보 조회 및 수정할 수 있는 form -->
            <v-text-field 
                label="email"
                :model-value="store.userInfo.id"
                v-model="email"
            ></v-text-field>
            <v-text-field 
                label="name"
                :model-value="store.userInfo.name"
                v-model="name"
            ></v-text-field>
            <v-text-field 
                label="position"
                :model-value="store.userInfo.position"
                v-model="position"
            ></v-text-field>
            <v-text-field 
                label="department"
                :model-value="store.userInfo.department"
                v-model="department"
            ></v-text-field>
            
            <!-- 수정하기 : 누르면 입력창이 활성화 됨 -->
            <div v-if="disable">
                <v-btn @click="toggleDisable" style="background-color: #E53935; color: white;">수정하기</v-btn>
            </div>
            <!-- 저장 : 입력된 정보 저장됨 -->
            <!-- 취소 : 유저 정보는 변경되지 않음. 입력창이 비활성화됨 -->
            <div v-else>
                <v-btn @click="updateUserInfo" style="background-color: #E53935; color: white;">저장</v-btn>
                <v-btn @click="toggleDisable" style="background-color: #E0E0E0;">취소</v-btn>
            </div>
        </v-form>
    </div>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "../../../stores/user";

const store = useUserStore()
const disable = ref(true)
const email = ref('')
const name = ref('')
const position = ref('')
const department = ref('')
const user = ref()

// 입력창 활성화 비활성화를 토글하는 함수
const toggleDisable = () => {
    disable.value = !disable.value
}
// 회원 정보를 수정하는 rest api
// 유효성 검사 후 api
const updateUserInfo = () => {
    if (window.confirm('회원 정보를 수정하시겠습니까?')){
        user.value = {
            email:email.value,
            name:name.value,
            position:position.value,
            department:department.value,
        }
        // api user info update
    }
}
</script>

<style scoped>
</style>