<template>
    <v-card class="mx-auto my-4 hover-card" max-width="374" @click="goToDetail">
      <v-img :src="product.productPhoto" height="250" cover class="product-image" />
  
      <v-card-item>
        <v-card-title class="text-truncate">{{ product.productName }}</v-card-title>
        <v-card-subtitle>{{ product.categoryName }}</v-card-subtitle>
      </v-card-item>
  
      <v-card-text>
        <div class="text-subtitle-1 font-weight-bold">NT$ {{ product.productPrice }}</div>
        <v-rating :model-value="product.averageRating" color="amber" readonly dense />
        <div class="text-caption text-grey">{{ product.totalReview }} 則評價</div>
        <div class="text-body-2 mt-1 product-desc">
          {{ truncatedDescription }}
        </div>
      </v-card-text>
  
      <v-card-actions>
        <v-btn color="green" variant="text" block @click.stop="addToCart">加入購物車</v-btn>
      </v-card-actions>
  
      <v-snackbar v-model="showSnackbar" :timeout="2000" color="green" location="bottom right">
        已將「{{ product.productName }}」加入購物車！
      </v-snackbar>
    </v-card>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue'
  import { useRouter } from 'vue-router'
  
  const props = defineProps({
    product: Object
  })
  
  const emit = defineEmits(['add-to-cart'])
  
  const router = useRouter()
  const showSnackbar = ref(false)
  
  function addToCart() {
    emit('add-to-cart', {
      id: props.product.productId,
      name: props.product.productName,
      price: props.product.productPrice,
      quantity: 1
    })
    showSnackbar.value = true
  }
  
  function goToDetail() {
    router.push(`/products/${props.product.productId}`)
  }
  
  const truncatedDescription = computed(() => {
    const text = props.product.productDes || ''
    return text.length > 60 ? text.slice(0, 60) + '...' : text
  })
  </script>
  
  <style scoped>
  .hover-card {
    transition: transform 0.3s, box-shadow 0.3s;
    cursor: pointer;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  .hover-card:hover {
    transform: translateY(-4px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
  }
  .product-image {
    transition: transform 0.3s ease;
  }
  .hover-card:hover .product-image {
    transform: scale(1.05);
  }
  .product-desc {
    min-height: 48px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    line-clamp: 2;
    -webkit-box-orient: vertical;
  }
  </style>
  