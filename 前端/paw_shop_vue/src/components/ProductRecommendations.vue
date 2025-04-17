<template>
  <v-container>
    <h3 class="text-h6 font-weight-medium mb-4">
      <i class="fas fa-thumbs-up mr-2"></i>Top Recommendations
    </h3>
    <v-slide-group show-arrows>
      <v-slide-item v-for="(item, index) in items" :key="index">
        <v-card class="mx-2" width="150">
          <v-img :src="item.image" height="120" />
          <v-card-title class="text-subtitle-2">{{ item.name }}</v-card-title>
          <v-card-subtitle>€{{ item.price }}</v-card-subtitle>
          <div class="px-3 pb-2">
            <i
              class="fas fa-star"
              v-for="n in item.rating"
              :key="n"
              style="color: #ffd700"
            ></i>
          </div>
        </v-card>
      </v-slide-item>
    </v-slide-group>
  </v-container>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  items: {
    type: Array,
    default: () => [
      {
        name: "Product 1",
        image: "https://via.placeholder.com/150",
        price: 5.99,
        rating: 5,
      },
      {
        name: "Product 2",
        image: "https://via.placeholder.com/150",
        price: 8.99,
        rating: 4,
      },
      {
        name: "Product 3",
        image: "https://via.placeholder.com/150",
        price: 7.99,
        rating: 3,
      },
      {
        name: "Product 4",
        image: "https://via.placeholder.com/150",
        price: 6.99,
        rating: 5,
      },
      {
        name: "Product 5",
        image: "https://via.placeholder.com/150",
        price: 9.99,
        rating: 4,
      },
      {
        name: "Product 6",
        image: "https://via.placeholder.com/150",
        price: 4.99,
        rating: 3,
      },
    ],
  },
});
</script>

<style scoped>
.v-card-title {
  font-size: 0.85rem;
}
.v-card-subtitle {
  font-size: 0.8rem;
  color: #555;
}
i.fas.fa-star {
  margin-right: 2px;
  font-size: 14px;
}
</style>
