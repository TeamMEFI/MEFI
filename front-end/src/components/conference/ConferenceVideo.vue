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
          @click="updateMainVideoStreamManager(sub)"
        />
      </div>
      <v-infinite-scroll id="chatBox" :height="100">
        <template v-for="chat in chats">
          <div>{{ chat }}</div>
        </template>
        <template v-slot:loading></template>
      </v-infinite-scroll>
      <input v-model="chatInput" />
      <v-btn @click="sendChat(chatInput)" class="my-2" rounded="sm" size="large">Send chat</v-btn>
      <v-btn @click="leaveSession" class="my-2" rounded="sm" size="large">Leave session</v-btn>
      <v-btn @click="check(mySessionId)" class="my-2" rounded="sm" size="large">Check</v-btn>
    </div>
    <!-- 내 카메라 아직 켜지지 않았으면 로딩 스피너 출력 -->
    <div v-else class="loading">
      <v-progress-circular indeterminate size="64" width="6"></v-progress-circular>
      <v-btn @click="joinSession" class="my-2" rounded="sm" size="large">Join session</v-btn>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onBeforeUnmount, onMounted, defineProps, onUpdated, watch } from 'vue'
import { OpenVidu } from 'openvidu-browser'
import UserVideo from './UserVideo.vue'
import { useRouter } from 'vue-router'

axios.defaults.headers.post['Content-Type'] = 'application/json'

const APPLICATION_SERVER_URL = process.env.NODE_ENV === 'production' ? '' : 'http://localhost:5000/'

const router = useRouter()
const props = defineProps({
  videoStatus: Object
})

// 1, 2번 layout은 true | 3번 layout은 false
const isSide = ref(true)

// 본인 마이크 입력 인식
const onSpeak = ref(false)

// 말하고 있는 참가자 id 배열
const onSpeakSub = ref([])

const chatInput = ref('')
const chats = ref([])

const OVCamera = ref(null)
const OVScreen = ref(null)

const sessionCamera = ref(undefined)
const sessionScreen = ref(undefined)

const mainStreamManager = ref(null)
const publisher = ref(null)
const publisherScreen = ref(null)
const subscribers = ref([])

// Join form
const mySessionId = ref('Sessios')
const myUserName = ref('Participant' + Math.floor(Math.random() * 100))

onMounted(() => {
  joinSession()

  // 레이아웃에 따라 ref 변수 변경
  changeOverlay()
})

onUpdated(() => {
  // 레이아웃에 따라 ref 변수 변경
  changeOverlay()
})

// 레이아웃에 따라 ref 변수 변경
const changeOverlay = () => {
  if (props.videoStatus.overlay.slice(-1) === '3') {
    isSide.value = false
  } else {
    isSide.value = true
  }
}

// 화면 공유 여부 확인
watch(
  () => props.videoStatus.screenShared,
  () => {
    publishScreenShare()
  }
)

// 세션 나가기 여부 확인
watch(
  () => props.videoStatus.leaveSession,
  () => {
    leaveSession()
  }
)

// 채팅 보내는 함수
const sendChat = (content) => {
  // 입력값이 없을 경우 pass
  if (!content) return

  // session signal 메서드를 통해
  // 세션 내의 참가자에게 'chat' 타입의 시그널을 임의의 데이터와 보냄
  sessionCamera.value
    .signal({
      data: `${myUserName.value}: ${content}`,
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

// 세션 참가
const joinSession = () => {
  OVCamera.value = new OpenVidu()
  OVScreen.value = new OpenVidu()

  // 마이크 입력 빈도와 제한 조절
  OVCamera.value.setAdvancedConfiguration({
    publisherSpeakingEventsOptions: {
      interval: 100, // Frequency of the polling of audio streams in ms (default 100)
      threshold: -50 // Threshold volume in dB (default -50)
    }
  })

  // 카메라 세션과 화면 공유 세션을 따로 생성
  sessionCamera.value = OVCamera.value.initSession()
  sessionScreen.value = OVScreen.value.initSession()

  // 카메라 스트림 생성
  sessionCamera.value.on('streamCreated', ({ stream }) => {
    if (stream.typeOfVideo == 'CAMERA') {
      const subscriber = sessionCamera.value.subscribe(stream)
      subscribers.value.push(subscriber)
    }
  })

  // 화면 공유 스트림 생성
  sessionScreen.value.on('streamCreated', ({ stream }) => {
    if (stream.typeOfVideo == 'SCREEN') {
      // Subscribe to the Stream to receive it. HTML video will be appended to element with 'container-screens' id
      const subscriberScreen = sessionScreen.value.subscribe(stream)
      subscribers.value.push(subscriberScreen)
    }
  })

  // 카메라 스트림 삭제
  sessionCamera.value.on('streamDestroyed', ({ stream }) => {
    const index = subscribers.value.indexOf(stream.streamManager, 0)

    if (index >= 0) {
      subscribers.value.splice(index, 1)
    }
  })

  // 화면 공유 스트림 삭제
  sessionScreen.value.on('streamDestroyed', ({ stream }) => {
    const index = subscribers.value.indexOf(stream.streamManager, 0)

    if (index >= 0) {
      subscribers.value.splice(index, 1)
    }
  })

  sessionCamera.value.on('exception', ({ exception }) => {
    console.warn(exception)
  })

  // 마이크 입력 시작 이벤트
  sessionCamera.value.on('publisherStartSpeaking', (event) => {
    onSpeak.value = true
    // 말하고 있는 사람 connection id를 onSpeakSub 배열에 삽입
    onSpeakSub.value.push(event.connection.connectionId)
  })

  // 마이크 입력 종료 이벤트
  sessionCamera.value.on('publisherStopSpeaking', (event) => {
    onSpeak.value = false
    // onSpeakSub 배열에서 마이크 입력이 없는 connection id를 삭제
    onSpeakSub.value.splice(event.connection.connectionId, 1)
  })

  // 미디어 서버와 카메라 세션을 연결
  getToken(mySessionId.value).then((token) => {
    sessionCamera.value
      .connect(token, { clientData: myUserName.value })
      .then(() => {
        const newPublisher = OVCamera.value.initPublisher(undefined, {
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

        sessionCamera.value.publish(publisher.value)
      })
      .catch((error) => {
        console.log('There was an error connecting to the session:', error.code, error.message)
      })
  })

  // 미디어 서버와 화면 공유 세션을 연결
  getToken(mySessionId.value).then((tokenScreen) => {
    // Create a token for screen share
    sessionScreen.value
      .connect(tokenScreen, { clientData: myUserName.value })
      .then(() => {
        console.log('Session screen connected')
      })
      .catch((error) => {
        console.warn(
          'There was an error connecting to the session for screen share:',
          error.code,
          error.message
        )
      })
  })

  // type이 chat인 signal을 받을 때 chats 배열에 data 삽입
  sessionCamera.value.on('signal:chat', (event) => {
    chats.value.push(event.data)
    console.log(event.data)
    console.log(event.from)
    console.log(event.type)
  })

  window.addEventListener('beforeunload', leaveSession)
}

// 세션 퇴장
const leaveSession = async () => {
  if (sessionCamera.value) sessionCamera.value.disconnect()
  if (sessionScreen.value) sessionScreen.value.disconnect()

  sessionCamera.value = null
  sessionScreen.value = null
  mainStreamManager.value = null
  publisher.value = null
  publisherScreen.value = null
  subscribers.value = []
  OVCamera.value = null

  check(mySessionId.value);
}

// 화면 공유
const publishScreenShare = () => {
  // 화면 공유 초기 설정
  const publisherScreen = OVScreen.value.initPublisher(undefined, {
    audioSource: undefined,
    videoSource: 'screen',
    resolution: '640x480',
    frameRate: 30,
    insertMode: 'APPEND',
    mirror: false
  })

  // 화면 공유 권한이 있을 경우
  publisherScreen.once('accessAllowed', (event) => {
    // 화면 공유를 중지하면
    // 화면 공유 스트림 삭제 후 카메라 스트림 생성
    publisherScreen.stream
      .getMediaStream()
      .getVideoTracks()[0]
      .addEventListener('ended', () => {
        console.log('User pressed the "Stop sharing" button')
        sessionScreen.value.unpublish(publisherScreen)
        sessionCamera.value.publish(publisher.value)
      })

    // 카메라 스트림 삭제 및 화면 공유 스트림 생성
    sessionCamera.value.unpublish(publisher.value)
    sessionScreen.value.publish(publisherScreen)
  })

  publisherScreen.once('accessDenied', (event) => {
    console.error('Screen Share: Access Denied')
  })
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

const emit = defineEmits(['endConference'])

// 회의가 종료되었는지 확인하는 메서드
// response.status가 200이면 회의 진행 중
// response.status가 204이면 회의 종료
const check = async (sessionId) => {
  const response = await axios.post( 
    APPLICATION_SERVER_URL + 'api/sessions/' + sessionId + '/',
    {},
    {
      headers: { 'Content-Type': 'application/json' }
    }
  )

  // 회의 종료 시 상위 컴포넌트에 알림
  if (response.status == 204) {
    emit('endConference')
  } else {
    router.push({ name: 'home' })
  }
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
  padding: 20px;
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
  /* animation: sizeup linear; */
  border: 2px solid green;
}
</style>
