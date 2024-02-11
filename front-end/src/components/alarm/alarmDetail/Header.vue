<template>
    <div class="d-flex flex-row justify-space-between pa-2" style="background-color: #45566F; color: white; min-width: 400px;">
        <span style="font-size: large;">알림</span>
        <span @click="close" class="cursor-pointer mx-2" style="font-size: large; padding: 0px 2px;">X</span>
    </div>
</template>

<script setup>
const emit = defineEmits(['close'])
const close = () => {
    console.log('알림 단일 헤더')
    emit('close')
}
</script>

<style scoped>
</style>