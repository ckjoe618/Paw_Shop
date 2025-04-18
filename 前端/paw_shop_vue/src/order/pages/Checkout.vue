<template>
  <v-container>
    <div class="checkout-container">
      <div class="d-flex justify-center mt-8 mb-10">
        <img src="@/order/assets/pictures/step1ing.jpg" />
        <img src="@/order/assets/pictures/step2.jpg" />
        <img src="@/order/assets/pictures/step3.jpg" />
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
          <template v-for="item in cartItems" :key="item.id">
            <tr v-if="item.product">
              <td>
                <img src="@/order/assets/pictures/step1ing.jpg" width="80" />
              </td>
              <td>{{ item.product.productName }}</td>
              <td>
                <div class="d-inline-flex">
                  <v-btn
                    icon
                    elevation="0"
                    size="x-small"
                    @click="decreaseQty(item)"
                  >
                    <v-icon>mdi-minus</v-icon>
                  </v-btn>
                  <div
                    class="text-center"
                    style="
                      width: 40px;
                      border: 1px solid #ccc;
                      line-height: 32px;
                    "
                  >
                    {{ item.quantity }}
                  </div>
                  <v-btn
                    icon
                    elevation="0"
                    size="x-small"
                    @click="increaseQty(item)"
                  >
                    <v-icon>mdi-plus</v-icon>
                  </v-btn>
                </div>
              </td>
              <td>{{ item.product.productPrice }}</td>
              <td>{{ item.product.productPrice * item.quantity }}</td>
              <td>
                <v-btn
                  icon
                  variant="plain"
                  density="comfortable"
                  @click="deleteItem(item)"
                  ><v-icon size="22">mdi-delete</v-icon></v-btn
                >
              </td>
            </tr>
          </template>
        </tbody>
      </v-table>
      <div class="total-area text-right mt-4">
        <p>商品金額總計：NT$ {{ subtotal }}</p>
        <p>折抵小計：NT$ -{{ discount }}</p>
        <p>運費小計：NT$ {{ shipping }}</p>
        <h5 class="text-red fw-bold">
          應付金額總計：NT$ {{ subtotal - discount + shipping }}
        </h5>
      </div>
      <v-form>
        <!-- 優惠方式 -->
        <v-divider class="my-4" />
        <h4>
          <v-icon class="mr-2">mdi-checkbox-multiple-marked-outline</v-icon
          >使用折抵
        </h4>
        <v-checkbox
          label="使用購物金扣款"
          v-model="usePoints"
          color="success"
        />
        <span style="font-size: 14px; color: #5b5b5b; margin-left: 16px">
          目前可使用購物金：{{ ablePoints }}
        </span>
        <v-text-field
          label="輸入購物金"
          v-model="usedPoints"
          :disabled="!usePoints"
          :rules="[validatePoints]"
          type="number"
        >
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
        <h4>
          <v-icon class="mr-2">mdi-credit-card-outline</v-icon>確認付款方式
        </h4>
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
          <v-btn
            color="#F0F0F0"
            class="mt-6"
            elevation="0"
            @click="keppShopping"
            >繼續購物</v-btn
          >
          <v-btn
            color="#006000"
            class="mt-6"
            elevation="0"
            @click="orderingInfo"
            >下一步</v-btn
          >
        </div>
      </v-form>
    </div>
  </v-container>
</template>

<script setup>
import { ref, computed, watch, onMounted } from "vue";
import axios from "axios";
import router from "@/router";
import {
  apiFindShoppingCartItem,
  apiUpdateShoppingCartItem,
  apiDeleteShoppingCartItem,
} from "@/member/api/api";

// 向後端拿購物車內容
const cartItems = ref([]);
const getAllItems = async () => {
  try {
    const res = await apiFindShoppingCartItem();
    console.log("載入成功");
    cartItems.value = res.data;
  } catch (err) {
    console.error("載入購物車失敗", err);
  }
};
onMounted(getAllItems);

const subtotal = computed(() =>
  Array.isArray(cartItems.value)
    ? cartItems.value.reduce(
        (sum, item) => sum + item.product.productPrice * item.quantity,
        0
      )
    : 0
);

//修改購物車商品數量
const decreaseQty = async (item) => {
  if (item.quantity > 1) {
    item.quantity--;
    try {
      const res = await apiUpdateShoppingCartItem({
        cartItemId: item.cartItemId,
        quantity: item.quantity,
      });
      console.log("更新成功", res.data);
    } catch (error) {
      console.error("更新失敗", error);
    }
  }
};

const increaseQty = async (item) => {
  if (item.quantity > 1) {
    item.quantity++;
    try {
      const res = await apiUpdateShoppingCartItem({
        cartItemId: item.cartItemId,
        quantity: item.quantity,
      });
      console.log("更新成功", res.data);
    } catch (error) {
      console.error("更新失敗", error);
    }
  }
};

//刪除購物車商品
const deleteItem = async (item) => {
  try {
    const res = await apiDeleteShoppingCartItem(item.cartItemId);
    await getAllItems();
    console.log("刪除成功", res.data);
  } catch (error) {
    console.error("刪除失敗", error);
  }
};

//折扣方式
const usePoints = ref(false);
const usedPoints = ref(0);
const ablePoints = ref(20);

const discount = computed(() =>
  usePoints.value ? parseInt(usedPoints.value) : 0
);
const validatePoints = (val) => {
  if (!usePoints.value) return true; // 若未勾選使用購物金，跳過驗證
  if (val === null || val === "") return "請輸入購物金";
  if (val <= 0) return "金額需大於 0";
  if (val > ablePoints.value) return `最多可使用 ${ablePoints.value} 點`;
  return true;
};
watch(usedPoints, (val) => {
  if (val > ablePoints.value) {
    usedPoints.value = ablePoints.value;
  }
});

//取貨方式 & 付款方式
const pickupMethod = ref("");
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

//根據總金額跟取貨方式計算運費
const shipping = ref(0);
watch([pickupMethod, subtotal], ([newPickup, newSubtotal]) => {
  if (newSubtotal >= 1000) {
    shipping.value = 0;
  } else {
    if (newPickup === "Delivery") {
      shipping.value = 85;
    } else if (newPickup === "CashOnDelivery" || newPickup === "In-store") {
      shipping.value = 60;
    } else {
      shipping.value = 0;
    }
  }
});

//點擊繼續購物/前往下一步
const keppShopping = () => {
  router.back();
};
const orderingInfo = async () => {
  router.push("/OrderingInfo");
};
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
