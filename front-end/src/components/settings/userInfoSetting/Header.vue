<template>
    <div>
        <!-- 헤더창에 띄울 제목 : 옵션에 따라 제목 변경 -->
        <span :key="props.option" v-if="props.option===1">회원 정보 조회 및 수정</span>
        <span :key="props.option" v-if="props.option===2">비밀 번호 변경</span>
        <span :key="props.option" v-if="props.option===3">회원 탈퇴</span>
        <!-- close icon -->
        <span @click="close" id="close">X</span>
    </div>
</template>

<script setup>
const props = defineProps({
    option: Number
})
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