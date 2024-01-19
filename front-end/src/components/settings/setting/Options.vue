<template>
    <div id="option-container">
        <div id="option-item" @click="optionChange(1)">알림 설정</div>
        <div id="option-item" @click="optionChange(2)">입출력 설정</div>
    </div>
</template>

<script setup>
const emit = defineEmits(['changeOption'])
const optionChange = (num) => {
    emit('changeOption', num)
}
</script>

<style scoped>
#option-item{
    text-align: center;
    margin: 10px;
    cursor: pointer;
    border: 1px solid black;
}
</style>