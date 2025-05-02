import api from "@/api/axiosInterceptor";
import { handleResponse } from "../handleResponse";

// 全部會員查詢
export const apiFindMemberAll = () =>
  handleResponse(api.get("/api/admin/member"));
// 新增會員
export const apiAddMember = (data) =>
  handleResponse(api.post("/api/admin/member", data));
// 單筆會員修改
export const apiUpdateMember = (data) =>
  handleResponse(api.put(`/api/admin/member/${data.memberId}`, data));
// 單筆會員刪除
export const apiDeleteMember = (data) =>
  handleResponse(api.delete(`/api/admin/member/${data}`));
// 分析會員成長
export const apiMemberGrowthAnalysis = () =>
  handleResponse(api.get("/api/admin/member-growth"));
// 分析會員性別
export const apiMemberGenderAnalysis = () =>
  handleResponse(api.get("/api/admin/member-gender"));
// 分析會員所在地
export const apiMemberCityDistribution = () =>
  handleResponse(api.get("/api/admin/member-location"));
