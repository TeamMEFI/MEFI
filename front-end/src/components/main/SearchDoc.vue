<template>
    <div class="bg-white w-100 h-100 elevation-3 ma-0 pa-0">
        <v-toolbar color="#2A4770" class="elevation-1 w-100" >
            <v-toolbar-title class="font-weight-bold text-h5">This Date's Dox</v-toolbar-title>
            <v-text-field
                density="compact"
                variant="solo"
                append-inner-icon="mdi-magnify"
                single-line
                hide-details
                v-model="keyword"
                @click:append-inner="console.log(keyword)"
                class="me-3 w-10"
            ></v-text-field>
            
        </v-toolbar>
        <v-list :items="docsList"></v-list>
    </div>
</template>

<script setup>
import { ref } from 'vue';
// 검색어
const keyword = ref('hello')
// 검색 API 호출 함수
const searchdocs = () => {
    console.log(1)
}
// 검색 결과 값 List
const docsList = ref([
        {
          title: 'Item #1',
          value: 2,
        },
        {
          title: 'Item #2',
          value: 2,
        },
        {
          title: 'Item #3',
          value: 3,
        },
])


</script>

<style scoped>

</style>