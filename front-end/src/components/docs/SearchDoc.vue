<template>
  <v-sheet
    height="100%"
    width="100%"
    rounded="lg"
    border
    elevation="0"
    :class="[isDragged ? 'dragged' : '', 'pa-5']"
    @dragenter.prevent="onDragenter"
    @dragover.prevent="onDragenter"
    @dragleave.prevent="onDragleave"
    @drop.prevent="onDrop"
  >
    <div v-if="fileList.length + addedFileList.length > 0">
      <!-- 업로드된 리스트 -->
      <div
        class="d-flex justify-space-between file-upload-list"
        v-for="file in fileList"
        :key="file.fileName"
      >
        <a class="file-name">{{ file.fileName }}</a>
        <div class="d-flex">
          <v-btn @click="saveFile(file.fileName)">다운</v-btn>
          <v-btn @click="eraseFile(file.fileName)">삭제</v-btn>
        </div>
      </div>
      <!-- 업로드할 리스트 -->
      <div
        class="d-flex justify-space-between file-upload-list"
        v-for="addedFile in addedFileList"
        :key="addedFile.fileName"
      >
        <a>{{ addedFile.name }}</a>
        <div class="d-flex">
          <!-- <v-btn disabled>추가됨</v-btn> -->
          <v-btn @click="removeFile(addedFile.name)">삭제</v-btn>
        </div>
      </div>
    </div>
    <div v-else>관련 문서가 없습니다.</div>
  </v-sheet>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getFiles, createFile, downloadFile, deleteFile } from '@/api/file.js'

const route = useRoute()
const teamId = ref(route.params?.teamid)
const conferenceId = ref(route.params?.conferenceid)

// API 호출 함수
const file = ref(null)
const fileList = ref([])
const addedFileList = ref([])

const isDragged = ref(false)

const fetchFiles = () => {
  getFiles(
    {},
    conferenceId.value,
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
        teamId: teamId.value,
        conferenceId: conferenceId.value,
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
      fetchFiles()
      addedFileList.value = []
    },
    (error) => {
      console.log(error)
    }
  )
}

const saveFile = (fileName) => {
  const originFileName = fileName

  downloadFile(
    {
      fileName: originFileName
    },
    conferenceId.value,
    (response) => {
      // Blob 파일 형식을 URL 객체로 변환
      const filepath = URL.createObjectURL(response.data)

      // 임의의 a 태그 생성 후 href 속성에 URL 객체 할당
      const element = document.createElement('a')
      element.setAttribute('href', filepath)
      element.setAttribute('download', originFileName)
      document.body.appendChild(element)

      // 생성한 a 태그 클릭
      element.click()

      // a 태그와 URL 객체 삭제
      document.body.removeChild(element)
      // URL.revokeObjectUrl(filepath)
    },
    (error) => {
      console.log(error)
    }
  )
}

const eraseFile = (fileName) => {
  deleteFile(
    {
      fileName: fileName
    },
    546,
    (response) => {
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

// 드래그 앤 드롭을 사용하지 않고 수동으로 파일을 넣는 함수
// const onFileChange = (event) => {
//   const files = event.target.files[0]
//   console.log(files)
//   addedFileList.value.push(files)
// }

const onDragenter = (event) => {
  isDragged.value = true
}

const onDragleave = (event) => {
  isDragged.value = false
}

const onDrop = (event) => {
  isDragged.value = false
  const files = event.dataTransfer.files[0]
  addedFileList.value.push(files)
}
</script>

<style scoped>
.dragged {
  border: 1px dashed #495464;
  background-color: #f7f9ff;
  opacity: 0.6;
}

.file-name {
  width: 75%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
