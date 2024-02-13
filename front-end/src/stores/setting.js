import { ref } from 'vue'
import { defineStore } from 'pinia'

// setting store
// 알림, 입출력 설정 저장 
export const useSettingStore = defineStore('setting', () => {
    const conferenceLayout = ref('conference-view1')

    const alarmPermission = ref(true)
    
    const alarmSound = ref(false)

    const alarmFlag = ref(false)
    const alarmContent = ref("")

  return { conferenceLayout, alarmPermission, alarmSound, alarmFlag, alarmContent }
  }, { persist: true }
)
