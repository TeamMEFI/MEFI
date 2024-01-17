<template>
  <div :class="overlay">
    <div id="conference-video">
      <ConferenceVideo :overlay="overlay" />
    </div>
    <div id="conference-document">
      <ConferenceDocument />
      <div>
        <button @click="changeOverlay" value="conference-view1">1</button>
        <button @click="changeOverlay" value="conference-view2">2</button>
        <button @click="changeOverlay" value="conference-view3">3</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ConferenceVideo from '@/components/conference/ConferenceVideo.vue'
import ConferenceDocument from '@/components/conference/ConferenceDocument.vue'

// overlay 설정
const overlay = ref("conference-view3");

// overlay 설정 변경 메서드
const changeOverlay = (event) => {
  overlay.value = event.target.value;
}
</script>

<style scoped>
.conference-view1 {
  display: flex;
  justify-content: center;
  min-height: 80vh;
  padding: 60px 0;
  background-color: #555555;
}

.conference-view2 {
  display: flex;
  flex-direction: row-reverse;
  justify-content: center;
  min-height: 80vh;
  padding: 60px 0;
  background-color: #555555;
}

.conference-view3 {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 80vh;
  padding: 40px 0;
  background-color: #555555;
}

#conference-video {
  display: flex;
  padding: 20px;
}

#conference-document {
  display: flex;
  margin: 20px;
  background-color: white;
}
</style>
