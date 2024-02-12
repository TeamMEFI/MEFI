<template>
    <div>
        <div class="d-flex flex-column justify-center alien-center pa-6">
            <div class="d-flex flex-row alien-center mb-1" style="border: 1px solid rgb(199, 199, 199); border-radius: 10px;">
                <div class="ma-0 pa-3" style="text-align: center; width: 80px; border-top-left-radius: 10px; border-bottom-left-radius: 10px; background-color: rgb(213, 213, 213);">작성자</div>
                <div class="pa-3">{{ props.alarm.writer }}</div>
            </div>
            <div class="d-flex flex-row alien-center" style="border: 1px solid rgb(199, 199, 199); border-radius: 10px;">
                <div class="ma-0 pa-3" style="text-align: center; width: 80px; border-top-left-radius: 10px; border-bottom-left-radius: 10px; background-color: rgb(213, 213, 213);">작성 일자</div>
                <div class="pa-3">{{ props.alarm.day }}</div>
            </div>
        </div>
        <div style="min-height: 300px; padding: 30px; display: flex;">
            <span style="padding: auto; margin: auto;">{{ props.alarm.title }}</span>
        </div>
        <div class="d-flex flex-row justify-space-around pa-5">
            <v-btn @click="read" color="#45566F" variant="flat" class="w-40">확인</v-btn>
        </div>
    </div>
</template>

<script setup>
const props = defineProps({
    alarm:Object
})
const emit = defineEmits(['close'])
const read = () => {
    // api alarm alarmId
    emit('close')
}
</script>

<style scoped>
</style>