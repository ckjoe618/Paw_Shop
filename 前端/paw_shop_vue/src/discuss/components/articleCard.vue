<template>
  <v-card
    class="pa-4 mb-4"
    elevation="2"
    rounded="lg"
    style="transition: box-shadow 0.3s"
    @mouseover="hover = true"
    @mouseleave="hover = false"
    :style="hover ? 'box-shadow: 0 4px 20px rgba(0,0,0,0.15);' : ''"
  >
    <v-row>
      <v-col>
        <!-- 標題 -->
        <h3 class="text-h6 font-weight-bold mb-2">{{ article.title }}</h3>

        <!-- 作者 & 時間 -->
        <div class="text-body-2 text-grey-darken-1 mb-2">
          {{ article.memberName }} ｜ {{ formatDate(article.createdDate) }}
        </div>

        <!-- 分類標籤＋互動數字 -->
        <v-row class="align-center">
          <v-chip
            size="small"
            color="blue-lighten-4"
            text-color="blue-darken-4"
            class="mr-4"
          >
            {{ article.categoryName }}
          </v-chip>

          <v-icon small class="mr-1">mdi-eye</v-icon>
          <span class="mr-4">{{ article.viewCount }}</span>

          <v-icon small class="mr-1">mdi-comment-text-multiple-outline</v-icon>
          <span>{{ article.commentCount }}</span>
        </v-row>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup>
import { ref } from "vue";

const props = defineProps({
  article: {
    type: Object,
    required: true,
  },
});

const hover = ref(false);

function formatDate(dateString) {
  const date = new Date(dateString);
  return date.toLocaleString("zh-TW", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
  });
}
</script>

<style scoped></style>
