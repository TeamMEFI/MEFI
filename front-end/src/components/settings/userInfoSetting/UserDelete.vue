<template>
    <div id="delete-container">
        <div id="delete-main">
            <p id="body-title">회원 탈퇴 하시겠습니까?</p>
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
