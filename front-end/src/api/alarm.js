import { InterceptorAxios } from "@/util/http-axios";
const interceptor = InterceptorAxios();

async function alarmSubscribe(param){
    interceptor.defaults.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    await interceptor.get(`/alarm/subscribe`, {params:param})
    .then((res)=>{
        console.log('success')
        console.log(res)
    })
    .catch((err)=>{
        console.log('fail')
        console.log(err)
    });
}
async function alarmAll(success, fail){
    interceptor.defaults.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    await interceptor.post(`/alarm/all`).then(success).catch(fail);
}
export{
    alarmSubscribe,
    alarmAll,
}