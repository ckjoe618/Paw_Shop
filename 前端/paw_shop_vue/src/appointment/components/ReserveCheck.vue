<template>
    <div class="container my-5">
      <!-- 最終確認事項 -->
      <section class="border-top pt-4 p-4 border rounded shadow-sm bg-white">
        <p class="fw-bold mb-2">請確認以下事項後，進入預約表單：</p>
        <ul>
            <li>預約時段通常會根據美容師的可用時間來安排，並且會提供大約的美容時間長度（例如1-2小時）。</li>
            <li>若寵物主人遲到超過15分鐘，視為取消預約，請重新安排預約。</li>
            <li>美容過程中可能會根據寵物的狀況進行額外建議或服務變動，顧客會被提前告知。</li>
            <li>預約前應確保寵物健康狀況良好，無重大疾病或傳染性疾病。</li>
            <li>服務費用通常依據寵物的大小、毛發長短及選擇的服務項目而有所不同。</li>
        </ul>
  
        <!-- 同意 Checkbox -->
        <div class="form-check mt-3">
          <input
            class="form-check-input custom-checkbox"
            type="checkbox"
            id="agreeCheck"
            v-model="agreed"
          />
          <label class="form-check-label" for="agreeCheck">
            我已閱讀並同意以上內容
          </label>
        </div>
  
        <!-- 確認按鈕 -->
        <button
          class="btn btn-success px-4 py-2 rounded-pill w-100 mt-3"
          :disabled="!agreed"
          @click="goToForm"
        >
        前往預約表單
        </button>
      </section>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  import Swal from "sweetalert2"
  
  const agreed = ref(false)
  const router = useRouter()
  
  const memberId = ref(localStorage.getItem("memberId"))
  
  onMounted(() => {
  const redirectedFromLogin = sessionStorage.getItem("justLoggedIn") === "true";
  const agreedFromSession = sessionStorage.getItem("agreed") === "true";

  if (redirectedFromLogin && memberId.value && agreedFromSession) {
    // 確保 agreed 被設為 true 才能觸發按鈕跳轉條件
    agreed.value = true;

    sessionStorage.removeItem("justLoggedIn");
    sessionStorage.removeItem("agreed");

    // 跳轉到預約表單
    router.push("/appointments/reserve");
  }
});

  function goToForm() {
    if (!memberId.value) {
      localStorage.setItem("redirectPath", "/appointments/reserve")
      // 透過 sessionStorage 記錄：稍後登入完要導回來再判斷
      sessionStorage.setItem("justLoggedIn", "true")
      sessionStorage.setItem("agreed", "true");
      router.push("/login?redirect=/appointments/reserve");
      return
    }
  
    if (agreed.value) {
      router.push("/appointments/reserve")
    } else {
      Swal.fire({
        icon: "info",
        title: "請勾選同意條款後再繼續",
        confirmButtonText: "好的",
      })
    }
  }
  </script>
  
  
  <style scoped>
  .container {
    max-width: 700px;
  }
  
  section {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 20px;
    background-color: white;
  }
  
  button {
    font-size: 1.1rem;
    padding: 0.8rem;
  }
  
  button:disabled {
    opacity: 0.7;
  }
  
  section + section {
    margin-top: 20px;
  }
  
  /* 自定義綠色勾選框樣式 */
  .custom-checkbox:checked {
    background-color: #28a745; /* 綠色 */
    border-color: #28a745;
  }
  
  .custom-checkbox:checked:focus {
    box-shadow: 0 0 0 0.25rem rgba(40, 167, 69, 0.5);
  }
  </style>
  