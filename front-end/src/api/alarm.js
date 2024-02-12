import { InterceptorAxios } from "@/util/http-axios";
import { EventSourcePolyfill, NativeEventSource } from "event-source-polyfill";
import { useSettingStore } from "@/stores/setting";
import { createPinia } from "pinia";

const pinia = createPinia()
const settingStore = useSettingStore(pinia)
const interceptor = InterceptorAxios();
const VITE_APP_API_URL = import.meta.env.VITE_APP_API_URL

// sse 연결
async function alarmSubscribe(lastEventId){
    const EventSource = EventSourcePolyfill || NativeEventSource
    const SSE = new EventSource(
        `${VITE_APP_API_URL}/api/alarm/subscribe?lastEventId=${lastEventId}`,
        { 
            headers: { Authorization: `Bearer ${localStorage.getItem('accessToken')}` },
            heartbeatTimeout: 120000
        }
    )
    console.log(SSE)

    SSE.addEventListener("sse", (event)=>{
        console.log(event.data)
        // 알림창 띄우기
        console.log(settingStore.alarm)
        settingStore.alarm = true
        console.log(settingStore.alarm)
    })

    SSE.onmessage = (event)=>{
        console.log(`received event: ${event}`)
    }

    SSE.onerror = (err) => {
        console.error(`SSE error : ${err}`)
    }
}

// 알림 전체 조회
async function alarmAll(){
    interceptor.defaults.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    try{
        const response = interceptor.get('/alarm/all')
        return response;
    }catch(error){
        throw error;
    }
}

// 알림 전체 읽음
async function alarmReadAll(){
    interceptor.defaults.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    try{
        const response = interceptor.patch('/alarm/all')
        return response;
    }catch(error){
        throw error;
    }
}

// 알림 읽음
async function alarmReadOne(alarmId){
    console.log('api')
    interceptor.defaults.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    try{
        const response = interceptor.patch(`/alarm/all/${alarmId}`)
        return response;
    }catch(error){
        throw error;
    }
}

export{
    alarmSubscribe,
    alarmAll,
    alarmReadAll,
    alarmReadOne,
    settingStore
}