<template>
  <v-container
    class="fill-height d-flex flex-column justify-center align-center text-center"
  >
    <v-icon color="warning" size="96">mdi-alert</v-icon>
    <h1 class="text-h4 font-weight-bold my-4">無權限</h1>
    <p class="text-body-1">您沒有權限瀏覽此頁面，請洽管理員。</p>
    <v-btn class="mt-6" color="primary" @click="$router.push('/')">
      回首頁
    </v-btn>
  </v-container>
</template>

<script setup></script>
