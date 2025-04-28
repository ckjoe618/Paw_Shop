<template>
  <v-container class="py-8">
    <v-row justify="center">
      <v-col cols="12" md="8">
        <v-card elevation="2" class="pa-6">
          <v-card-title class="text-h5 font-weight-bold mb-6"
            >發表新文章</v-card-title
          >

          <!-- 分類選擇 -->
          <v-select
            v-model="selectedCategory"
            :items="categories"
            item-title="categoryName"
            item-value="categoryId"
            label="選擇分類"
            variant="outlined"
            density="comfortable"
            class="mb-4"
            :disabled="loading"
          ></v-select>

          <!-- 標題 -->
          <v-text-field
            v-model="title"
            label="文章標題"
            variant="outlined"
            density="comfortable"
            maxlength="80"
            counter
            class="mb-4"
            :disabled="loading"
          ></v-text-field>

          <!-- 內文 -->
          <v-textarea
            v-model="content"
            label="文章內容"
            variant="outlined"
            density="comfortable"
            auto-grow
            rows="8"
            class="mb-6"
            :disabled="loading"
          ></v-textarea>

          <!-- 發文按鈕 -->
          <v-btn
            :loading="loading"
            :disabled="loading"
            color="primary"
            block
            @click="submitArticle"
          >
            發表文章
          </v-btn>
        </v-card>
      </v-col>
    </v-row>

    <!-- 成功 / 失敗訊息 -->
    <v-snackbar v-model="snackbar.show" :color="snackbar.color" timeout="3000">
      {{ snackbar.message }}
    </v-snackbar>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useCategoryStore } from "@/discuss/stores/categoryStore";
import { useArticleStore } from "@/discuss/stores/articleStore";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";

const router = useRouter();
const categoryStore = useCategoryStore();
const articleStore = useArticleStore();
const { categories } = storeToRefs(categoryStore);

const selectedCategory = ref(null);
const title = ref("");
const content = ref("");

const memberId = localStorage.getItem("memberId");

const loading = ref(false); // 按鈕loading狀態
const snackbar = ref({
  show: false,
  message: "",
  color: "success",
});

onMounted(() => {
  categoryStore.fetchCategories();
});

// 發文送出
const submitArticle = async () => {
  if (!selectedCategory.value || !title.value.trim() || !content.value.trim()) {
    showSnackbar("請填寫所有欄位！", "error");
    return;
  }

  loading.value = true;

  try {
    await articleStore.createArticle({
      categoryId: selectedCategory.value,
      title: title.value,
      content: content.value,
      memberId: memberId,
    });

    showSnackbar("發文成功！", "success");

    setTimeout(() => {
      router.push("/discuss");
    }, 1000); // 稍微等Snackbar顯示一下再跳轉
  } catch (error) {
    console.error("發文失敗", error);
    showSnackbar("發文失敗，請稍後再試！", "error");
  } finally {
    loading.value = false;
  }
};

// 顯示Snackbar
const showSnackbar = (message, color = "success") => {
  snackbar.value.message = message;
  snackbar.value.color = color;
  snackbar.value.show = true;
};
</script>

<style scoped>
.v-card-title {
  font-weight: bold;
}
</style>
