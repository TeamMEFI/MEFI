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
            return response
        },
        function(error){
            const originalRequest = error.config
            let refreshToken = localStorage.getItem('refreshToken')
            if (refreshToken === null ){
                return Promise.reject(error)
            }
            else if (error.response.status === 403) { // refresh token -> router push login // access token -> updateToken
                updateToken(
                    (res)=>{
                        localStorage.setItem('accessToken', res.data.dataBody.accessToken)
                        originalRequest.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
                        // return axiosInstance(originalRequest)
                    },(err)=>{
                        return Promise.reject(err)
                    })
            }
            else{
                return Promise.reject(error)
            }
        }
    )

    return axiosInstance;
}

export {InterceptorAxios}