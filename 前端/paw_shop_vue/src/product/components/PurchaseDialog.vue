
<template>
    <v-dialog v-model="dialog" max-width="500">
      <v-card>
        <v-card-title class="text-h6">📦 進貨商品</v-card-title>
        <v-card-text>
          <v-form @submit.prevent="submitPurchase">
            <v-select
              v-model="selectedId"
              :items="productOptions"
              item-title="label"
              item-value="value"
              label="選擇商品 ID"
              required
            />
            <v-text-field
              v-model="quantity"
              type="number"
              label="進貨數量"
              min="1"
              required
            />
          </v-form>
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn text @click="dialog = false">取消</v-btn>
          <v-btn color="green" @click="submitPurchase">確認進貨</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script setup>
  import { ref, watch } from 'vue'
  import axios from 'axios'
  
  const props = defineProps({
    dialog: Boolean
  })
  const emit = defineEmits(['update:dialog'])
  
  const dialog = ref(props.dialog)
  watch(() => props.dialog, (val) => (dialog.value = val))
  watch(dialog, (val) => {emit('update:dialog', val)
                         if (val) fetchProducts()   // ✅ 加這一行，每次打開就重新撈商品                          
       })
  
  const selectedId = ref(null)
  const quantity = ref(1)
  
  const productOptions = ref([])
  
  async function fetchProducts() {
    try {
      const response = await axios.get('http://localhost:8080/product/stock/available')
      productOptions.value = response.data.map((p) => ({
        label: `#${p.productId} - ${p.productName}`,
        value: p.productId
      }))
    } catch (err) {
      console.error('載入商品清單失敗:', err)
    }
  }
  
  function submitPurchase() {
    if (!selectedId.value || quantity.value <= 0) {
      alert('請輸入有效的商品 ID 與數量')
      return
    }
  
    axios.post(
      `http://localhost:8080/product/orders?productId=${selectedId.value}&quantity=${quantity.value}`
    )
      .then(() => {
        alert('進貨成功')
        emit('success')
        dialog.value = false
        selectedId.value = null
        quantity.value = 1
      })
      .catch(() => {
        alert('進貨失敗')
      })
  }
  
  fetchProducts()
  </script>
  