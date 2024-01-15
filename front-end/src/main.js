// main.js

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

// pinia 관련 local storage 활용 모듈
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

const app = createApp(App)
const pinia = createPinia()

// pinia에 바인딩
pinia.use(piniaPluginPersistedstate)

app.use(router)

app.mount('#app')
