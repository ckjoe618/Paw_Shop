<template>
  <v-container class="py-8">
    <h1 class="text-h5 font-weight-bold mb-6">我的收藏文章</h1>

    <v-row v-if="favorites.length > 0" dense>
      <v-col
        v-for="article in favorites"
        :key="article.articleId"
        cols="12"
        md="6"
      >
        <v-card @click="goToDetail(article.articleId)" class="hoverable">
          <v-card-title class="text-h6 font-weight-bold">
            {{ article.title }}
          </v-card-title>

          <v-card-subtitle class="d-flex align-center">
            <span>{{ article.categoryName }}</span>
            <v-divider vertical class="mx-2" />
            <span>{{ article.memberName }}</span>
          </v-card-subtitle>

          <v-card-text>
            <div class="text-caption grey--text">
              {{ formatDate(article.createdDate) }}
            </div>
            <div class="text-body-2 mt-2">
              瀏覽 {{ article.viewCount }} ｜留言 {{ article.commentCount }}
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-alert v-else type="info" class="mt-6"> 尚未收藏任何文章。 </v-alert>
  </v-container>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import { useAuthStore } from "@/member/stores/auth";

const router = useRouter();
const authStore = useAuthStore();
const favorites = ref([]);

const goToDetail = (articleId) => {
  router.push(`/discuss/${articleId}`);
};

const formatDate = (dateString) => {
  return new Date(dateString).toLocaleString();
};

onMounted(async () => {
  if (!authStore.isLoggedIn) {
    router.push("/login");
    return;
  }

  try {
    const res = await axios.get(`/api/favorites/member/${authStore.memberId}`);
    favorites.value = res.data;
  } catch (err) {
    console.error("收藏文章載入失敗", err);
  }
});
</script>

<style scoped>
.hoverable {
  cursor: pointer;
  transition: box-shadow 0.3s ease;
}
.hoverable:hover {
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.2);
}
</style>
