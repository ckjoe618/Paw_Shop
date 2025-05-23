/**
 * 統一處理後端回傳 response 的情況
 * 注意：HTTP 錯誤（401, 500）會由 axios interceptor 處理
 * 這裡只處理 200 的狀況
 */
export const handleResponse = async (promise) => {
  try {
    const response = await promise;
    const { data, message } = response.data;
    return data; // 回傳純資料，簡化呼叫端
  } catch (error) {
    // 錯誤訊息已由 interceptor 顯示，不再 alert，保留錯誤讓呼叫端 decide
    throw error;
  }
};
