<template>
  <v-card class="pa-4">
    <v-card-title>聊天室</v-card-title>

    <v-card-text class="chat-room" style="height: 300px; overflow-y: auto">
      <div v-if="messages.length === 0" class="text-grey">
        還沒有訊息，快來發送第一則吧！
      </div>
      <div v-for="(msg, index) in messages" :key="index" class="mb-2">
        <strong>{{ msg.memberName }}：</strong>{{ msg.content }}
      </div>
    </v-card-text>

    <v-divider></v-divider>

    <v-card-actions>
      <v-text-field
        v-model="newMessage"
        label="輸入訊息"
        variant="outlined"
        dense
        @keyup.enter="sendMessage"
        :disabled="!isLoggedIn"
      ></v-text-field>
      <v-btn @click="sendMessage" :disabled="!isLoggedIn || !newMessage.trim()">
        送出
      </v-btn>
    </v-card-actions>

    <v-alert v-if="!isLoggedIn" type="info" class="mt-2" dense>
      請先登入才能參與聊天！
    </v-alert>
  </v-card>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import axios from "axios";

const messages = ref([]);
const newMessage = ref("");
const socket = ref(null);

const token = localStorage.getItem("token");
const isLoggedIn = !!token;

onMounted(async () => {
    await loadHistory();
  if (isLoggedIn) {
    connectWebSocket();
  }
});

onUnmounted(() => {
  if (socket.value && socket.value.readyState === WebSocket.OPEN) {
    socket.value.close();
  }
});

// 載入歷史訊息
const loadHistory = async () => {
  try {
    const res = await axios.get("/api/chat-messages");
    messages.value = res.data;
  } catch (error) {
    console.error("載入歷史訊息失敗", error);
  }
};

// 連線 WebSocket
const connectWebSocket = () => {
  socket.value = new WebSocket(`ws://localhost:8080/ws/chat?token=${token}`);

  socket.value.onmessage = (event) => {
    const msg = JSON.parse(event.data);
    messages.value.push({
      memberName: msg.memberName,
      content: msg.content,
      createdDate: msg.createdDate,
    });
  };

  socket.value.onclose = () => {
    console.log("聊天室連線關閉");
  };

  socket.value.onerror = (error) => {
    console.error("聊天室連線錯誤", error);
  };
};

// 發送訊息
const sendMessage = () => {
  if (socket.value && socket.value.readyState === WebSocket.OPEN) {
    socket.value.send(JSON.stringify({ content: newMessage.value.trim() }));
    newMessage.value = "";
  }
};
</script>

<style scoped>
.chat-room {
  background-color: #f5f5f5;
  padding: 8px;
  border-radius: 8px;
}
</style>
