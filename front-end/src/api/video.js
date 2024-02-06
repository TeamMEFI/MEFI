import { localAxios } from '@/util/http-commons'

const local = localAxios()

const makeSession = async (param, success, fail) => {
  local.defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken')
  await local.post(`/api/openvidu/api/sessions`, param).then(success).catch(fail)
}

const makeToken = async (sessionId, success, fail) => {
  local.defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken')
  await local.post(`/api/openvidu/api/sessions/${sessionId}/connections`).then(success).catch(fail)
}

const checkDone = async (sessionId, success, fail) => {
  local.defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken')
  await local.get(`/api/openvidu/api/sessions/${sessionId}/`, {}).then(success).catch(fail)
}

export { makeToken, makeSession, checkDone }
