<template>
    <div class="d-flex flex-row justify-space-between pa-2" style="background-color: #45566F; color: white;">
        <span style="font-size: large;">알림</span>
        <div class="d-flex flex-row">
            <!-- 전체 읽음 표현 어떻게 할지 -->
            <div class="d-flex flex-row alien-center justify-center mr-2">
                <input type="checkbox" id="checkbox" v-model="allread" class="pa-0 ma-0">
                <label for="checkbox" class="pa-0 ma-0">전체읽음</label>
            </div>

            <span @click="close" class="cursor-pointer mx-2" style="font-size: large;">X</span>
        </div>
    </div>
</template>

<script setup>
import { ref, watch } from "vue"

const emit = defineEmits(['close','readAll'])
const close = () => {
    emit('close')
}

const allread = ref(false)
watch(allread, async()=>{
    if(allread.value == true){
        emit('readAll')
    }
})
</script>

<style scoped>
#checkbox{
    display: flex;
    flex-direction: row;
}
</style>