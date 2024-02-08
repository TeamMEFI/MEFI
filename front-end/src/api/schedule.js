import { InterceptorAxios } from "@/util/http-axios";
import { query } from "quill";

// const local = localAxios();
const interceptor = InterceptorAxios();

async function setHeaders() {
    interceptor.defaults.headers["Authorization"] = 'Bearer ' + localStorage.getItem("accessToken");
}

async function selectSchedule(query, success, fail){
    await setHeaders();
    await interceptor.get(`/schedule?${query}`).then(success).catch(fail);
}

async function createSchedule(param, success, fail){
    await setHeaders();
    await interceptor.post(`/schedule`, param).then(success).catch(fail);
}

async function deleteSchedule(param, success, fail){
    await setHeaders();
    await interceptor.delete(`/schedule/${param.id}`, param.data).then(success).catch(fail);
}

export { selectSchedule, createSchedule, deleteSchedule }