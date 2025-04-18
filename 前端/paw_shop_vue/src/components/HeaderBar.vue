<template>
  <v-app-bar app color="#215d1e" dark height="80">
    <v-toolbar-title class="text-h5">
      <router-link to="/" style="text-decoration: none">
        <img
          src="@/member/assets/images/ChatGPT Image 2025年4月16日 上午10_53_06.png"
          alt="PawShop"
          style="height: 80px"
          class="ml-6"
        />
      </router-link>
    </v-toolbar-title>

    <v-spacer />

    <v-text-field
      v-model="search"
      label="Search the store"
      single-line
      hide-details
      dense
      outlined
      @keyup.enter="handleSearch"
    >
      <template #append-inner>
        <v-btn @click="handleSearch"><i class="fas fa-search"></i></v-btn>
      </template>
    </v-text-field>

    <!-- 收藏按鈕 -->
    <v-btn icon><i class="fas fa-heart fa-lg"></i></v-btn>

    <!-- 購物車按鈕 -->
    <v-menu v-model="cartMenuVisible" offset-y transition="slide-y-transition">
      <template #activator="{ props }">
        <v-badge
          v-if="totalCartQty > 0"
          :content="totalCartQty"
          color="#FFED97"
          offset-x="8"
          offset-y="8"
          bordered
        >
          <v-btn
            icon
            v-bind="props"
            @hover="cartMenuVisible = !cartMenuVisible"
          >
            <i class="fas fa-cart-shopping fa-lg"></i>
          </v-btn>
        </v-badge>
        <v-btn icon v-else v-bind="props">
          <i class="fas fa-cart-shopping fa-lg"></i>
        </v-btn>
      </template>
      <!-- 購物車預覽 -->
      <CartPreview
        v-if="totalCartQty > 0"
        :items="cartItems"
        @closeMenu="cartMenuVisible = false"
      />
    </v-menu>

    <!-- 會員按鈕 -->
    <div v-if="authStore.isLoggedIn">
      <v-menu>
        <template v-slot:activator="{ props }">
          <v-btn icon v-bind="props">
            <v-avatar size="32">
              <v-img
                src="https://cdn-icons-png.flaticon.com/512/847/847969.png"
              />
            </v-avatar>
          </v-btn>
        </template>
        <v-list>
          <v-list-item title="會員中心" to=""></v-list-item>
          <v-list-item title="登出" @click="logout"></v-list-item>
        </v-list>
      </v-menu>
    </div>
    <div v-else>
      <v-btn icon @click="login"> 登入 </v-btn>
    </div>
  </v-app-bar>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/member/stores/auth";
import { memberRequest } from "@/member/api/api.js";
import CartPreview from "@/order/components/frontsite/CartPreview.vue";

const search = ref("");
const router = useRouter();
const authStore = useAuthStore();
const cartItems = ref([{ name: "好吃的餅乾", qty: 5, price: 50 }]);
const cartMenuVisible = ref(false);

const handleSearch = () => {
  if (search.trim() !== "") {
    // router.push("/search?q=" + search);
  }
};

const login = () => {
  router.push("/login");
};

const logout = () => {
  authStore.logout();
  router.push("/");
};

const goToMember = () => {
  // router.push("/member");
};

const totalCartQty = computed(() =>
  cartItems.value.reduce((sum, item) => sum + item.qty, 0)
);

// 載入購物車資料
const loadCart = async () => {
  if (authStore.token) {
    // ✅ 已登入 → 從後端拿購物車
    try {
      const res = await memberRequest.get("localhost:8080/shoppingcart");
      cartItems.value = res.data;
    } catch (err) {
      console.error("後端購物車取得失敗", err);
      cartItems.value = [];
    }
  } else {
    // ✅ 未登入 → 從 localStorage 拿購物車
    try {
      const localCart = localStorage.getItem("cart");
      cartItems.value = localCart ? JSON.parse(localCart) : [];
    } catch (err) {
      console.error("讀 localStorage cart 發生錯誤", err);
      cartItems.value = [];
    }
  }
};
</script>

<style scoped></style>
