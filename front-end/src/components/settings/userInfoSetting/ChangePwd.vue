<template>
    <div>
        <v-form>
            <!-- 현재 비밀번호 입력 -->
            <v-text-field
                label="현재 비밀번호"
            ></v-text-field>
            <!-- 새로운 비밀 번호 입력  -->
            <v-text-field
                label="새로운 비밀번호"
            ></v-text-field>
            <!-- 새로운 비밀 번호 확인 -->
            <v-text-field
                label="새로운 비밀번호 확인"
            ></v-text-field>
            <!-- 저장 : 유효성 검사와 현재 비밀 번호가 일치한 경우, alert창에서 '예'를 누를 경우, 비밀 번호 변경 -->
            <v-btn @click="changePWD">저장</v-btn>
        </v-form>
    </div>
</template>

<script setup>
const changePWD = () => {
    alert('비밀 번호를 변경하시겠습니까?')
    
}
</script>

<style scoped>
</style>