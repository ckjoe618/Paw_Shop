<template>
  <div class="container mt-3">
    <div id="messageContainer"></div>

    <!-- 查詢表單 -->
    <form @submit.prevent="selectAppointmentByPhoneNum">
      <div class="row g-3 align-items-center">
        <div class="col-auto">
          <label class="col-form-label">預約電話:</label>
        </div>
        <div class="col-auto">
          <input type="text" v-model="phoneNumber" class="form-control" />
        </div>
        <div class="col-auto">
          <button type="submit" class="btn btn-success" :disabled="!phoneNumber">查詢</button>
        </div>
      </div>
    </form>

    <br />

    <!-- 新增按鈕 -->
    <AddButton @click="() => openCreateModal = true" />
    <br>
    <!-- 新增預約 Modal -->
    <div class="modal fade" tabindex="-1" ref="modalRef">
      <div class="modal-dialog">
        <div class="modal-content">
          <form @submit.prevent="submitAppointment">
            <div class="modal-header">
              <h5 class="modal-title">新增預約</h5>
              <button type="button" class="btn-close" @click="openCreateModal = false"></button>
            </div>
            <div class="modal-body">

              <div class="mb-3">
                <label>會員 ID</label>
                <input type="number" v-model="form.memberId" class="form-control" required />
              </div>

              <div class="mb-3">
                <label>寵物</label>
                <select v-model="form.petId" class="form-select" :disabled="pets.length === 0" required>
                  <option value="" disabled>請選擇寵物</option>
                  <option v-for="pet in pets" :key="pet.petId" :value="pet.petId">{{ pet.petName }}</option>
                </select>
              </div>

              <div class="mb-3">
                <label for="appointmentDate">預約日期</label>
                <input type="date" v-model="form.date" id="appointmentDate" class="form-control" @change="onDateChange"
                  required />
              </div>

              <div class="mb-3">
                <label for="appointmentTimeslot">預約時段</label>
                <select v-model="form.timeslot" id="appointmentTimeslot" class="form-select"
                  :disabled="!availableTimeslots.length" required>
                  <option value="" disabled selected>請選擇時段</option>
                  <option v-for="(timeslot, index) in availableTimeslots" :key="index" :value="timeslot.slot"
                    :disabled="timeslot.disabled">
                    {{ timeslot.slot }}</option>
                </select>
              </div>

              <div class="mb-3">
                <label>選擇服務</label>
                <select v-model="form.service" class="form-select" required>
                  <option value="" disabled>請選擇服務</option>
                  <option v-for="service in services" :value="service.id" :key="service.id">
                    {{ service.name }} +{{ service.price }}元
                  </option>
                </select>
              </div>

              <div class="mb-3">
                <label>額外加購</label>
                <div v-for="pkg in extraPackages" :key="pkg.id" class="form-check">
                  <input class="form-check-input" type="checkbox" :value="pkg.id" v-model="form.extraPackages"
                    :id="'extra' + pkg.id">
                  <label class="form-check-label" :for="'extra' + pkg.id">{{ pkg.name }} +{{ pkg.price }}元</label>
                </div>
              </div>

              <div class="mb-3">
                <strong>總價: {{ totalPrice }}元</strong>
              </div>

              <div class="modal-footer">
                <button type="submit" class="btn btn-primary">新增</button>
                <button type="button" class="btn btn-secondary" @click="openCreateModal = false">取消</button>
              </div>

            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- 資料表 -->
    <v-data-table :headers="headers" :items="appointments" item-value="appointmentId" class="elevation-1">
      <thead>
        <tr>
          <th>預約編號</th>
          <th>預約日期</th>
          <th>時間</th>
          <th>預約服務</th>
          <th>加購服務</th>
          <th>總價格</th>
          <th>預約狀態</th>
          <th>付款狀態</th>
          <th>刪除</th>
          <th>修改</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="appointment in appointments" :key="appointment.appointmentId">
          <td>{{ appointment.appointmentId }}</td>
          <td>{{ appointment.appointmentDate }}</td>
          <td>{{ appointment.appointmentTimeslot }}</td>
          <td>{{ getServiceNames(appointment) }}</td>
          <td>{{ getAdditionalPackages(appointment) }}</td>
          <td>{{ appointment.appointmentTotal }}</td>
          <td>{{ getAppointmentStatus(appointment.appointmentStatus) }}</td>
          <td>{{ getPaymentStatus(appointment.paymentStatus) }}</td>
          <td>
            <DeleteButton @click="() => showDeleteModal(appointment.appointmentId)" />
          </td>
          <td>
            <EditButton @click="() => showUpdateModal(appointment.appointmentId)" />
          </td>
        </tr>
      </tbody>
    </v-data-table>

    <!-- 隱藏欄位用來存 appointmentId -->
    <input type="hidden" id="appointmentIdToDelete">
    <input type="hidden" id="appointmentIdToUpdate">

    <!-- 刪除確認 Modal -->
    <v-dialog v-model="showDelete" max-width="400">
      <v-card>
        <v-card-title class="text-h6">確認刪除</v-card-title>
        <v-card-text>您確定要刪除此項目嗎？</v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="secondary" @click="showDelete = false">取消</v-btn>
          <v-btn color="error" @click="confirmDelete">確認刪除</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- 確認修改 Modal -->
    <v-dialog v-model="showUpdate" max-width="500">
      <v-card>
        <v-card-title class="text-h6">
          確認修改
        </v-card-title>

        <v-card-text>
          <p>您確定要修改此項目嗎？</p>
          <!-- 可視需求保留這個欄位 -->
          <input type="hidden" v-model="appointmentIdToUpdate" />
        </v-card-text>

        <v-card-actions class="justify-end">
          <v-btn color="secondary" @click="showUpdate = false">取消</v-btn>
          <v-btn color="primary" @click="goToEditPage">確認修改</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { Modal } from 'bootstrap'
import 'datatables.net'
import {
  apiFindAppointmentAll,
  apiFindAppointment,
  apiaddAppointment,
  apishowUpdateAppointment,
  apiUpdateAppointment,
  apiDeleteAppointment,
  apihandleQueryAppointmentPet,
  apihandleQueryBookingTime
} from "@/member/api/api"
import { useRouter } from 'vue-router'
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import DeleteButton from '@/order/components/buttons/formDeletebtn.vue'
import EditButton from '@/order/components/buttons/formEditbtn.vue'
import AddButton from '@/order/components/buttons/addbtn.vue'

const router = useRouter()

const appointments = ref([])
const phoneNumber = ref('')
const openCreateModal = ref(false)
const modalRef = ref(null)
let modalInstance = null
const modalTitle = ref('新增預約');

watch(openCreateModal, (newVal) => {
  if (newVal && modalRef.value) {
    modalInstance = new Modal(modalRef.value)
    modalInstance.show()
  } else if (!newVal && modalInstance) {
    modalInstance.hide()
  }
})

const hideModal = () => {
  openCreateModal.value = false
}


const form = ref({
  memberId: '',
  petId: '',
  date: '',
  timeslot: '',
  service: '',
  extraPackages: []
})


const pets = ref([])
const appointmentDate = ref('')
const appointmentTimeslot = ref('')
const availableTimeslots = ref([])
const showDelete = ref(false)

const services = ref([
  { id: 1, name: '基礎洗澡', price: 1000 },
  { id: 2, name: '基礎洗護含美容修剪', price: 2000 },
  { id: 3, name: '專業洗護', price: 1900 },
  { id: 4, name: '專業洗護含美容修剪', price: 2800 }
])

const extraPackages = ref([
  { id: 1, name: '7公斤以上', price: 100 },
  { id: 2, name: '防蚤洗劑', price: 300 },
  { id: 3, name: '貓咪草本洗毛精', price: 350 },
  { id: 4, name: '狗狗草本洗毛精', price: 350 }
])
const getServiceNames = (appointment) => {
  if (!appointment.serviceNames) return '';
  const names = appointment.serviceNames.split(/[,|\t]/).map(s => s.trim());
  return [...new Set(names)].join(', ');
}

const getAdditionalPackages = (appointment) => {
  return appointment.additionalPackages || '';
}
const getAppointmentStatus = (status) => {
  if (status === 0) return '未完成';
  if (status === 1) return '已完成';
  return '已取消';
}

const getPaymentStatus = (status) => {
  return status === 1 ? '已付款' : '未付款';
}
const totalPrice = computed(() => {
  let total = 0
  const selectedService = services.value.find(s => s.id === form.value.service)
  if (selectedService) total += selectedService.price
  for (const id of form.value.extraPackages) {
    const pack = extraPackages.value.find(p => p.id === id)
    if (pack) total += pack.price
  }
  return total
})


// 查詢會員的寵物
const fetchPetsByMember = async () => {
  const data = form.value.memberId

  if (!data) {
    pets.value = []
    return
  }

  try {
    const res = await apihandleQueryAppointmentPet(data)
    pets.value = res.data
    console.log("✅ 成功載入寵物資料:", pets.value)
  } catch (err) {
    console.error("❌ 載入寵物資料失敗", err)
  }
}
watch(() => form.value.memberId, (newId) => {
  if (newId) {
    fetchPetsByMember(newId)
  } else {
    pets.value = []
  }
})

//查到預約時段
const onDateChange = async () => {
  const data = form.value.date
  if (!data) {
    alert("請選擇一個日期")
    return
  }

  try {
    const res = await apihandleQueryBookingTime(data)

    const bookedTimeslots = res.data.bookedTimeslots || []
    const allTimeslots = ["10:00-12:00", "12:00-14:00", "14:00-16:00", "16:00-18:00", "18:00-20:00"]

    availableTimeslots.value = allTimeslots.map(slot => ({
      slot,
      disabled: bookedTimeslots.includes(slot)
    }))
  } catch (error) {
    console.error("無法獲取預約資料: ", error)
  }
}

//電話查詢
const selectAppointmentByPhoneNum = async () => {
  if (!phoneNumber.value) return

  try {
    const data=phoneNumber.value;
    const res = await apiFindAppointment(data)
    appointments.value = res.data || []
  } catch (error) {
    console.error('查詢失敗:', error)
  }
}


//送出表單
const submitAppointment = async () => {
  console.log('表單資料:', form.value)
  openCreateModal.value = false

  const data = {
    memberId: parseInt(form.value.memberId, 10),
    petId: parseInt(form.value.petId, 10),
    appointmentDate: form.value.date,
    appointmentTimeslot: form.value.timeslot,
    services: form.value.service,
    extraPackages: form.value.extraPackages
  }
  console.log('送出資料:', JSON.stringify(data))
  try {
    const res = await apiaddAppointment(data)
    console.log("預約成功", res.data)
    window.location.reload();
  } catch (error) {
    console.error("預約失敗", error)
  }
}

//刪除預約
const deleteModalRef = ref(null)
let deleteModalInstance = null
onMounted(() => {
  if (deleteModalRef.value) {
    deleteModalInstance = new Modal(deleteModalRef.value)
  }
})
const appointmentIdToDelete = ref(null)
const showDeleteModal = (appointmentId) => {
  console.log('設置刪除預約 ID:', appointmentId)
  appointmentIdToDelete.value = appointmentId
  showDelete.value = true
}

const confirmDelete = async () => {
  console.log('confirmDelete 被觸發');
  if (!appointmentIdToDelete.value) return;
  console.log('正在刪除預約 ID:', appointmentIdToDelete.value);


  try {
    const response = await apiDeleteAppointment(appointmentIdToDelete.value);

    console.log('API 返回:', response);

    const result = response.data;
    console.log('Response data:', result);
    console.log('typeof success:', typeof result.success);

    if (result.success) {

      appointments.value = appointments.value.filter(
        a => a.appointmentId !== appointmentIdToDelete.value
      );



      if (deleteModalInstance) {
        deleteModalInstance.hide();
      }
      showDelete.value = false;

      console.log('刪除成功');

      window.location.reload();
    } else {
      console.error('刪除預約失敗: success = false', result.message);
    }

  } catch (error) {
    console.error('刪除預約時發生錯誤:', error);
  }
};

//修改預約
const showUpdate = ref(false)
const appointmentIdToUpdate = ref(null)

const showUpdateModal = (appointmentId) => {
  console.log('設置修改預約 ID:', appointmentId)
  appointmentIdToUpdate.value = appointmentId
  showUpdate.value = true
}

const goToEditPage = () => {
  if (!appointmentIdToUpdate.value) return
  router.push(`/appointments/edit/${appointmentIdToUpdate.value}`).then(() => {

    window.location.reload();
  });
}

//顯示資料
onMounted(async () => {
  try {
    const res = await apiFindAppointmentAll()
    appointments.value = res.data
    console.log('預約資料:', appointments.value);
  } catch (error) {
    console.error('獲取預約資料失敗:', error)
  }
})

</script>