import { InterceptorAxios } from '@/util/http-axios'

const interceptor = InterceptorAxios()

const createConference = async (param, success, fail) => {
      interceptor.defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken')
    await interceptor.post(`/meeting`, param).then(success).catch(fail)
}

export { createConference }
