import { localAxios } from '@/util/http-commons'

const local = localAxios()

const getFiles = async (param, conferenceId, success, fail) => {
  local.defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken')
  await local.get(`/file/all/${conferenceId}`).then(success).catch(fail)
}

const createFile = async (param, success, fail) => {
  local.defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken')
  local.defaults.headers['Content-Type'] = 'multipart/form-data'
  await local.post(`/file`, param).then(success).catch(fail)
}

const downloadFile = async (param, conferenceId, success, fail) => {
  local.defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken')
  // 바이너리 데이터를 response로 받기 위한 설정
  local.defaults['responseType'] = 'blob'
  await local.get(`/file/download/${conferenceId}`, { params: param }).then(success).catch(fail)
}

const deleteFile = async (param, conferenceId, success, fail) => {
  local.defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken')
  await local.delete(`/file/${conferenceId}`, { params: param }).then(success).catch(fail)
}

export { getFiles, createFile, downloadFile, deleteFile }
