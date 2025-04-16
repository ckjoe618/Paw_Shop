<template>
  <v-container>
    <v-row align="center" class="d-flex" style="gap: 16px">
      <h2 class="mb-0">所有訂單</h2>
      <addbtn icon-size="25" @click="showCreateDialog = true" />
    </v-row>

    <v-row class="mt-4" justify="center" align="center" style="gap: 32px">
      <v-col cols="12" sm="3" class="d-flex">
        <v-text-field v-model="filterMemberId" label="輸入會員編號" prepend-icon="mdi-account-outline" density="compact"
          @input="filterOrders"></v-text-field>
      </v-col>
      <v-col cols="12" sm="4" class="d-flex">
        <DatePicker v-model="filterRange" @update:modelValue="filterOrders" />
        <v-btn icon variant="plain" size="28" class="ml-10" @click="clearFilter"
          style="box-shadow: none; min-width: 0; height: 40px">
          <v-icon size="24">mdi-close</v-icon>
        </v-btn>
      </v-col>
      <v-col cols="2" sm="2" class="d-flex"></v-col>
      <v-col>
        <v-checkbox label="顯示已失效訂單" v-model="invalidOrders"></v-checkbox>
      </v-col>
    </v-row>

    <v-data-table :headers="headers" :items="filteredOrders" item-value="orderId" class="elevation-1"
      items-per-page-text="每頁顯示筆數：">
      <template #item.actions="{ item }">
        <div class="d-flex align-center" style="gap: 8px">
          <formCheckbtn @click="openDetailDialog(item)" />
          <formEditbtn @click="openEditDialog(item.orderId)" />
          <formDeletebtn @click="confirmDelete(item.orderId)" />
        </div>
      </template>
    </v-data-table>

    <v-dialog v-model="showCreateDialog" max-width="600px">
      <v-card>
        <v-card-title>{{
          editingOrderId ? "編輯訂單" : "新增訂單"
        }}</v-card-title>
        <v-card-text>
          <OrderForm :order-id="editingOrderId" @success="onOrderCreated" @cancel="showCreateDialog = false" />
        </v-card-text>
      </v-card>
    </v-dialog>

    <OrderDeleteDialog :order-id="deletingOrderId" v-model:visible="showDeleteDialog" @confirm="deleteOrder" />

    <OrderDetail :order="selectedOrder" v-model:visible="showDetailDialog" @updateOrderPriceTotal="updatePriceTotal" />
  </v-container>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import OrderDeleteDialog from "../../order/components/backsite/OrderDeleteDialog.vue";
import DatePicker from "../../order/components/date/DatePicker.vue";
import addbtn from "../../order/components/buttons/addbtn.vue";
import formCheckbtn from "../../order/components/buttons/formCheckbtn.vue";
import formDeletebtn from "../../order/components/buttons/formDeletebtn.vue";
import formEditbtn from "../../order/components/buttons/formEditbtn.vue";
import OrderForm from "../../order/components/backsite/OrderForm.vue";
import OrderDetail from "../../order/components/backsite/OrderDetail.vue";
import axios from "axios";

const router = useRouter();

const headers = [
  { text: "訂單編號", value: "orderId" },
  { text: "會員編號", value: "memberId" },
  { text: "訂單金額", value: "priceTotal" },
  { text: "交易時間", value: "transactionTime" },
  { text: "付款方式", value: "paymentMethod" },
  { text: "付款狀態", value: "paymentStatus" },
  { text: "訂單狀態", value: "orderStatus" },
  { text: "取貨方式", value: "pickupMethod" },
  { text: "物流編號", value: "trackingNum" },
  { text: "操作", value: "actions", sortable: false },
];

//查詢所有訂單
const orders = ref([]);
const allOrders = ref([]);
const invalidOrders = ref(false);
const filteredOrders = ref([]);
const selectedOrderId = ref(null);

//根據有沒有勾選已失效訂單載入所有訂單
const reloadOrders = async () => {
  if (invalidOrders.value) {
    // 載入所有訂單（含失效）
    const res = await axios.get("http://localhost:8080/order/allorders");
    allOrders.value = res.data;
    filteredOrders.value = res.data;
  } else {
    // 載入active訂單
    const res = await axios.get("http://localhost:8080/order/allactiveorders");
    orders.value = res.data;
    filteredOrders.value = res.data;
  }
};
watch(invalidOrders, async () => {
  await reloadOrders();
  filterOrders();
});
//filter
const filterMemberId = ref();
const filterRange = ref({ start: "", end: "" });

const filterOrders = () => {
  const memberId = filterMemberId.value;
  const { start, end } = filterRange.value || {};

  const source = invalidOrders.value ? allOrders.value : orders.value;

  filteredOrders.value = source.filter((order) => {
    const txnDate = order.transactionTime?.split("T")[0];
    const matchMemberId = memberId ? String(order.memberId) == memberId : true;
    const matchDateRange =
      start && end ? txnDate >= start && txnDate <= end : true;

    return matchMemberId && matchDateRange;
  });
};

const clearFilter = () => {
  filterMemberId.value = "";
  filterRange.value = { start: "", end: "" };
  filterOrders();
};

//新增
const showCreateDialog = ref(false);
const onOrderCreated = () => {
  showCreateDialog.value = false;
  reloadOrders();
};
//編輯
const editingOrderId = ref(null);
const openEditDialog = (id) => {
  editingOrderId.value = id;
  showCreateDialog.value = true;
};
watch(showCreateDialog, (val) => {
  if (!val) {
    setTimeout(() => {
      editingOrderId.value = null;
    }, 500);
  }
});

//刪除
const showDeleteDialog = ref(false);
const deletingOrderId = ref(null);
const confirmDelete = (id) => {
  deletingOrderId.value = id;
  showDeleteDialog.value = true;
};
const deleteOrder = async () => {
  try {
    await axios.delete(
      `http://localhost:8080/order/delete/${deletingOrderId.value}`
    );
    showDeleteDialog.value = false;
    await reloadOrders();
  } catch (error) {
    console.error("刪除失敗", error);
  }
};

//訂單明細
const selectedOrder = ref(null);
const showDetailDialog = ref(false);
const openDetailDialog = (order) => {
  console.log("打開明細的訂單：", order); // ⬅️ 加這行
  selectedOrder.value = order;
  showDetailDialog.value = true;
};
const updatePriceTotal = (newTotal) => {
  if (selectedOrder.value) {
    selectedOrder.value.priceTotal = newTotal;
    axios.put("http://localhost:8080/order/updatePriceTotal", {
      orderId: selectedOrder.value.orderId,
      priceTotal: newTotal,
    });
  }
};

onMounted(reloadOrders);
</script>

<style scoped></style>
