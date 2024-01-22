<template>
    <div>
        <v-form :disabled="disable">
            <!-- {{ store.userInfo }} -->
            <v-text-field 
                label="email"
                :model-value="store.userInfo.id"
                v-model="id"
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
            
            <div v-if="disable">
                <v-btn @click="toggleDisable" style="background-color: #E53935; color: white;">수정하기</v-btn>
            </div>
            <div v-else>
                <v-btn @submit.stop="updateUserInfo" style="background-color: #E53935; color: white;">저장</v-btn>
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

const toggleDisable = () => {
    disable.value = !disable.value
}
const updateUserInfo = () => {
    user = {
        email:email.value,
        name:name.value,
        position:position.value,
        department:department.value,
    }
    // store.userInfoUpdate()
}
</script>

<style scoped>
</style>