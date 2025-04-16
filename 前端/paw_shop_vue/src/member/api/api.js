import axios from "axios";
import { useAuthStore } from "@/member/stores/auth";

const memberRequest = axios.create({
  baseURL: "http://localhost:8080", // 預設localhost
  timeout: 5000,
});

memberRequest.interceptors.request.use(
  (config) => {
    const authStore = useAuthStore();
    if (authStore.token) {
      config.headers.Authorization = `Bearer ${authStore.token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// CRUD
// 全部會員查詢
const apiFindMemberAll = () => memberRequest.get("/api/member");
// 單筆會員查詢
const apiFindMember = (data) => memberRequest.get(`/api/member/${data}`);
// 新增會員
const apiAddMember = (data) => memberRequest.post("/api/member", data);
// 全部會員修改
const apiUpdateMemberAll = (data) => memberRequest.put("/api/member", data);
// 單筆會員修改
const apiUpdateMember = (data) => memberRequest.put(`/api/member/${data}`);
// 單筆會員刪除
const apiDeleteMember = (data) => memberRequest.delete(`/api/member/${data}`);
// 全部會員刪除
const apiDeleteMemberAll = () => memberRequest.delete("/api/member");

// Login
const apiLogin = (data) => memberRequest.post("/api/auth/login", data);

export {
  memberRequest,
  apiLogin,
  apiFindMemberAll,
  apiFindMember,
  apiAddMember,
  apiUpdateMember,
  apiDeleteMember,
};
