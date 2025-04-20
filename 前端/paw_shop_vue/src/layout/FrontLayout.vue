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
    <v-main class="pt-10">
      <v-container fluid class="pa-0 mt-3">
        <router-view />
      </v-container>

      <!-- Footer 區塊滿版 -->
      <v-sheet class="w-100">
        <FooterBar />
      </v-sheet>
    </v-main>
  </v-app>
</template>

<script setup>
import HeaderBar from "@/components/HeaderBar.vue";
import NavTabs from "@/components/NavTabs.vue";
import FooterBar from "@/components/FooterBar.vue";

const navItems = [
  { label: "Product", link: { path: "/products", query: {} } },
  { label: "Dog", link: "" },
  { label: "Cat", link: "" },
  { label: "Small Pet", link: "" },
  { label: "+ Vet", link: "" },
  { label: "Top Brands", link: "" },
  { label: "Special Offers", link: "" },
  { label: "Appointment", link: "" },
  { label: "Forum", link: "" },
];
</script>

<style scoped></style>

