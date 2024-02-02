<template>
    <v-card class="h-100 pa-10">
        <v-card-title class="d-flex h-5 align-center pa-2">
            <p class="text-h5 font-weight-black">회의 예약</p>
            <v-spacer></v-spacer>
            <v-btn>회의 예약하기</v-btn>
        </v-card-title>
        <v-card-item class="pa-3 h-40">
            <form @submit.prevent="submit">
                <v-container>
                    <v-row>
                        <v-col cols="6">
                            <p>일정 종류</p>
                        </v-col>
                        <v-col cols="3">
                            <p>시작 시간</p>
                        </v-col>
                        <v-col cols="3">
                            <p>종료 시간</p>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="6">
                            <v-select
                                v-model="selectvalue"
                                :items="typeschedule"
                                hide-selected="1"
                                variant="outlined"
                                density="compact"
                                hide-details="true"
                                disabled
                            ></v-select>
                        </v-col>
                        <v-col cols="6">
                            <v-row>
                                <v-col cols="3">
                                    <v-select
                                        :items="starthours"
                                        variant="outlined"
                                        density="compact"
                                        hide-details="true"
                                    ></v-select>
                                </v-col>
                                <v-col cols="3">
                                    <v-select
                                        :items="startmins"
                                        variant="outlined"
                                        density="compact"
                                        hide-details="true"
                                    ></v-select>
                                </v-col>
                                <v-col cols="3">
                                    <v-select
                                        :items="endhours"
                                        variant="outlined"
                                        density="compact"
                                        hide-details="true"
                                    ></v-select>
                                </v-col>
                                <v-col cols="3">
                                    <v-select
                                        :items="endmins"
                                        variant="outlined"
                                        density="compact"
                                        hide-details="true"
                                    ></v-select>
                                </v-col>
                            </v-row>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="6">
                            <p>요약</p>
                        </v-col>
                        <v-col cols="6">
                            <p>설명</p>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="6">
                            <v-text-field
                                label="Summary"
                                variant="outlined"
                                required
                            ></v-text-field>
                        </v-col>
                        <v-col cols="6">
                            <v-text-field
                                label="Summary"
                                variant="outlined"
                                required
                            ></v-text-field>
                        </v-col>
                    </v-row>
                </v-container>
            </form>
        </v-card-item>
        <v-card-title class="d-flex h-5 align-center pa-2">
            <p class="text-h5 font-weight-black">회의 관련 문서</p>
        </v-card-title>
        <div class="h-50 pa-3">
            <SearchDoc/>
        </div>
    </v-card>
</template>

<script setup>
import SearchDoc from '@/components/docs/SearchDoc.vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
const starthours = ref(['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'])
const startmins = ref(['00', '05', '10', '15', '20', '25', '30', '35', '40', '45', '50', '55'])
const endhours = ref(['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'])
const endmins = ref(['00', '05', '10', '15', '20', '25', '30', '35', '40', '45', '50', '55'])
const typeschedule = ref(['회의'])
const selectvalue = ref('회의')
const router = useRouter()

</script>

<style lang="scss" scoped>

</style>