<template>
    <div>
        <!-- 선택지 1 : 알림 설정 -->
        <div id="option-item" @click="optionChange(1)">알림 설정</div>
        <!-- 선택지 2 : 입출력 설정 -->
        <div id="option-item" @click="optionChange(2)">입출력 설정</div>
    </div>
</template>

<script setup>
// 목적 : 상위 컴포넌트에 전달
// 기능 : 선택한 선택지를 반영
const emit = defineEmits(['changeOption'])
const optionChange = (num) => {
    emit('changeOption', num)
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