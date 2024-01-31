<template>
  <h1>404 Not Found</h1>
  <p>3초 후 메인 페이지로 이동</p>
</template>

<script setup>
import { useRouter } from "vue-router"

const router = useRouter()

setTimeout(() => {
    router.push({name: 'main'})
}, 3000)
</script>

<style></style>
