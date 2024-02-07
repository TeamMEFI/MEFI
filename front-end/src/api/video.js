import { InterceptorAxios } from '@/util/http-axios'

const interceptor = InterceptorAxios()

const makeSession = async (param, success, fail) => {
  interceptor.defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken')
  await interceptor.post(`/openvidu/api/sessions`, param).then(success).catch(fail)
}

const makeToken = async (sessionId, success, fail) => {
  interceptor.defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken')
  await interceptor
    .post(`/openvidu/api/sessions/${sessionId}/connections`)
    .then(success)
    .catch(fail)
}

const checkDone = async (sessionId, success, fail) => {
  interceptor.defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken')
  await interceptor.get(`/openvidu/api/sessions/${sessionId}/`, {}).then(success).catch(fail)
}

export { makeToken, makeSession, checkDone }
