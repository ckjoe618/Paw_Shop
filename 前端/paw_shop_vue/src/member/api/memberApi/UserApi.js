import api from "@/member/api/axiosInterceptor";
import { handleResponse } from "../handleResponse";

// 全部會員查詢
export const apiFindMemberAll = () =>
  handleResponse(api.get("/api/user/member"));
// 單筆會員查詢
export const apiFindMember = (data) =>
  handleResponse(api.get(`/api/user/member/${data}`));
// 新增會員
export const apiAddMember = (data) =>
  handleResponse(api.post("/api/user/member", data));
// 單筆會員修改
export const apiUpdateMember = (data, file = null) => {
  if (file) {
    // 如果有圖片 → 使用 multipart/form-data
    const formData = new FormData();
    formData.append(
      "data",
      new Blob([JSON.stringify(data)], { type: "application/json" })
    );
    formData.append("file", file);
    return handleResponse(
      api.put(`/api/user/member/${data.memberId}`, formData, {
        headers: { "Content-Type": "multipart/form-data" },
      })
    );
  }
  return handleResponse(api.put(`/api/user/member/${data.memberId}`, data)); // 沒有圖片 → 使用 JSON 格式
};
// 單筆會員刪除
export const apiDeleteMember = (data) =>
  handleResponse(api.delete(`/api/user/member/${data}`));
// Login
export const apiLogin = (data) =>
  handleResponse(api.post("/api/auth/login", data));
