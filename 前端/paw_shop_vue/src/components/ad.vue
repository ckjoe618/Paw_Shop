<template>
  <v-carousel
    height="400"
    cycle
    show-arrows="hover"
    hide-delimiter-background
    class="rounded-0"
  >
    <v-carousel-item
      v-for="(img, i) in carouselImages"
      :key="i"
      :src="img"
      cover
    />
  </v-carousel>
</template>

<script setup>
import img1 from "@/order/assets/pictures/1_Carousel_Travel_Desktop.png";
import img2 from "@/order/assets/pictures/2_Desktop_Carousel_IndoorToys.png";
import img3 from "@/order/assets/pictures/3_Desktop_Carousel_CalmingAids.png";
import img4 from "@/order/assets/pictures/4_Carousel_Desktop_HarryPotter.png";

const carouselImages = [img1, img2, img3, img4];
</script>

<style scoped>
.full-width-carousel {
  width: 100vw;
  margin-left: calc(-50vw + 50%);
}
.v-carousel__controls__prev,
.v-carousel__controls__next {
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 50%;
}
</style>
