<template>
    <v-card flat tile class="pa-0" style="position: sticky; top: 140px; z-index: 1;">
      <!-- 標題 -->
      <div class="text-h6 font-weight-bold px-4 pt-4 pb-2">
        Category
      </div>
      <v-divider class="mx-4"></v-divider>
  
      <!-- 分類選單 -->
      <v-list nav dense class="px-2">
        <!-- 全部 -->
        <v-list-item
          :value="null"
          @click="selectCategory(null)"
          :class="{ 'bg-grey-lighten-3': selectedCategory === null }"
          class="rounded"
        >
          <template v-slot:prepend>
            <v-icon v-if="selectedCategory === null" size="small" color="green">mdi-check-circle</v-icon>
          </template>
          <v-list-item-title>All</v-list-item-title>
        </v-list-item>
  
        <!-- 其他分類 -->
        <v-list-item
          v-for="category in categories"
          :key="category"
          :value="category"
          @click="selectCategory(category)"
          :class="{ 'bg-grey-lighten-3': selectedCategory === category }"
          class="rounded"
        >
          <template v-slot:prepend>
            <v-icon
              v-if="selectedCategory === category"
              size="small"
              color="green"
            >mdi-check-circle</v-icon>
          </template>
          <v-list-item-title class="text-capitalize">{{ category }}</v-list-item-title>
        </v-list-item>
      </v-list>
  
      <!-- 價格範圍 -->
      <v-divider class="my-4"></v-divider>
      <div class="px-4 pb-4">
        <div class="text-subtitle-2 font-weight-medium mb-2">Price</div>
        <v-range-slider
          v-model="priceRange"
          :min="0"
          :max="5000"
          :step="100"
          thumb-label
          hide-details
          class="mt-0"
        />
        <div class="text-caption text-center">NT${{ priceRange[0] }} ~ NT${{ priceRange[1] }}</div>
      </div>
    </v-card>
  </template>
  
  <script setup>
  import { ref, watch } from 'vue'
  
  const emit = defineEmits(['filter-change'])
  
  const categories = ['food', 'supplies', 'supplements', 'pet_snacks', 'clean', 'catlitterseries']
  const selectedCategory = ref(null)
  const priceRange = ref([0, 5000])
  
  watch([selectedCategory, priceRange], () => {
    emit('filter-change', {
      category: selectedCategory.value,
      price: priceRange.value
    })
  })
  
  function selectCategory(category) {
    selectedCategory.value = category
  }
  </script>
  
  <style scoped>
  </style>