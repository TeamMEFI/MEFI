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
    border: 1px solid black;
    height: 400px;
}
#body-content{
    border: 1px solid black;
    height: 400px;
}
</style>
