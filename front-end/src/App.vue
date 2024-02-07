<template>
    <v-app>
        <RouterView/>
    </v-app>
</template>

<script setup>
import { RouterView, useRouter } from "vue-router";
import { updateToken } from "./api/user";

const router = useRouter()

// login
// 동작 : refreshtoken 없으면 바로 login page / 있으면 updateToken api 날려주고, 에러 발생하면 login page로 이동
router.beforeEach((to, from) => {
    if (localStorage.getItem('refreshToken') !== null) {
        updateToken(
            (res) => {
                console.log(res)
                return {name:'home'}
            },
            (err) => {
                console.log(err)
                // refresh token 만료 및 그 외 에러 발생 시 home으로 이동
                localStorage.clear();
                if (to.name != 'login' &&
                    to.name != 'signup' &&
                    to.name != 'email' &&
                    to.name != 'search-password1' &&
                    to.name != 'search-password2' &&
                    to.name != 'search-password3') {
                    console.log('if')
                    return {name:'login'}
                    // next({name:'login'})
                    // return
                }
            }
        )
    } else {
        console.log('else')
        if (to.name != 'login' &&
            to.name != 'signup' &&
            to.name != 'email' &&
            to.name != 'search-password1' &&
            to.name != 'search-password2' &&
            to.name != 'search-password3') {
            return {name:'login'}
            // next({name:'login'})
            // return
        }
    }
})
</script>

<style scoped>
</style>
