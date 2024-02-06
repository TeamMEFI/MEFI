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
                v-model="email"
                variant="outlined"
                disabled="true"
            ></v-text-field>

            <v-text-field 
                label="name"
                :model-value="name"
                v-model="name"
                variant="outlined"
            ></v-text-field>

            <v-text-field 
                label="position"
                :model-value="position"
                v-model="position"
                variant="outlined"
            ></v-text-field>

            <v-text-field 
                label="department"
                :model-value="department"
                v-model="department"
                variant="outlined"
            ></v-text-field>
            
            <!-- 수정하기 : 누르면 입력창이 활성화 됨 -->
            <div v-if="disable">
                <v-btn @click="toggleDisable" class="w-100" color="#45566F" variant="flat" >수정하기</v-btn>
            </div>
            <!-- 저장 : 입력된 정보 저장됨 -->
            <!-- 취소 : 유저 정보는 변경되지 않음. 입력창이 비활성화됨 -->
            <div v-else class="d-flex flex-row justify-space-around">
                <v-btn @click="updateUserinfo" color="#45566F" variant="flat" class="w-40">저장</v-btn>
                <v-btn @click="toggleDisable" color="#45566F" variant="outlined" class="w-40">취소</v-btn>
            </div>
        </v-form>
    </v-sheet>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useUserStore } from "../../../stores/user";
import { userModify } from "../../../api/user"
const store = useUserStore()

// 기능 : 변수 할당 후 렌더링 되도록
const rendering = ref(false)

// 수정될 정보
const email = ref("")
const name = ref("")
const position = ref("")
const department = ref("")
const img = ref("")

// 변수 선언 및 값 저장
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

// 기능 : v-form 비활성화 토글
const disable = ref(true)
const toggleDisable = () => {
    disable.value = !disable.value
}

// 회원 정보를 수정하는 rest api
// 유효성 검사 후 api
const updateUserinfo = async () => {
    if (window.confirm('회원 정보를 수정하시겠습니까?')){
        const user = {
            name:name.value,
            position:position.value,
            dept:department.value,
            imgUrl:img.value,
        }
        await userModify(user,(res)=>{
            console.log(res)
            // res userinfo store
            store.userInfo.name = name.value
            store.userInfo.position = position.value
            store.userInfo.dept = department.value
            store.userInfo.imgUrl = img.value
            disable.value = true
        },(err)=>{
            console.log(err)
        })
    }
}
</script>

<style scoped>
</style>