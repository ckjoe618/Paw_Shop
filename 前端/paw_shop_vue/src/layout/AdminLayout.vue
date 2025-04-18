<template>
  <v-layout>
    <v-navigation-drawer v-model="drawer" width="220">
      <div class="d-flex flex-column align-center">
        <v-img
          :src="PawShopLogo"
          width="120"
          height="120"
          alt="logo"
          style="margin-bottom: 50px"
        />
        <v-list v-model:opened="openedGroups">
          <v-list-group v-for="item in items">
            <template #activator="{ props }">
              <v-list-item
                v-bind="props"
                :prepend-icon="item.icon"
                :title="item.title"
              ></v-list-item>
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
    </v-navigation-drawer>

    <v-app-bar border="b" class="ps-4" flat>
      <v-app-bar-nav-icon
        v-if="$vuetify.display.smAndDown"
        @click="drawer = !drawer"
      >
        <i class="fas fa-bars" />
      </v-app-bar-nav-icon>

      <v-app-bar-title class="text-h5">PawShop後臺</v-app-bar-title>

      <template #append>
        <v-btn class="text-none me-2" height="48" icon slim>
          <v-avatar
            color="surface-light"
            image="https://cdn.vuetifyjs.com/images/john.png"
            size="50"
          />

          <v-menu activator="parent">
            <v-list density="compact" nav>
              <v-list-item link title="Settings"> </v-list-item>
              <v-list-item link title="Logout"> </v-list-item>
            </v-list>
          </v-menu>
        </v-btn>
      </template>
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
          <cardHover />
        </v-sheet>
      </div>
    </v-main>
  </v-layout>
</template>

<script setup>
import PawShopLogo from "@/member/assets/images/PawShop_logo.png";
import CardHover from "@/components/CardHover.vue";
import { ref } from "vue";

const drawer = ref(true);
const openedGroups = ref(["使用者"]);

const items = ref([
  {
    title: "使用者",
    icon: "mdi-account",
    children: [
      {
        label: "用戶管理",
        link: "/user/manage",
        icon: "mdi-account-multiple-outline",
      },
      { label: "角色權限", link: "/user/roles" },
    ],
  },
  {
    title: "賣場",
    icon: "mdi-storefront",
    children: [
      {
        label: "商品管理",
        link: "/shop/products",
      },
      { label: "訂單管理", link: "/shop/orders" },
    ],
  },
  {
    title: "美容預約",
    icon: "mdi-calendar-account",
    children: [
      {
        label: "預約紀錄",
        link: "/reservation/history",
      },
      {
        label: "服務項目資訊",
        link: "/reservation/services",
      },
    ],
  },
  {
    title: "論壇",
    icon: "mdi-forum",
    children: [
      {
        label: "文章管理",
        link: "/forum/posts",
      },
      { label: "分類", link: "/forum/categories" },
    ],
  },
  {
    title: "首頁內容",
    icon: "mdi-home-outline",
    children: [
      { label: "最新活動", link: "/home/events" },
      { label: "公司資訊", link: "/home/about" },
      {
        label: "常見問答",
        link: "/home/faq",
      },
    ],
  },
]);
</script>

<style scoped></style>
