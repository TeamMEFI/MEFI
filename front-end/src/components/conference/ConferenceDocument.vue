<template>
  <div>
    <div ref="editorContainer" id="editor"></div>
    <v-btn @click="createPDF">버튼</v-btn>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRoute } from 'vue-router'

// yjs 사용하기
import * as Y from 'yjs'
import { WebsocketProvider } from 'y-websocket'
import { QuillBinding } from 'y-quill'

// Quill Editor + Markdown 사용을 위한 최소한의 설정
import 'quill/dist/quill.snow.css'
import Quill from 'quill'
import QuillCursors from 'quill-cursors'
import MarkdownShortcuts from 'quill-markdown-shortcuts'
// import { quillEditor } from "vue-quill-editor";

const route = useRoute()

Quill.register('modules/cursors', QuillCursors)
Quill.register('modules/markdownShortcuts', MarkdownShortcuts)

const ydoc = new Y.Doc()

const provider = new WebsocketProvider(
  // `ws${location.protocol.slice(4)}//${location.host}/ws`,
  'wss://demos.yjs.dev/ws', // use the public ws server
  `mefi${route.params.id}`,
  ydoc
)

// Yjs + Quill 연동
const ytext = ydoc.getText('quill')
const editorContainer = ref(null)

onMounted(() => {
  const editor = new Quill(editorContainer.value, {
    theme: 'snow', // or 'bubble'
    modules: {
      cursors: true,
      markdownShortcuts: {},
      toolbar: [
        ['bold', 'italic', 'underline', 'strike'],
        [{ header: 1 }, { header: 2 }],
        [{ list: 'ordered' }, { list: 'bullet' }],
        [{ script: 'sub' }, { script: 'super' }],
        [{ indent: '-1' }, { indent: '+1' }],
        [{ direction: 'rtl' }],
        [{ size: ['small', false, 'large', 'huge'] }],
        [{ header: [1, 2, 3, 4, 5, 6, false] }],
        [{ color: [] }, { background: [] }],
        [{ font: [] }],
        [{ align: [] }],
        ['clean'],
        ['link', 'image', 'video']
      ],
      history: {
        userOnly: true
      }
    },
    placeholder: 'Start collaborating...'
  })

  const binding = new QuillBinding(ytext, editor, provider.awareness)

  // 문서 작성 중인 이름 및 색상 설정
  provider.awareness.setLocalStateField('user', {
    name: 'Typing User',
    // 색깔 랜덤 할당
    color: `#${Math.floor(Math.random() * 16777215).toString(16)}`
  })

  // window.example = { provider, ydoc, ytext, binding, Y };

  onBeforeUnmount(() => {
    binding.destroy()
  })

  console.log(`ws${location.protocol.slice(4)}//${location.host}/ws`)
})

// const connectState = ref("Disconnect");

// const connect = () => {
//   if (provider.shouldConnect) {
//     provider.disconnect();
//     connectState.value = "Connect";
//   } else {
//     provider.connect();
//     connectState.value = "Disconnect";
//   }
// };

// 공동 작업 문서 PDF 파일로 변환하여 저장
const createPDF = () => {
  // editorContainer을 canvas객체로 변환
  html2canvas(editorContainer.value).then((canvas) => {
    const filename = 'OTA-REPORT_' + Date.now() + '.pdf'
    const doc = new jsPDF('p', 'mm', 'a4') // jsPDF 객체 생성
    const imgData = canvas.toDataURL('image/png') // canvas를 .png 이미지로 변환
    const imgWidth = 210
    const pageHeight = 295
    const imgHeight = (canvas.height * imgWidth) / canvas.width

    let position = 0
    let heightLeft = imgHeight

    // 첫 페이지 생성
    doc.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight)
    heightLeft -= pageHeight

    // 저장해야할 남은 문서 내용이 없을 때까지 페이지 추가
    while (heightLeft >= 0) {
      position = heightLeft - imgHeight
      doc.addPage()
      doc.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight)
      heightLeft -= pageHeight
    }

    doc.save(filename)

    // 서버로 axios 요청
    // 공동 작업 문서를 pdf(binary) 형식으로 보냄
    // 백엔드와 소통하여 axios 연결할 예정
    // const formData = new FormData()
    // const file = doc.output('blob', filename)

    // formData.append('file', file)

    // console.log(...formData)

    // axios({
    //   url: 'http://localhost:5000/document',
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'multipart/form-data'
    //   },
    //   data: formData
    // })
    //   .then((res) => {
    //     console.log(res.data)
    //   })
    //   .catch((err) => console.error(err))
  })
}
</script>

<style scoped>
#editor {
  background-color: white;
  height: 90%;
}
</style>
