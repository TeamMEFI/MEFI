import { localAxios } from "@/util/http-commons";
const local = localAxios();

async function userLogin(param, success, fail){
    await local.post(`/users/login`, param).then(success).catch(fail);
}
async function userSignup(param, success, fail){
    await local.post(`/users`, param).then(success).catch(fail);
}
async function userSearch(param, success, fail){
    await local.get(`/users/search/${param}`, param).then(success).catch(fail);
}
export { userLogin, userSignup, userSearch };