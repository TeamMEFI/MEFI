<template>
    <v-sheet v-if="rendering===true" class="pa-5 d-flex flex-column justify-center alien-center">
        <!-- 정보 조회 및 수정할 수 있는 form -->
        <v-form :disabled="disable">

            <div class="d-flex flex-row justify-center alien-center my-5">
                <label for="photo">
                    <!-- 이미지 src 동적으로 할당하고 싶은데, 왜 반영이 안될까 -->
                    <img class="cursor-pointer" src="@/assets/defaultImg.PNG" style="object-fit: cover; margin: 5px; width: 120px; height: 120px; border-radius: 50%;"/>
                </label>
                <input id="photo" type="file" ref="photo" hidden @change="changePhoto"/>
            </div>

            <v-text-field
                label="email"
                :model-value="email"
                variant="outlined"
                disabled="true"
            ></v-text-field>

            <v-text-field 
                label="name"
                :model-value="name"
                variant="outlined"
            ></v-text-field>

            <v-text-field 
                label="position"
                :model-value="position"
                variant="outlined"
            ></v-text-field>

            <v-text-field 
                label="department"
                :model-value="department"
                variant="outlined"
            ></v-text-field>
            
            <!-- 수정하기 : 누르면 입력창이 활성화 됨 -->
            <div v-if="disable" >
                <v-btn @click="toggleDisable" color="#45566F" variant="flat">수정하기</v-btn>
            </div>
            <!-- 저장 : 입력된 정보 저장됨 -->
            <!-- 취소 : 유저 정보는 변경되지 않음. 입력창이 비활성화됨 -->
            <div v-else>
                <v-btn @click="updateUserInfo" color="#45566F" variant="flat">저장</v-btn>
                <v-btn @click="toggleDisable" color="#45566F" variant="outlined">취소</v-btn>
            </div>
        </v-form>
    </v-sheet>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useUserStore } from "../../../stores/user";
const store = useUserStore()
const rendering = ref(false)
const email = ref("")
const name = ref("")
const position = ref("")
const department = ref("")
const img = ref("")

onMounted(()=>{
    email.value = store.userInfo.email
    name.value = store.userInfo.name
    if (store.userInfo.position == ""){
        position.value = " "
    }
    else{
        position.value = store.userInfo.position
    }
    if(store.userInfo.dept == ""){
        department.value = " "
    }else{
        department.value = store.userInfo.dept
    }
    img.value = "@/assets/defaultImg.PNG"
    rendering.value = true
})

const disable = ref(true)

// 수정될 정보


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