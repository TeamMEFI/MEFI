<template>
  <v-app class="bg-grey">
    <div :class="['conference-view', overlay]">
      <div id="conference-video">
        <ConferenceVideo :videoStatus="videoStatus" @end-conference="changeConferenceState" />
      </div>
      <div id="conference-document">
        <ConferenceDocument :conferenceState="conferenceState" />
      </div>
    </div>
    <v-bottom-sheet md-inset v-model="sheet" >
      <template v-slot:activator="{ props }">
        <div class="text-center" style="position: absolute; bottom: 0; right: 50vw; width: 0px">
          <v-btn v-bind="props" size="small" text="설정"></v-btn>
        </div>
      </template>

      <v-list class="d-flex flex-wrap justify-center">
        <v-list-item>
          <v-btn size="small" text="비디오"></v-btn>
        </v-list-item>
        <v-list-item>
          <v-btn size="small" text="마이크"></v-btn>
        </v-list-item>
        <v-list-item>
          <v-btn size="small" text="화면공유" @click="videoStatus.screenShared = !videoStatus.screenShared"></v-btn>
        </v-list-item>
        <v-list-item class="text-center">
          <v-menu>
            <template v-slot:activator="{ props: menu }">
              <v-btn size="small" v-bind="mergeProps(menu)" text="레이아웃"></v-btn>
            </template>
            <v-list class="text-center">
              <v-list-item @click="changeOverlay('conference-view1')">1번</v-list-item>
              <v-list-item @click="changeOverlay('conference-view2')">2번</v-list-item>
              <v-list-item @click="changeOverlay('conference-view3')">3번</v-list-item>
            </v-list>
          </v-menu>
        </v-list-item>
        <v-list-item>
          <v-btn size="small" text="채팅"></v-btn>
        </v-list-item>
        <v-list-item>
          <v-btn size="small" text="나가기" @click="videoStatus.leaveSession = !videoStatus.leaveSession"></v-btn>
        </v-list-item>
      </v-list>
    </v-bottom-sheet>
  </v-app>
</template>

<script setup>
import { ref, mergeProps } from 'vue'
import ConferenceVideo from '@/components/conference/ConferenceVideo.vue'
import ConferenceDocument from '@/components/conference/ConferenceDocument.vue'

const sheet = ref(false)

// overlay 설정
const overlay = ref('conference-view1')

const conferenceState = ref(true);

const videoStatus = ref({
  overlay: overlay.value,
  screenShared: false,
  videoStatus: true,
  voiceStatus: true,
  leaveSession: false,
})

const changeConferenceState = () => {
  conferenceState.value = false;
}

// overlay 설정 변경 메서드
const changeOverlay = (overlayType) => {
  overlay.value = overlayType
}
</script>

<style scoped>
/* 레이아웃 배치 공통 스타일 */
.conference-view {
  display: flex;
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
    transform: scale(1.5);
  }
}

.toRight {
  animation: rotate 3s linear infinite;
  transform-origin: 50% 50%;
}
</style>
