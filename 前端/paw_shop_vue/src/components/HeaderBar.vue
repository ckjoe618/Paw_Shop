<template>
  <v-app-bar app color="#215d1e" dark height="80">
    <v-toolbar-title class="text-h5">
      <router-link to="/" style="text-decoration: none">
        <img
          src="@/member/assets/images/PawShop_gtreen_logo.png"
          alt="PawShop"
          style="height: 80px"
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
      <!-- 🔍 搜尋圖示按鈕 -->
      <v-btn icon @click="toggleSearch" class="search-icon-btn">
        <i class="fas fa-search"></i>
      </v-btn>

      <!-- 🔄 展開搜尋欄 -->
      <v-expand-transition>
        <div v-if="showSearch" class="search-bar">
          <v-text-field
            v-model="search"
            label="Search the store"
            dense
            hide-details
            solo-inverted
            flat
            class="search-input"
            @keyup.enter="handleSearch"
          >
            <template #append-inner>
              <v-btn icon @click="handleSearch">
                <i class="fas fa-search"></i>
              </v-btn>
            </template>
          </v-text-field>
        </div>
      </v-expand-transition>
    </div>

    <!-- 收藏按鈕 -->
    <v-btn><i class="fas fa-heart"></i></v-btn>
    <!-- 購物車按鈕 -->
    <v-btn><i class="fas fa-cart-shopping"></i></v-btn>

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
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/member/stores/auth";

const search = ref("");
const router = useRouter();
const authStore = useAuthStore();
const searchContainer = ref(null);

const handleSearch = () => {
  if (search.trim() !== "") {
    // router.push("/search?q=" + search);
  }
};

const toggleSearch = () => {
  showSearch.value = !showSearch.value;
};

const login = () => {
  router.push("/login");
};

const logout = () => {
  authStore.logout();
};

const goToMember = () => {
  // router.push("/member");
};
</script>

<style scoped></style>
