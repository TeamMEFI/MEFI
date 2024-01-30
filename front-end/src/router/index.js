import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import LoginView from "@/views/user/LoginView.vue"
import SignupView from "@/views/user/SignupView.vue"
import ConferenceView from "@/views/conference/ConferenceView.vue"
import MainView from "@/views/main/MainView.vue"
import TeamView from "@/views/team/TeamView.vue"
import InsertView from "@/views/schedule/InsertView.vue"
import DetailView from "@/views/schedule/DetailView.vue"
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
        {
          path: '/team',
          name: 'team',
          component: TeamView
        },
        {
          path: '/insert/:type',
          name: 'insert',
          component: InsertView,
          props: (route) =>({
            type: route.params.type,
          })
        },
        {
          path: '/detail/:type',
          name: 'detail',
          component: DetailView,
          props: route => ({
            type: route.params.type,
          })
        },
      ]
    },

    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignupView
    },
    {
      path: '/conference',
      name: 'conference',
      component: ConferenceView
    },
  ]
})

export default router
