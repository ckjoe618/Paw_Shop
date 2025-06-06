<template>
  <v-container class="pt-10 px-0">
    <!-- 排序選單 -->
    <v-row class="pa-4" justify="end">
      <v-col cols="12" sm="3" md="2">
        <v-select
          v-model="sortOption"
          :items="sortOptions"
          label="排序方式"
          variant="outlined"
          density="compact"
          hide-details
          attach="body"
        />
      </v-col>
    </v-row>

    <v-row class="ma-0 pa-0">
      <!-- 左側分類欄 -->
      <v-col cols="12" sm="2" md="2" class="pa-0">
        <CategorySidebar
          v-model:modelValueCategory="filters.category"
          v-model:modelValuePrice="filters.price"
          @filter-change="handleFilterChange"
        />
      </v-col>

      <!-- 商品卡片與分頁 -->
      <v-col cols="12" sm="10" md="10" class="px-2">
        <v-row>
          <v-col
            v-for="(product, index) in paginatedProducts"
            :key="index"
            cols="12"
            sm="6"
            md="4"
          >
            <ProductCard :product="product" />
          </v-col>
        </v-row>

        <v-pagination
          v-model="currentPage"
          :length="totalPages"
          class="mt-10"
          total-visible="5"
          color="green"
          @update:model-value="$scrollToTop()"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";
import ProductCard from "./ProductCard.vue";
import CategorySidebar from "./CategorySidebar.vue";

let isDataLoaded = false;
const router = useRouter();
const route = useRoute();

const allProducts = ref([]);

// 初始狀態從 query 拿
const currentPage = computed({
  get() {
    // 從 URL 直接讀
    return Number(route.query.page) || 1;
  },
  set(page) {
    // 推到新 query，自動保留其他 query
    router.replace({
      // 不指定 path/name
      query: {
        ...route.query,
        page,
      },
    });
  },
});
const sortOption = ref(route.query.sort || "top");
const filters = ref({
  category: route.query.category || null,
  price: [
    Number(route.query.priceMin) || 0,
    Number(route.query.priceMax) || 5000,
  ],
});

const sortOptions = [
  { title: "熱門排序", value: "top" },
  { title: "最低價格", value: "low" },
  { title: "最高價格", value: "high" },
  { title: "評價最高", value: "rating" },
];

const itemsPerPage = 9;

onMounted(async () => {
  if (!isDataLoaded) {
    const { data } = await axios.get(
      "http://localhost:8080/product/stock/available"
    );
    allProducts.value = data;
    isDataLoaded = true;
  }
});

const filteredProducts = computed(() => {
  let filtered = allProducts.value.filter((product) => {
    const inCategory =
      !filters.value.category ||
      product.categoryName === filters.value.category;
    const inPriceRange =
      product.productPrice >= filters.value.price[0] &&
      product.productPrice <= filters.value.price[1];
    return inCategory && inPriceRange;
  });

  switch (sortOption.value) {
    case "low":
      filtered.sort((a, b) => a.productPrice - b.productPrice);
      break;
    case "high":
      filtered.sort((a, b) => b.productPrice - a.productPrice);
      break;
    case "rating":
      filtered.sort((a, b) => b.averageRating - a.averageRating);
      break;
    default:
      break;
  }

  return filtered;
});

const paginatedProducts = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return filteredProducts.value.slice(start, end);
});

const totalPages = computed(() =>
  Math.ceil(filteredProducts.value.length / itemsPerPage)
);

function handleFilterChange(payload) {
  filters.value = payload;
  currentPage.value = 1;
  updateQuery();
}

function onPageChange(page) {
  console.log(
    "[onPageChange] 點到 page =",
    page,
    "；換頁前 route.query.page =",
    route.query.page
  );
  currentPage.value = page;
  updateQuery();
  window.scrollTo({ top: 0, behavior: "smooth" });
}

watch(sortOption, () => {
  currentPage.value = 1;
  updateQuery();
});

function updateQuery() {
  router.replace({
    query: {
      page: currentPage.value,
      sort: sortOption.value,
      category: filters.value.category || undefined,
      priceMin: filters.value.price[0],
      priceMax: filters.value.price[1],
    },
  });
}

watch(
  () => route.query,
  (newQuery) => {
    console.log("[watch query.page] 看到 query.page 變成", newPage);
    currentPage.value = Number(newQuery.page) || 1;
    sortOption.value = newQuery.sort || "top";
    filters.value = {
      category: newQuery.category || null,
      price: [
        Number(newQuery.priceMin) || 0,
        Number(newQuery.priceMax) || 5000,
      ],
    };
  }
);

function $scrollToTop() {
  window.scrollTo({ top: 0, behavior: "smooth" });
}
</script>

<style scoped></style>
