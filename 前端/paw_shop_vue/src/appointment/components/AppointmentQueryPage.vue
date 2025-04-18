<template>
    <div class="container py-5">
      <h2 class="mb-4 text-success text-center">🐾 預約美容服務查詢結果</h2>
  
      <div class="border rounded p-4 shadow-sm bg-light-subtle">
        <div class="mb-3">
          <strong>寵物名稱:</strong> {{ pet }}
        </div>
  
        <div class="mb-3">
          <strong>預約日期:</strong> {{ date }}
        </div>
  
        <div class="mb-3">
          <strong>預約時段:</strong> {{ timeslot }}
        </div>
  
        <div class="mb-3">
          <strong>選擇服務:</strong> {{ service }}
        </div>
  
        <div v-if="extras.length" class="mb-3">
          <strong>額外加購:</strong>
          <ul>
            <li v-for="(extra, index) in extras" :key="index">{{ extra }}</li>
          </ul>
        </div>
  
        <div class="mb-3">
          <strong>總價:</strong> {{ total }}
        </div>
  
        <div class="text-end">
          <button class="btn btn-green" @click="goBack">🔙 返回預約頁面</button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { useRoute, useRouter } from 'vue-router';
  
  const route = useRoute();
  const router = useRouter();
  
  // 從路由查詢參數中取得預約資料
  const pet = route.query.pet || '未選擇';
  const date = route.query.date || '未選擇';
  const timeslot = route.query.timeslot || '未選擇';
  const service = route.query.service || '未選擇';
  const extras = route.query.extras ? route.query.extras.split(',') : [];
  const total = route.query.total || '0';
  
  // 返回預約頁面
  const goBack = () => {
    router.push('/toappointments');
  };
  </script>
  
  <style scoped>
  .btn-green {
    background-color: #81c784;
    color: white;
    font-weight: bold;
    border-radius: 25px;
    padding: 0.5rem 1.5rem;
    transition: all 0.3s ease;
  }
  
  .btn-green:hover {
    background-color: #66bb6a;
  }
  </style>
  