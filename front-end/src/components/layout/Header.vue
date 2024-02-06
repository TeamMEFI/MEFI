<template>
  <v-toolbar title="MEFI" color="#495464" class="w-100">
    <v-spacer></v-spacer>
    <!-- 알림 -->
    <v-btn variant="text" size="40">
      <v-badge :content="alarmCount" color="#E53935">
        <font-awesome-icon :icon="['fas', 'bell']" style="font-size: x-large;" @click="openAlarms()"/>
        <v-dialog>
          <v-card>
            {{  }}
          </v-card>
        </v-dialog>
      </v-badge>
    </v-btn>
    <!-- 설정 -->
    <v-btn variant="text" size="40">
      <font-awesome-icon :icon="['fas', 'gear']" style="font-size: 16px"/>
      <v-dialog v-model="setting" activator="parent" width="auto">
        <v-card>
          <Setting @close="settingClose"></Setting>
        </v-card>
      </v-dialog>
    </v-btn>

    <!-- user 설정 -->
    <!-- user 설정 drawer -->
    <v-btn variant="text" size="40" @click.stop="userSetting = !userSetting">
      <!-- 헤더에 보이는 이미지 & status -->
      <font-awesome-icon :icon="['fas', 'user']" style="font-size: 16px" />
      <!-- 회원 설정 -->
      <v-menu location="buttom" activator="parent">
        <v-list class="overflow-hidden ma-0">
          <!-- 상태 정보 -->
          <v-list-item @click.stop="userStatus = !userStatus">
            상태 정보
            <UserStateSetting @status-change="changeStatus"></UserStateSetting>
          </v-list-item>

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
      </v-menu>
    </v-btn>
  </v-toolbar>
</template>

<script setup>
import { ref } from 'vue'
import { useUserStore } from '../../stores/user'
import Setting from '../../components/settings/setting/Setting.vue'
import UserInfoSettingVue from '../settings/userInfoSetting/UserInfoSetting.vue'
import UserStateSetting from '../settings/userStateSetting/UserStateSetting.vue'
import AlarmListVue from '../alarm/AlarmList.vue'
import {alarmAll} from "@/api/alarm"

const store = useUserStore()
const setting = ref(false)
const userSetting = ref(false)
const userInfo = ref(false)
const userStatus = ref(false)

// 로그아웃
// store logout 함수에 api 추가 및 router.push({name:'home'})
const goLogout = () => {
  store.logout()
}

// 설정 모달창 닫기
const settingClose = () => {
  setting.value = false
}

// 회원 정보 설정 모달창 열기
const openUserInfo = () => {
  userSetting.value = true
}

// 회원 정보 설정 모달창 닫기
const userInfoClose = () => {
  userInfo.value = false
}

// 회원 상태 변화 반영
const changeStatus = (color) => {
  store.userInfo.status = color
  userStatus.value = false
}

// 알림
const alarmCount = ref(0)
const openAlarms = async () => {
  alarmAll((res)=>{console.log(res), (err)=>{console.log(err)}})
}
</script>

<style scoped>
.v-list {
  width: 150px;
  overflow: hidden !important;
}
#profile {
  overflow: visible;
}
.green {
  color: green;
}
.red {
  color: red;
}
.yellow {
  color: yellow;
}
.white {
  color: white;
}
</style>
