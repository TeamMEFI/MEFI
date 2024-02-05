<template>
    <!-- 이메일 인증 모달창 -->
    <v-sheet class="w-30 ma-auto d-flex flex-column justify-center" min-width="500" style="border-radius: 10px;">
        <!-- 헤더 : close 버튼 -->
        <div style="display: flex; flex-direction: row; justify-content: space-between;" class="pr-5 pt-5">
            <span></span>
            <span style="cursor: pointer;"><font-awesome-icon :icon="['fas', 'xmark']" style="font-size:x-large;"/></span>
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
                v-model="authCode"
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
import { checkEmailCode } from "@/api/user"
import { useRoute, useRouter } from "vue-router"
const route = useRoute()
const router = useRouter()
// 이메일 인증 정보
const authCode = ref("")

// api 보낼때 필요한 데이터
const email = route.params.email

// 기능 : 인증코드 확인 api
// successs : 비밀번호 변경창으로 이동
// fail : 인증 번호를 다시 확인해주세요
const vertificate = async () => {
    const param = {
        "email":email,
        "authCode":authCode.value,
    }
    console.log(param)
    router.push({name:'search-password3'})
    // await checkEmailCode(param, (res)=>{
    //     console.log(res)
    // },(err)=>{
    //     console.log(err)
    // })
}
</script>

<style scoped>
</style>
