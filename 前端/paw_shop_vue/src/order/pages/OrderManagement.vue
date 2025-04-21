<template>
  <v-container>
    <v-data-table
      :headers="headers"
      :items="orders"
      class="elevation-1"
      density="comfortable"
    >
      <template #item.customerService="{ item }">
        <v-icon small class="me-2">mdi-message-text-outline</v-icon>
        <span class="me-2">詢問</span>｜<span class="ms-2">查看</span>
      </template>
    </v-data-table>
  </v-container>
</template>

<script setup>
import { ref } from "vue";

const headers = [
  { title: "訂購日期", key: "orderDate" },
  { title: "訂單編號", key: "orderId" },
  { title: "付款方式", key: "paymentMethod" },
  { title: "取貨方式", key: "pickupMethod" },
  { title: "處理進度", key: "orderStatus" },
  { title: "應付金額", key: "amount" },
  { title: "客服記錄", key: "customerService", sortable: false },
];

const orders = ref([
  {
    orderDate: "2023-05-03",
    orderId: "202305030310",
    paymentMethod: "信用卡刷卡",
    pickupMethod: "超商取貨",
    orderStatus: "退貨訂單(退貨完成)",
    amount: 1062,
  },
  {
    orderDate: "2022-03-07",
    orderId: "202202110421-1",
    paymentMethod: "信用卡刷卡",
    pickupMethod: "超商取貨",
    orderStatus: "退貨訂單(退貨完成)",
    amount: 419,
  },
]);
</script>

<style scoped></style>
