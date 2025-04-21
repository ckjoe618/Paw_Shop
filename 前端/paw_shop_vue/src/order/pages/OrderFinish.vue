<template>
  <v-container class="text-center py-12">
    <v-card class="pa-8 mx-auto" max-width="500" elevation="2" rounded="xl">
      <v-icon size="80" color="success" class="mb-4"
        >mdi-check-circle-outline</v-icon
      >
      <h2 class="mb-2" style="font-weight: bold; color: #4caf50">訂單完成</h2>
      <p class="mb-6" style="font-size: 16px; color: #555">
        感謝您的訂購！我們已成功收到您的訂單。<br />
        請至「會員中心」可查詢您的訂單進度。
      </p>

      <v-row class="justify-center" style="gap: 16px">
        <v-btn color="success" variant="elevated" @click="goToOrders">
          查看訂單
        </v-btn>
        <v-btn color="grey lighten-1" variant="flat" @click="goToHome">
          回首頁
        </v-btn>
      </v-row>
    </v-card>
  </v-container>
</template>

<script setup>
import router from "@/router";
import { ref, onMounted } from "vue";
import {
  apiFindShoppingCartItem,
  apiDeleteAllShoppingCartItem,
} from "@/member/api/api";
import { loadCart } from "@/order/components/frontsite/useCart";

onMounted(async () => {
  try {
    const res = await apiFindShoppingCartItem();
    const cartItemIds = res.data.map((item) => item.cartItemId);
    console.log(cartItemIds);

    await apiDeleteAllShoppingCartItem(cartItemIds);
    await loadCart();
    console.log("購物車已清空");
  } catch (err) {
    console.error("清空購物車失敗", err);
  }
});

// 模擬訂單編號（實際應從後端回傳）

const goToOrders = () => {
  router.push("/");
};

const goToHome = () => {
  router.push("/");
};
</script>

<style scoped></style>
