<template>
  <v-container>
    <h3 class="text-h6 font-weight-medium mb-2">
      <i class="fas fa-thumbs-up mr-2"></i>Top Recommendations
    </h3>
    <v-row>
      <v-col
        v-for="product in topProducts"
        :key="product.productId"
        cols="12"
        sm="6"
        md="3"
      >
        <ProductCard :product="product" :showRating="false"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import ProductCard from "@/product/frontsite/ProductCard.vue";
import axios from "axios";

const topProducts = ref([]);

onMounted(async () => {
  try {
    const res = await axios.get("http://localhost:8080/product/expensive");

    if (Array.isArray(res.data)) {
      topProducts.value = res.data.slice(0, 4);
    } else {
      console.warn("⚠️ 回傳不是陣列：", res.data);
    }
  } catch (error) {
    console.error("❌ 無法取得資料", error);
  }
});
</script>

<style scoped>
.v-card-title {
  font-size: 0.85rem;
}
.v-card-subtitle {
  font-size: 0.8rem;
  color: #555;
}
i.fas.fa-star {
  margin-right: 2px;
  font-size: 14px;
}
</style>
