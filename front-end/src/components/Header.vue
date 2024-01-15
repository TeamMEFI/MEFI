<template>
    <div>
        <v-card>
            <v-layout>

                <v-app-bar  color="#495464">
                    <v-toolbar-title>MEFI</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-btn variant="text" icon="mdi-magnify"></v-btn>
                    <v-btn variant="text" icon="mdi-dots-vertical" @click.stop="drawer = !drawer"></v-btn>
                </v-app-bar>

                <v-navigation-drawer v-model="drawer" location="right" temporary="" v-if="store.isLogin">
                    <v-list>
                        <v-list-item @click="goLogout">로그아웃</v-list-item>
                        <v-list-item>회원 정보 조회</v-list-item>
                        <v-list-item>입출력 장치 설정</v-list-item>
                        <v-list-item>회원 탈퇴</v-list-item>
                    </v-list>
                </v-navigation-drawer>

                <v-navigation-drawer v-model="drawer" location="right" temporary="" v-else>
                    <v-list>
                        <v-list-item @click="goLogin">로그인</v-list-item>
                        <v-list-item @click="goSignup">회원가입</v-list-item>
                    </v-list>
                </v-navigation-drawer>

            </v-layout>
        </v-card>
    </div>
</template>

<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import {useUserStore} from "../stores/user"

const router = useRouter()
const store = useUserStore();
const drawer = ref(false)

const goLogin = function(){
    router.push({name:'login'})
}

const goSignup = function(){
    router.push({name:'signup'})
}

const goLogout = function(){
    store.logout()
}
</script>

<style scoped></style>