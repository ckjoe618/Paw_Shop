<template>
  <nav class="navbar">
    <router-link
      v-for="item in navItems"
      :key="item.path"
      :to="'/' + item.path"
      class="nav-item"
    >
      {{ item.label }}
    </router-link>
  </nav>
</template>

<script setup>
const navItems = [
  { label: "狗狗", path: "dog" },
  { label: "貓咪", path: "cat" },
  { label: "其他寵物", path: "other-pets" },
  { label: "美容", path: "grooming" },
  { label: "討論區", path: "forum" },
  { label: "領養資訊", path: "adoption" },
];
</script>

<style scoped>
.navbar {
  background-color: #2e7d32;
  padding: 10px 20px;
  display: flex;
  gap: 20px;
  justify-content: center;
}

.nav-item {
  color: white;
  text-decoration: none;
  font-weight: bold;
}

.nav-item:hover {
  text-decoration: underline;
}
</style>
