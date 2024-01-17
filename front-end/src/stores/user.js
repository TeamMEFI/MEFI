import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRoute, useRouter } from 'vue-router'

export const useUserStore = defineStore('user', () => {

  const isLogin = ref(true)
  const router = useRouter()

  const signup = function(){
    console.log('sign in')
    isLogin.value = true
    router.push({name:'home'})
  }

  const login = function(user){
    const { id, password } = user
    console.log('log in')
    console.log(`user info ${id} ${password}`)
    isLogin.value = true
    router.push({name:'home'})
  }

  const logout = function(){
    console.log('log out')
    isLogin.value = false
    localStorage.clear()
    router.push({name:'home'})
  }

  return { isLogin, signup, login, logout }
})
