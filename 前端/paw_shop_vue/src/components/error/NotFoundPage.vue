<template>
  <v-container
    class="fill-height d-flex flex-column justify-center align-center text-center"
  >
    <v-icon color="error" size="96">mdi-emoticon-sad-outline</v-icon>
    <h1 class="text-h4 font-weight-bold my-4">找不到頁面</h1>
    <p class="text-body-1">您要尋找的頁面不存在，可能是連結錯誤或已被移除。</p>
    <v-btn class="mt-6" color="primary" @click="$router.push('/')">
      回首頁
    </v-btn>
  </v-container>
</template>

<script setup></script>
