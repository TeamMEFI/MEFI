<template>
    <div>
        <v-card>
            <v-layout>
                <!-- 상위 헤더 -->
                <v-app-bar  color="#495464">
                    <!-- log -->
                    <v-toolbar-title>MEFI</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <!-- 알림 -->
                    <v-btn variant="text"><font-awesome-icon :icon="['fas', 'bell']" style="font-size: large;"/></v-btn>
                    <!-- 설정 -->
                    <v-btn variant="text">
                        <font-awesome-icon :icon="['fas', 'gear']" style="font-size: large;"/>
                        <v-dialog v-model="setting" activator="parent" width="auto">
                            <v-card>
                                <Setting @close="settingClose"></Setting>
                            </v-card>
                        </v-dialog>
                    </v-btn>
                    <!-- user 설정 -->
                    <v-btn variant="text" @click.stop="userSetting = !userSetting"><font-awesome-icon :icon="['fas', 'user-gear']" style="font-size: large;"/></v-btn>
                </v-app-bar>

                <!-- user 설정 drawer -->
                <v-navigation-drawer v-model="userSetting" location="right" temporary>
                    <v-list>
                        <!-- 상태 정보 -->
                        <v-list-item>상태 정보</v-list-item>
                        <!-- 회원 정보 조회 및 수정 -->
                        <v-list-item @click="openUserInfo">
                            회원 정보
                            <v-dialog v-model="userInfo" activator="parent" width="auto">
                                <v-card>
                                    <UserInfoSettingVue @close="userInfoClose"></UserInfoSettingVue>
                                </v-card>
                            </v-dialog>
                        </v-list-item>
                        <!-- 로그 아웃 -->
                        <v-list-item @click="goLogout">로그아웃</v-list-item>
                    </v-list>
                </v-navigation-drawer>
            </v-layout>
        </v-card>
    </div>
</template>

<script setup>
import { ref } from "vue";
import {useUserStore} from "../../stores/user"
import Setting from "../../components/settings/setting/Setting.vue";
import UserInfoSettingVue from "../settings/userInfoSetting/UserInfoSetting.vue";

const store = useUserStore();
const setting = ref(false)
const userSetting = ref(false)
const userInfo = ref(false)
const userState = ref(false)

const goLogout = ()=>{
    store.logout()
}
const settingClose = () => {
    setting.value = false
}
const openUserInfo = () => {
    userSetting.value = true
}
const userInfoClose = () => {
    userInfo.value = false
}
</script>

<style scoped>

</style>