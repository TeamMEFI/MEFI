<template>
    <!-- 이메일 인증 모달창 -->
    <v-sheet class="w-30 ma-auto d-flex flex-column justify-center" min-width="500" style="border-radius: 10px;">
        <!-- 헤더 : close 버튼 -->
        <div style="display: flex; flex-direction: row; justify-content: space-between;" class="pr-5 pt-5">
            <span></span>
            <span @click="close" style="cursor: pointer;"><font-awesome-icon :icon="['fas', 'xmark']" style="font-size:x-large;"/></span>
        </div>

        <!-- 입력창 -->
        <v-form @submit.prevent="vertificate" class="d-flex flex-column justify-center pa-10 pt-0">
            <!-- 문구 -->
            <div class="d-flex justify-center mb-3">
                <span style="font-size:medium;">인증 번호를 입력해주세요</span>
            </div>

            <!-- 인증 번호 입력창  -->
            <v-text-field
                label="인증 번호"
                v-model="email_verification"
                hide-details="auto"
                type="email"
                variant="outlined"
                class="mb-5"
            ></v-text-field>

            <!-- 인증 번호 확인 버튼 -->
            <v-btn type="sumbit" class="w-100" variant="flat" color="#45566F">인증 하기</v-btn>
        </v-form>
    </v-sheet>
</template>

<script setup>
import { ref } from "vue"
import { useRouter } from "vue-router"
const router = useRouter();

// 이메일 인증 정보
const email_verification = ref("")

// 기능 : 인증번호 확인 api 던지기
// successs : close
// fail : 인증 번호를 다시 확인해주세요
const vertificate = () => {
    // api : 인증번호 비교
    // 돌아갔을때 데이터가 유지되어야함
    router.go(-1);
}

// close 버튼 상위로 전달
const emit = defineEmits(['close'])
const close = () => {
    emit('close')
}
</script>

<style scoped>
</style>