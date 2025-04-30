<!-- /src/discuss/components/CommentInput.vue -->
<template>
  <div>
    <v-textarea
      v-model="content"
      label="輸入留言"
      auto-grow
      outlined
      rows="2"
    />
    <v-btn
      color="primary"
      class="mt-2"
      @click="submitComment"
      :disabled="!content.trim()"
    >
      送出
    </v-btn>
    <v-alert type="info" class="mt-2" v-if="!isLoggedIn">
      請先登入才能留言！
    </v-alert>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { useAuthStore } from "@/member/stores/auth";

const props = defineProps({
  articleId: Number,
  parentCommentId: [Number, null],
  onSuccess: Function, // 留言成功後回呼
});

const authStore = useAuthStore();
const isLoggedIn = authStore.isLoggedIn;
const content = ref("");

const submitComment = async () => {
  if (!isLoggedIn) return;

  try {
    await axios.post("/api/comments", {
      articleId: props.articleId,
      parentCommentId: props.parentCommentId,
      content: content.value.trim(),
    });

    content.value = "";
    if (props.onSuccess) props.onSuccess(); // 呼叫父層刷新
  } catch (err) {
    console.error("留言失敗", err);
  }
};
</script>
