<template>
  <form class="search-bar" @submit.prevent="handleSearch">
    <input type="text" v-model="keyword" placeholder="請輸入關鍵字..." />
    <button type="submit">
      <i class="fas fa-search"></i>
    </button>
  </form>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const keyword = ref("");
const router = useRouter();

const handleSearch = () => {
  if (keyword.value.trim() !== "") {
    const trimmed = keyword.value.trim();
    router.push({ path: "/search", query: { keyword: trimmed } });
  }
};
</script>

<style scoped>
.search-bar {
  display: flex;
  width: 100%;
  max-width: 400px;
  height: 40px; /* ✅ 統一高度 */
  border-radius: 15px;
  overflow: hidden; /* ✅ 避免邊角露出 */
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.1);
}

.search-bar input {
  flex: 1;
  padding: 0 12px;
  border: none;
  outline: none;
  font-size: 16px;
  background-color: white;
  color: #333;
}

.search-bar button {
  width: 50px; /* ✅ 給按鈕固定寬度 */
  background-color: #388e3c;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.search-bar button i {
  font-size: 18px;
  color: white;
}
.search-bar input::placeholder {
  color: #aaa;
}
.search-bar button:hover {
  background-color: #2e7d32;
}
.search-bar input,
.search-bar button {
  height: 100%;
}
</style>
