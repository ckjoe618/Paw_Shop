<template>
  <v-layout>
    <v-navigation-drawer v-model="drawer" width="220">
      <div class="d-flex flex-column align-center">
        <v-img
          :src="PawShopLogo"
          width="120"
          height="120"
          alt="logo"
          class="mb-6"
        />
        <v-list v-model:opened="openedGroups">
          <v-list-group
            v-for="item in items"
            :key="item.title"
            :value="item.title"
            :prepend-icon="item.icon"
            class="mb-4"
          >
            <template #activator="{ props }">
              <v-list-item v-bind="props" :title="item.title"></v-list-item>
            </template>

            <v-list-item
              v-for="(child, index) in item.children"
              :key="index"
              :title="child.label"
              :value="child.label"
              :to="child.link"
            ></v-list-item>
          </v-list-group>
        </v-list>
      </div>
      <div class="pa-8 d-flex flex-column ga-5">
        <v-card
          class="pa-2 d-flex align-center justify-center hover-scale"
          elevation="3"
          rounded="xl"
          color="white"
          style="cursor: pointer"
          @click="router.push('/admin')"
        >
          <v-icon class="me-2" color="primary">mdi-arrow-left</v-icon>
          <span class="text-body-1 font-weight-medium text-primary"
            >返回後台</span
          >
        </v-card>

        <v-card
          class="pa-2 d-flex align-center justify-center hover-scale"
          elevation="3"
          rounded="xl"
          color="white"
          style="cursor: pointer"
          @click="router.push('/home')"
        >
          <v-icon class="me-2" color="primary">mdi-arrow-left</v-icon>
          <span class="text-body-1 font-weight-medium text-primary"
            >返回首頁</span
          >
        </v-card>
      </div>
    </v-navigation-drawer>

    <v-app-bar border="b" class="ps-4" flat>
      <v-app-bar-nav-icon
        v-if="$vuetify.display.smAndDown || $vuetify.display.md"
        @click="drawer = !drawer"
      >
        <v-icon>mdi-menu</v-icon>
      </v-app-bar-nav-icon>

      <v-app-bar-title class="text-h5">PawShop後臺</v-app-bar-title>

      <v-menu>
        <template #activator="{ props }">
          <v-btn icon v-bind="props" class="me-10">
            <v-avatar
              size="50"
              class="elevation-3"
              style="border: 2px solid #fff"
            >
              <v-img :src="authStore.memberPhoto" alt="avatar" />
            </v-avatar>
          </v-btn>
        </template>

        <v-list density="compact" nav>
          <v-list-item title="Settings" />
          <v-list-item title="Logout" @click="logout" />
        </v-list>
      </v-menu>
    </v-app-bar>

    <v-main>
      <div class="pa-4">
        <v-sheet
          border="dashed md"
          color="surface-light"
          height="100%"
          rounded="lg"
          width="100%"
        >
          <router-view />
        </v-sheet>
      </div>
    </v-main>
  </v-layout>
</template>

<script setup>
<<<<<<< HEAD
<<<<<<< HEAD
import PawShopLogo from "@/member/assets/images/PawShop_logo.png";
import CardHover from "@/components/CardHover.vue";
=======
import PawShopLogo from "@/member/assets/images/PawShop_white_logo.png";
import { useAuthStore } from "@/member/stores/auth";
import router from "@/router";
>>>>>>> e35bf0476e36d90496b9da0de646acbce4ae3fd9
=======

import PawShopLogo from "@/member/assets/images/PawShop_white_logo.png";
import { useAuthStore } from "@/member/stores/auth";
import router from "@/router";
>>>>>>> 24072d3ee682da96e290bb88e3a286229f9079da
import { ref } from "vue";

const authStore = useAuthStore();
const drawer = ref(true);
const openedGroups = ref(["使用者"]);

const logout = () => {
  authStore.logout();
  router.push("/login");
};

const items = ref([
  {
    title: "使用者",
    icon: "mdi-account",
    children: [
      { label: "用戶管理", link: "/admin/member" },
      { label: "角色權限", link: "/" },
    ],
  },
  {
    title: "賣場",
    icon: "mdi-storefront",
    children: [
      { label: "商品管理", link: "/" },
      { label: "訂單管理", link: "/admin/orders" },
    ],
  },
  {
    title: "美容預約",
    icon: "mdi-calendar-account",
    children: [
      { label: "預約紀錄", link: "/" },
      { label: "服務項目資訊", link: "/" },
    ],
  },
  {
    title: "論壇",
    icon: "mdi-forum",
    children: [
      { label: "文章管理", link: "/" },
      { label: "分類", link: "/" },
    ],
  },
  {
    title: "首頁內容",
    icon: "mdi-home-outline",
    children: [
      { label: "最新活動", link: "/" },
      { label: "公司資訊", link: "/" },
      { label: "常見問答", link: "/" },
    ],
  },
]);
</script>

<style scoped></style>
