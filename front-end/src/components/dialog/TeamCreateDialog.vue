<template>
    <v-card class="bgcolor-setting w-100 h-100">
        <v-card-title class="ma-0 pa-0">
            <v-toolbar color="#2A4770" >
                <v-toolbar-title class="font-weight-bold text-h5">Team Create</v-toolbar-title>
                <v-spacer></v-spacer>
            </v-toolbar>
        </v-card-title>
        <v-card-text>
            <v-container class="h-85">
                <v-row>
                    <v-col cols="5">
                        <v-text-field
                        label="Team Name"
                        variant="solo"
                        required
                        ></v-text-field>
                    </v-col>
                    <v-col cols="7">
                        <v-text-field
                        label="Team Explanation"
                        variant="solo"
                        required
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row class="h-100">
                    <v-col cols="5" class="h-100">
                        <div class="bg-white h-100 elevation-3">
                            <v-toolbar color="#2A4770" >
                                <v-toolbar-title class="font-weight-bold text-h5">New Join</v-toolbar-title>
 
                                <v-text-field
                                    :loading="loading"
                                    density="compact"
                                    variant="solo"
                                    append-inner-icon="mdi-magnify"
                                    single-line
                                    hide-details
                                    @click:append-inner="onClick"
                                    class="me-3"
                                ></v-text-field>
                            </v-toolbar>
                            <v-list :items="searchList"></v-list>
                        </div>
                    </v-col>
                    <v-col cols="2" class="d-flex justify-space-evenly flex-column align-center">
                        <v-btn icon>
                            <v-icon>mdi-account-plus</v-icon>
                        </v-btn>
                        <v-btn icon>
                            <v-icon>mdi-account-minus</v-icon>
                        </v-btn>
                        <v-btn icon>
                            <v-icon>mdi-delete-sweep</v-icon>
                        </v-btn>
                    </v-col>
                    <v-col cols="5" class="h-100">
                        <div class="bg-white h-100 elevation-3">
                            <v-toolbar color="#2A4770" >
                                <v-toolbar-title class="font-weight-bold text-h5">Added</v-toolbar-title>
                                <v-spacer></v-spacer>
                            </v-toolbar>
                            <v-list :items="joinList"></v-list>
                        </div>
                    </v-col>
                </v-row>
            </v-container>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
            variant="text"
            class="text-h5"
            @click="$emit('closeDialog')"
            >
            Close
            </v-btn>
            <v-btn
            variant="text"
            class="text-h5"
            >
            Save
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script setup>
import { ref } from 'vue';

const joinList = ref([
        {
          title: 'Item #1',
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
const searchList = ref([
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

<style lang="scss" scoped>

</style>