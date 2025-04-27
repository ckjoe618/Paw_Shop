import api from "@/member/api/axiosInterceptor";
import { handleResponse } from "../handleResponse";

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
  return handleResponse(
    api.put(`/api/user/member/json/${data.memberId}`, data)
  ); // 沒有圖片 → 使用 JSON 格式
};
// 單筆會員刪除
export const apiDeleteMember = (data) =>
  handleResponse(api.delete(`/api/user/member/${data}`));
// Login
export const apiLogin = (data) =>
  handleResponse(api.post("/api/auth/login", data));
// Google login
export const apiGoogleLogin = () =>
  handleResponse(api.get("/api/auth/oauth2/authorize/google"));
// Google Callback
export const apiLoginByGoogleCallback = (data) =>
  handleResponse(api.get(`/api/auth/oauth2/callback/google?code=${data}`));
// 忘記密碼
export const apiForgotPassword = (data) =>
  handleResponse(api.post("/api/auth/forgot-password", data));
// 重設密碼
export const apiResetPassword = (data) =>
  handleResponse(api.post("/api/auth/reset-password", data));
// 查詢地址
export const apiFindAddress = (data) =>
  handleResponse(api.get(`/api/user/address/${data}`));
// 新增地址
export const apiAddAddress = (data) =>
  handleResponse(api.post("/api/user/address", data));
// 修改地址
export const apiUpdateAddress = (data) =>
  handleResponse(api.put(`/api/user/address/${data.memberId}`, data));
// 修改預設地址
export const apiUpdateDefaultAddress = (data) =>
  handleResponse(api.put(`/api/user/address/default/${data}`));
// 刪除地址
export const apiDeleteAddress = (data) =>
  handleResponse(api.delete(`/api/user/address/${data}`));
// 修改密碼
export const apiUpdatePassword = (data) =>
  handleResponse(api.put(`/api/user/password/${data.memberId}`, data));
