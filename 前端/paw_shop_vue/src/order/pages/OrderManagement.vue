<template>
  <v-container class="pt-16">
    <div class="orderManage-container">
      <h2 class="text-h5 mb-4">我的訂單</h2>
      <v-table class="elevation-1" density="comfortable">
        <thead style="font-size: 18px">
          <tr style="background-color: #f5f5f5">
            <th>訂購日期</th>
            <th>訂單編號</th>
            <th>付款方式</th>
            <th>付款狀態</th>
            <th>取貨方式</th>
            <th>處理進度</th>
            <th>訂單金額</th>
            <th>訂單明細</th>
            <th>取消訂單</th>
          </tr>
        </thead>
        <tbody style="font-size: 16px">
          <tr v-for="item in paginatedOrders" :key="item.orderId">
            <td>{{ item.transactionTime?.substring(0, 10) }}</td>
            <td>{{ item.orderId }}</td>
            <td>{{ item.paymentMethod }}</td>
            <td>{{ item.paymentStatus }}</td>
            <td style="width: 120px">{{ item.pickupMethod }}</td>
            <td>{{ item.orderStatus }}</td>
            <td>${{ item.priceTotal }}</td>
            <td>
              <div class="d-flex align-center" style="gap: 8px">
                <v-btn
                  size="small"
                  color="#E2E9E2"
                  @click="openDetailsDialog(item)"
                  >查看</v-btn
                >
              </div>
            </td>
            <td>
              <div class="d-flex align-center" style="gap: 8px">
                <v-btn
                  size="small"
                  @click="showCancelDialog(item)"
                  :color="canCancel(item.orderStatus) ? '#F5EDE1' : '#808080'"
                  :disabled="!canCancel(item.orderStatus)"
                >
                  取消訂單
                </v-btn>
              </div>
            </td>
          </tr>
        </tbody>
      </v-table>
      <!-- 分頁控制器 -->
      <div class="d-flex justify-center my-4">
        <v-pagination
          v-model="currentPage"
          :length="pageCount"
          total-visible="5"
        ></v-pagination>
      </div>
    </div>
  </v-container>
  <!-- 取消訂單dialog -->
  <OrderCancelDialog
    :visible="dialogVisible"
    :order-id="cancelTargetOrder?.orderId"
    @update:visible="dialogVisible = $event"
    @confirm="confirmCancel"
  />

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
                <th class="text-left"></th>
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

              <template
                v-for="(item, index) in orderDetails"
                :key="item.orderDetailId"
              >
                <!-- 訂單明細列 -->
                <tr
                  :style="
                    item.status === 'disabled'
                      ? 'text-decoration: line-through'
                      : ''
                  "
                >
                  <td><img :src="item.product.productPhoto" width="60" /></td>
                  <td>{{ item.product.productName }}</td>
                  <td>{{ item.quantity }}</td>
                  <td>${{ item.unitPrice }}</td>
                  <td>${{ item.unitPrice * item.quantity }}</td>
                  <td>
                    <v-btn
                      size="small"
                      color="#F3F3FA"
                      @click="
                        expandedRow === index
                          ? (expandedRow = null)
                          : (expandedRow = index)
                      "
                      :disabled="selectedOrder?.orderStatus !== '包裹已送達'"
                      >評論</v-btn
                    >
                  </td>
                </tr>
                <!-- 評論區塊（展開） -->
                <tr v-if="expandedRow === index">
                  <td colspan="6" class="pa-0">
                    <v-expansion-panels
                      v-model="activePanels"
                      class="pa-0"
                      elevation="0"
                    >
                      <v-expansion-panel :value="index">
                        <v-expansion-panel-content>
                          <div class="pa-4">
                            <v-rating
                              v-model="item.rating"
                              length="5"
                              color="amber"
                              size="32"
                              class="mb-3"
                              :readonly="item.commented"
                            />
                            <v-textarea
                              v-model="item.comment"
                              label="撰寫評論..."
                              rows="2"
                              auto-grow
                              variant="outlined"
                              :readonly="item.commented"
                            />

                            <div class="text-right mt-2">
                              <!-- 如果已評論，顯示灰色 chip -->
                              <v-chip
                                v-if="item.commented"
                                color="grey lighten-2"
                                size="small"
                                class="text-caption"
                              >
                                已評論
                              </v-chip>

                              <!-- 如果未評論，但尚未評分，顯示 Tooltip 提示 -->
                              <v-tooltip
                                v-else-if="item.rating <= 0"
                                text="請先給予評分"
                              >
                                <template #activator="{ props }">
                                  <v-btn
                                    v-bind="props"
                                    size="small"
                                    color="green"
                                    variant="outlined"
                                    disabled
                                  >
                                    送出
                                  </v-btn>
                                </template>
                              </v-tooltip>

                              <!-- 如果未評論且評分有效，顯示可送出的按鈕 -->
                              <v-btn
                                v-else
                                size="small"
                                color="green"
                                variant="outlined"
                                @click="submitComment(item)"
                              >
                                送出
                              </v-btn>
                            </div>
                          </div>
                        </v-expansion-panel-content>
                      </v-expansion-panel>
                    </v-expansion-panels>
                  </td>
                </tr>
              </template>
              <!-- 總計 -->
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
  apiUpdateOrderDetail,
} from "@/api/api";
import OrderCancelDialog from "@/order/components/frontsite/OrderCancelDialog.vue";
import Swal from "sweetalert2";

//查詢所有會員訂單
const orders = ref([]);
const fetchOrders = async () => {
  try {
    const res = await apiFindOrdersByMemberId();
    orders.value = res.data;
    console.log(res.data);
  } catch (error) {
    console.error("取得訂單失敗：", error);
  }
};
//分頁
const currentPage = ref(1);
const itemsPerPage = 10;

const paginatedOrders = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return orders.value.slice(start, end);
});

const pageCount = computed(() => Math.ceil(orders.value.length / itemsPerPage));

//查看明細
const seeDetails = ref(false);
const selectedOrder = ref(null);
const orderDetails = ref([]);
const shippingFee = computed(() => selectedOrder.value?.shippingFee || 0);

const openDetailsDialog = (order) => {
  expandedRow.value = null;
  activePanels.value = [];
  selectedOrder.value = order;
  fetchOrderDetails(order.orderId);
  seeDetails.value = true;
};

const fetchOrderDetails = async (orderId) => {
  if (orderId) {
    try {
      const res = await apiFindOrderDetails(orderId);
      orderDetails.value = res.data.map((item) => ({
        ...item,
        commented: item.rating > 0,
      }));
    } catch (error) {
      console.error("取得訂單明細失敗：", error);
    }
  }
};
watch(seeDetails, (val) => {
  if (!val) {
    orderDetails.value = [];
    selectedOrder.value = null;
    expandedRow.value = null;
    activePanels.value = [];
  }
});

//商品評論
const expandedRow = ref(null);
const activePanels = ref([]);
const submitComment = async (item) => {
  console.log("評論送出", item.orderDetailId);
  try {
    const orderDetail = {
      orderDetailId: item.orderDetailId,
      rating: item.rating,
      comment: item.comment,
    };

    await apiUpdateOrderDetail(orderDetail);

    item.commented = true;
    expandedRow.value = null;
    activePanels.value = activePanels.value.filter((i) => i !== item.index);
  } catch (error) {
    console.error("評論送出失敗", error);
  }
};

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
const dialogVisible = ref(false);
const cancelTargetOrder = ref(null);

const showCancelDialog = (item) => {
  cancelTargetOrder.value = item;
  dialogVisible.value = true;
};

const confirmCancel = async () => {
  try {
    const res = await apiCancelOrder(cancelTargetOrder.value.orderId);
    console.log("取消成功", res.data);
    cancelTargetOrder.value.orderStatus = "訂單取消";
    await Swal.fire({
      icon: "success",
      title: "已成功取消訂單",
      showConfirmButton: false,
      timer: 1000,
    });
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
