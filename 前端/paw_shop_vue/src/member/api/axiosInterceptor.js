import axios from "axios";
import { useAuthStore } from "@/member/stores/auth";
import router from "@/router";

const api = axios.create({
  baseURL: "http://localhost:8080", // 預設localhost
  timeout: 5000,
});

// 加入 request interceptor：自動加上 token
api.interceptors.request.use(
  (config) => {
    const authStore = useAuthStore();
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
    const status = error?.response?.status;
    const message =
      error?.response?.data?.message || error.message || "系統錯誤";

    // ✅ 顯示錯誤訊息
    alert(message);

    // ✅ 根據錯誤狀態碼做不同處理
    const authStore = useAuthStore();

    switch (status) {
      case 400:
        // 錯誤參數
        break;
      case 401:
        // 未授權，強制登出
        authStore.logout();
        router.push("/login");
        break;
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
        if (!status) {
          alert("無法連線到伺服器，請檢查網路或稍後再試");
        }
        break;
    }

    return Promise.reject(error); // 保留錯誤傳遞
  }
);

export default api;
