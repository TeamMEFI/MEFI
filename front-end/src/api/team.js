import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function setHeaders() {
    local.defaults.headers["Authorization"] = 'Bearer ' + localStorage.getItem("accessToken");
}

// 팀목록 조회 profile.vue에 물려있음
async function selectTeam(success, fail){
    await setHeaders();
    await local.get(`/api/team`).then(success).catch(fail);
}

// 팀생성 teamcreateDialog.vue에 물려있음
async function createTeam(param, success, fail){
    await setHeaders();
    await local.post(`/api/team`, param).then(success).catch(fail);
}

// 팀원 조회 TeamPage.vue에 물려있음
async function selectTeamMate(param, success, fail){
    await setHeaders();
    await local.get(`/api/team/${param}`).then(success).catch(fail);
}

// 팀원 추가 API 
async function addTeamMate(param, success, fail){
    await setHeaders();
    await local.post(`/api/team/${param.teamid}/${param.userid}`).then(success).catch(fail);
}

// 팀원 삭제 API
async function excludeTeamMate(param, success, fail){
    await setHeaders();
    await local.delete(`/api/team/${param.teamid}/${param.userid}`).then(success).catch(fail);
}

// 리더 위임
async function changeLeader(param, success, fail){
    await setHeaders();
    await local.patch(`/api/team/${param.teamid}/${param.userid}`).then(success).catch(fail);
}

export { selectTeam, createTeam, selectTeamMate, addTeamMate, excludeTeamMate, changeLeader };