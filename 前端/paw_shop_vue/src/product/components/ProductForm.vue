<template>
    <v-form @submit.prevent="submitForm">
        <v-text-field v-model="product.productName" label="商品名稱" variant="solo" required />

        <v-text-field v-model.number="product.productPrice" label="商品價格" type="number" variant="solo" required />

        <v-text-field v-model.number="product.productStock" label="商品庫存" type="number" variant="solo" required />

        <v-text-field v-model="product.productDes" label="商品描述" variant="solo" required />

        <v-select v-model="product.categoryName" :items="categories" label="商品分類" variant="solo" required />

        <v-text-field v-model="product.productPhoto" label="圖片網址" variant="solo" />

        <div class="d-flex justify-end">
            <v-btn @click="$emit('cancel')" color="grey" class="me-2">取消</v-btn>
            <v-btn @click="submitForm" color="primary">送出</v-btn>
        </div>
    </v-form>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';

const props = defineProps({
    productData: Object,
});

const emit = defineEmits(['success', 'cancel']);

const product = ref({
    productName: '',
    productPrice: 0,
    productStock: 0,
    productDes: '',
    categoryName: '',
    productPhoto: '',
});

const categories = [
    'food',
    'supplies',
    'supplements',
    'pet_snacks',
    'clean',
    'catlitterseries',
];

watch(
    () => props.productData,
    (newData) => {
        if (newData) {
            product.value = { ...newData };
        } else {
            // 若為 null，則重置表單（新增用）
            product.value = {
                productName: '',
                productPrice: 0,
                productStock: 0,
                productDes: '',
                categoryName: '',
                productPhoto: '',
            };
        }
    },
    { immediate: true } // 一進來就執行一次
);

const submitForm = async () => {
    try {
        if (product.value.productId) {
            await axios.put(`http://localhost:8080/product/${product.value.productId}`, product.value);
        } else {
            await axios.post('http://localhost:8080/product', product.value);
        }
        emit('success');
    } catch (error) {
        console.error('儲存失敗', error);
    }
};
</script>

<style scoped></style>