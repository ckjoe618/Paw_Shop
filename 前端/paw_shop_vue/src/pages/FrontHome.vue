<template>
  <v-container class="py-6">
    <ad></ad>
    <CategoryIcons :categories="petCategories" />
    <ProductRecommendations class="mb-4" />
    <QA></QA>
  </v-container>
</template>

<script setup>
import QA from "@/components/QA.vue";
import ad from "@/components/ad.vue";
import CategoryIcons from "@/member/components/CategoryIcons.vue";
import ProductRecommendations from "@/member/components/ProductRecommendations.vue";

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

<style scoped></style>
