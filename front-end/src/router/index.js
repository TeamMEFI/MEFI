import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'

import ConferenceView from "@/views/conference/ConferenceView.vue"
import MainView from "@/views/main/MainView.vue"

import UserView from "@/views/user/UserView.vue"
import Login from "@/components/user/Login.vue"
import Signup from "@/components/user/Signup.vue"
import Email from "@/components/user/Email.vue"
import SearchPassword1 from "@/components/user/SearchPassword1.vue"
import SearchPassword2 from "@/components/user/SearchPassword2.vue"
import TeamView from "@/views/team/TeamView.vue"

import InsertSchedule from "@/views/schedule/InsertSchedule.vue"
import InsertConference from "@/views/schedule/InsertConference.vue"
import DetailSchedule from "@/views/schedule/DetailSchedule.vue"
import DetailConference from "@/views/schedule/DetailConference.vue"

import NotFoundView from "@/views/notFound/NotFoundView.vue"


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      children: [
        {
          path: '/',
          name: 'main',
          component: MainView
        },
        {
          path: '/team/:id',
          name: 'team',
          component: TeamView,
          props: (route) =>({
            id: route.params.id,
          })
        },
        {
          path: '/insert/schedule',
          name: 'insertschedule',
          component: InsertSchedule,
        },
        {
          path: '/insert/conference/:teamid',
          name: 'insertconference',
          component: InsertConference,
          props: (route) =>({
            teamid: route.params.teamid,
          })
        },
        {
          path: '/Detail/schedule/:scheduleid',
          name: 'detailschedule',
          component: DetailSchedule,
          props: (route) =>({
            scheduleid: route.params.scheduleid,
          })
        },
        {
          path: '/Detail/conference/:teamid/:conferenceid',
          name: 'detailconference',
          component: DetailConference,
          props: (route) =>({
            teamid: route.params.teamid,
            conferenceid: route.params.conferenceid,
          })
        },
      ]
    },
    {
      path: '/user',
      name: 'user',
      component: UserView,
      children:[
        {
          path:'/user/login',
          name:'login',
          component: Login,
        },
        {
          path:'/user/signup',
          name:'signup',
          component: Signup,
        },
        {
          path:'/user/signup/email',
          name:'search-password1',
          component: SearchPassword1,
        },
        {
          path:'/user/signup/search-password',
          name:'search-password2',
          component: SearchPassword2,
        }
      ]
    },
    {
      path: '/conference/:teamId/:conferenceId',
      name: 'conference',
      component: ConferenceView
    },
    {
      path: "/:pathMatch(.*)*",
      name: 'notFound',
      component: NotFoundView
    }
  ]
})

export default router
