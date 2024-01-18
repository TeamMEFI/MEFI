<template>
  <div :class="['conference-view', overlay]">
    <div id="conference-video">
      <ConferenceVideo :overlay="overlay" />
    </div>
    <div id="conference-document">
      <ConferenceDocument />
      <!-- 임시 Overlay 변경 버튼 -->
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
const overlay = ref("conference-view1");

// overlay 설정 변경 메서드
const changeOverlay = (event) => {
  overlay.value = event.target.value;
}
</script>

<style scoped>
/* 레이아웃 배치 공통 스타일 */
.conference-view {
  display: flex;
  min-height: 100vh;
  background-color: #555555;
}

/* 1번 레이아웃 배치 스타일 */
.conference-view1 {
  justify-content: center;
  padding: 40px 0;
}

/* 2번 레이아웃 배치 스타일 */
.conference-view2 {
  flex-direction: row-reverse;
  justify-content: center;
  padding: 40px 0;
}

/* 3번 레이아웃 배치 스타일 */
.conference-view3 {
  flex-direction: column;
  align-items: center;
}

#conference-video {
  display: flex;
  padding: 0 20px;
  border-radius: 10px;
}

#conference-document {
  display: flex;
  margin: 0 20px;
  background-color: white;
}

/* 사용하지 말 것 */
@keyframes rotate {
	to {
		transform: rotate(360deg);
    transform: scale(0.2);
	}
}

.toRight {
	animation: rotate 2s linear infinite;
	transform-origin: 50% 50%;
}
</style>
