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
    icon: "mdi-cat",
  },
  {
    name: "Dog",
    icon: "mdi-dog",
  },
  {
    name: "Small Pet",
    icon: "mdi-rabbit-variant",
  },
  {
    name: "Fish",
    icon: "mdi-fish",
  },
  {
    name: "Bird",
    icon: "mdi-bird",
  },
];
</script>

<style scoped>
.v-carousel-item {
  color: white;
}
</style>
