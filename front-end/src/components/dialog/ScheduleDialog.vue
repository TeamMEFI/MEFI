<template>
    <v-card class="bgcolor-setting w-100 h-100">
        <v-card-title class="ma-0 pa-0">
            <v-toolbar color="#2A4770" >
                <v-toolbar-title class="font-weight-bold text-h5">Create Scheldule</v-toolbar-title>
                <v-spacer></v-spacer>
            </v-toolbar>
        </v-card-title>
        <v-card-text>
            <v-container class=" ma-0">
                <v-row class="h-100">
                    <v-col cols="12">
                        <v-container class="pa-0">
                            <v-row>
                                <v-col>
                                    <v-select
                                        label="Choice Type"
                                        :items="typeschedule"
                                        variant="outlined"
                                        density="compact"
                                        hide-details="true"
                                    ></v-select>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-col>
                    <v-col cols="6">
                        <v-container class="pa-0 pe-3">
                            <v-row>
                                <v-col cols="12 py-0">
                                    <p>start</p>
                                </v-col>
                                <v-col>
                                    <v-select
                                        :items="starthours"
                                        variant="outlined"
                                        density="compact"
                                        hide-details="true"
                                    ></v-select>
                                </v-col>
                                <v-col>
                                    <v-select
                                        :items="startmins"
                                        variant="outlined"
                                        density="compact"
                                        hide-details="true"
                                    ></v-select>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-col>
                    <v-col cols="6">
                        <v-container class="pa-0 ps-3">
                            <v-row>
                                <v-col cols="12 py-0">
                                    <p>end</p>
                                </v-col>
                                <v-col>
                                    <v-select
                                        :items="endhours"
                                        variant="outlined"
                                        density="compact"
                                        hide-details="true"
                                    ></v-select>
                                </v-col>
                                <v-col>
                                    <v-select
                                        :items="endmins"
                                        variant="outlined"
                                        density="compact"
                                        hide-details="true"
                                    ></v-select>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-col>
                    <v-col cols="12">
                        <v-text-field
                            label="Summary"
                            variant="outlined"
                            required
                        ></v-text-field>
                    </v-col>
                    <v-col cols="12 pt-0">
                        <v-textarea
                            label="Label"
                            variant="outlined"
                            rows="10"
                            hide-details="ture"
                            >
                        </v-textarea>
                    </v-col>
                </v-row>
            </v-container>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
            variant="text"
            class="text-h5"
            @click="router.back()"
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
import { useRouter } from 'vue-router';
const starthours = ref(['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'])
const startmins = ref(['00', '05', '10', '15', '20', '25', '30', '35', '40', '45', '50', '55'])
const endhours = ref(['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'])
const endmins = ref(['00', '05', '10', '15', '20', '25', '30', '35', '40', '45', '50', '55'])
const typeschedule = ref(['연차', '회의', '출장','기타'])
const router = useRouter()

</script>

<style lang="scss" scoped>

</style>