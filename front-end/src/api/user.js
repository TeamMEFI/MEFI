import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function userLogin(param, success, fail){
    console.log('api/user.js userLogin')
    await local.post(`/user/login`, param).then(success).catch(fail);
    console.log('api/user.js userLogin success')
}
export { userLogin };