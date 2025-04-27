import { defineStore } from "pinia"; // pinia的主力方法
import { ref } from "vue"; // vue的響應式狀態
import {
  fetchArticles,
  createArticle as createArticleApi,
} from "@/discuss/api/articleApi"; // 剛剛建好的API方法

export const useArticleStore = defineStore("article", () => {
  const articles = ref([]); // 文章資料
  const loading = ref(false); // 載入中狀態

  // 撈取文章列表
  const getArticles = async (sortType = "latest") => {
    try {
      loading.value = true; // 開始loading
      const result = await fetchArticles(sortType); // 呼叫API
      articles.value = result; // 把資料存進去
    } catch (error) {
      console.error("取得文章失敗", error); // 錯誤處理
    } finally {
      loading.value = false; // loading結束
    }
  };

  // 新增文章
  const createArticle = async (articleData) => {
    try {
      await createArticleApi(articleData); // 呼叫API
      alert("發文成功！");
    } catch (error) {
      console.error("發文失敗", error);
      alert("發文失敗，請稍後再試！");
    }
  };

  return {
    articles,
    loading,
    getArticles,
    createArticle,
  };
});
