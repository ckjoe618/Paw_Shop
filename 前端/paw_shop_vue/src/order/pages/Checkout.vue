<template>
  <v-container>
    <div class="checkout-container">
      <div class="step-indicator">
        <p>選擇付款方式 → 填寫訂購資料 → 完成訂購</p>
      </div>

      <v-table class="custom-table">
        <thead>
          <tr>
            <th>商品圖片</th>
            <th>商品名稱</th>
            <th>數量</th>
            <th>單價</th>
            <th>小計</th>
            <th>刪除</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in cartItems" :key="item.id">
            <td><img :src="item.imageUrl" width="80" /></td>
            <td>{{ item.name }}</td>
            <td>{{ item.qty }}</td>
            <td>{{ item.price }}</td>
            <td>{{ item.price * item.qty }}</td>
            <td>
              <v-btn icon variant="plain" density="comfortable"
                ><v-icon size="22">mdi-delete</v-icon></v-btn
              >
            </td>
          </tr>
        </tbody>
      </v-table>
      <div class="total-area text-right mt-4">
        <p>商品金額總計：NT$ {{ subtotal }}</p>
        <p>折扣小計：NT$ -{{ discount }}</p>
        <p>運費小計：NT$ {{ shipping }}</p>
        <h5 class="text-red fw-bold">
          應付金額總計：NT$ {{ subtotal - discount + shipping }}
        </h5>
      </div>

      <!-- 優惠方式 -->
      <v-divider class="my-4" />
      <h4>
        <v-icon class="mr-2">mdi-checkbox-multiple-marked-outline</v-icon
        >使用折抵
      </h4>
      <v-checkbox label="使用購物金扣款" v-model="usePoints" color="success" />
      <v-text-field v-model="usedPoints" :disabled="!usePoints">
        <template #label>
          <span>輸入購物金</span>
          <span style="font-size: 14px; color: #5b5b5b; margin-left: 10px">
            目前可使用購物金：{{ ablePoints }}
          </span>
        </template>
      </v-text-field>

      <!-- 運送方式 -->
      <v-divider class="my-4" />
      <h4>
        <v-icon class="mr-2">mdi-truck-cargo-container</v-icon>選擇取貨方式
      </h4>
      <v-radio-group v-model="pickupMethod">
        <v-radio value="Delivery" color="success">
          <template #label>
            <span style="font-weight: bold">宅配</span>
            <span style="font-size: 14px; color: #5b5b5b; margin-left: 10px">
              (單筆未滿 $1000，運費 $85)
            </span>
          </template>
        </v-radio>
        <v-radio value="CashOnDelivery" color="success">
          <template #label>
            <span style="font-weight: bold">超商取貨付款</span>
            <span style="font-size: 14px; color: #5b5b5b; margin-left: 10px">
              (單筆未滿 $1000，運費 $60)
            </span>
          </template>
        </v-radio>
        <v-radio value="In-store" color="success">
          <template #label>
            <span style="font-weight: bold">超商取貨不付款</span>
            <span style="font-size: 14px; color: #5b5b5b; margin-left: 10px">
              (單筆未滿 $1000，運費 $60)
            </span>
          </template>
        </v-radio>
      </v-radio-group>

      <!-- 付款方式 -->
      <v-divider class="my-4" />
      <h4><v-icon class="mr-2">mdi-credit-card-outline</v-icon>確認付款方式</h4>
      <v-row class="mt-2">
        <v-col cols="auto">
          <v-btn
            size="large"
            :color="paymentMethod === 'card' ? 'success' : '#FBFFFD'"
            :variant="paymentMethod === 'card' ? 'elevated' : 'flat'"
            @click="paymentMethod = 'card'"
            :class="{ 'btn-disabled': paymentLocked }"
            >線上刷卡</v-btn
          >
        </v-col>
        <v-col cols="auto">
          <v-btn
            size="large"
            :color="paymentMethod === 'cash' ? 'success' : '#FBFFFD'"
            :variant="paymentMethod === 'cash' ? 'elevated' : 'flat'"
            @click="paymentMethod = 'cash'"
            :class="{ 'btn-disabled': paymentLocked }"
            >現金付款</v-btn
          >
        </v-col>
      </v-row>

      <div class="d-flex justify-end mt-12" style="gap: 20px">
        <v-btn color="#F0F0F0" class="mt-6" elevation="0">繼續購物</v-btn>
        <v-btn color="#006000" class="mt-6" elevation="0">下一步</v-btn>
      </div>
    </div>
  </v-container>
</template>

<script setup>
import { ref, computed, watch } from "vue";

// 假資料，可以從前端 localStorage 或 Pinia 拿
const cartItems = ref([
  {
    id: 1,
    name: "木耳邊透膚彈性上衣",
    qty: 1,
    price: 590,
    imageUrl: "/img/product-01.jpg",
  },
]);

//折扣方式
const usePoints = ref(false);
const usedPoints = ref(0);

const subtotal = computed(() =>
  cartItems.value.reduce((sum, item) => sum + item.price * item.qty, 0)
);
const discount = computed(() =>
  usePoints.value ? parseInt(usedPoints.value) : 0
);
const shipping = ref(85);

//取貨方式 & 付款方式
const pickupMethod = ref(null);
const paymentMethod = ref(null);
const paymentLocked = ref(false);
watch(pickupMethod, (newMethod) => {
  if (newMethod === "Delivery" || newMethod === "In-store") {
    paymentMethod.value = "card";
    paymentLocked.value = true;
  } else if (newMethod === "CashOnDelivery") {
    paymentMethod.value = "cash";
    paymentLocked.value = true;
  }
});
</script>

<style scoped>
.checkout-container {
  max-width: 900px;
  margin: 0 auto;
}
.text-red {
  color: #d32f2f;
}
.custom-table {
  border: 1px solid #ccc; /* 外框線 */
  border-radius: 6px; /* 可選：圓角 */
  overflow: hidden; /* 防止圓角被蓋掉 */
}
.custom-table th,
.custom-table td {
  border: 1px solid #ccc; /* 每個 cell 加邊框 */
  border-left: none;
  border-right: none;
  padding: 8px;
  font-size: 18px;
}
.custom-table th {
  background-color: #f9f9f9;
  font-weight: 600;
}
.btn-disabled {
  pointer-events: none;
  opacity: 1 !important; /* 取消灰色 */
}
</style>
