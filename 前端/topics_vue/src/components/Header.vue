<template>
  <header class="header">
    <div class="header-top">
      <div class="logo">Paw Shop</div>

      <SearchBar @search="handleSearch" />

      <div class="user-actions">
        <a href="#">登入</a>
        <button class="basket-btn">
          <i class="fas fa-shopping-cart"></i> 購物車
        </button>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref } from "vue";
import SearchBar from "./SearchBar.vue";
</script>

<style scoped>
header {
  background: #d7ccc8;
  color: white;
  padding: 10px 20px;
  font-family: sans-serif;
}

.header-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  font-size: 36px;
  font-weight: bold;
}

.user-actions {
  display: flex;
  gap: 15px;
  align-items: center;
}

.basket-btn {
  background-color: #fdd835;
  border: none;
  padding: 8px 12px;
  border-radius: 20px;
  cursor: pointer;
  font-weight: bold;
  color: #333;
}
</style>
