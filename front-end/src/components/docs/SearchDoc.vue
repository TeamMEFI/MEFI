<template>
  <v-sheet height="100%" width="100%" rounded="xl" border elevation="0" class="pa-5">
    <v-list :items="docsList"></v-list>
    <div
      class="file-upload-container"
      @dragenter="onDragenter"
      @dragover="onDragover"
      @dragleave="onDragleave"
      @drop="onDrop"
      @click="onClick"
    >
      <div class="file-upload" :class="isDragged ? 'dragged' : ''">Drag & Drop Files</div>
    </div>
    <!-- 파일 업로드 -->
    <input type="file" ref="file" class="file-upload-input" @change="onFileChange" multiple />
    <!-- 업로드된 리스트 -->
    <div class="file-upload-list">
      <div class="file-upload-list__item" v-for="(file, index) in fileList" :key="index">
        <div class="file-upload-list__item__data">
          <img class="file-upload-list__item__data-thumbnail" :src="file.src" />
          <div class="file-upload-list__item__data-name">
            {{ file.name }}
          </div>
        </div>
        <div class="file-upload-list__item__btn-remove" @click="handleRemove(index)">삭제</div>
      </div>
    </div>
    <v-btn @click="uploadFile"></v-btn>
  </v-sheet>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getFiles, createFile } from '@/api/file.js'

const route = useRoute()
const router = useRouter()

// API 호출 함수
const files = ref(null)
const file = ref(null)

const uploadFile = () => {
  const formData = new FormData()

  const fileRequestDto = new Blob(
    [
      JSON.stringify({
        teamId: 123,
        conferenceId: 546,
        fileName: 'test.png',
        type: 'DOCUMENT'
      })
    ],
    { type: 'application/json' }
  )

  formData.append('file', file.value.files[0])
  formData.append('fileRequestDto', fileRequestDto)

  console.log(file.value.files[0])
  console.log(formData.get('fileRequestDto'))

  createFile(
    formData,
    (response) => {
      console.log(response)
    },
    (error) => {
      console.log(error)
    }
  )
}

onMounted(() => {
  getFiles(
    {
      conferenceId: 546
    },
    (response) => {
      console.log(response.data)
    },
    (error) => {
      console.log(error)
    }
  )
})

// 검색 결과 값 List
const docsList = ref([
  {
    title: 'Item #1',
    value: 2
  },
  {
    title: 'Item #2',
    value: 2
  },
  {
    title: 'Item #3',
    value: 3
  }
])

// const onClick = () => {
//   this.$refs.fileInput.click()
// }

// const onDragenter = (event) => {
//   // class 넣기
//   this.isDragged = true
// }

// const onDragleave = (event) => {
//   // class 삭제
//   this.isDragged = false
// }

// const onDragover = (event) => {
//   // 드롭을 허용하도록 prevetDefault() 호출
//   event.preventDefault()
// }

// const onDrop = (event) => {
//   // 기본 액션을 막음 (링크 열기같은 것들)
//   event.preventDefault()
//   this.isDragged = false
//   const files = event.dataTransfer.files
//   this.addFiles(files)
// }

// const onFileChange = (event) => {
//   const files = event.target.files
//   this.addFiles(files)
// }

// const addFiles = async (files) => {
//   for (let i = 0; i < files.length; i++) {
//     const src = await this.readFiles(files[i])
//     files[i].src = src
//     this.fileList.push(files[i])
//   }
// }
// // FileReader를 통해 파일을 읽어 thumbnail 영역의 src 값으로 셋팅
// const readFiles = async (files) => {
//   return new Promise((resolve, reject) => {
//     const reader = new FileReader()
//     reader.onload = async (e) => {
//       resolve(e.target.result)
//     }
//     reader.readAsDataURL(files)
//   })
// }
const handleRemove = (index) => {
  this.fileList.splice(index, 1)
}
</script>

<style scoped>
.bg {
  background-color: #f0f0f0;
}
.file-upload {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  border: transparent;
  border-radius: 20px;
  cursor: pointer;
  &.dragged {
    border: 1px dashed powderblue;
    opacity: 0.6;
  }
  &-container {
    height: 300px;
    padding: 20px;
    margin: 0 auto;
    box-shadow: 0 0.625rem 1.25rem #0000001a;
    border-radius: 20px;
  }
  &-input {
    display: none;
  }
  &-list {
    margin-top: 10px;
    width: 100%;
    &__item {
      padding: 10px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      &__data {
        display: flex;
        align-items: center;
        &-thumbnail {
          margin-right: 10px;
          border-radius: 20px;
          width: 120px;
          height: 120px;
        }
      }
      &__btn-remove {
        cursor: pointer;
        border: 1px solid powderblue;
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 5px;
        border-radius: 6px;
      }
    }
  }
}
</style>
