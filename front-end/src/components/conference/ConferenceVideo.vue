<template>
  <div id="main-container">
    <div id="session" v-if="mainStreamManager">
      <div
        id="video-container"
        ref="videos"
        class="videos"
        :style="{ 'flex-wrap': isSide ? 'wrap' : 'no-wrap' }"
      >
        <UserVideo
          class="video"
          :style="{ height: !isSide && '100px' }"
          :stream-manager="mainStreamManager"
        />
        <UserVideo
          v-for="sub in subscribers"
          class="video"
          :style="{ height: !isSide && '100px' }"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click="updateMainVideoStreamManager(sub)"
        />
      </div>
      <div id="session-header">
        <input
          class="btn btn-large btn-danger"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="Leave session"
        />
      </div>
    </div>
    <div v-else>
      <img src="@/assets/8469.jpg" width="100px" alt="" />
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onBeforeUnmount, onMounted, defineProps, onUpdated } from 'vue'
import { OpenVidu } from 'openvidu-browser'
import UserVideo from './UserVideo.vue'

axios.defaults.headers.post['Content-Type'] = 'application/json'

const APPLICATION_SERVER_URL = process.env.NODE_ENV === 'production' ? '' : 'http://localhost:5000/'

const props = defineProps({
  overlay: String
})

const isSide = ref(true)

const OV = ref(null)
const session = ref(undefined)
const mainStreamManager = ref(null)
const publisher = ref(null)
const subscribers = ref([])

// Join form
const mySessionId = ref('SessionA')
const myUserName = ref('Participant' + Math.floor(Math.random() * 100))

onMounted(() => {
  joinSession()

  if (props.overlay.slice(-1) == '3') {
    isSide.value = false
  } else {
    isSide.value = true
  }
})

onUpdated(() => {
  if (props.overlay.slice(-1) == '3') {
    isSide.value = false
  } else {
    isSide.value = true
  }
})

const joinSession = () => {
  OV.value = new OpenVidu()
  session.value = OV.value.initSession()

  session.value.on('streamCreated', ({ stream }) => {
    const subscriber = session.value.subscribe(stream)
    subscribers.value.push(subscriber)
  })

  session.value.on('streamDestroyed', ({ stream }) => {
    const index = subscribers.value.indexOf(stream.streamManager, 0)

    if (index >= 0) {
      subscribers.value.splice(index, 1)
    }
  })

  session.value.on('exception', ({ exception }) => {
    console.warn(exception)
  })

  getToken(mySessionId.value).then((token) => {
    session.value
      .connect(token, { clientData: myUserName.value })
      .then(() => {
        let newPublisher = OV.value.initPublisher(undefined, {
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
  if (mainStreamManager.value === stream) return
  mainStreamManager.value = stream
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
.videos {
  display: flex;
  justify-content: center;
  background-color: black;
  padding: 10px;
  gap: 10px;
  max-width: 600px;
}

.video {
  display: flex;
  width: 45%;
  height: 100%;
}

.upSideVideo {
  height: 100px;
}

@media (max-width: 720px) {
  .videos {
    flex-direction: column;
  }

  .video {
    width: 100%;
  }
}
</style>
