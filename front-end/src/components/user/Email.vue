<template>
    <v-sheet class="w-30 mx-auto pa-12 ma-10" style="border-radius: 10px;">
        <v-form @submit.prevent="vertificate">
            <div>인증 번호를 입력해주세요</div>
            <v-text-field
                label="인증 번호"
                v-model="email_verification"
                hide-details="auto"
                type="email"
            ></v-text-field>
            <v-btn type="submit">인증 하기</v-btn>
        </v-form>
    </v-sheet>
</template>

<script setup>
import { ref } from "vue"
import { useRouter } from "vue-router"


const router = useRouter();
const email_verification = ref("")
const vertificate = () => {
    // api : 인증번호 비교
    // 돌아갔을때 데이터가 유지되어야함
    router.go(-1);
}
</script>

<style scoped>
</style>