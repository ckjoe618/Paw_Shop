<template>
  <v-container class="pt-16 px-4 pb-6">
    <v-row>
      <!-- 商品圖片區 -->
      <v-col cols="12" md="5">
        <v-img :src="product.productPhoto" max-height="400" cover />
      </v-col>

      <!-- 商品資訊區 -->
      <v-col cols="12" md="7">
        <!-- 商品名稱與收藏按鈕 -->
        <div class="d-flex align-center mb-2">
          <h2 class="me-2">{{ product.productName }}</h2>
          <v-btn
            icon
            size="small"
            @click="toggleFavorite"
            :color="isFavorite ? 'red' : 'grey'"
          >
            <v-icon>{{ isFavorite ? 'mdi-heart' : 'mdi-heart-outline' }}</v-icon>
          </v-btn>
        </div>
        <div class="text-subtitle-2 text-grey-darken-1 mb-1">
          分類：{{ product.categoryName }}
        </div>

        <v-rating :model-value="ratingInfo.averageRating" color="amber" readonly density="compact" />
        <div class="text-caption text-grey">{{ ratingInfo.totalReview }} 則評價</div>

        <div class="my-4">
          <div class="text-h6 font-weight-bold text-red">
            NT$ {{ product.productPrice }}
          </div>
        </div>

        <p class="mb-4">{{ product.productDes }}</p>

        <!-- 數量選擇 + 加入購物車 -->
        <v-row align="center" class="mb-4">
          <v-col cols="4">
            <v-text-field
              v-model="quantity"
              type="number"
              label="數量"
              min="1"
              density="compact"
            />
          </v-col>
          <v-col cols="8">
            <v-btn color="green" @click="addToCart" block> 加入購物車 </v-btn>
          </v-col>
        </v-row>

        <v-snackbar
          v-model="showSnackbar"
          :timeout="2000"
          color="green"
          location="bottom right"
        >
          已將「{{ product.productName }}」加入購物車！
        </v-snackbar>
      </v-col>
    </v-row>

    <!-- 評論區塊 -->
    <v-divider class="my-8"></v-divider>
    <h3 class="text-h6 font-weight-bold mb-4">用戶評論</h3>
    <v-row>
      <v-col cols="12" v-for="(review, index) in reviews" :key="index">
  <v-card class="pa-4 mb-3">
    <div class="d-flex justify-space-between align-center mb-1">
      <div class="text-subtitle-2 font-weight-medium">
        {{ review.reviewer || "匿名用戶" }}
      </div>
      <v-rating :model-value="review.rating" color="amber" readonly dense size="small" />
    </div>
    <div class="text-caption text-grey-darken-1 mb-2">
      {{ review.date || "無日期" }}
    </div>
    <div class="text-body-2">{{ review.comment }}</div>
  </v-card>
</v-col>
    </v-row>

    <!-- 推薦商品區塊 -->
    <v-divider class="my-8"></v-divider>
    <h3 class="text-h6 font-weight-bold mb-4">你可能也喜歡</h3>
    <v-row>
      <v-col
        v-for="rec in recommendedProducts"
        :key="rec.productId"
        cols="12"
        sm="6"
        md="3"
      >
        <ProductCard
          :product="rec"
          @add-to-cart="$emit('add-to-cart', $event)"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";
import api from "@/member/api/axiosInterceptor";
import ProductCard from "./ProductCard.vue";
import { loadCart } from "@/order/components/frontsite/useCart";
import { useAuthStore } from "@/member/stores/auth";
import {
  apiAddShoppingCartItem,
  apiFindShoppingCartItem,
  apiUpdateShoppingCartItem,
} from "@/member/api/api";

const route = useRoute();
const emit = defineEmits(["add-to-cart"]);

const product = ref({});
const quantity = ref(1);
const showSnackbar = ref(false);
const allProducts = ref([]);
const authStore = useAuthStore();
const recommendedProducts = ref([]);
const ratingInfo = ref({ averageRating: 0, totalReview: 0 });

const reviews = ref([]);

const isFavorite = ref(false);

async function checkFavorite() {
  if (!authStore.isLoggedIn) return;

  try {
    const res = await api.get("/api/collections");
    const favorites = res.data;
    isFavorite.value = favorites.some(
      (item) => item.productId === product.value.productId
    );
  } catch (err) {
    console.error("❌ 收藏狀態載入失敗", err);
  }
}

async function toggleFavorite() {
  if (!authStore.isLoggedIn) {
    alert("請先登入才能收藏商品！");
    return;
  }

  const url = `/api/collections/${product.value.productId}`;
  try {
    if (isFavorite.value) {
      await api.delete(url);
    } else {
      await api.post(url);
    }
    isFavorite.value = !isFavorite.value;
  } catch (err) {
    console.error("❌ 收藏操作失敗", err);
  }
}

async function fetchReviews(id) {
  try {
    const res = await axios.get(`http://localhost:8080/api/orderdetail/comments/${id}`);
    reviews.value = res.data;
  } catch (error) {
    console.error("載入評論失敗:", error);
  }
}

async function fetchProduct(id) {
  try {
    const res = await axios.get(`http://localhost:8080/product/${id}`);
    product.value = res.data;
  } catch (error) {
    console.error("載入商品詳細資料失敗:", error);
  }
}

async function fetchAllProducts() {
  try {
    const res = await axios.get(
      "http://localhost:8080/product/stock/available"
    );
    allProducts.value = res.data;
  } catch (error) {
    console.error("載入推薦商品失敗:", error);
  }
}

function updateRecommendations() {
  recommendedProducts.value = allProducts.value
    .filter(
      (p) =>
        p.categoryName === product.value.categoryName &&
        p.productId !== product.value.productId
    )
    .slice(0, 4);
}

onMounted(async () => {
  await Promise.all([fetchProduct(route.params.id), fetchAllProducts(), fetchRatingInfo(route.params.id),fetchReviews(route.params.id)]);
  updateRecommendations();
  await checkFavorite();
});

watch(
  () => route.params.id,
  async (newId) => {
    await fetchProduct(newId);
    await fetchRatingInfo(newId);
    await fetchReviews(newId);
    updateRecommendations();
    await checkFavorite(); 
    window.scrollTo({ top: 0, behavior: "smooth" });
  }
);

async function fetchRatingInfo(id) {
  try {
    const res = await axios.get(`http://localhost:8080/product/${id}/rating-info`);
    ratingInfo.value = res.data;
  } catch (error) {
    console.error("載入評價資訊失敗:", error);
  }
}

//加入購物車
async function addToCart() {
  const item = {
    product: {
      productId: product.value.productId,
      productName: product.productName,
      productPrice: product.productPrice,
    },
    quantity: Number(quantity.value),
  };
  if (authStore.isLoggedIn) {
    //已登入
    try {
      const res = await apiFindShoppingCartItem();
      const cart = res.data;

      const existing = cart.find(
        (i) => i.product?.productId === item.product.productId
      );

      if (existing) {
        const newQty = existing.quantity + item.quantity;
        await apiUpdateShoppingCartItem({
          cartItemId: existing.cartItemId,
          quantity: newQty,
        });
      } else {
        await apiAddShoppingCartItem([item]);
      }

      await loadCart();
      showSnackbar.value = true;
    } catch (err) {
      console.error("加入後端購物車失敗", err);
    }
  } else {
    //未登入
    const cart = JSON.parse(localStorage.getItem("cart")) || [];
    const existing = cart.find(
      (i) => i.product.productId === product.value.productId
    );

    if (existing) {
      existing.quantity += item.quantity;
    } else {
      cart.push(item);
    }
    localStorage.setItem("cart", JSON.stringify(cart));
    await loadCart();
    showSnackbar.value = true;
  }
}
</script>
