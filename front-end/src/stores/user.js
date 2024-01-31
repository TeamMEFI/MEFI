import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { userLogin, userSignup } from '@/api/user.js'

// user store
// login, signup, user info, token regenerate
export const useUserStore = defineStore('user', () => {

  const isLogin = ref(false)
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
  const signup = async (user) => {
    await userSignup(
      user,(response)=>{
        console.log(response)
        
        isLogin.value = true
        router.push({name:'home'})
      },
      (error)=>{
        console.log(error)
      }
    )
  }

  // 로그인 함수
  // user 정보 : email, password
  const login = async (user) => {
    console.log('store', user)
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
