import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { userLogin } from '@/api/user.js'


// user store
// login, signup, user info, token regenerate
export const useUserStore = defineStore('user', () => {

  const isLogin = ref(true)
  const router = useRouter()
  const userInfo = ref({
    email:'ooo@gmail.com',
    name:'ooo',
    department:'department',
    position:'position',
    status:'green',
  })

  // 회원 가입 함수
  // user 정보 : email, password, name, position, department
  const signup = (user) => {
    console.log('sign in')
    isLogin.value = true
    router.push({name:'home'})
  }

  // 로그인 함수
  // user 정보 : email, password
  // async : 비동기 방시긍로 처리
  // await : 비동기 작업 끝날때 까지 대기
  const login = async (user) => {
    await userLogin(user)(
      user,(response) => {
        console.log(response)
        isLogin.value = true
        router.push({name:'home'})
      },
      (error)=>{
        console.log(error)
      }
    )
  }

  // 로그아웃 함수
  // isLogin, token delete
  const logout = () => {
    console.log('log out')
    isLogin.value = false
    localStorage.clear()
    router.push({name:'home'})
  }

  return { isLogin, signup, login, logout, userInfo }
})
