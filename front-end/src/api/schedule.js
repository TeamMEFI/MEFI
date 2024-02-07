import { InterceptorAxios } from "@/util/http-axios";
import { localAxios } from "@/util/http-commons";

// const local = localAxios();
const interceptor = InterceptorAxios();

async function setHeaders() {
    interceptor.defaults.headers["Authorization"] = 'Bearer ' + localStorage.getItem("accessToken");
}

async function createSchedule(param, success, fail){
    await setHeaders();
    await interceptor.post(`/schedule`, param).then(success).catch(fail);
}

async function deleteSchedule(param, success, fail){
    await setHeaders();
    await interceptor.delete(`/schedule/${param.id}`, param.data).then(success).catch(fail);
}

export { createSchedule, deleteSchedule }