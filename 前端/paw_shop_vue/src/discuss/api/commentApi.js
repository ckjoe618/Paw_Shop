// 取得一篇文章的巢狀留言
import axios from "axios";
import { useAuthStore } from "@/member/stores/auth";

export async function fetchCommentsByArticle(articleId) {
  try {
    const response = await axios.get(
      `/api/comments/grouped/article/${articleId}`,
      {
        headers: {
          Authorization: `Bearer ${useAuthStore()}`,
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("取得留言失敗", error);
    throw error;
  }
}
