import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'

import ConferenceView from "@/views/conference/ConferenceView.vue"
import MainView from "@/views/main/MainView.vue"
import UserView from "@/views/user/UserView.vue"
import Login from "@/components/user/Login.vue"
import Signup from "@/components/user/Signup.vue"
import Email from "@/components/user/Email.vue"
import SearchPassword from "@/components/user/SearchPassword.vue"


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      children: [
        {
          path: '/main',
          name: 'main',
          component: MainView
        },
      ]
    },
    {
      path: '/',
      name: 'user',
      component: UserView,
      children:[
        {
          path:'/login',
          name:'login',
          component: Login,
        },
        {
          path:'/signup',
          name:'signup',
          component: Signup,
        },
        {
          path:'/email',
          name:'email',
          component: Email,
        },
        {
          path:'/search-password',
          name:'search-password',
          component: SearchPassword,
        }
      ]
    },
    {
      path: '/conference',
      name: 'conference',
      component: ConferenceView
    },
  ]
})

export default router
