<template>
    <div>
        <v-card>
            <v-layout>

                <v-app-bar  color="#495464">
                    <v-toolbar-title>MEFI</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-btn variant="text"><font-awesome-icon :icon="['fas', 'bell']" style="font-size: large;"/></v-btn>
                    <v-btn variant="text">
                        <font-awesome-icon :icon="['fas', 'gear']" style="font-size: large;"/>
                        <v-dialog v-model="setting" activator="parent" width="auto">
                            <v-card>
                                <Setting @close="close"></Setting>
                            </v-card>
                        </v-dialog>
                    </v-btn>
                    <v-btn variant="text" @click.stop="userSetting = !userSetting"><font-awesome-icon :icon="['fas', 'user-gear']" style="font-size: large;"/></v-btn>
                </v-app-bar>

                <v-navigation-drawer v-model="userSetting" location="right" temporary>
                    <v-list>
                        <v-list-item>상태 정보</v-list-item>
                        <v-list-item>회원 정보</v-list-item>
                        <v-list-item @click="goLogout">로그아웃</v-list-item>
                    </v-list>
                </v-navigation-drawer>

            </v-layout>
        </v-card>
    </div>
</template>

<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import {useUserStore} from "../../stores/user"
import Setting from "../../components/settings/setting/Setting.vue";

const router = useRouter()
const store = useUserStore();
const userSetting = ref(false)
const setting = ref(false)

const goLogout = ()=>{
    store.logout()
}
const close = () => {
    setting.value = false
}
</script>

<style scoped>

</style>