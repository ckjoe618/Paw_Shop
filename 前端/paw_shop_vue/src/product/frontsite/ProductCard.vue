<template>
  <v-card class="mx-auto my-4 hover-card" max-width="374" @click="goToDetail">
    <div style="position: relative">
      <v-img
        :src="product.productPhoto"
        height="250"
        cover
        class="product-image"
      />
      <v-btn
        icon
        class="favorite-btn"
        @click.stop="toggleFavorite"
        :color="isFavorite ? 'red' : 'grey'"
      >
        <v-icon>{{ isFavorite ? "mdi-heart" : "mdi-heart-outline" }}</v-icon>
      </v-btn>
    </div>
    <v-card-item>
      <v-card-title class="text-truncate">{{
        product.productName
      }}</v-card-title>
      <v-card-subtitle>{{ product.categoryName }}</v-card-subtitle>
    </v-card-item>

    <v-card-text>
      <div class="text-subtitle-1 font-weight-bold">
        NT$ {{ product.productPrice }}
      </div>
      <v-rating
        v-if="showRating"
        :model-value="ratingInfo.averageRating"
        color="amber"
        readonly
        dense
      />
      <div v-if="showRating" class="text-caption text-grey">
        {{ ratingInfo.totalReview }} 則評價
      </div>
      <div class="text-body-2 mt-1 product-desc">
        {{ truncatedDescription }}
      </div>
    </v-card-text>

    <v-card-actions>
      <v-btn color="green" variant="text" block @click.stop="addToCart"
        >加入購物車</v-btn
      >
    </v-card-actions>

    <v-snackbar
      v-model="showSnackbar"
      :timeout="2000"
      color="green"
      location="bottom right"
    >
      已將「{{ product.productName }}」加入購物車！
    </v-snackbar>
  </v-card>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { loadCart } from "@/order/components/frontsite/useCart";
import api from "@/api/axiosInterceptor"; // ✅ 使用自訂 axios 實例
import { useAuthStore } from "@/member/stores/auth";
import {
  apiAddShoppingCartItem,
  apiFindShoppingCartItem,
  apiUpdateShoppingCartItem,
} from "@/api/api";

const router = useRouter();
const ratingInfo = ref({ averageRating: 0, totalReview: 0 });

const props = defineProps({
  product: Object,
  showRating: {
    type: Boolean,
    default: true,
  },
});

const showSnackbar = ref(false);
const authStore = useAuthStore();
const isFavorite = ref(false);

async function checkFavorite() {
  if (!authStore.isLoggedIn) return;

  try {
    const res = await api.get("/api/collections");
    const favorites = res.data;
    isFavorite.value = favorites.some(
      (item) => item.productId === props.product.productId
    );
  } catch (err) {
    console.error("❌ 載入收藏狀態失敗", err);
  }
}

async function toggleFavorite() {
  if (!authStore.isLoggedIn) {
    alert("請先登入才能收藏商品！");
    return;
  }

  const url = `/api/collections/${props.product.productId}`;
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

async function addToCart() {
  const item = {
    product: {
      productId: props.product.productId,
      productName: props.product.productName,
      productPrice: props.product.productPrice,
    },
    quantity: 1,
  };
  console.log(item);

  if (authStore.isLoggedIn) {
    //已登入：呼叫後端一次 insert 一筆
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
      showSnackbar.value = true;
      await loadCart();
    } catch (err) {
      console.error("加入後端購物車失敗", err);
    }
  } else {
    //未登入：寫 localStorage

    const cart = JSON.parse(localStorage.getItem("cart")) || [];
    const existing = cart.find(
      (i) => i.product.productId === props.product.productId
    );
    if (existing) {
      existing.quantity += 1;
    } else {
      cart.push(item);
    }
    localStorage.setItem("cart", JSON.stringify(cart));
    showSnackbar.value = true;
    await loadCart();
  }
}

function goToDetail() {
  router.push(`/products/${props.product.productId}`);
}

const truncatedDescription = computed(() => {
  const text = props.product.productDes || "";
  return text.length > 60 ? text.slice(0, 60) + "..." : text;
});

onMounted(async () => {
  const res = await axios.get(
    `http://localhost:8080/product/${props.product.productId}/rating-info`
  );
  ratingInfo.value = res.data;
  await checkFavorite();
});
</script>

<style scoped>
.hover-card {
  transition: transform 0.3s, box-shadow 0.3s;
  cursor: pointer;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.hover-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}
.product-image {
  transition: transform 0.3s ease;
}
.hover-card:hover .product-image {
  transform: scale(1.05);
}
.product-desc {
  min-height: 48px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
}

.favorite-btn {
  position: absolute;
  top: 8px;
  right: 8px;
  z-index: 2;
}
</style>
