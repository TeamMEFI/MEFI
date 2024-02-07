import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { userLogin, userSignup } from '@/api/user.js'
import { alarmSubscribe } from '@/api/alarm'

// user store
// login, signup, user info, 토큰 관리
export const useUserStore = defineStore('user', () => {
  const router = useRouter()
  const isLogin = ref(true) // store.isLogin
  const userInfo = ref(null)

  // 회원 가입 함수
  // user 정보 : email, password, name, position, department
  const signup = async (user) => {
    await userSignup(
      user,(response)=>{
        const loginUser = {
          email:user.email,
          password:user.password,
        }
        login(loginUser)
      },
      (error)=>{console.log(error)}
    )
  }

  // 로그인 함수
  // user 정보 : email, password
  const login = async (user) => {
    const loginFlage = ref(false)
    await userLogin(
      user,
      (response) => {
        userInfo.value = response.data.dataBody
        localStorage.setItem("accessToken", response.headers.accesstoken)
        localStorage.setItem("refreshToken", response.headers.refreshtoken)
        isLogin.value = true;
        loginFlage.value = true
        console.log('login api', localStorage.getItem('accessToken'),)
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
    
    .then( async ()=>{
      console.log(loginFlage.value)
      const param = {
        "lastEventId":"",
      }
      if(loginFlage.value===true){
        console.log('sse 연결 api')
        await alarmSubscribe(param,
          (res)=>{console.log(res, "성공"),
          (err)=>{console.log(err, "실패")}
        }).then( async()=>{
          await router.push({name:'main'})
        })  
      }
    })
  }

  // 로그아웃 함수
  // isLogin, token delete
  const logout = () => {
    isLogin.value = false
    userInfo.value = null
    localStorage.clear()
    router.push({name:'login'})
  }

  return { isLogin, signup, login, logout, userInfo }
},{ persist:true }
)
