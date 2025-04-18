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
      <v-container class="mt-8">
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
  "Product",
  "Dog",
  "Cat",
  "Small Pet",
  "+ Vet",
  "Top Brands",
  "Special Offers",
  "Appointment",
  "Forum",
];
</script>

<style scoped></style>
