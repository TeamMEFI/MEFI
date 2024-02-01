import { localAxios } from "@/util/http-commons";
const local = localAxios();
local.defaults.headers["Authorization"] = 'Bearer ' + localStorage.getItem("accessToken");
async function teamSelect(param, success, fail){
    await local.get(`/team`, param).then(success).catch(fail);
}
async function teamCreate(param, success, fail){
    await local.post(`/team`, param).then(success).catch(fail);
}
export { teamSelect, teamCreate };

