<template>
    <div id="setting-container">
        <header>
            <Header id="setting-header"></Header>
        </header>
        <main id="setting-body">
            <OptionsVue id="body-option" @option-change="changeOption"></OptionsVue>
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
const changeOption = (num)=>{
    option.value = num
}
</script>

<style scoped>
#setting-container{
    width: 800px;
    height: 680px;
}
#setting-header{
    height: 30px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    padding: 10px;
}
</style>