import { InterceptorAxios } from "@/util/http-axios";
const interceptor = InterceptorAxios();

// sse 연결
async function alarmSubscribe(param){
    interceptor.defaults.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    await interceptor.get(`/alarm/subscribe`, {params:param}).then().catch();
}
// 알림 전체 조회
async function alarmAll(){
    interceptor.defaults.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    await interceptor.get(`/alarm/all`).then().catch();
}
// 알림 전체 읽음
async function alarmReadAll(){
    interceptor.defaults.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    await interceptor.post(`/alarm/all`).then().catch();
}
// 알림 읽음
async function alarmReadOne(alarmId, param){
    interceptor.defaults.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    await interceptor.post(`/alarm/all/${alarmId}`, {params:param}).then().catch();
}
export{
    alarmSubscribe,
    alarmAll,
    alarmReadAll,
    alarmReadOne
}