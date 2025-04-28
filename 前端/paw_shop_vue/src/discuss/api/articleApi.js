import axios from "axios";
import { useAuthStore } from "@/member/stores/auth";

const API_BASE_URL = "/api/articles/previews"; // 這個是後端討論區API開頭

// 查詢文章列表
export const fetchArticles = async (sortType = "latest") => {
  const response = await axios.get(`${API_BASE_URL}?sort=${sortType}`);
  return response.data;
};

// 發文
export const createArticle = async (articleData) => {
  const res = await axios.post("/api/articles", articleData);
  return res.data;
};

// 單篇文章詳情 API
export async function fetchArticleDetail(articleId) {
  try {
    const response = await axios.get(`/api/articles/${articleId}`, {
      headers: {
        Authorization: `Bearer ${useAuthStore()}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error("取得單篇文章失敗", error);
    throw error;
  }
}
