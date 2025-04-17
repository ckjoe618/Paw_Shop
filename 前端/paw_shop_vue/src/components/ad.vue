<template>
  <div style="width: 100%">
    <div
      id="carouselAds"
      class="carousel slide"
      data-bs-ride="carousel"
      data-bs-interval="4000"
    >
      <div class="carousel-indicators">
        <button
          type="button"
          data-bs-target="#carouselAds"
          data-bs-slide-to="0"
          class="active"
          aria-current="true"
          aria-label="Slide 1"
        ></button>
        <button
          type="button"
          data-bs-target="#carouselAds"
          data-bs-slide-to="1"
          aria-label="Slide 2"
        ></button>
        <button
          type="button"
          data-bs-target="#carouselAds"
          data-bs-slide-to="2"
          aria-label="Slide 3"
        ></button>
        <button
          type="button"
          data-bs-target="#carouselAds"
          data-bs-slide-to="3"
          aria-label="Slide 4"
        ></button>
        <button
          type="button"
          data-bs-target="#carouselAds"
          data-bs-slide-to="4"
          aria-label="Slide 5"
        ></button>
      </div>
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img
            src="../order/assets/pictures/messageImage_1744271934310.jpg"
            class="d-block w-100"
            alt="新開幕"
          />
        </div>
        <div class="carousel-item">
          <img
            src="../order/assets/pictures/1_Carousel_Travel_Desktop.png"
            class="d-block w-100"
            alt="超划算"
          />
        </div>
        <div class="carousel-item">
          <img
            src="../order/assets/pictures/2_Desktop_Carousel_IndoorToys.png"
            class="d-block w-100"
            alt="狗狗等你"
          />
        </div>
        <div class="carousel-item">
          <img
            src="../order/assets/pictures/3_Desktop_Carousel_CalmingAids.png"
            class="d-block w-100"
            alt="貓貓毛"
          />
        </div>
        <div class="carousel-item">
          <img
            src="../order/assets/pictures/4_Carousel_Desktop_HarryPotter.png"
            class="d-block w-100"
            alt="快來買"
          />
        </div>
      </div>
      <button
        class="carousel-control-prev"
        type="button"
        data-bs-target="#carouselAds"
        data-bs-slide="prev"
      >
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button
        class="carousel-control-next"
        type="button"
        data-bs-target="#carouselAds"
        data-bs-slide="next"
      >
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
  </div>
</template>

<script setup>
// import image1 from "../assets/pictures/1_Carousel_Travel_Desktop.png";
</script>

<style scoped>
.carousel-item img {
  width: 100%;
  object-fit: cover;
}
.carousel-control-prev-icon,
.carousel-control-next-icon {
  background-color: rgba(0, 0, 0, 0.5); /* 半透明黑色 */
  border-radius: 50%;
}
</style>
