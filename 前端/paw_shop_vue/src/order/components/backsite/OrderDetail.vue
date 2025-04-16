<template>
  <v-dialog v-model="dialogVisible" max-width="800px">
    <v-card>
      <v-card-title class="text-h6">訂單明細</v-card-title>
      <v-card-text>
        <v-divider class="my-2" />
        <div>
          <h4 class="text-subtitle-1 mb-2" style="font-weight: bold; color: #4f4f4f">
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
                <th class="text-left">操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in orderDetails" :key="item.orderDetailId" :style="item.status === 'disabled'
                ? 'text-decoration: line-through'
                : ''
                ">
                <td>{{ item.product.productId }}</td>
                <td>{{ item.product.productName }}</td>
                <td>{{ item.quantity }}</td>
                <td>${{ item.unitPrice }}</td>
                <td>${{ item.unitPrice * item.quantity }}</td>
                <td>
                  <formDeletebtn @click="confirmDelete(item.orderDetailId)"></formDeletebtn>
                </td>
              </tr>
              <tr>
                <td colspan="4"></td>
                <td class="fw-bold">總計：${{ totalPrice }}</td>
                <td>
                  <addbtn size="28" @click="showAddDetail = true">新增</addbtn>
                </td>
              </tr>
            </tbody>
          </v-table>
        </div>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text @click="dialogVisible = false">關閉</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="showAddDetail" max-width="500px">
    <v-card>
      <v-card-title>新增明細</v-card-title>
      <v-card-text>
        <v-autocomplete v-model="selectedProduct" :items="filteredProducts" item-title="customLabel"
          item-value="productId" label="商品編號" variant="solo" :search-input.sync="searchText" return-object
          @update:modelValue="onProductSelect"></v-autocomplete>
        <v-text-field v-model="form.productName" label="商品名稱" variant="solo" readonly />
        <v-text-field v-model="form.unitPrice" label="單價" type="number" prefix="$" variant="solo" required />
        <v-text-field v-model="form.quantity" label="數量" type="number" variant="solo" required />
      </v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn text @click="showAddDetail = false">取消</v-btn>
        <v-btn color="primary" @click="submitDetail">確認</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, watch, computed, onMounted } from "vue";
import axios from "axios";
import addbtn from "../buttons/addbtn.vue";
import formDeletebtn from "../buttons/formDeletebtn.vue";

const props = defineProps({
  visible: Boolean,
  order: Object,
});

const emit = defineEmits(["update:visible", "updateOrderPriceTotal"]);

const dialogVisible = ref(props.visible);
const showAddDetail = ref(false);
const orderDetails = ref([]);
const form = ref({
  productId: "",
  productName: "",
  unitPrice: "",
  quantity: "",
});

//查詢所有明細
//Watch 外部 visible → 同步到內部 dialogVisible
watch(
  () => props.visible,
  (newVal) => {
    dialogVisible.value = newVal;
  }
);
//Watch 內部 dialogVisible → 通知父元件更新
watch(dialogVisible, (val) => {
  emit("update:visible", val);
});

const fetchOrderDetails = async () => {
  if (props.order && props.order.orderId) {
    try {
      const res = await axios.get(
        `http://localhost:8080/orderdetail/allorderdetails?orderId=${props.order.orderId}`
      );
      orderDetails.value = res.data;
    } catch (error) {
      console.error("取得訂單明細失敗：", error);
    }
  }
};
watch(
  () => props.order,
  () => {
    fetchOrderDetails();
  },
  { immediate: true }
);

//總計計算
const totalPrice = computed(() => {
  return orderDetails.value
    .filter((item) => item.status !== "disabled")
    .reduce(
      (sum, item) => sum + (item.subtotal || item.unitPrice * item.quantity),
      0
    );
});
watch(totalPrice, (newTotal) => {
  emit("updateOrderPriceTotal", newTotal);
});

//刪除明細
const confirmDelete = async (orderDetailId) => {
  console.log("要刪除明細 ID：", orderDetailId);
  const index = orderDetails.value.findIndex(
    (d) => d.orderDetailId === orderDetailId
  );
  if (index === -1) return;
  orderDetails.value[index].status = "disabled";
  try {
    await axios.delete(
      `http://localhost:8080/orderdetail/delete/${orderDetailId}`
    );
  } catch (err) {
    console.error("刪除失敗", err);
    orderDetails.value[index].status = "active";
  }
};

//新增明細
const productList = ref([]);
const searchText = ref("");
const selectedProduct = ref(null);
onMounted(async () => {
  try {
    const res = await axios.get("http://localhost:8080/product/available");
    productList.value = res.data.map((p) => ({
      ...p,
      customLabel: `${p.productId} - ${p.productName}`,
    }));
  } catch (err) {
    console.error("取得商品列表失敗", err);
  }
});
// 根據使用者輸入文字做 filter
const filteredProducts = computed(() => {
  if (!searchText.value) return productList.value;
  return productList.value.filter((p) =>
    String(p.productId).includes(searchText.value)
  );
});
// 點選後設定欄位
const onProductSelect = (product) => {
  if (product) {
    form.value.productId = product.productId;
    form.value.productName = product.productName;
    form.value.unitPrice = product.productPrice;
  } else {
    form.value.productId = "";
    form.value.productName = "";
    form.value.unitPrice = "";
  }
};

const submitDetail = async () => {
  const newData = {
    order: { orderId: props.order.orderId },
    product: {
      productId: form.value.productId,
    },
    unitPrice: form.value.unitPrice,
    quantity: form.value.quantity,
  };
  console.log(newData);

  try {
    const res = await axios.post(
      "http://localhost:8080/orderdetail/add",
      newData
    );
    if (res.data) {
      await fetchOrderDetails();
    }
    showAddDetail.value = false;
    form.value = {
      productId: "",
      productName: "",
      unitPrice: "",
      quantity: "",
    };
  } catch (err) {
    console.error("新增失敗", err);
  }
};
</script>

<style scoped>
.fw-bold {
  font-weight: bold;
}
</style>
