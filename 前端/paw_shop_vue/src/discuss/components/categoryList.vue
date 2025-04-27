<template>
  <v-list>
    <v-list-item v-for="category in categories" :key="category">
      {{ category }}
    </v-list-item>
  </v-list>
</template>

<script setup>
const categories = ["日常分享", "飼養心得", "用品推薦", "領養送養", "問題詢問"];
</script>
