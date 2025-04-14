<template>
  <v-app>
    <!-- Header -->
    <v-app-bar app color="green darken-3" dark>
      <v-toolbar-title class="text-h5">zooplus</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-inner-icon="mdi-magnify"
        label="Search the store"
        single-line
        hide-details
        dense
        class="ma-2"
      ></v-text-field>
      <v-btn icon><v-icon>mdi-cart</v-icon></v-btn>
      <v-btn icon><v-icon>mdi-account</v-icon></v-btn>
    </v-app-bar>

    <!-- Nav Tabs -->
    <v-tabs background-color="grey lighten-4" grow>
      <v-tab v-for="item in navItems" :key="item">{{ item }}</v-tab>
    </v-tabs>

    <!-- Carousel -->
    <v-container class="mt-4">
      <v-carousel height="200">
        <v-carousel-item v-for="(slide, i) in 3" :key="i">
          <v-sheet
            class="fill-height d-flex justify-center align-center"
            color="green lighten-3"
          >
            <h3 class="text-h5">Banner Slide {{ i + 1 }}</h3>
          </v-sheet>
        </v-carousel-item>
      </v-carousel>
    </v-container>

    <!-- Category Icons -->
    <v-container class="my-6">
      <v-row justify="center">
        <v-col
          v-for="pet in petCategories"
          :key="pet.name"
          cols="6"
          sm="2"
          class="text-center"
        >
          <v-avatar size="80" class="mb-2">
            <v-img :src="pet.image" />
          </v-avatar>
          <div>{{ pet.name }}</div>
        </v-col>
      </v-row>
    </v-container>

    <!-- Product Recommendations -->
    <v-container>
      <h3 class="text-h6 font-weight-medium mb-4">Top Recommendations</h3>
      <v-slide-group show-arrows>
        <v-slide-item v-for="(item, index) in 6" :key="index">
          <v-card class="mx-2" width="150">
            <v-img src="https://via.placeholder.com/150" height="120" />
            <v-card-title class="text-subtitle-2"
              >Product {{ index + 1 }}</v-card-title
            >
            <v-card-subtitle>€{{ (index + 1) * 5 }}.99</v-card-subtitle>
            <v-rating :value="5" dense readonly size="14"></v-rating>
          </v-card>
        </v-slide-item>
      </v-slide-group>
    </v-container>

    <!-- Footer -->
    <v-footer color="green darken-4" dark class="mt-10 pa-6">
      <v-container>
        <v-row>
          <v-col cols="12" sm="6">&copy; 2025 zooplus</v-col>
          <v-col cols="12" sm="6" class="text-sm-right">
            <v-btn text small>About</v-btn>
            <v-btn text small>Help</v-btn>
            <v-btn text small>Contact</v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-footer>
  </v-app>
</template>

<script setup>
import { ref } from "vue";

const search = ref("");
const navItems = [
  "Dog",
  "Cat",
  "Small Pet",
  "Bird",
  "Aquatic",
  "+ Vet",
  "Top Brands",
  "Special Offers",
];

const petCategories = [
  {
    name: "Cat",
    image: "https://cdn-icons-png.flaticon.com/512/616/616408.png",
  },
  {
    name: "Dog",
    image: "https://cdn-icons-png.flaticon.com/512/616/616408.png",
  },
  {
    name: "Small Pet",
    image: "https://cdn-icons-png.flaticon.com/512/616/616408.png",
  },
  {
    name: "Fish",
    image: "https://cdn-icons-png.flaticon.com/512/616/616408.png",
  },
  {
    name: "Bird",
    image: "https://cdn-icons-png.flaticon.com/512/616/616408.png",
  },
];
</script>

<style scoped>
.v-carousel-item {
  color: white;
}
</style>
