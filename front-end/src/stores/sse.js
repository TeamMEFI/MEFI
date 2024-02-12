import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { alarmSubscribe } from '@/api/alarm'

// sse 관련 store 
export const useSSEStore = defineStore('sse', () => {
  let eventSource;

  const sseConnect = async (param, userId) => {
    await alarmSubscribe(param,
      (res)=>{
        console.log(res, ' sse 연결 성공');
        eventSource = new EventSource(`http://localhost:8080/api/alarm?userId=${userId}`);
        console.log(eventSource)
        
        eventSource.addEventListener("sse", (event)=>{console.log(event)})

        eventSource.onerror = (err) => {
          console.log(err)
          eventSource.close();
        }

        eventSource.onopen = () => {
          console.log('connection to server opened');
        }
      },(err)=>{
        console.log(err)
      })
  }

  return { sseConnect }
  }, { persist: true }
)
