<template>
  <v-container class="my-6">
    <v-row justify="center">
      <v-col
        v-for="category in categories"
        :key="category.name"
        cols="6"
        sm="2"
        class="text-center"
      >
        <v-avatar size="80" class="mb-2" color="grey lighten-4">
          <i
            :class="iconMap[category.name]"
            class="fa-2x"
            style="line-height: 80px"
          ></i>
        </v-avatar>
        <div>{{ category.name }}</div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  categories: {
    type: Array,
    required: true,
  },
});

const iconMap = {
  Cat: "fas fa-cat",
  Dog: "fas fa-dog",
  "Small Pet": "fas fa-otter",
  Fish: "fas fa-fish",
  Bird: "fas fa-dove",
};
</script>

<style scoped>
i {
  color: #4caf50;
  text-align: center;
}
</style>
