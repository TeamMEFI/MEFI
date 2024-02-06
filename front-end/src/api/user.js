import { InterceptorAxios } from "@/util/http-axios";
import { localAxios } from "@/util/http-commons";

const local = localAxios();
const interceptor = InterceptorAxios();

async function userLogin(param, success, fail){
    await local.post(`/api/users/login`, param).then(success).catch(fail);
}
async function userSignup(param, success, fail){
    await local.post(`/api/users`, param).then(success).catch(fail);
}
async function sendEmailCode(param, success, fail){
    await local.post(`/api/users/join/auth`,param).then(success).catch(fail)
}
async function checkEmailCode(param, success, fail){
    await local.post(`/api/users/join/auth/check`,param).then(success).catch(fail)
}
async function updateToken(success, fail){
    local.defaults.headers.Authorization = `Bearer ${localStorage.getItem('refreshToken')}`
    await local.post(`/api/token`).then(success).catch(fail)
}
async function userSearch(param, success, fail){
    await local.get(`/api/users/search/${param}`, param).then(success).catch(fail);
}
async function userModify(param, success, fail){
    local.defaults.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    await interceptor.put(`/api/users/info`,param).then(success).catch(fail)
}

async function userDelete(param, success, fail){
    local.defaults.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    await interceptor.delete(`/api/users`, {params: param}).then(success).catch(fail)
}
export { 
    userLogin, 
    userSignup, 
    updateToken, 
    sendEmailCode, 
    checkEmailCode, 
    userSearch, 
    userModify, 
    userDelete 
};

