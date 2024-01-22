<template>
    <!-- 회원 탈퇴 창 -->
    <div id="delete-container">
        <div id="delete-main">
            <p id="body-title">회원 탈퇴 하시겠습니까?</p>
            <!-- 예 : 회원 탈퇴함 -->
            <!-- 아니오 : 회원 탈퇴하지 않고 해당 모달 창이 닫힘 -->
            <div id="body-button">
                <v-btn @click="userDelete" style="background-color: #E53935; color: white;">예</v-btn>
                <v-btn @click="goOut" style="background-color: #BDBDBD; color: white;">아니오</v-btn>
            </div>
        </div>
    </div>
</template>

<script setup>
const userDelete = () => {
    // user delete api
}
const emit = defineEmits(['close'])
const goOut = () => {
    emit('close')
}
</script>

<style scoped>
#delete-container{
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-content: center;
}
#delete-main{
    margin: 150px auto;
}
</style>
