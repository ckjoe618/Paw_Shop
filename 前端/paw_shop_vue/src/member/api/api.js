import api from "@/member/api/axiosInterceptor";
import { handleResponse } from "./handleResponse";

// 預約
// 全部預約查詢
export const apiFindAppointmentAll = () => api.get("/api/appointment");
// 單筆預約查詢
export const apiFindAppointment = (data) =>
  api.get(`/api/appointment/phone/${data}`);
// 新增預約
export const apiaddAppointment = (data) => api.post("/api/appointment", data);
// 顯示單筆預約修改頁面
export const apishowUpdateAppointment = (appointmentId) =>
  api.get(`/api/appointment/${appointmentId}`);
// 單筆預約修改
export const apiUpdateAppointment = (appointmentId, data) =>
  api.put(`/api/appointment/${appointmentId}`, data);
// 刪除單筆預約
export const apiDeleteAppointment = (appointmentId) =>
  api.delete(`/api/appointment/${appointmentId}`);
// 查詢單筆會員寵物
export const apihandleQueryAppointmentPet = (data) =>
  api.get(`/api/querypet/${data}`);
// 查詢已預約時段
export const apihandleQueryBookingTime = (data) =>
  api.get(`/api/querybookingtime/${data}`);
// 預約報到
export const apiAppointmentcheckIn = (id) =>
  api.put(`/api/appointment/checkin/${id}`);
// 顯示預約狀態
export const apiAppointmentsByStatus = (memberId, status) =>
  api.get(`/api/appointment/${memberId}/${status}`);
//取消預約
export const apiAppointmentCancel = (id) =>
  api.put(`/api/appointment/cancel/${id}`);

//購物車內容
export const apiFindShoppingCartItem = () => api.get(`/api/shoppingcart`);
export const apiUpdateShoppingCartItem = (data) =>
  api.put(`/api/shoppingcart`, data);
export const apiDeleteShoppingCartItem = (data) =>
  api.delete(`/api/shoppingcart/${data}`);
export const apiDeleteAllShoppingCartItem = (data) => {
  return api.delete(`/api/shoppingcart/truncatecart`, {
    data,
  });
};
export const apiAddShoppingCartItem = (data) =>
  api.post(`/api/shoppingcart`, data);

//前台訂單
export const apiFindOrdersByMemberId = () => api.get(`/api/order`);
export const apiCancelOrder = (data) => api.put(`/api/order/${data}`);
export const apiCreateOrder = (data) => api.post(`/api/order`, data);
export const apiFindOrderDetails = (data) =>
  api.get(`/api/orderdetail/${data}`);
export const apiUpdateOrderDetail = (data) => api.put(`/api/orderdetail`, data);

//綠界
export const apiECpay = (data) => api.post(`/api/ecpay/checkout`, data);
