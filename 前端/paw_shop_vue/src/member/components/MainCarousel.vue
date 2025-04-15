<template>
  <v-container class="mt-4">
    <v-carousel height="200">
      <v-carousel-item v-for="(slide, i) in slides" :key="i">
        <v-sheet
          class="fill-height d-flex justify-center align-center"
          color="green lighten-3"
        >
          <div class="text-center">
            <i
              class="fas fa-bullhorn fa-lg d-block mb-2"
              style="color: white"
            ></i>
            <h3 class="text-h6 font-weight-medium">{{ slide }}</h3>
          </div>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
  </v-container>
</template>

<script setup>
const slides = [
  "Free Delivery for Orders over $49",
  "Repeat Order Discounts",
  "New Products Weekly",
];
</script>

<style scoped>
.text-center i {
  font-size: 24px;
}
</style>
