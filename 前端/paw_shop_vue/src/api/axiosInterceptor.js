import axios from "axios";
import { useAuthStore } from "@/member/stores/auth";
import router from "@/router";

const api = axios.create({
  baseURL: "http://localhost:8080", // 預設localhost
  timeout: 5000,
});

// 防止 token 錯誤重複跳轉用的旗標
let isHandlingTokenError = false;
// 加這個防止 alert 重複出現
let hasShownAlert = false;

const authStore = useAuthStore();
// 加入 request interceptor：自動加上 token
api.interceptors.request.use(
  (config) => {
    if (authStore.token) {
      config.headers.Authorization = `Bearer ${authStore.token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// 加入 response interceptor：統一錯誤處理
api.interceptors.response.use(
  (response) => response,
  (error) => {
    const status = error.response.status;
    const message = error.response.data.message || error.message || "系統錯誤";

    // 根據錯誤狀態碼做不同處理
    const authStore = useAuthStore();

    // 401 未授權處理（避免重複彈窗與跳轉）
    if (status === 401 && !isHandlingTokenError) {
      isHandlingTokenError = true;

      if (!hasShownAlert) {
        hasShownAlert = true;
        alert("登入已過期，請重新登入");
      }
      authStore.logout();
      router.push("/login");
      // 可延遲一段時間後重置 flag，避免短時間重複觸發
      setTimeout(() => {
        isHandlingTokenError = false;
        hasShownAlert = false;
      }, 3000);
      return;
    }

    if (!hasShownAlert && status !== 401) {
      alert(message);
    }

    switch (status) {
      case 403:
        // 沒有權限，導到無權限提示頁
        router.push("/unauthorized");
        break;
      case 404:
        // 找不到資源
        router.push("/not-found");
        break;
      case 500:
        // 伺服器錯誤
        router.push("/server-error");
        break;
      default:
        // 其他錯誤
        if (!status && !hasShownAlert) {
          alert("無法連線到伺服器，請檢查網路或稍後再試");
        }
        break;
    }
    return Promise.reject(error); // 保留錯誤傳遞
  }
);

export default api;
