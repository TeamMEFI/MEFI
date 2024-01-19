<template>
    <div>
        <div @click="optionChange(1)">알림 설정</div>
        <div @click="optionChange(2)">입출력 설정</div>
    </div>
</template>

<script setup>
const emit = defineEmits(['changeOption'])
const optionChange = (num) => {
    emit('changeOption', num)
}
</script>

<style scoped>
</style>