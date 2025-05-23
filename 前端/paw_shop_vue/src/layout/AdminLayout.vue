<template>
  <v-layout>
    <v-navigation-drawer v-model="drawer" width="220">
      <div class="d-flex flex-column align-center">
        <v-img
          :src="PawShopLogo"
          width="120"
          height="120"
          alt="logo"
          class="mb-6 logo-hover"
          @click="router.push('/admin')"
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
            >返回首頁</span
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
            >前往前臺</span
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

      <v-app-bar-title class="text-h5">PawShop 後臺管理中心</v-app-bar-title>

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
          <v-list-item title="登出" @click="logout" />
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
          <router-view :key="$route.fullPath" />
        </v-sheet>
      </div>
    </v-main>
  </v-layout>
</template>

<script setup>
import PawShopLogo from "@/member/assets/images/PawShop_white_logo.png";
import { ref } from "vue";
import { useAuthStore } from "@/member/stores/auth";
import router from "@/router";

const openedGroups = ref(["使用者"]);
const authStore = useAuthStore();
const drawer = ref(true);

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
      { label: "用戶分析", link: "/admin/memberAnalyze" },
    ],
  },
  {
    title: "賣場",
    icon: "mdi-storefront",
    children: [
      { label: "商品管理", link: "/admin/products" },
      { label: "訂單管理", link: "/admin/orders" },
    ],
  },
  {
    title: "美容預約",
    icon: "mdi-calendar-account",
    children: [
      { label: "預約紀錄", link: "/admin/appointments" },
      { label: "預約報到管理", link: "/admin/checkin?appointmentId=" },
    ],
  },
  {
    title: "論壇",
    icon: "mdi-forum",
    children: [
      { label: "文章管理", link: "/admin/discuss" },
      { label: "檢舉審核", link: "/" },
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

<style scoped>
.logo-hover {
  cursor: pointer;
  transition: transform 0.3s ease, opacity 0.3s ease;
}
.logo-hover:hover {
  transform: scale(1.08);
  opacity: 0.85;
}
</style>
