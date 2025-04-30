<template>
  <v-card class="mb-4" outlined rounded>
    <v-card-title class="text-h5 font-weight-bold">{{ title }}</v-card-title>

    <v-card-subtitle class="d-flex align-center">
      <span class="mr-2">{{ categoryName }}</span>
      <v-divider vertical class="mx-2"></v-divider>
      <span>{{ memberName }}</span>
    </v-card-subtitle>

    <v-card-text class="py-4">
      <slot />
      <!-- 內文插槽 -->
    </v-card-text>

    <v-divider></v-divider>

    <v-card-actions>
      <!-- 按讚按鈕，根據 isLiked 顯示不同的文字與樣式 -->
      <v-btn
        variant="text"
        :icon="isLiked ? 'mdi-heart' : 'mdi-heart-outline'"
        ripple
        @click="toggleLike"
      >
        {{ likeCount }}
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup>
import { ref, watch } from "vue";

// 接收來自父組件的props
const props = defineProps({
  title: String,
  categoryName: String,
  memberName: String,
  isLiked: Boolean,
  likeCount: Number,
});

// local state，用來管理按讚狀態
const likeCount = ref(props.likeCount);
const isLiked = ref(props.isLiked);

// 當 props 改變時，更新 local state
watch(
  () => props.likeCount,
  (newValue) => {
    likeCount.value = newValue;
  }
);

watch(
  () => props.isLiked,
  (newValue) => {
    isLiked.value = newValue;
  }
);

// 定義事件發射
const emit = defineEmits(["toggle-like"]);

// 按讚邏輯：切換按讚狀態，並向父組件發射事件
const toggleLike = () => {
  isLiked.value = !isLiked.value; // 切換按讚狀態
  likeCount.value += isLiked.value ? 1 : -1; // 根據狀態增加或減少讚數
  emit("toggle-like", isLiked.value); // 發送事件給父組件，更新按讚狀態
};
</script>
