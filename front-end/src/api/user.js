import { localAxios } from "@/util/http-commons";
const local = localAxios();

async function userLogin(param, success, fail){
    console.log('api userLogin',param)
    await local.post(`/users/login`, param).then(success).catch(fail);
    console.log('api userLogin success')
}
async function userSignup(param, success, fail){
    await local.post(`/users`, param).then(success).catch(fail);
}
export { userLogin, userSignup };