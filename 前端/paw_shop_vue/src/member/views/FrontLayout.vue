<template>
  <v-app>
    <!-- HeaderBar 本身是 v-app-bar -->
    <HeaderBar />

    <!-- Sticky NavTabs -->
    <v-sheet
      class="w-100"
      elevation="2"
      style="position: sticky; top: 64px; z-index: 10; background: white"
    >
      <NavTabs :items="navItems" />
    </v-sheet>

    <!-- Main content -->
    <v-main>
      <v-container class="py-6">
        <MainCarousel />
        <CategoryIcons :categories="petCategories" />
        <ProductRecommendations />
      </v-container>

      <!-- Footer 區塊滿版 -->
      <v-sheet class="w-100">
        <FooterBar />
      </v-sheet>
    </v-main>
  </v-app>
</template>

<script setup>
import HeaderBar from "@/member/components/HeaderBar.vue";
import NavTabs from "@/member/components/NavTabs.vue";
import MainCarousel from "@/member/components/MainCarousel.vue";
import CategoryIcons from "@/member/components/CategoryIcons.vue";
import ProductRecommendations from "@/member/components/ProductRecommendations.vue";
import FooterBar from "@/member/components/FooterBar.vue";

const navItems = [
  "Dog",
  "Cat",
  "Small Pet",
  "Bird",
  "Aquatic",
  "+ Vet",
  "Top Brands",
  "Special Offers",
];

const petCategories = [
  {
    name: "Cat",
    image: "https://cdn-icons-png.flaticon.com/512/616/616408.png",
  },
  {
    name: "Dog",
    image: "https://cdn-icons-png.flaticon.com/512/616/616408.png",
  },
  {
    name: "Small Pet",
    image: "https://cdn-icons-png.flaticon.com/512/616/616408.png",
  },
  {
    name: "Fish",
    image: "https://cdn-icons-png.flaticon.com/512/616/616408.png",
  },
  {
    name: "Bird",
    image: "https://cdn-icons-png.flaticon.com/512/616/616408.png",
  },
];
</script>

<style scoped>
.v-carousel-item {
  color: white;
}
</style>
