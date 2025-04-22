<template>
  <v-tabs background-color="grey lighten-4" grow hide-slider>
    <v-tab
      v-for="item in items"
      :key="item.label"
      class="text-subtitle-2 text-capitalize"
      :to="item.link"
      replace
    >
      <i v-if="iconMap[item.label]" :class="iconMap[item.label] + ' mr-2'"></i>
      {{ item.label }}
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

