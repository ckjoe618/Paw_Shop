<template>
    <div class="container mt-4">
      <!-- 美容標題 -->
      <div class="text-center mb-4">
        <h2>歡迎來到PawShop寵物美容預約</h2>
      </div>
  
        <!-- 卡片區塊：腳印樣式按鈕 -->
<div class="row row-cols-1 row-cols-md-4 g-4 mb-5 text-center">
  <div class="col" v-for="(item, index) in services" :key="index">
    <div
      class="paw-btn"
      :class="{ 'active-paw': selectedService?.title === item.title }"
      @click="selectedService = item"
    >
      <div class="paw-icon">🐾</div>
      <div class="paw-title mt-2">{{ item.title }}</div>
    </div>
  </div>
</div>


    <!-- 圖片與介紹區 -->
    <div class="row mb-4">
     <!-- 左邊圖片 -->
<div class="col-md-6">
  <div class="img-frame">
    <img
      v-if="selectedService"
      :src="selectedService.img"
      alt="選擇的美容圖片"
      class="img-fluid"
    />
    <div v-else class="placeholder-text">
      請點選上方服務項目 🐶🐱
    </div>
  </div>
</div>

      <!-- 右文 -->
      <div class="col-md-6">
        <div class="border rounded p-4 shadow-sm bg-light">
          <h5 v-if="selectedService">{{ selectedService.title }}</h5>
          <p v-if="selectedService">{{ selectedService.description }}</p>
        </div>
        <div class="text-end mt-3">
          <button class="btn btn-pink" :disabled="!selectedService" @click="goToAppointment">🐾 我要預約</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import { useRouter } from 'vue-router'
const router = useRouter();
const selectedService = ref(null);

const services = [
  {
    id: 1,
    title: "基礎洗護",
    img: new URL('@/assets/imags/basicgrooming02.png', import.meta.url).href,
    description: "使用溫和洗劑，包含洗澡、吹乾、簡單梳理。"
  },
  {
    id: 2,
    title: "基礎洗護含美容修剪",
   img: new URL('@/assets/imags/basicgrooming01.png', import.meta.url).href,
   description: "除了洗澡外，包含基本剪毛、腳底清理、修臉等。"
  },
  {
    id: 3,
    title: "專業洗護",
    img: new URL('@/assets/imags/grooming02.png', import.meta.url).href,
     description: "使用專業洗劑與深層護理，含全身修剪與造型。"
  },
  {
    id: 4,
    title: "專業洗護含美容修剪",
    img: new URL('@/assets/imags/grooming01.png', import.meta.url).href,
    description: "提供精油按摩、護毛護膚，讓毛孩徹底放鬆。"
  }

];

const goToAppointment = () => {
  if (selectedService.value) {
    router.push({
      path: '/toappointments/reserve',
      query: {
    service: selectedService.value?.title || '' 
  }
    });
  }
};


</script>

<style scoped>
/* 卡片樣式 */
.service-card {
  border: 2px solid #66bb6a;
  border-radius: 1rem;
  background-color: #e8f5e9;
  transition: transform 0.3s, box-shadow 0.3s;
  cursor: pointer;
}
.service-card:hover {
  transform: scale(1.05);
  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
}
.active-card {
  border-color: #43a047;
  background-color: #c8e6c9;
}

/* 圖片預覽區塊 */
.preview-box {
  border: 2px dashed #a5d6a7;
  background-color: #f1f8e9;
  border-radius: 1rem;
  min-height: 250px;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 我要預約按鈕 */
.btn-pink {
  background-color: #66bb6a;
  color: white;
  border: none;
  padding: 0.5rem 1.5rem;
  font-weight: bold;
  border-radius: 2rem;
  transition: background-color 0.3s;
}
.btn-pink:hover {
  background-color: #43a047;
}
.btn-pink:disabled {
  background-color: #c8e6c9;
  cursor: not-allowed;
}

/* 腳印按鈕樣式 */
.paw-btn {
  background-color: #e8f5e9;
  border: 3px dashed #81c784;
  border-radius: 50%;
  width: 120px;
  height: 120px;
  margin: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  transition: transform 0.2s ease, box-shadow 0.2s;
  cursor: pointer;
  box-shadow: 0 4px 8px rgba(102, 187, 106, 0.2);
  position: relative;
  overflow: hidden;
}
.paw-btn:hover {
  animation: hop 0.4s ease;
  box-shadow: 0 0 15px 4px #a5d6a7;
}
.active-paw {
  background-color: #c8e6c9;
  box-shadow: 0 0 10px 6px #a5d6a7;
  animation: pulseGlow 1.2s infinite;
}

/* 腳印 emoji */
.paw-icon {
  font-size: 2rem;
  animation: sparkle 1.6s infinite ease-in-out alternate;
}

/* 標題字 */
.paw-title {
  font-size: 0.85rem;
  font-weight: bold;
  color: #2e7d32;
}

/* 腳印背景毛球感 */
.paw-btn::before {
  content: "";
  position: absolute;
  top: -10%;
  left: -10%;
  width: 120%;
  height: 120%;
  background: radial-gradient(circle, #c8e6c9 30%, transparent 70%);
  opacity: 0.2;
  border-radius: 50%;
  z-index: 0;
}

/* 圖片框 */
.img-frame {
  background-color: #f1f8e9;
  border: 4px dashed #a5d6a7;
  border-radius: 24px;
  padding: 16px;
  box-shadow: 0 4px 12px rgba(102, 187, 106, 0.3);
  transition: all 0.4s ease-in-out;
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.img-frame img {
  border-radius: 16px;
  max-height: 320px;
  object-fit: cover;
  transition: transform 0.4s ease;
}
.img-frame img:hover {
  transform: scale(1.05);
}
.placeholder-text {
  font-size: 1.1rem;
  color: #9e9e9e;
  text-align: center;
  animation: pulse 1.5s infinite ease-in-out;
}

/* 呼吸動效 */
@keyframes pulse {
  0% { opacity: 0.5; }
  50% { opacity: 1; }
  100% { opacity: 0.5; }
}

/* 跳動 */
@keyframes hop {
  0% { transform: translateY(0); }
  40% { transform: translateY(-10px) scale(1.05); }
  100% { transform: translateY(0); }
}

/* 光暈閃爍 */
@keyframes pulseGlow {
  0% { box-shadow: 0 0 8px 3px #81c784; }
  50% { box-shadow: 0 0 16px 8px #a5d6a7; }
  100% { box-shadow: 0 0 8px 3px #81c784; }
}

/* 星星閃爍 */
@keyframes sparkle {
  0% { filter: drop-shadow(0 0 0px #66bb6a); }
  100% { filter: drop-shadow(0 0 6px #aed581); }
}
</style>
