<template>
  <v-container
    class="fill-height d-flex flex-column align-center justify-center text-center"
  >
    <v-icon size="96" color="red">mdi-server</v-icon>
    <h1 class="text-h4 mt-4">500 - 系統錯誤</h1>
    <p class="text-subtitle-1">伺服器發生錯誤，請稍後再試。</p>
    <v-btn color="primary" class="mt-4" @click="$router.push('/')">
      回首頁
    </v-btn>
  </v-container>
</template>

<script setup></script>
