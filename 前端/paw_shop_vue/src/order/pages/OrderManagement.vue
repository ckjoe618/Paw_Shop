<template>
  <v-container class="pt-16">
    <div class="orderManage-container">
      <v-data-table
        :headers="headers"
        :items="orders"
        class="elevation-1"
        density="comfortable"
      >
        <template #item.priceTotal="{ item }">
          ${{ item.priceTotal }}
        </template>

        <template #item.transactionTime="{ item }">
          {{ item.transactionTime?.substring(0, 10) }}
        </template>
        <template #item.details="{ item }">
          <div class="d-flex align-center" style="gap: 8px">
            <v-btn size="small" color="#E2E9E2" @click="openDetailsDialog(item)"
              >查看</v-btn
            >
          </div>
        </template>
        <template #item.cancel="{ item }">
          <div class="d-flex align-center" style="gap: 8px">
            <v-btn
              size="small"
              @click="cancelOrder(item)"
              :color="canCancel(item.orderStatus) ? '#F5EDE1' : '#808080'"
              :disabled="!canCancel(item.orderStatus)"
              >取消訂單</v-btn
            >
          </div>
        </template>
      </v-data-table>
    </div>
  </v-container>

  <!-- 訂單明細dialog -->
  <v-dialog v-model="seeDetails" max-width="800px">
    <v-card>
      <v-card-title class="text-h5">訂單明細</v-card-title>
      <v-card-text>
        <v-divider class="my-2" />
        <div>
          <h4
            class="text-subtitle-1 mb-2"
            style="font-weight: bold; color: #4f4f4f"
          >
            <v-icon>mdi-shopping-search</v-icon>明細項目：
          </h4>
          <v-table density="comfortable">
            <thead>
              <tr>
                <th class="text-left">商品編號</th>
                <th class="text-left">商品名稱</th>
                <th class="text-left">數量</th>
                <th class="text-left">單價</th>
                <th class="text-left">小計</th>
                <th class="text-left"></th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-if="Array.isArray(orderDetails) && orderDetails.length"
                v-for="(item, index) in orderDetails"
                :key="item.orderDetailId"
                :style="
                  item.status === 'disabled'
                    ? 'text-decoration: line-through'
                    : ''
                "
              ></tr>

              <tr
                v-for="(item, index) in orderDetails"
                :key="item.orderDetailId"
                :style="
                  item.status === 'disabled'
                    ? 'text-decoration: line-through'
                    : ''
                "
              >
                <td>{{ item.product.productId }}</td>
                <td>{{ item.product.productName }}</td>
                <td>{{ item.quantity }}</td>
                <td>${{ item.unitPrice }}</td>
                <td>${{ item.unitPrice * item.quantity }}</td>
                <td>
                  <v-btn size="small" color="#F3F3FA" @click="">評論</v-btn>
                </td>
              </tr>
              <tr>
                <td colspan="4"></td>
                <td>運費 ${{ shippingFee }}</td>
                <td></td>
              </tr>
              <tr>
                <td colspan="4"></td>
                <td class="fw-bold">總計 ${{ totalPrice }}</td>
                <td></td>
              </tr>
            </tbody>
          </v-table>
        </div>
        <div class="mt-10">
          <h4
            class="text-subtitle-1 mb-2"
            style="font-weight: bold; color: #4f4f4f"
          >
            <v-icon>mdi-store-marker</v-icon>收件資訊：
          </h4>
          <v-table class="elevation-1" density="comfortable">
            <tbody>
              <tr>
                <td
                  class="bg-grey-lighten-4 font-weight-medium text-grey-darken-1"
                  style="width: 160px"
                >
                  收件人姓名
                </td>
                <td>{{ selectedOrder?.recipientName }}</td>
              </tr>
              <tr>
                <td
                  class="bg-grey-lighten-4 font-weight-medium text-grey-darken-1"
                >
                  收件人電話
                </td>
                <td>{{ selectedOrder?.recipientPhone }}</td>
              </tr>
              <tr>
                <td
                  class="bg-grey-lighten-4 font-weight-medium text-grey-darken-1"
                >
                  收件地址
                </td>
                <td>{{ selectedOrder?.recipientAddress }}</td>
              </tr>
            </tbody>
          </v-table>
        </div>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text @click="seeDetails = false">關閉</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, onMounted, computed, watch } from "vue";
import {
  apiFindOrdersByMemberId,
  apiCancelOrder,
  apiFindOrderDetails,
} from "@/member/api/api";

const headers = [
  { title: "訂購日期", key: "transactionTime" },
  { title: "訂單編號", key: "orderId" },
  { title: "付款方式", key: "paymentMethod", sortable: false },
  { title: "付款狀態", key: "paymentStatus", sortable: false },
  { title: "取貨方式", key: "pickupMethod", sortable: false },
  { title: "處理進度", key: "orderStatus", sortable: false },
  { title: "訂單金額", key: "priceTotal" },
  { title: "訂單明細", key: "details", sortable: false },
  { title: "取消訂單", key: "cancel", sortable: false },
];

//查詢所有會員訂單
const orders = ref([]);
const fetchOrders = async () => {
  try {
    const res = await apiFindOrdersByMemberId();
    orders.value = res.data;
  } catch (error) {
    console.error("取得訂單失敗：", error);
  }
};

//查看明細
const seeDetails = ref(false);
const selectedOrder = ref(null);
const orderDetails = ref([]);
const shippingFee = computed(() => selectedOrder.value?.shippingFee || 0);

const openDetailsDialog = (order) => {
  selectedOrder.value = order;
  fetchOrderDetails(order.orderId);
  seeDetails.value = true;
};

const fetchOrderDetails = async (orderId) => {
  if (orderId) {
    try {
      const res = await apiFindOrderDetails(orderId);
      orderDetails.value = res.data;
    } catch (error) {
      console.error("取得訂單明細失敗：", error);
    }
  }
};
watch(seeDetails, (val) => {
  if (!val) {
    orderDetails.value = [];
    selectedOrder.value = null;
  }
});

//總計計算
const totalPrice = computed(() => {
  const itemTotal = orderDetails.value
    .filter((item) => item.status !== "disabled")
    .reduce(
      (sum, item) => sum + (item.subtotal || item.unitPrice * item.quantity),
      0
    );
  const shipping = selectedOrder.value?.shippingFee || 0;
  return itemTotal + shipping;
});

onMounted(() => {
  fetchOrders();
});

//取消訂單
const cancelOrder = async (item) => {
  const confirm = window.confirm(`確定要取消編號 ${item.orderId} 的訂單？`);
  if (!confirm) return;
  try {
    const res = await apiCancelOrder(item.orderId);
    console.log("取消成功", res.data);
    item.orderStatus = "訂單取消";
  } catch (error) {
    console.error("取消訂單失敗", error);
  }
};

const canCancel = (status) => {
  return status === "訂單已成立" || status === "備貨中";
};
</script>

<style scoped>
.orderManage-container {
  max-width: 1000px;
  margin: 0 auto;
}
</style>
