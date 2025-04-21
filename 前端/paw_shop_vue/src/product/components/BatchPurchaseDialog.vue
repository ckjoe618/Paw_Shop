<template>
  <v-dialog v-model="dialog" max-width="500">
    <v-card>
      <v-card-title>批次進貨</v-card-title>
      <v-card-text>
        <v-form ref="form">
          <v-select
            v-model="selectedProductIds"
            :items="availableProducts"
            item-title="productName"
            item-value="productId"
            label="選擇商品"
            multiple
            return-object
            chips
            dense
          />
          <div v-for="(product, index) in selectedProductIds" :key="product.productId" class="mt-3">
            <v-text-field
              v-model.number="quantities[product.productId]"
              :label="`${product.productName} 的進貨數量`"
              type="number"
              min="1"
              required
              dense
            />
          </div>
        </v-form>
      </v-card-text>

      <v-card-actions>
        <v-spacer />
        <v-btn color="green" @click="submit">送出</v-btn>
        <v-btn text @click="dialog = false">取消</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import axios from 'axios'

const props = defineProps({
  modelValue: Boolean
})
const emit = defineEmits(['update:modelValue', 'success'])

const dialog = ref(props.modelValue)
watch(() => props.modelValue, val => dialog.value = val)
watch(dialog, val => {
  emit('update:modelValue', val)
  if (val) fetchProducts() // ✅ 每次開啟 dialog 時更新商品清單
})

const availableProducts = ref([])
const selectedProductIds = ref([])
const quantities = ref({})

function open() {
  dialog.value = true
  selectedProductIds.value = []
  quantities.value = {}
}
defineExpose({ open })

async function fetchProducts() {
  try {
    const res = await axios.get('http://localhost:8080/product/stock/available')
    availableProducts.value = res.data
  } catch (err) {
    console.error('無法載入商品清單', err)
  }
}

onMounted(fetchProducts)

async function submit() {
  const payload = selectedProductIds.value
    .map((product) => ({
      productId: product.productId,
      quantity: quantities.value[product.productId]
    }))
    .filter((item) => item.quantity && item.quantity > 0)

  if (!payload.length) {
    alert('請輸入有效的進貨數量')
    return
  }

  try {
    await axios.post('http://localhost:8080/product/batch-orders', payload)
    alert('批次進貨成功！')
    dialog.value = false
    emit('success')
  } catch (error) {
    alert('送出失敗')
    console.error(error)
  }
}
</script>