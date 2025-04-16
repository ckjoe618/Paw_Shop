<template>
  <v-app-bar app color="green darken-3" dark>
    <v-toolbar-title class="text-h5">
      <i class="fas fa-paw mr-2"></i> PawShop
    </v-toolbar-title>

    <v-spacer />

    <v-text-field
      v-model="search"
      label="Search the store"
      single-line
      hide-details
      dense
      class="mr-4"
      @keyup.enter="handleSearch"
    >
      <template #append-inner>
        <v-btn @click="handleSearch"><i class="fas fa-search"></i></v-btn>
      </template>
    </v-text-field>

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
};

const goToMember = () => {
  // router.push("/member");
};
</script>

<style scoped></style>
