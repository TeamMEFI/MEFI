<template>
  <v-sheet height="100%" width="100%" rounded="xl" border elevation="0" class="pa-5">
    <!-- <div
      class="file-upload-container"
      @dragenter="onDragenter"
      @dragover="onDragover"
      @dragleave="onDragleave"
      @drop="onDrop"
      @click="onClick"
    > -->
    <div>
      <!-- </div> -->
      <!-- <div class="file-upload" :class="isDragged ? 'dragged' : ''">Drag & Drop Files</div> -->
    </div>
    <!-- 파일 업로드 -->
    <input type="file" ref="file" class="file-upload-input" @change="onFileChange" multiple />
    <!-- 업로드된 리스트 -->
    <div class="d-flex file-upload-list" v-for="file in fileList" :key="file.fileName">
      <a>{{ file.fileName }}</a>
      <v-btn @click="saveFile(file.fileName)">다운</v-btn>
      <v-btn @click="eraseFile(file.fileName)">삭제</v-btn>
    </div>
    <!-- 업로드할 리스트 -->
    <div
      class="d-flex file-upload-list"
      v-for="addedFile in addedFileList"
      :key="addedFile.fileName"
    >
      <a>{{ addedFile.name }}</a>
      <v-btn disabled>추가됨</v-btn>
      <v-btn @click="removeFile(addedFile.name)">삭제</v-btn>
    </div>
    <v-btn @click="uploadFile">업로드?</v-btn>
  </v-sheet>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getFiles, createFile, downloadFile, deleteFile } from '@/api/file.js'

// API 호출 함수
const file = ref(null)
const fileList = ref([])
const addedFileList = ref([])

const fetchFiles = () => {
  getFiles(
    {},
    546,
    (response) => {
      fileList.value = response.data.dataBody
    },
    (error) => {
      console.log(error)
    }
  )
}

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

const saveFile = (fileName) => {
  downloadFile(
    {
      fileName: fileName
    },
    546,
    (response) => {
      const blob = new Blob([response.data], { type: 'application/octet-stream' });
      const filepath = URL.createObjectURL(blob)

      var element = document.createElement('a')
      element.setAttribute('href', filepath)
      document.body.appendChild(element)
      element.click()
      document.body.removeChild(element)
    },
    (error) => {
      console.log(error)
    }
  )
}

const eraseFile = (fileName) => {
  console.log(name)
  deleteFile(
    {
      fileName: fileName
    },
    546,
    (response) => {
      console.log(response.data)
      alert('삭제되었답니다~')
      fetchFiles()
    },
    (error) => {
      console.log(error)
    }
  )
}

const removeFile = (fileName) => {
  addedFileList.value = addedFileList.value.filter((addedFile) => addedFile.name !== fileName)
}

onMounted(() => {
  fetchFiles()
})

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

const onFileChange = (event) => {
  const files = event.target.files[0]
  console.log(files)
  addedFileList.value.push(files)
}

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
// const handleRemove = (index) => {
//   this.fileList.splice(index, 1)
// }
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
