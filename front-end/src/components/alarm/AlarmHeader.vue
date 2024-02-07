<template>
    <div class="d-flex flex-row justify-space-between pa-2" style="background-color: #45566F; color: white;">
        <span>알림</span>
        <div class="d-flex flex-row">
            <!-- 전체 읽음 표현 어떻게 할지 -->
            <div class="d-flex flex-row">
                <input type="checkbox" id="checkbox" v-model="allread">
                <label for="checkbox">전체읽음</label>
            </div>
            <span @click="close">X</span>
        </div>
    </div>
</template>

<script setup>
import { ref, watch } from "vue"
import { alarmReadAll } from "@/api/alarm"

const emit = defineEmits(['close','readAll'])
const close = () => {
    emit('close')
}
const allread = ref(false)
watch(allread, async()=>{
    if(allread.value == true){
        await alarmReadAll(
            (res)=>{console.log(res),
            (err)=>{console.log(err)}}
        )
        await emit('readAll')
    }
})
</script>

<style scoped>
#checkbox{
    display: flex;
    flex-direction: row;
}
</style>