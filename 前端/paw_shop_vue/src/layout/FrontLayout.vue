<template>
  <v-app>
    <!-- HeaderBar 本身是 v-app-bar  -->
    <HeaderBar />
    <!-- Sticky NavTabs -->
    <v-sheet
      class="w-100"
      elevation="2"
      style="position: sticky; top: 80px; z-index: 10; background: #f0f0f0"
    >
      <NavTabs :items="navItems" />
    </v-sheet>
    <!-- Main content -->
    <v-main>
      <router-view :key="$route.fullPath" />
    </v-main>
    <FooterBar />
  </v-app>
</template>

<script setup>
import HeaderBar from "@/components/HeaderBar.vue";
import NavTabs from "@/components/NavTabs.vue";
import FooterBar from "@/components/FooterBar.vue";

const navItems = [
  { label: "所有商品", link: "/products" },
  { label: "狗狗", link: "" },
  { label: "貓咪", link: "" },
  { label: "小型寵物", link: "" },
  { label: "熱銷品牌", link: "" },
  { label: "促銷活動", link: "" },
  { label: "美容預約", link: "/appointments" },
  { label: "寵物討論區", link: "/discuss" },
];
</script>

<style scoped></style>
