<template>
  <v-tabs background-color="grey lighten-4" grow>
    <v-tab
      v-for="item in items"
      :key="item"
      class="text-subtitle-2 text-capitalize"
    >
      <i v-if="iconMap[item]" :class="iconMap[item] + ' mr-2'"></i>
      {{ item }}
    </v-tab>
  </v-tabs>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  items: {
    type: Array,
    required: true,
  },
});

const iconMap = {
  Product: "fa-solid fa-bag-shopping",
  Dog: "fas fa-dog",
  Cat: "fas fa-cat",
  "Small Pet": "fas fa-otter",
  // Bird: "fas fa-dove",
  // Aquatic: "fas fa-fish",
  "+ Vet": "fas fa-user-md",
  "Top Brands": "fas fa-star",
  "Special Offers": "fas fa-tags",
  Appointment: "fa-solid fa-calendar-check",
  Forum: "fa-solid fa-comment",
};
</script>

<style scoped>
i {
  font-size: 16px;
  vertical-align: middle;
  color: #444;
}
</style>
