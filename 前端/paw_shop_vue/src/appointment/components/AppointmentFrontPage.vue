<template>
  <div class="container mt-5">
    <div class="grooming-wrapper">

      <div class="text-center mb-4 text-dark">
        <h2 class="fw-bold">Paw-Shop</h2>
        <p class="lead">爪娃專業洗護你家毛小孩！</p>
      </div>

      <div class="row justify-content-center g-3 mb-4">
        <div class="col-6 col-md-3" v-for="(item, index) in services" :key="index">
          <div class="service-btn" :class="{ 'active': selectedService?.title === item.title }"
            @click="selectedService = item">
            <div class="icon">🐾</div>
            <div class="title">{{ item.title }}</div>
          </div>
        </div>
      </div>

      <div class="row align-items-center g-4">
        <div class="col-md-6">
          <div class="image-box">
            <img v-if="selectedService" :src="selectedService.img" alt="美容圖片" class="img-fluid shadow" />
            <div v-else id="carouselExampleFade" class="carousel slide carousel-fade" data-bs-ride="carousel">
              <div class="carousel-inner shadow">
                <div class="carousel-item" v-for="(item, index) in services" :class="{ active: index === 0 }"
                  :key="'slide-' + index">
                  <img :src="item.img" class="d-block w-100" alt="服務輪播" />
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-6">
          <div class="info-card">
            <h4 v-if="selectedService">{{ selectedService.title }}</h4>
            <p v-if="selectedService">{{ selectedService.description }}</p>
            <p v-else class="text-muted">請選擇上方服務，或觀看幻燈片了解內容</p>
            <div class="text-end mt-3">
              <button class="btn btn-success px-4 py-2 rounded-pill" :disabled="!selectedService"
                @click="goToAppointment">
                🐾 我要預約
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const selectedService = ref(null)

const services = [
  {
    id: 1,
    title: '基礎洗護',
    img: new URL('@/assets/imags/basicgrooming02.png', import.meta.url).href,
    description: '使用溫和洗劑，包含洗澡、吹乾、簡單梳理。',
  },
  {
    id: 2,
    title: '基礎洗護含美容修剪',
    img: new URL('@/assets/imags/basicgrooming01.png', import.meta.url).href,
    description: '除了洗澡外，包含基本剪毛、腳底清理、修臉等。',
  },
  {
    id: 3,
    title: '專業洗護',
    img: new URL('@/assets/imags/grooming02.png', import.meta.url).href,
    description: '使用專業洗劑與深層護理，含全身修剪與造型。',
  },
  {
    id: 4,
    title: '專業洗護含美容修剪',
    img: new URL('@/assets/imags/grooming01.png', import.meta.url).href,
    description: '提供精油按摩、護毛護膚，讓毛孩徹底放鬆。',
  },
]

const goToAppointment = () => {
  if (selectedService.value) {
    router.push({
      path: '/toappointments/reserve',
      query: {
        service: selectedService.value.title,
      },
    })
  }
}
</script>

<style scoped>
.grooming-wrapper {
  background: linear-gradient(135deg, #e8f5e9 0%, #ffffff 100%);
  padding: 3rem 2rem;
  border-radius: 1rem;
  box-shadow: 0 4px 20px rgba(102, 187, 106, 0.2);
}

.service-btn {
  background-color: #fff;
  border: 2px solid #66bb6a;
  border-radius: 1rem;
  text-align: center;
  padding: 1rem;
  cursor: pointer;
  transition: all 0.3s;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
}

.service-btn:hover {
  background-color: #e8f5e9;
  transform: translateY(-4px);
}

.service-btn.active {
  background-color: #c8e6c9;
  box-shadow: 0 0 0 4px #a5d6a7;
}

.service-btn .icon {
  font-size: 2rem;
}

.service-btn .title {
  font-weight: bold;
  margin-top: 0.5rem;
  font-size: 0.95rem;
}

.image-box {
  width: 500px;
  height: 500px;
  overflow: hidden;
  position: relative;
  margin: 0 auto;
  border-radius: 50%;
}

.carousel-inner img {
  object-fit: cover;
  height: 100%;
  width: 100%;
  object-position: center;
}

.info-card {
  background-color: #fff;
  padding: 2rem;
  border: 1px solid #c8e6c9;
  border-radius: 1rem;
  box-shadow: 0 4px 12px rgba(102, 187, 106, 0.2);
  height: 320px;
}
</style>
