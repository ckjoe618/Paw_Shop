<!-- src/product/components/frontsite/ProductCard.vue -->
<template>
    <v-card :loading="loading" class="mx-auto my-6" max-width="374">
        <template v-slot:loader="{ isActive }">
            <v-progress-linear :active="isActive" color="green" height="4" indeterminate></v-progress-linear>
        </template>

        <v-img :src="product.productPhoto" height="250" cover></v-img>

        <v-card-item>
            <v-card-title class="text-truncate">{{ product.productName }}</v-card-title>
            <v-card-subtitle>{{ product.categoryName }}</v-card-subtitle>
        </v-card-item>

        <v-card-text>
            <div class="text-subtitle-1 font-weight-bold">NT$ {{ product.productPrice }}</div>

            <v-rating :model-value="product.averageRating" color="amber" density="compact" size="small" half-increments
                readonly class="mt-2"></v-rating>

            <div class="text-grey text-caption mt-1">
                {{ product.totalReview }} 則評價
            </div>

            <div class="mt-2 text-body-2">
                {{ product.productDes }}
            </div>
        </v-card-text>

        <v-divider class="mx-4 mb-2"></v-divider>

        <v-card-actions>
            <v-btn color="green" text="加入購物車" block @click="addToCart"></v-btn>
        </v-card-actions>
    </v-card>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
    product: Object
})

const loading = ref(false)

function addToCart() {
    loading.value = true
    // 實際開發可改為 emit 或接後端購物車 API
    setTimeout(() => {
        loading.value = false
        alert(`已將「${props.product.productName}」加入購物車！`)
    }, 1000)
}
</script>