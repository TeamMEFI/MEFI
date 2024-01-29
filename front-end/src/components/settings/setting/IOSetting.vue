<template>
    <div>
        <h1>입출력 설정</h1>
        <div>
            <div id="item">
                <div>마이크 설정</div>
                <div>
                    <!-- 마이크 선택 -->
                    <v-select
                        :items="['마이크1', '마이크2', '마이크3']"
                    ></v-select>
                    <!-- 마이크 사운드 조절 -->
                    <v-slider
                        v-model="volumeMic"
                        prepend-icon="mdi-volume-high"
                    ></v-slider>
                </div>
            </div>
            <div id="item">
                <div>스피커 설정</div>
                <div>
                    <!-- 스피커 설정 -->
                    <v-select
                        :items="['스피커1', '스피커2', '스피커3']"
                    ></v-select>
                    <!-- 스피커 사운드 설정 -->
                    <v-slider
                        v-model="volumeSpeaker"
                        prepend-icon="mdi-volume-high"
                    ></v-slider>
                </div>
            </div>
            <div id="item">
                <div>카메라 설정</div>
                <div>
                    <!-- 카메라 설정 -->
                    <v-select
                        :items="['카메라', '카메라', '카메라']"
                    ></v-select>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue"

// 마이크 볼륨
const volumeMic = ref(10)
// 스피커 볼륨
const volumeSpeaker = ref(90)
</script>

<style scoped>
#item{
    display: grid;
    grid-template-columns: 2fr 8fr
}
</style>