<template>
    <v-list-item class="pa-0">
        <div class="d-flex flex-row justify-space-between pa-3" style="border-bottom: 1px solid rgb(196, 196, 196);">
            <div class="d-flex flex-column">
                <span style="font-size: large;">{{ alarm.title }}</span>
                <span style="font-size: small;">{{ alarm.day }}</span>
            </div>
            <input type="checkbox" class="mr-5">
        </div>
    </v-list-item>
</template>

<script setup>
const props = defineProps({
    alarm:Object,
})
</script>

<style scoped>
</style>