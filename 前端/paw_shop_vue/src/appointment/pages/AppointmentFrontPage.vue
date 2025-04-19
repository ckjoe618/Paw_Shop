<template>
  <div class="grooming-wrapper">
    <div class="text-center mb-4 text-dark">
      <h2 class="fw-bold">Paw-Shop</h2>
      <p class="lead">爪娃專業洗護你家毛小孩！</p>
    </div>

    <div class="row justify-content-center g-3 mb-4">
      <div
        class="col-6 col-md-3"
        v-for="(item, index) in services"
        :key="index"
      >
        <div
          class="service-btn"
          :class="{ active: selectedService?.title === item.title }"
          @click="selectedService = item"
        >
          <div class="icon">🐾</div>
          <div class="title">{{ item.title }}</div>
        </div>
      </div>
    </div>

    <div class="row align-items-center g-4">
      <div class="col-md-6">
        <div class="image-box">
          <img
            v-if="selectedService"
            :src="selectedService.img"
            alt="美容圖片"
            class="img-fluid shadow"
          />
          <div
            v-else
            id="carouselExampleFade"
            class="carousel slide carousel-fade"
            data-bs-ride="carousel"
          >
            <div class="carousel-inner shadow">
              <div
                class="carousel-item"
                v-for="(item, index) in services"
                :class="{ active: index === 0 }"
                :key="'slide-' + index"
              >
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
          <p v-else class="text-muted">爪娃專業洗護你家毛小孩！</p>
          <div class="text-end mt-3">
            <button
              class="btn btn-success px-4 py-2 rounded-pill"
              :disabled="!selectedService"
              @click="goToAppointment"
            >
              🐾 我要預約
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const selectedService = ref(null);

const services = [
  {
    id: 1,
    title: "基礎洗護",
    img: new URL(
      "@/appointment/assets/imags/basicgrooming02.png",
      import.meta.url
    ).href,
    description:
      "此服務使用溫和無刺激的洗劑，專為寵物肌膚設計，能夠有效清潔寵物的皮毛。服務內容包含洗澡、吹乾，並進行簡單梳理，幫助保持毛發的乾淨與順滑。這是最基本的洗護方案，適合需要常規清潔保養的毛孩。",
  },
  {
    id: 2,
    title: "基礎洗護含美容修剪",
    img: new URL(
      "@/appointment/assets/imags/basicgrooming01.png",
      import.meta.url
    ).href,
    description:
      "在基礎洗護的基礎上，我們提供額外的美容修剪服務，讓您的毛孩不僅乾淨，還能保持整齊美觀。此服務包含基本剪毛、腳底清理、修整臉部毛髮等，特別適合長毛或需要定期修剪的毛孩，讓牠們看起來更加精緻，並保持清爽。 ",
  },
  {
    id: 3,
    title: "專業洗護",
    img: new URL("@/appointment/assets/imags/grooming02.png", import.meta.url)
      .href,
    description:
      "專業洗護使用高品質的洗劑和深層護理配方，能夠提供寵物毛發與皮膚更為深入的清潔與保養。這項服務不僅包括全身洗澡，還包含全身修剪與造型設計，讓毛孩更顯美麗。無論是短毛或長毛犬貓，都能從專業洗護中獲得舒適的洗護體驗，毛髮質地會變得更加柔順光滑。",
  },
  {
    id: 4,
    title: "專業洗護含美容修剪",
    img: new URL("@/appointment/assets/imags/grooming01.png", import.meta.url)
      .href,
    description:
      "此服務將專業洗護和美容修剪結合，讓您的毛孩在徹底清潔的同時，還能獲得美容修剪服務。我們將使用精油按摩與護毛護膚的技術，讓毛孩在洗護過程中放鬆，舒緩壓力，並促進毛發的健康生長。此方案特別適合需要全方位護理的毛孩，讓牠們不僅外觀靚麗，還能獲得身心的放鬆。",
  },
];

const goToAppointment = () => {
  if (selectedService.value) {
    router.push({
      path: "/appointments/reserve",
    });
  }
};
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
