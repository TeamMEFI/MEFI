import { updateToken } from '@/api/user';
import axios from 'axios';

const VUE_APP_API_URL = import.meta.env.VITE_APP_API_URL;

// accessToken 담아서 던지는 API
const InterceptorAxios = () => {
    const axiosInstance = axios.create({
        baseURL: `${VUE_APP_API_URL}/api`,
        headers:{
            "Content-Type": "application/json;charset=utf-8"
        }
    })
    
    // axios interceptor request
    axiosInstance.interceptors.request.use(
        function(config){
            let accessToken = localStorage.getItem('accessToken')
            console.log('axios interceptor accesstoken', accessToken)
            if(accessToken){
                config.headers.Authorization = `Bearer ${accessToken}`
            }
            return config
        },
        function(error){
            console.log('axios interceptor accesstoken error')
            return Promise.reject(error)
        }
    )
    
    // axios interceptor response
    axiosInstance.interceptors.response.use(
        function(response){
            console.log('axios interceptors response ',response)
            return response
        },
        function(error){
            console.log('axios interceptors response error ',error)
            const originalRequest = error.config
            let refreshToken = localStorage.getItem('refreshToken')
            if(error.response.status === 403){
                console.log(error.response)
                // if (error.response.error_code==='T001'){}
                updateToken(
                    (res)=>{
                        console.log(res.data.dataBody.accessToken)
                        localStorage.setItem('accessToken', res.data.dataBody.accessToken)
                        originalRequest.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
                        return axiosInstance(originalRequest)
                    },(err)=>{
                        console.log(err)
                        console.log('에세스토큰 재발급 API error', err)
                        // router.push({name:'home'})
                        // return Promise.reject(err)
                    })
            }else{
                console.log('error response 400 아님')
                // router.push({name:'login'})
            }
        }
    )

    return axiosInstance;
}

export {InterceptorAxios}