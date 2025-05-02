<template>
  <v-card class="mb-4" outlined rounded>
    <!-- 文章標題 -->
    <v-card-title class="text-h5 font-weight-bold">{{ title }}</v-card-title>

    <!-- 分類與作者 -->
    <v-card-subtitle class="d-flex align-center">
      <span class="mr-2">{{ categoryName }}</span>
      <v-divider vertical class="mx-2"></v-divider>
      <span>{{ memberName }}</span>
    </v-card-subtitle>

    <!-- 📝 內文 -->
    <v-card-text class="py-4">
      <slot />
    </v-card-text>

    <!-- ❤️ 按讚 + 操作選單（移到內文底下） -->
    <v-row class="px-4 pb-2" align="center" justify="space-between">
      <v-btn variant="text" ripple @click="emit('toggle-like')">
        <v-icon start>
          {{ isLiked ? "mdi-thumb-up" : "mdi-thumb-up-outline" }}
        </v-icon>
        {{ likeCount }}
      </v-btn>

      <ActionMenu
        :isOwner="authStore.memberId === memberId"
        :isArticle="true"
        :articleId="articleId"
        @delete="$emit('delete')"
        @edit="emit('edit')"
        @favorite="emit('toggle-favorite')"
      />
    </v-row>

    <!-- 🗨️ 主文留言與留言輸入插槽 -->
    <v-card-text class="pt-0">
      <slot name="footer" />
    </v-card-text>
  </v-card>
</template>

<script setup>
import ActionMenu from "./comment/actionMenu.vue";
import { useAuthStore } from "@/member/stores/auth";

const authStore = useAuthStore();

const props = defineProps({
  title: String,
  categoryName: String,
  memberName: String,
  memberId: Number,
  articleId: Number,
  isLiked: Boolean,
  likeCount: Number,
  isFavorited: Boolean,
});

const emit = defineEmits(["toggle-like", "delete", "edit", "toggle-favorite"]);

// 是否是作者
const isOwner = authStore.memberId === props.memberId;
</script>
