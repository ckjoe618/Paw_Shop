<template>
  <v-app-bar app color="green darken-3" dark>
    <v-toolbar-title class="text-h5">
      <i class="fas fa-paw mr-2"></i> PawShop
    </v-toolbar-title>
    <v-spacer />
    <v-text-field
      v-model="search"
      label="Search the store"
      single-line
      hide-details
      dense
      class="mr-4"
    >
      <template #append-inner>
        <v-btn icon><i class="fas fa-search"></i></v-btn>
      </template>
    </v-text-field>
    <v-btn icon><i class="fas fa-heart"></i></v-btn>
    <v-btn icon><i class="fas fa-cart-shopping"></i></v-btn>
    <v-btn icon><i class="fas fa-user"></i></v-btn>
  </v-app-bar>
</template>

<script setup>
import { ref } from "vue";
const search = ref("");
</script>

<style scoped></style>
