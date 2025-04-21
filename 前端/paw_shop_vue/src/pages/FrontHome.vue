<template>
  <ad></ad>
  <v-container class="py-6">
    <CategoryIcons class="mt-10" />
    <ProductRecommendations class="mb-4" />
    <QA></QA>
  </v-container>
</template>

<script setup>
import QA from "@/components/QA.vue";
import ad from "@/components/ad.vue";
import CategoryIcons from "@/components/CategoryIcons.vue";
import ProductRecommendations from "@/components/ProductRecommendations.vue";
</script>

<style scoped></style>
