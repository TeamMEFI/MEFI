<template>
    <div>
        <div>이름 {{ store.userInfo.name }}</div>
        <div>아이디 {{ store.userInfo.id }}</div>
        <div>부서 {{ store.userInfo.department }}</div>
        <div>직급 {{ store.userInfo.position }}</div>
    </div>
</template>

<script setup>
import { useUserStore } from "../../../stores/user";
const store = useUserStore()
</script>

<style scoped>
</style>