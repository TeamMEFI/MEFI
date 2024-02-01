import { localAxios } from "@/util/http-commons";
const local = localAxios();

const getFiles = async (param, success, fail) => {
    const conferenceId = param?.conferenceId;
    local.defaults.headers["Authorization"] = 'Bearer ' + localStorage.getItem("accessToken");
    await local.get(`/file/all/${conferenceId}`, param).then(success).catch(fail);
}

const createFile = async (param, success, fail) => {
    local.defaults.headers["Authorization"] = 'Bearer ' + localStorage.getItem("accessToken");
    local.defaults.headers["Content-Type"] = 'multipart/form-data';
    console.log(local.defaults)
    await local.post(`/file`, param).then(success).catch(fail);
}

export { getFiles, createFile };

