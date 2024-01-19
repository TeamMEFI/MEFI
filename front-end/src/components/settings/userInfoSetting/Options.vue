<template>
    <div>
        <div id="option-item" @click="optionChange(1)">회원 정보</div>
        <div id="option-item" @click="optionChange(2)">비밀 번호 변경</div>
        <div id="option-item" @click="optionChange(3)">회원 탈퇴</div>
    </div>
</template>

<script setup>
const emit = defineEmits(['changeOption'])
const optionChange = (num)=>{
    emit('changeOption',num)
}
</script>

<style scoped>
#option-item{
    text-align: center;
    margin: 10px;
    padding: 10px 0px;
    cursor: pointer;
}
</style>