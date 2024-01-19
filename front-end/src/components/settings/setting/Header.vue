<template>
    <div>
        <span>설정</span>
        <span @click="close" id="close">X</span>
    </div>
</template>

<script setup>
const emit = defineEmits(['close'])
const close = () => {
    emit('close')
}
</script>

<style scoped>
*{
    font-size: larger;
    background-color: rgb(73, 84, 100);
    color: white;
}
#close{
    cursor: pointer;
}
</style>