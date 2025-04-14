<template>
  <div class="search-result">
    <h2>搜尋結果：{{ keyword }}</h2>

    <div v-if="filtered.length > 0" class="result-grid">
      <ProductCard v-for="item in filtered" :key="item.id" :product="item" />
    </div>

    <div v-else class="no-result">找不到相關商品 😢</div>
  </div>
</template>

<script setup>
import { useRoute } from "vue-router";
import { computed } from "vue";
import products from "@/data/mockProducts.js";
import ProductCard from "@/components/ProductCard.vue";

const route = useRoute();
const keyword = route.query.keyword || "";

const filtered = computed(() => {
  return products.filter((item) => {
    item.name.includes(keyword) || item.description.includes(keyword);
  });
});
</script>

<style scoped>
.search-result {
  padding: 20px;
}
.result-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}
.no-result {
  margin-top: 20px;
  color: #999;
}
</style>
