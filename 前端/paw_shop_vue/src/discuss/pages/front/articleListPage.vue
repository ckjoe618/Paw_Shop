<template>
  <!-- 搜尋列區塊 -->
  <v-sheet
    elevation="2"
    style="
      background-color: white;
      position: sticky;
      top: 80px;
      z-index: 5;
      padding: 16px 24px;
    "
  >
    <v-row class="align-center">
      <!-- 左側空間 -->
      <v-col cols="2"></v-col>

      <!-- 中間搜尋＋排序 -->
      <v-col cols="8" class="d-flex justify-center">
        <v-row class="align-center">
          <v-text-field
            v-model="search"
            label="搜尋文章"
            variant="outlined"
            density="compact"
            hide-details
            rounded
            prepend-inner-icon="mdi-magnify"
            class="mr-4"
            style="max-width: 300px"
          />
          <v-select
            v-model="sortOption"
            :items="['最新', '熱門']"
            label="排序"
            variant="outlined"
            density="compact"
            hide-details
            rounded
            style="max-width: 150px"
          />
        </v-row>
      </v-col>

      <!-- 右邊功能按鈕 -->
      <v-col cols="2" class="d-flex justify-end">
        <v-btn icon>
          <v-icon>mdi-bell</v-icon>
        </v-btn>
        <v-btn icon class="ml-2" @click="goToPostPage">
          <v-icon>mdi-plus</v-icon>
        </v-btn>
        <v-btn icon class="ml-2" @click="goToProfilePage">
          <v-icon>mdi-account-circle</v-icon>
        </v-btn>
      </v-col>
    </v-row>
  </v-sheet>

  <v-container fluid>
    <v-row>
      <!-- 左側分類清單 -->
      <v-col cols="2">
        <v-list>
          <v-list-item
            @click="selectCategory('')"
            :class="{ active: selectedCategory === '' }"
            style="cursor: pointer"
          >
            <v-list-item-title>全部分類</v-list-item-title>
          </v-list-item>
          <v-list-item
            v-for="category in categories"
            :key="category.categoryId"
            @click="selectCategory(category.categoryName)"
            :class="{ active: selectedCategory === category.categoryName }"
            style="cursor: pointer"
          >
            <v-list-item-title>{{ category.categoryName }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-col>

      <!-- 中間文章列表 -->
      <v-col cols="7">
        <!-- Loading圈圈 -->
        <v-row justify="center" v-if="articleStore.loading">
          <v-progress-circular indeterminate color="primary" size="50" />
        </v-row>

        <!-- 文章小卡 -->
        <v-card
          v-for="article in pagedArticles"
          :key="article.articleId"
          class="mb-4"
          outlined
          rounded
          @click="goToArticle(article.articleId)"
          hover
          style="cursor: pointer"
          v-ripple
        >
          <v-card-title>{{ article.title }}</v-card-title>
          <v-card-subtitle>
            {{ article.memberName }} | {{ formatDate(article.createdDate) }}
          </v-card-subtitle>
          <v-card-text>
            <v-chip class="ma-2" color="primary" text-color="white">
              {{ article.categoryName }}
            </v-chip>
            <v-icon small class="ml-2">mdi-eye</v-icon> {{ article.viewCount }}
            <v-icon small class="ml-2">mdi-comment</v-icon>
            {{ article.commentCount }}
          </v-card-text>
        </v-card>

        <!-- 分頁器 -->
        <v-row justify="center" class="mt-4">
          <v-pagination
            v-model="currentPage"
            :length="totalPages"
            rounded
            total-visible="7"
            color="primary"
          ></v-pagination>
        </v-row>
      </v-col>

      <!-- 右側聊天室 -->
      <v-col cols="3">
        <chatRoom />
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useArticleStore } from "@/discuss/stores/articleStore";
import { useCategoryStore } from "@/discuss/stores/categoryStore";
import chatRoom from "@/discuss/components/chatRoom.vue";

const router = useRouter();
const articleStore = useArticleStore();

const categoryStore = useCategoryStore();
const { categories } = storeToRefs(categoryStore);

const search = ref("");
const sortOption = ref("最新");
const selectedCategory = ref("");

const currentPage = ref(1); // 分頁用
const itemsPerPage = 5; // 每頁5筆

const goToPostPage = () => {
  router.push("/discuss/post");
};

const goToProfilePage = () => {
  router.push("/member");
};

const goToArticle = (articleId) => {
  router.push(`/discuss/${articleId}`);
};

const selectCategory = (categoryName) => {
  selectedCategory.value = categoryName;
};

const formatDate = (dateStr) => {
  const date = new Date(dateStr);
  return date.toLocaleString("zh-TW", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
  });
};

// 篩選 + 排序後的所有文章
const filteredArticles = computed(() => {
  let filtered = articleStore.articles;

  if (selectedCategory.value) {
    filtered = filtered.filter(
      (a) => a.categoryName === selectedCategory.value
    );
  }
  if (search.value) {
    filtered = filtered.filter((a) =>
      a.title.toLowerCase().includes(search.value.toLowerCase())
    );
  }
  if (sortOption.value === "最新") {
    filtered = filtered.sort(
      (a, b) => new Date(b.createdDate) - new Date(a.createdDate)
    );
  } else if (sortOption.value === "熱門") {
    filtered = filtered.sort((a, b) => b.viewCount - a.viewCount);
  }
  return filtered;
});

// 目前分頁應該顯示的文章
const pagedArticles = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return filteredArticles.value.slice(start, end);
});

// 計算總頁數
const totalPages = computed(() => {
  return Math.ceil(filteredArticles.value.length / itemsPerPage);
});

// 頁面載入時撈資料
onMounted(() => {
  articleStore.getArticles();
  categoryStore.fetchCategories();
});
</script>

<style scoped>
.v-list-item-title {
  font-size: 16px;
}
.v-card-title {
  font-weight: bold;
}
.active {
  background-color: #eeeeee;
  font-weight: bold;
}
</style>
