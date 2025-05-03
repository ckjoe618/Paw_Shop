<template>
  <v-card flat tile class="pa-0" style="position: sticky; top: 140px; z-index: 1;">
    <!-- 標題 -->
    <div class="text-h6 font-weight-bold px-4 pt-4 pb-2">
      分類
    </div>
    <v-divider class="mx-4"></v-divider>

    <!-- 分類選單 -->
    <v-list nav dense class="px-2">
      <v-list-item
        :value="null"
        @click="selectCategory(null)"
        :class="{ 'bg-grey-lighten-3': selectedCategory === null }"
        class="rounded"
      >
        <template v-slot:prepend>
          <v-icon v-if="selectedCategory === null" size="small" color="green">mdi-check-circle</v-icon>
        </template>
        <v-list-item-title>全部</v-list-item-title>
      </v-list-item>

      <v-list-item
        v-for="cat in categoryOptions"
        :key="cat.value"
        :value="cat.value"
        @click="selectCategory(cat.value)"
        :class="{ 'bg-grey-lighten-3': selectedCategory === cat.value }"
        class="rounded"
      >
        <template v-slot:prepend>
          <v-icon v-if="selectedCategory === cat.value" size="small" color="green">mdi-check-circle</v-icon>
        </template>
        <v-list-item-title>{{ cat.label }}</v-list-item-title>
      </v-list-item>
    </v-list>

    <!-- 價格範圍 -->
    <v-divider class="my-4"></v-divider>
    <div class="px-4 pb-4">
      <div class="text-subtitle-2 font-weight-medium mb-2">價格區間</div>
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
import { ref, watchEffect } from 'vue'

const props = defineProps({
  modelValueCategory: String,
  modelValuePrice: Array
})

const emit = defineEmits(['update:modelValueCategory', 'update:modelValuePrice', 'filter-change'])

const categoryOptions = [
  { value: 'food',            label: '主食' },
  { value: 'supplies',        label: '生活用品&玩具' },
  { value: 'supplements',     label: '保健品' },
  { value: 'pet_snacks',      label: '副食&零食' },
  { value: 'clean',           label: '沐浴&潔淨' },
  { value: 'catlitterseries', label: '貓砂' }
]
const selectedCategory = ref(null)
const priceRange = ref([0, 5000])

// 同步 props
watchEffect(() => {
  selectedCategory.value = props.modelValueCategory
})
watchEffect(() => {
  priceRange.value = props.modelValuePrice
})

watchEffect(() => {
  emit('update:modelValueCategory', selectedCategory.value)
  emit('update:modelValuePrice', priceRange.value)
  emit('filter-change', {
    category: selectedCategory.value,
    price: priceRange.value
  })
})

function selectCategory(category) {
  selectedCategory.value = selectedCategory.value === category ? null : category
}
</script>

<style scoped>
</style>
