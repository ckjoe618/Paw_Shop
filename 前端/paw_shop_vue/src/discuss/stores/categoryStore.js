import { defineStore } from "pinia";
import axios from "axios";

export const useCategoryStore = defineStore("categoryStore", {
  state: () => ({
    categories: [],
  }),
  actions: {
    async fetchCategories() {
      try {
        const response = await axios.get("/api/article-category");
        this.categories = response.data;
      } catch (error) {
        console.error("取得分類失敗", error);
      }
    },
  },
});
