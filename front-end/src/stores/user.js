import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { userLogin, userSignup } from '@/api/user.js'
import { useSSEStore } from './sse'


// user store
// login, signup, user info, 토큰 관리
export const useUserStore = defineStore('user', () => {
  const seeStore = useSSEStore();
  const router = useRouter()
  const userInfo = ref(null)

  // 회원 가입 함수
  const signup = async (user) => {
    await userSignup(
      user,()=>{
        const loginUser = {
          email:user.email,
          password:user.password,
        }
        login(loginUser)
      },
      (error)=>{console.log(error)}
    )
  }

  // 로그인 및 sse 연결
  const login = async (user) => {

    // 성공하면 지우기
    const loginFlage = ref(false)

    await userLogin(
      user,
      (response) => {
        userInfo.value = response.data.dataBody
        console.log(userInfo.value)
        localStorage.setItem("accessToken", response.headers.accesstoken)
        localStorage.setItem("refreshToken", response.headers.refreshtoken)
        loginFlage.value = true
        router.push({name:'main'})
      },
      (error)=>{
        console.log(error.response)
        if (error.response.status===401){
          alert('아이디와 비밀번호를 확인하여 주세요')
        }
        else{
          console.log(error)
        }
      }
    )
    
    // if(loginFlage.value===true){
    //   console('user store')
    //   const param = {
    //     "lastEventId":"",
    //   }
    //   await seeStore.sseConnect(param, userInfo.value.id,
    //     (res)=>{console.log(res)
    //     ,(err)=>{console.log(err)}
    //   })
    //   .then( async()=>{
    //     await router.push({name:'main'})
    //   })  
    // }
    
  }

  // 로그아웃 함수
  // localStorage 삭제
  const logout = () => {
    userInfo.value = null
    localStorage.clear()
    router.push({name:'login'})
  }

  return { signup, login, logout, userInfo }
},{ persist:true }
)
