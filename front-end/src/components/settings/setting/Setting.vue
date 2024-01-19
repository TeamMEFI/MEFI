<template>
    <div id="setting-container">
        <header>
            <Header id="setting-header" @close="close"></Header>
        </header>
        <main id="setting-body">
            <OptionsVue id="body-option" @change-option="optionChange"></OptionsVue>
            <div id="body-content">
                <AlarmSettingVue v-show="option===1"></AlarmSettingVue>
                <IOSettingVue v-show="option===2"></IOSettingVue>
            </div>
        </main>
    </div>
</template>

<script setup>
import Header from "./Header.vue";
import OptionsVue from "./Options.vue";
import AlarmSettingVue from "./AlarmSetting.vue";
import IOSettingVue from "./IOSetting.vue";
import { ref } from "vue";

const option = ref(1)
const optionChange = (num)=>{
    option.value = num
}
const emit = defineEmits(['close'])
const close = () => {
    emit('close')
}
</script>

<style scoped>
#setting-header{
    width: 600px;
    height: 30px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    padding: 10px;
}
#setting-body{
    display: grid;
    grid-template-columns: 2fr 8fr
}
#body-option{
    background-color: rgb(96, 106, 121);
    color: white;
}
#body-content{
    height: 400px;
}
</style>
