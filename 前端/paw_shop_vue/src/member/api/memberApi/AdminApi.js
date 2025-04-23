import api from "@/member/api/axiosInterceptor";
import { handleResponse } from "../handleResponse";

// 全部會員查詢q
export const apiFindMemberAll = () =>
  handleResponse(api.get("/api/admin/member"));
// 單筆會員查詢
export const apiFindMember = (data) =>
  handleResponse(api.get(`/api/admin/member/${data}`));
// 新增會員
export const apiAddMember = (data) =>
  handleResponse(api.post("/api/admin/member", data));
// 單筆會員修改
export const apiUpdateMember = (data) =>
  handleResponse(api.put(`/api/admin/member/${data.memberId}`, data));
// 單筆會員刪除
export const apiDeleteMember = (data) =>
  handleResponse(api.delete(`/api/admin/member/${data}`));
