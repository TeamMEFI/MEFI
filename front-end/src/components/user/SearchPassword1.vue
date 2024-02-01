<template>
    <v-sheet class="w-30 ma-auto pa-12 d-flex flex-column justify-center" style="border-radius: 10px;">
        <p class="text-center">
            비밀번호를 찾고자하는 이메일을 입력하세요.
        </p>
        <v-form>
            <v-text-field
                label="이메일"
                v-model="email"
                :rules="rule_email"
            ></v-text-field>
            <div class="d-flex align-center justify-center">
                <v-btn :disabled="email_check" @click="goSearchPassword">다음</v-btn>
            </div>
        </v-form>
    </v-sheet>
</template>

<script setup>
import { ref, watch } from "vue"
import { useRouter } from "vue-router"
const router = useRouter()
const email = ref("")
const email_check = ref(true)
watch(
    ()=>email.value,
    ()=>{
        if (regex_email.test(email.value)) {
            email_check.value = false
        }
    }
)
const rule_email = [
    value => !!value || '필수 항목 입니다.',
    value => (value && regex_email.test(value)) || '이메일 주소를 정확히 입력해주세요',
]
const regex_email = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
const goSearchPassword = () => {
    router.push({name:"search-password2"})
}
</script>

<style scoped>
</style>