<template>
    <v-container class="bgcolor-setting w-100 h-100 d-flex flex-column aling-center justify-space-between">
        <div class="d-flex justify-center mb-10"><img class="body-img-img elevation-20" src="../../assets/sampleImg.PNG" alt=""></div>
        <div class="h-50">
            <div class="list-bg-color h-100 elevation-20">
                <v-toolbar color="#2A4770" >
                    <v-toolbar-title class="font-weight-bold text-h5">TeamList</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-btn icon>
                        <v-icon>mdi-dots-vertical</v-icon>
                    </v-btn>
                </v-toolbar>
                <v-list :items="items"></v-list>
            </div>
        </div>
        <div class="h-50">팀캘린더</div>
    </v-container>
</template>

<script setup>
import { ref } from 'vue';

const items = ref([
        {
          title: 'Item #1',
          value: 1,
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
.body-img-img{
    width: 300px;
    height: 300px;
    border-radius: 50%;
    margin: 10px;
}

</style>