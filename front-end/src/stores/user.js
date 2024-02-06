import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { userLogin, userSignup } from '@/api/user.js'
import { alarmSubscribe } from '@/api/alarm'

// user store
// login, signup, user info, 토큰 관리
export const useUserStore = defineStore('user', () => {
  const router = useRouter()
  const isLogin = ref(true)
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
    await userLogin(
      user,(response) => {
        userInfo.value = response.data.dataBody
        localStorage.setItem("accessToken", response.headers.accesstoken)
        localStorage.setItem("refreshToken", response.headers.refreshtoken)
        isLogin.value = true;
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
    ).then( async ()=>{
      // cache 활용해서 저장하기 1. localStorage 2. Cookies 사용 하기

      // 사용할거면 localStorage를 사용해야함. 수동 만료 & 오래 보관함.

      // 근데 logout localStorage 전체 삭제가 아니라, token & userinfo만 삭제

      // 의문? local에 저장해서 사용하는 방식이 과연 안전하고 확실한가
      // 근데 해당 컴퓨터로 다른 사용자가 이용하면?
      
      // sse api lasteventid -> 필수아님, 전체읽음으로 판단.
      // "lastEventId":""
      const param = {
        // 마지막 수신된 데이터 id
        "lastEventId":"",
      }
      await alarmSubscribe(param,
        (res)=>{console.log(res),
        (err)=>{console.log(err)}
      }).then( async()=>{
        await router.push({name:'main'})
      })
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
