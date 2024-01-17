import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/main/HomeView.vue'
import LoginView from "../views/user/LoginView.vue"
import SignupView from "../views/user/SignupView.vue"
import ConferenceView from "../views/conference/ConferenceView.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
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
