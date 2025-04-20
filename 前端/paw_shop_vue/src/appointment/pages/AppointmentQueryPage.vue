<template>
  <div class="container mt-5">
    <h2 class="text-center mb-4">預約查詢</h2>

    <!-- 狀態選擇下拉選單 -->
    <div class="mb-3">
      <label for="statusSelect" class="form-label">選擇預約狀態：</label>
      <select
        id="statusSelect"
        v-model="selectedStatus"
        @change="fetchAppointments"
        class="form-select"
      >
        <option :value="0">待處理</option>
        <option :value="1">已完成</option>
        <option :value="2">已取消</option>
      </select>
    </div>

    <!-- 預約列表 -->
    <div
      v-if="appointments.length === 0"
      class="alert alert-warning text-center"
    >
      尚未找到任何預約。
    </div>
    <ul v-else>
      <!-- 分頁後顯示的預約 -->
      <li
        v-for="appointment in paginatedAppointments"
        :key="appointment.appointmentId"
        class="mb-4 p-3 border rounded shadow-sm"
      >
        <div class="d-flex justify-content-between align-items-start mb-2">
          <p class="fw-bold">預約編號: {{ appointment.appointmentId }}</p>
          <p class="text-muted">
            {{ getAppointmentStatus(appointment.appointmentStatus) }}
          </p>
        </div>
        <div>
          <p>
            <strong>寵物名稱:</strong>
            {{ appointment.petName || "無提供寵物名稱" }}
          </p>
          <p>
            <strong>預約日期:</strong>
            {{ formatDate(appointment.appointmentDate) }}
          </p>
          <p>
            <strong>時段:</strong>
            {{ appointment.appointmentTimeslot || "無提供時段" }}
          </p>
          <p>
            <strong>基本服務:</strong>
            {{ appointment.serviceNames || "無服務項" }}
          </p>
          <p>
            <strong>加購服務:</strong>
            {{ appointment.additionalPackages || "無加購服務" }}
          </p>
          <p>
            <strong>總費用:</strong> ${{
              appointment.appointmentTotal || "未提供費用"
            }}
          </p>
          <p>
            <strong>支付狀態:</strong>
            {{ getPaymentStatus(appointment.paymentStatus) }}
          </p>
        </div>

        <!-- QR Code 顯示按鈕 -->
        <button
          class="btn"
          :class="
            showQRCodeForAppointment === appointment.appointmentId
              ? 'btn-danger'
              : 'btn-success'
          "
          @click="toggleQRCode(appointment.appointmentId)"
        >
          {{
            showQRCodeForAppointment === appointment.appointmentId
              ? "關閉 QR Code"
              : "顯示 QR Code"
          }}
        </button>

        <!-- QR Code 顯示區 -->
        <div
          v-if="showQRCodeForAppointment === appointment.appointmentId"
          class="mt-3"
        >
          <QrCodeDisplay :appointment-id="appointment.appointmentId" />
        </div>
      </li>
    </ul>

    <!-- 分頁 -->
    <div class="d-flex justify-content-center mt-4">
      <button
        class="btn btn-secondary"
        @click="previousPage"
        :disabled="currentPage === 1"
      >
        上一頁
      </button>
      <span class="mx-3">第 {{ currentPage }} 頁</span>
      <button
        class="btn btn-secondary"
        @click="nextPage"
        :disabled="currentPage === totalPages"
      >
        下一頁
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { apiAppointmentsByStatus } from "@/member/api/api";
import QrCodeDisplay from "@/appointment/components/QrCodeDisplay.vue";

const toggleQRCode = (appointmentId) => {
  if (showQRCodeForAppointment.value === appointmentId) {
    showQRCodeForAppointment.value = null; // 再按一次就關閉
  } else {
    showQRCodeForAppointment.value = appointmentId; // 顯示該筆
  }
};

const memberId = ref();
const selectedStatus = ref(0);
const appointments = ref([]);
const currentPage = ref(1);
const itemsPerPage = 3;
const showQRCodeForAppointment = ref(null);

const fetchAppointments = async () => {
  try {
    const response = await apiAppointmentsByStatus(
      memberId.value,
      selectedStatus.value
    );
    appointments.value = response.data;
  } catch (error) {
    console.error("獲取預約資料失敗", error);
    alert(`無法加載預約資料，錯誤訊息: ${error.message}`);
  }
};

onMounted(() => {
  const storedId = localStorage.getItem("memberId");
  if (storedId) {
    memberId.value = storedId;
    fetchAppointments(); // 此時 memberId 已經有值
  } else {
    alert("尚未登入，請先登入會員");
  }
});

const getAppointmentStatus = (status) => {
  const statusMapping = {
    0: "待處理",
    1: "已完成",
    2: "已取消",
  };
  return statusMapping[status] || "未知狀態";
};

const getPaymentStatus = (status) => {
  const paymentMapping = {
    0: "未支付",
    1: "已支付",
  };
  return paymentMapping[status] || "未知支付狀態";
};

const formatDate = (date) => {
  if (!date) return "未提供日期";
  const options = { year: "numeric", month: "long", day: "numeric" };
  return new Date(date).toLocaleDateString("zh-TW", options);
};

const paginatedAppointments = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return appointments.value.slice(start, end);
});

const totalPages = computed(() => {
  return Math.ceil(appointments.value.length / itemsPerPage);
});

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};

const previousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};
</script>

<style scoped>
.container {
  max-width: 900px;
  margin: 0 auto;
}

h2 {
  color: #3e8e41;
  font-weight: 700;
  font-size: 2rem;
}

select.form-select {
  width: 200px;
}

ul {
  list-style-type: none;
  padding-left: 0;
}

li {
  background-color: #f1f8f1;
  border-radius: 15px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

li:hover {
  background-color: #d4e8d4;
}

p {
  margin-bottom: 10px;
  font-size: 1.1rem;
}

strong {
  color: #3e8e41;
}

.text-muted {
  color: #6c757d;
}

.fw-bold {
  font-weight: 700;
}

button {
  margin-top: 10px;
  font-weight: 600;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #2c6e2c;
}

button:disabled {
  background-color: #c0c0c0;
  cursor: not-allowed;
}
</style>
