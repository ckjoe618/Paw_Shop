<template>
  <v-app-bar app color="#215d1e" dark height="80" class="pe-6">
    <v-toolbar-title class="text-h5">
      <router-link to="/home" style="text-decoration: none">
        <img
          :src="PawShopLogo"
          alt="PawShop"
          style="height: 80px"
          class="ml-6"
        />
      </router-link>
    </v-toolbar-title>

    <v-spacer />
    <!-- 搜尋欄 -->
    <div
      class="d-flex align-center search-container"
      ref="searchContainer"
      @keyup.esc="closeSearch"
    >
      <!-- 搜尋圖示按鈕 -->
      <v-btn
        icon
        class="text-white mx-1"
        @click.stop="toggleSearch"
        v-if="showSearchIcon"
      >
        <v-icon size="28">mdi-magnify</v-icon>
      </v-btn>

      <!-- 展開搜尋欄 -->
      <v-slide-x-reverse-transition>
        <div v-if="showSearch" class="search-bar">
          <v-text-field
            v-model="search"
            label="Search the store"
            dense
            hide-details
            solo-inverted
            flat
            class="search-input"
            style="width: 250px"
            @keyup.enter="handleSearch"
          >
            <template #append-inner>
              <v-btn icon @click.stop="handleSearch">
                <v-icon size="28">mdi-magnify</v-icon>
              </v-btn>
            </template>
          </v-text-field>
        </div>
      </v-slide-x-reverse-transition>
    </div>

    <!-- 收藏按鈕 -->
    <v-btn icon class="text-white mx-1">
      <v-icon size="28">mdi-heart-outline</v-icon>
    </v-btn>

    <!-- 購物車按鈕 -->
    <v-menu
      v-model="cartMenuVisible"
      offset-y
      transition="slide-y-transition"
      :key="cartItems.length"
    >
      <template #activator="{ props }">
        <v-badge
          v-if="totalCartQty > 0"
          :content="totalCartQty"
          color="#FFED97"
          offset-x="8"
          offset-y="8"
          bordered
        >
          <v-btn icon class="text-white mx-1" v-bind="props">
            <v-icon size="28">mdi-cart</v-icon>
          </v-btn>
        </v-badge>
        <v-btn icon v-else v-bind="props">
          <v-icon size="28">mdi-cart</v-icon>
        </v-btn>
      </template>
      <!-- 購物車預覽 -->
      <CartPreview :items="cartItems" @closeMenu="cartMenuVisible = false" />
    </v-menu>

    <!-- 會員按鈕 -->
    <div v-if="authStore.isLoggedIn">
      <v-menu>
        <template v-slot:activator="{ props }">
          <v-btn icon v-bind="props" class="mx-1">
            <v-avatar size="50">
              <v-img :src="authStore.memberPhoto" />
            </v-avatar>
          </v-btn>
        </template>
        <v-list>
          <v-list-item
            v-for="item in items"
            :key="item.title"
            :to="item.link"
            @click="handleMenuClick(item)"
            >{{ item.title }}</v-list-item
          >
        </v-list>
      </v-menu>
    </div>
    <div v-else>
      <v-btn icon class="text-white mx-1" @click="router.push(`/login`)">
        <v-icon size="28">mdi-login</v-icon>
      </v-btn>
    </div>
    <div v-if="authStore.isAdmin && authStore.isLoggedIn">
      <v-btn
        prepend-icon="mdi-shield-account"
        class="text-white border-white mx-3"
        variant="outlined"
        @click="router.push(`/admin`)"
      >
        前往後臺
      </v-btn>
    </div>
  </v-app-bar>
</template>

<script setup>
import PawShopLogo from "@/member/assets/images/PawShop_green_logo.png";
import { ref, computed, onMounted, onBeforeUnmount, watch } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/member/stores/auth";
import CartPreview from "@/order/components/frontsite/CartPreview.vue";
import {
  loadCart,
  cartItems,
  totalCartQty,
} from "@/order/components/frontsite/useCart";

const router = useRouter();
const authStore = useAuthStore();
const cartMenuVisible = ref(false);

const search = ref("");
const showSearch = ref(false);
const showSearchIcon = ref(true);
const searchContainer = ref(null);

const items = ref([
  {
    title: "會員中心",
    link: "/member/profile",
  },
  {
    title: "訂單查詢",
    link: "/OrderManagement",
  },
  {
    title: "預約管理",
    link: "/appointments/queryreserve",
  },
  {
    title: "論壇管理",
    link: "#",
  },
  {
    title: "登出",
    link: "",
  },
]);

// 針對登出按鈕判斷
const handleMenuClick = (item) => {
  if (item.title === "登出") {
    logout();
  } else {
    router.push(item.link);
  }
};

const toggleSearch = () => {
  showSearch.value = true;
  showSearchIcon.value = false;
};

const logout = () => {
  authStore.logout();
  router.push("/");
};

const closeSearch = () => {
  showSearch.value = false;
  setTimeout(() => {
    showSearchIcon.value = true;
  }, 300); // 與動畫時間同步
  search.value = "";
};

const handleClickOutside = (e) => {
  if (searchContainer.value && !searchContainer.value.contains(e.target)) {
    closeSearch();
  }
};
// 在掛載前，建立點擊事件
onMounted(() => document.addEventListener("click", handleClickOutside));
// 在卸載後，移除點擊事件
onBeforeUnmount(() =>
  document.removeEventListener("click", handleClickOutside)
);

// 載入購物車資料
watch(cartMenuVisible, async (val) => {
  if (val) {
    await loadCart();
  }
});
// onMounted(() => {
//   loadCart();
// });

const handleSearch = () => {
  if (search.trim() !== "") {
    // router.push("/search?q=" + search);
    closeSearch();
  }
};
</script>

<style scoped>
.search-bar {
  width: 250px;
  margin-left: 8px;
  transition: width 0.3s ease;
}
</style>
