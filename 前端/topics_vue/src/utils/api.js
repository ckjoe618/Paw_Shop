import axios from "axios";
import { useMemberStore } from "@/stores/useMemberStores";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
  timeout: 3000,
});

// 攔截請求
api.interceptors.request.use(
  (config) => {
    const memberStore = useMemberStore();
    const token = memberStore.memebr.token;

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {}
);

api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    alert(`狀態碼:${error.response.status}, 訊息:${error.message}`);
  }
);

export default api;
