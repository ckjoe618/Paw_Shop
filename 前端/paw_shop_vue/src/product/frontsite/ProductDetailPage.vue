<template>
  <v-container class="pt-16 px-4 pb-6">
    <v-row>
      <!-- 商品圖片區 -->
      <v-col cols="12" md="5">
        <v-img :src="product.productPhoto" max-height="400" cover />
      </v-col>

      <!-- 商品資訊區 -->
      <v-col cols="12" md="7">
        <h2 class="mb-2">{{ product.productName }}</h2>
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
              {{ review.reviewer }}
            </div>
            <v-rating
              :model-value="review.rating"
              color="amber"
              readonly
              dense
              size="small"
            />
          </div>
          <div class="text-caption text-grey-darken-1 mb-2">
            {{ review.date }}
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

const reviews = ref([
  {
    reviewer: "狗狗主人A",
    rating: 5,
    comment: "超愛吃這款飼料，每次都吃光光！",
    date: "2024/12/10",
  },
  {
    reviewer: "毛爸B",
    rating: 4,
    comment: "成分安心，會回購。",
    date: "2024/11/03",
  },
  {
    reviewer: "阿喵媽咪",
    rating: 5,
    comment: "不只狗喜歡，連貓都搶著吃 😹",
    date: "2024/10/25",
  },
]);

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
  await Promise.all([fetchProduct(route.params.id), fetchAllProducts(), fetchRatingInfo(route.params.id)]);
  updateRecommendations();
});

watch(
  () => route.params.id,
  async (newId) => {
    await fetchProduct(newId);
    await fetchRatingInfo(newId);
    updateRecommendations();
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
