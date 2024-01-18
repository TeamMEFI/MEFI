<template>
  <div id="main-container">
    <!-- 내 카메라가 켜졌을 때 화상회의 열기 -->
    <div id="session" v-if="mainStreamManager">
      <!-- isSide ref 변수에 따라 class를 동적 할당하여 스타일 변경 -->
      <div id="video-container" ref="videos" :class="[isSide ? 'videos' : 'upSideVideos']">
        <UserVideo
          :stream-manager="mainStreamManager"
          :class="[
            isSide ? 'video' : 'upSideVideo',
            // 마이크 입력을 인식하면 클래스 적용
            onSpeak ? 'toRight' : ''
          ]"
        />
        <UserVideo
          v-for="sub in subscribers"
          :class="[
            isSide ? 'video' : 'upSideVideo',
            // 말하고 있는 참가자에 따라 class 속성 변경
            onSpeakSub.includes(sub.stream.connection.connectionId) ? 'toRight' : ''
          ]"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click.native="updateMainVideoStreamManager(sub)"
        />
      </div>
      <v-infinite-scroll id="chatBox" :height="100" :onLoad="ok">
        <template v-for="chat in chats">
          <div>{{ chat }}</div>
        </template>
        <template v-slot:loading></template>
      </v-infinite-scroll>
      <v-btn @click="sendChat('default')" class="my-2" rounded="sm" size="large">Send chat</v-btn>
      <v-btn @click="leaveSession" class="my-2" rounded="sm" size="large">Leave session</v-btn>
    </div>
    <!-- 내 카메라 아직 켜지지 않았으면 당근 출력 -->
    <div v-else class="loading">
      <v-progress-circular indeterminate size="64" width="6"></v-progress-circular>
      <v-btn @click="joinSession" class="my-2" rounded="sm" size="large">Join session</v-btn>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onBeforeUnmount, onMounted, defineProps, onUpdated, computed } from 'vue'
import { OpenVidu } from 'openvidu-browser'
import UserVideo from './UserVideo.vue'

axios.defaults.headers.post['Content-Type'] = 'application/json'

const APPLICATION_SERVER_URL = process.env.NODE_ENV === 'production' ? '' : 'http://localhost:5000/'

const props = defineProps({
  overlay: String
})

// 1, 2번 layout은 true | 3번 layout은 false
const isSide = ref(true)

// 본인 마이크 입력 인식
const onSpeak = ref(false)

// 말하고 있는 참가자 id 배열
const onSpeakSub = ref([])

const chats = ref(["sd"])

const OV = ref(null)
const session = ref(undefined)
const mainStreamManager = ref(null)
const publisher = ref(null)
const subscribers = ref([])

// Join form
const mySessionId = ref('Sessios')
const myUserName = ref('Participant' + Math.floor(Math.random() * 100))

onMounted(() => {
  joinSession()

  // 레이아웃에 따라 ref 변수 변경
  if (props.overlay.slice(-1) == '3') {
    isSide.value = false
  } else {
    isSide.value = true
  }
})

onUpdated(() => {
  // 레이아웃에 따라 ref 변수 변경
  if (props.overlay.slice(-1) === '3') {
    isSide.value = false
  } else {
    isSide.value = true
  }
})

// 채팅 보내는 함수
const sendChat = (content) => {
  session.value
    .signal({
      data: `${content}`, // Any string (optional)
      to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
      type: 'chat' // The type of message (optional)
    })
    .then(() => {
      console.log('Message successfully sent')
    })
    .catch((error) => {
      console.error(error)
    })
}

const joinSession = () => {
  OV.value = new OpenVidu()

  // 마이크 입력 빈도와 제한 조절
  OV.value.setAdvancedConfiguration({
    publisherSpeakingEventsOptions: {
      interval: 100, // Frequency of the polling of audio streams in ms (default 100)
      threshold: -50 // Threshold volume in dB (default -50)
    }
  })

  session.value = OV.value.initSession()

  // 세션 생성
  session.value.on('streamCreated', ({ stream }) => {
    const subscriber = session.value.subscribe(stream)
    subscribers.value.push(subscriber)
  })

  // 세션 삭제
  session.value.on('streamDestroyed', ({ stream }) => {
    const index = subscribers.value.indexOf(stream.streamManager, 0)

    if (index >= 0) {
      subscribers.value.splice(index, 1)
    }
  })

  session.value.on('exception', ({ exception }) => {
    console.warn(exception)
  })

  // 마이크 입력 시작 이벤트
  session.value.on('publisherStartSpeaking', (event) => {
    onSpeak.value = true
    // 말하고 있는 사람 connection id를 onSpeakSub 배열에 삽입
    onSpeakSub.value.push(event.connection.connectionId)
    console.log('User ' + event.connection.connectionId + ' start speaking')
  })

  // 마이크 입력 종료 이벤트
  session.value.on('publisherStopSpeaking', (event) => {
    onSpeak.value = false
    // onSpeakSub 배열에서 마이크 입력이 없는 connection id를 삭제
    onSpeakSub.value.splice(event.connection.connectionId, 1)
    console.log('User ' + event.connection.connectionId + ' stop speaking')
  })

  getToken(mySessionId.value).then((token) => {
    session.value
      .connect(token, { clientData: myUserName.value })
      .then(() => {
        const newPublisher = OV.value.initPublisher(undefined, {
          audioSource: undefined,
          videoSource: undefined,
          publishAudio: true,
          publishVideo: true,
          resolution: '640x480',
          frameRate: 30,
          insertMode: 'APPEND',
          mirror: false
        })

        mainStreamManager.value = newPublisher
        publisher.value = newPublisher

        session.value.publish(publisher.value)
      })
      .catch((error) => {
        console.log('There was an error connecting to the session:', error.code, error.message)
      })
  })

  // type이 chat인 signal을 받을 때 chats 배열에 data 삽입
  session.value.on('signal:chat', (event) => {
    chats.value.push(event.data)
    console.log(event.data) // Message
    console.log(event.from) // Connection object of the sender
    console.log(event.type) // The type of message ("my-chat")
  })

  window.addEventListener('beforeunload', leaveSession)
}

const leaveSession = () => {
  if (session.value) session.value.disconnect()

  session.value = undefined
  mainStreamManager.value = undefined
  publisher.value = undefined
  subscribers.value = []
  OV.value = undefined

  window.removeEventListener('beforeunload', leaveSession)
}

const updateMainVideoStreamManager = (stream) => {
  if (mainStreamManager.value === stream) return (mainStreamManager.value = stream)
}

const getToken = async (sessionId) => {
  const createdSessionId = await createSession(sessionId)
  return await createToken(createdSessionId)
}

const createSession = async (sessionId) => {
  const response = await axios.post(
    APPLICATION_SERVER_URL + 'api/sessions',
    { customSessionId: sessionId },
    {
      headers: { 'Content-Type': 'application/json' }
    }
  )
  return response.data
}

const createToken = async (sessionId) => {
  const response = await axios.post(
    APPLICATION_SERVER_URL + 'api/sessions/' + sessionId + '/connections',
    {},
    {
      headers: { 'Content-Type': 'application/json' }
    }
  )
  return response.data
}

onBeforeUnmount(() => {
  window.removeEventListener('beforeunload', leaveSession)
})
</script>

<style scoped>
/* 1, 2번 레이아웃 비디오 컨테이너 스타일 */
.videos {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  background-color: black;
  padding: 10px;
  gap: 10px;
  max-width: 600px;
}

/* 1, 2번 레이아웃 비디오 스타일 */
.video {
  display: flex;
  width: 45%;
  height: 100%;
}

/* 3번 레이아웃 비디오 컨테이너 스타일 */
.upSideVideos {
  display: flex;
  position: relative;
  top: 0;
  background-color: black;
  padding: 10px;
  gap: 10px;
}

/* 3번 레이아웃 비디오 스타일 */
.upSideVideo {
  display: flex;
  height: 100px;
}

/* 화면 크기에 따라 스타일 조정 */
@media (max-width: 720px) {
  .videos {
    flex-direction: column;
  }

  .video {
    width: 100%;
  }
}

.loading {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px
}

/* 캠돌리기 파티 */
@keyframes rotate {
  to {
    transform: rotate(360deg);
  }
}

/* 캠 크기 증가 애니메이션 */
@keyframes sizeup {
  to {
    transform: scale(1.2);
  }
}

.toRight {
  /* animation: rotate 2s linear infinite; */
  animation: sizeup linear;
  border: 2px solid green;
}
</style>
