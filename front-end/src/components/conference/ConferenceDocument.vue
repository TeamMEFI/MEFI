<template>
  <div>
    <div ref="editorContainer" id="editor"></div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";

import 'quill/dist/quill.snow.css';
import * as Y from "yjs";
import { WebsocketProvider } from "y-websocket";
import { QuillBinding } from "y-quill";
import Quill from "quill";
import QuillCursors from "quill-cursors";
// import { quillEditor } from "vue-quill-editor";

const route = useRoute();

Quill.register("modules/cursors", QuillCursors);

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
    modules: {
      cursors: true,
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
    theme: "snow", // or 'bubble'
  });

  console.log(provider);

  const binding = new QuillBinding(ytext, editor, provider.awareness);
  
  provider.awareness.setLocalStateField("user", {
    name: "Typing Jimmy",
    color: "blue",
  });
  
  window.example = { provider, ydoc, ytext, binding, Y };
});

// Define user name and user name
// Check the quill-cursors package on how to change the way cursors are rendered

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
  height: 95%
}
</style>
