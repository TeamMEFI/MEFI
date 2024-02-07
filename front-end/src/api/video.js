import { InterceptorAxios } from '@/util/http-axios'

const interceptor = InterceptorAxios()

const makeSession = async (param, teamId, success, fail) => {
  console.log(param)
  interceptor.defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken')
  await interceptor
    .post(`/openvidu/api/sessions/${teamId}`, param)
    .then(success)
    .catch(fail)
}

const makeToken = async (param, teamId, success, fail) => {
  interceptor.defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken')
  await interceptor
    .post(`/openvidu/api/sessions/${teamId}/connections`, null, {params : param})
    .then(success)
    .catch(fail)
}

const checkDone = async (sessionId, success, fail) => {
  interceptor.defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken')
  await interceptor.get(`/openvidu/api/sessions/${sessionId}/`, {}).then(success).catch(fail)
}

export { makeToken, makeSession, checkDone }
