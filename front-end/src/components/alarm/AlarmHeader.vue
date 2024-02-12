<template>
    <div class="d-flex flex-row justify-space-between pa-2" style="background-color: #45566F; color: white;">
        <span style="font-size: large;">알림</span>
        <div class="d-flex flex-row">
            <!-- 전체 읽음 체크박스 -->
            <div class="d-flex align-items-center mr-2" @click.stop>
                <input type="checkbox" id="checkbox" v-model="allread" class="pa-0 ma-0" @click.stop>
                <label for="checkbox" @click.stop>전체읽음</label>
            </div>
            <!-- 닫힘 버튼 -->
            <span @click.stop="close" class="cursor-pointer mx-2" style="font-size: large;">X</span>
        </div>
    </div>
</template>

<script setup>
import { ref, watch } from "vue"

// 닫힘
const emit = defineEmits(['close','readAll'])
const close = () => {
    emit('close')
}

// 전체 읽음
const allread = ref(false)
watch(allread, async()=>{
    if(allread.value == true){
        emit('readAll')
    }
})
</script>

<style scoped>
</style>