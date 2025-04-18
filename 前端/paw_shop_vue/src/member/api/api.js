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
const apiUpdateMemberAll = (data) =>
  memberRequest.put(`/api/member/${data.memberId}`, data);
// 單筆會員修改
const apiUpdateMember = (data) => memberRequest.put(`/api/member/${data}`);
// 單筆會員刪除
const apiDeleteMember = (data) => memberRequest.delete(`/api/member/${data}`);
// 全部會員刪除
const apiDeleteMemberAll = () => memberRequest.delete("/api/member");

// Login
const apiLogin = (data) => memberRequest.post("/api/auth/login", data);

// 全部預約查詢
const apiFindAppointmentAll = () => memberRequest.get("/api/appointment");
// 單筆預約查詢
const apiFindAppointment = (data) =>
  memberRequest.get(`/api/appointment/phone/${data}`);
// 新增預約
const apiaddAppointment = (data) =>
  memberRequest.post("/api/appointment", data);
// 顯示單筆預約修改頁面
const apishowUpdateAppointment = (appointmentId) =>
  memberRequest.get(`/api/appointment/${appointmentId}`);
// 單筆預約修改
const apiUpdateAppointment = (appointmentId, data) =>
  memberRequest.put(`/api/appointment/${appointmentId}`, data);
// 刪除單筆預約
const apiDeleteAppointment = (appointmentId) =>
  memberRequest.delete(`/api/appointment/${appointmentId}`);
// 查詢單筆會員寵物
const apihandleQueryAppointmentPet = (data) =>
  memberRequest.get(`/api/querypet/${data}`);
// 查詢已預約時段
const apihandleQueryBookingTime = (data) =>
  memberRequest.get(`/api/querybookingtime/${data}`);

export {
  memberRequest,
  apiLogin,
  apiFindMemberAll,
  apiFindMember,
  apiAddMember,
  apiUpdateMember,
  apiDeleteMember,
  apiFindAppointmentAll,
  apiFindAppointment,
  apiaddAppointment,
  apishowUpdateAppointment,
  apiUpdateAppointment,
  apiDeleteAppointment,
  apihandleQueryAppointmentPet,
  apihandleQueryBookingTime,
};
