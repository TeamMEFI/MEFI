<template>
    <div>
        <v-form>
            <v-text-field
                label="현재 비밀번호"
            ></v-text-field>

            <v-text-field
                label="새로운 비밀번호"
            ></v-text-field>
            <v-text-field
                label="새로운 비밀번호 확인"
            ></v-text-field>
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