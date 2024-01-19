<template>
  <div id="main-container">
    <!-- 내 카메라가 켜졌을 때 화상회의 열기 -->
    <div id="session" v-if="mainStreamManager">
      <div id="session-header">
        <h1 id="session-title"></h1>
        <input
          class="btn btn-large"
          type="button"
          @click="publishScreenShare()"
          :value="screenShared ? 'Stop share' : 'Screen share'"
        />
      </div>
      <div class="row panel panel-default">
        <div class="panel-heading">User Screens</div>
        <div class="panel-body" id="container-screens"></div>
      </div>
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
      <v-infinite-scroll id="chatBox" :height="100">         
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

// 화면 공유 중인지 검사
const screenShared = ref(false)

const chats = ref([])

const OV = ref(null)
const OVScreen = ref(null)

const session = ref(undefined)
const sessionScreen = ref(undefined)

const mainStreamManager = ref(null)
const publisher = ref(null)
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
  if (props.overlay.slice(-1) === '3') {
    isSide.value = false
  } else {
    isSide.value = true
  }
}

// 채팅 보내는 함수
// 현재 임시로 'default'를 보냄
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
  OVScreen.value = new OpenVidu()

  // 마이크 입력 빈도와 제한 조절
  OV.value.setAdvancedConfiguration({
    publisherSpeakingEventsOptions: {
      interval: 100, // Frequency of the polling of audio streams in ms (default 100)
      threshold: -50 // Threshold volume in dB (default -50)
    }
  })

  session.value = OV.value.initSession()
  sessionScreen.value = OVScreen.value.initSession()

  // 화면 공유 기능 전 streamCreated 이벤트
  // session.value.on('streamCreated', ({ stream }) => {
  //   const subscriber = session.value.subscribe(stream)
  //   subscribers.value.push(subscriber)
  // })

  // 세션 생성
  session.value.on('streamCreated', ({ stream }) => {
    if (stream.typeOfVideo == 'CAMERA') {
      // Subscribe to the Stream to receive it. HTML video will be appended to element with 'container-cameras' id
      const subscriber = session.value.subscribe(stream)
      subscribers.value.push(subscriber)
    }
  })

  sessionScreen.value.on('streamCreated', ({ stream }) => {
    if (stream.typeOfVideo == 'SCREEN') {
      // Subscribe to the Stream to receive it. HTML video will be appended to element with 'container-screens' id
      const subscriberScreen = sessionScreen.value.subscribe(stream)
      subscribers.value.push(subscriber)
    }
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
    console.log(onSpeakSub.value)
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
          publishAudio: false,
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

  getToken(mySessionId.value).then((tokenScreen) => {
    // Create a token for screen share
    sessionScreen.value
      .connect(tokenScreen, { clientData: myUserName.value })
      .then(() => {
        console.log('Session screen connected')
        const newPublisher = OVScreen.value.initPublisher(undefined, {
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
        console.warn(
          'There was an error connecting to the session for screen share:',
          error.code,
          error.message
        )
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
  if (sessionScreen.value) sessionScreen.value.disconnect()

  session.value = null
  sessionScreen.value = null
  mainStreamManager.value = null
  publisher.value = null
  subscribers.value = []
  OV.value = null
  screensharing.value = false

  window.removeEventListener('beforeunload', leaveSession)
}

const publishScreenShare = () => {
  // --- 9.1) To create a publisherScreen set the property 'videoSource' to 'screen'
  const publisherScreen = OVScreen.value.initPublisher(undefined, {
    videoSource: 'screen'
  })

  // --- 9.2) Publish the screen share stream only after the user grants permission to the browser
  publisherScreen.once('accessAllowed', (event) => {
    screenSharing.value = true

    // If the user closes the shared window or stops sharing it, unpublish the stream
    publisherScreen.stream
      .getMediaStream()
      .getVideoTracks()[0]
      .addEventListener('ended', () => {
        sessionScreen.value.unpublish(publisherScreen)
        screenSharing.value = false
      })

    session.value.publish(publisherScreen)
  })

  publisherScreen.on('videoElementCreated', function (event) {
    console.log("sdf")
    subscribers.value.push(event.stream)
    event.element['muted'] = true
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

function appendUserData(videoElement, connection) {
  var userData
  var nodeId

  if (typeof connection === 'string') {
    userData = connection
    nodeId = connection
  } else {
    userData = JSON.parse(connection.data).clientData
    nodeId = connection.connectionId
  }

  var dataNode = document.createElement('div')
  dataNode.className = 'data-node'
  dataNode.id = 'data-' + nodeId
  dataNode.innerHTML = '<p>' + userData + '</p>'
  videoElement.parentNode.insertBefore(dataNode, videoElement.nextSibling)
  addClickListener(videoElement, userData)
}

function addClickListener(videoElement, userData) {
  videoElement.addEventListener('click', function () {
    var mainVideo = $('#main-video video').get(0)
    if (mainVideo.srcObject !== videoElement.srcObject) {
      $('#main-video').fadeOut('fast', () => {
        $('#main-video p').html(userData)
        mainVideo.srcObject = videoElement.srcObject
        $('#main-video').fadeIn('fast')
      })
    }
  })
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
