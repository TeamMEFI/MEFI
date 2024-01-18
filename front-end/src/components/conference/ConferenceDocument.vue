<template>
  <div>
    <div ref="editorContainer" id="editor"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { useRoute } from "vue-router";

// yjs 사용하기
import * as Y from "yjs";
import { WebsocketProvider } from "y-websocket";
import { QuillBinding } from "y-quill";

// Quill Editor + Markdown 사용을 위한 최소한의 설정
import 'quill/dist/quill.snow.css';
import Quill from "quill";
import QuillCursors from "quill-cursors";
import MarkdownShortcuts from 'quill-markdown-shortcuts';
// import { quillEditor } from "vue-quill-editor";

const route = useRoute();

Quill.register("modules/cursors", QuillCursors);
Quill.register('modules/markdownShortcuts', MarkdownShortcuts);

const ydoc = new Y.Doc();

const provider = new WebsocketProvider(
  `ws${location.protocol.slice(4)}//${location.host}/ws`, // use the local ws server
  // 'wss://demos.yjs.dev/ws', // alternatively use the public ws server
  "quill-demo-5" + route.params.sessionId,
  ydoc
);

// Yjs + Quill 연동
const ytext = ydoc.getText("quill");
const editorContainer = ref(null);

onMounted(() => {
  const editor = new Quill(editorContainer.value, {
    theme: "snow", // or 'bubble'
    modules: {
      cursors: true,
      markdownShortcuts: {},
      toolbar: [
        ["bold", "italic", "underline", "strike"],
        [{ header: 1 }, { header: 2 }],
        [{ list: "ordered" }, { list: "bullet" }],
        [{ script: "sub" }, { script: "super" }],
        [{ indent: "-1" }, { indent: "+1" }],
        [{ direction: "rtl" }],
        [{ size: ["small", false, "large", "huge"] }],
        [{ header: [1, 2, 3, 4, 5, 6, false] }],
        [{ color: [] }, { background: [] }],
        [{ font: [] }],
        [{ align: [] }],
        ["clean"],
        ["link", "image", "video"],
      ],
      history: {
        userOnly: true,
      },
    },
    placeholder: "Start collaborating...",
  });

  const binding = new QuillBinding(ytext, editor, provider.awareness);
  
  // 문서 작성 중인 이름 및 색상 설정
  provider.awareness.setLocalStateField("user", {
    name: "Typing User",
    // 색깔 랜덤 할당
    color: `#${Math.floor(Math.random() * 16777215).toString(16)}`
  });
  
  // window.example = { provider, ydoc, ytext, binding, Y };

  onBeforeUnmount(() => {
    binding.destroy();
  });
});

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

// @ts-ignore
</script>

<style scoped>
#editor {
  background-color: white;
  height: 90%
}
</style>
