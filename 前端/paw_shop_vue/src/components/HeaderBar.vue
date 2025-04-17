<template>
  <v-app-bar app color="#215d1e" dark height="80">
    <v-toolbar-title class="text-h5">
      <router-link to="/" style="text-decoration: none">
        <img
          src="../assets/images/ChatGPT Image 2025年4月16日 上午10_53_06.png"
          alt="PawShop"
          style="height: 80px"
        />
      </router-link>
    </v-toolbar-title>
    <v-spacer />
    <v-text-field
      v-model="search"
      label="Search the store"
      single-line
      hide-details
      dense
      class="mr-4"
      outlined
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
