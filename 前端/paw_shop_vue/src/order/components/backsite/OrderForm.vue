<template>
  <v-container>
    <v-form @submit.prevent="submitForm" ref="formRef">
      <v-text-field
        v-if="form.orderId"
        v-model="form.orderId"
        label="訂單編號"
        variant="solo"
        disabled
      ></v-text-field>
      <v-text-field
        v-model="form.member.memberId"
        label="會員編號"
        :rules="[numberRule]"
        variant="solo"
        disabled
      ></v-text-field>
      <v-text-field
        v-model="form.priceTotal"
        label="訂單金額"
        prefix="$"
        type="number"
        variant="solo"
        required
      ></v-text-field>
      <v-select
        v-model="form.paymentMethod"
        :items="['信用卡', '貨到付款']"
        label="付款方式"
        variant="solo"
        required
      ></v-select>
      <v-select
        v-model="form.paymentStatus"
        :items="['已付款', '待付款', '付款失敗']"
        label="付款狀態"
        variant="solo"
        required
      ></v-select>
      <v-select
        v-model="form.orderStatus"
        :items="[
          '訂單已成立',
          '備貨中',
          '已出貨',
          '包裹已送達',
          '退貨中',
          '退貨完成',
          '訂單取消',
          '訂單失效',
        ]"
        label="訂單狀態"
        variant="solo"
        required
      ></v-select>
      <v-select
        v-model="form.pickupMethod"
        :items="['超商取貨', '宅配']"
        label="取貨方式"
        variant="solo"
        required
      ></v-select>

      <v-text-field
        v-model="form.trackingNum"
        label="物流編號"
        variant="solo"
        :rules="[trackingNumRule]"
      ></v-text-field>

      <v-row class="mt-4" justify="end" no-gutters>
        <v-col cols="auto">
          <v-btn @click="emit('cancel')" color="grey" class="me-2">取消</v-btn>
        </v-col>
        <v-col cols="auto">
          <v-btn @click="submitForm" color="primary">送出</v-btn>
        </v-col>
      </v-row>
    </v-form>
  </v-container>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";

const route = useRoute();
const router = useRouter();
const formRef = ref(null);
const emit = defineEmits(["success", "cancel"]);

const form = ref({
  member: {
    memberId: null,
  },
  priceTotal: "",
  paymentMethod: "",
  paymentStatus: "",
  orderStatus: "",
  pickupMethod: "",
  trackingNum: "",
});

const numberRule = (v) => {
  if (v === "") return "此欄位為必填";
  return /^\d+$/.test(v) || "只能輸入數字";
};
const trackingNumRule = (v) => {
  if (!v) return true;
  if (!/^\d+$/.test(v)) return "只能輸入數字";
  if (v.length !== 10) return "物流編號必須是10位數";
  return true;
};

const props = defineProps({
  orderId: Number,
});
watch(
  () => props.orderId,
  async (newId) => {
    if (newId) {
      try {
        const res = await axios.get(
          `http://localhost:8080/order/queryone/${newId}`
        );
        Object.assign(form.value, res.data);
      } catch (e) {
        console.error("載入訂單失敗", e);
      }
    } else {
      // 僅在開啟 dialog 且 orderId 為空的當下清空
      Object.keys(form.value).forEach((key) => (form.value[key] = ""));
    }
  },
  { immediate: true }
);

const submitForm = async () => {
  if (!formRef.value.validate()) return;

  try {
    if (props.orderId) {
      // 編輯
      const newData = {
        ...form.value,
        member: { memberId: form.value.memberId },
      };
      console.log("送出資料:", JSON.stringify(form.value));
      const response = await axios.put(
        `http://localhost:8080/order/update/${props.orderId}`,
        newData
      );
      console.log("訂單更新成功！", response.data);
      emit("success", response.data);
    } else {
      // 新增
      const newData = {
        ...form.value,
        member: { memberId: form.value.memberId },
      };
      const response = await axios.post(
        "http://localhost:8080/order/add",
        newData
      );
      console.log("訂單建立成功！", response.data);
      emit("success", response.data);
    }
  } catch (error) {
    console.error("提交失敗：", error);
    alert("提交失敗，請稍後再試");
  }
};

onMounted(() => {
  if (route.params.id) fetchOrder(route.params.id);
});
</script>

<style scoped></style>
