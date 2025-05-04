<template>
  <v-container class="pt-16 px-4 pb-16">
    <h2 class="text-h5 font-weight-bold mb-1">我的收藏</h2>

    <v-row>
      <v-col
        v-for="product in favoriteProducts"
        :key="product.productId"
        cols="12"
        sm="6"
        md="3"
      >
        <ProductCard :product="product" :showRating="true" />
      </v-col>
    </v-row>

    <v-alert v-if="favoriteProducts.length === 0" type="info" class="mt-6" text>
      你目前沒有收藏任何商品。
    </v-alert>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import ProductCard from "@/product/frontsite/ProductCard.vue";
import api from "@/api/axiosInterceptor";

const favoriteProducts = ref([]);

onMounted(async () => {
  try {
    // 1️⃣ 取得收藏 productId 清單
    const { data: favorites } = await api.get("/api/collections");

    // 2️⃣ 根據每個 ID 去拿完整商品資料
    const promises = favorites.map((item) =>
      api.get(`/product/${item.productId}`)
    );
    const results = await Promise.all(promises);

    favoriteProducts.value = results.map((res) => res.data);
  } catch (err) {
    console.error("載入收藏清單失敗", err);
  }
});
</script>
