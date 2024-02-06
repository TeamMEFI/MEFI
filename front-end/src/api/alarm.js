import { InterceptorAxios } from "@/util/http-axios";
const interceptor = InterceptorAxios();

async function alarmSubscribe(param, success, fail){
    interceptor.defaults.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    console.log(localStorage.getItem('accessToken'))
    await interceptor.post(`/alarm/subscribe`, param).then(success).catch(fail);
}
async function alarmAll(success, fail){
    interceptor.defaults.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    await interceptor.post(`/alarm/all`).then(success).catch(fail);
}
export{
    alarmSubscribe,
    alarmAll,
}