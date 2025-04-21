<template>
  <v-container class="text-start">
    <h3 class="text-h6 font-weight-medium mb-1">
      <v-icon>mdi-store-search</v-icon> Shop for your pet
    </h3>

    <v-row justify="center">
      <v-col
        v-for="pet in pets"
        :key="pet.label"
        cols="12"
        sm="3"
        class="text-center"
      >
        <v-img
          :src="pet.img"
          width="100%"
          height="180"
          cover
          class="rounded-lg mb-2"
        />
        <div class="text-subtitle-1 font-weight-medium">{{ pet.label }}</div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import catImg from "@/order/assets/pictures/home_cat.webp";
import dogImg from "@/order/assets/pictures/home_dog.webp";
import smallpets from "@/order/assets/pictures/home_smallpets.webp";
import bird from "@/order/assets/pictures/home_bird.webp";

const pets = [
  {
    label: "Cat",
    img: catImg,
    bgColor: "#fde78b",
  },
  {
    label: "Dog",
    img: dogImg,
    bgColor: "#c8eafd",
  },
  {
    label: "Small Pet",
    img: smallpets,
    bgColor: "#c7f5b0",
  },
  {
    label: "Bird",
    img: bird,
    bgColor: "#fbd1d9",
  },
];
</script>

<style scoped></style>
