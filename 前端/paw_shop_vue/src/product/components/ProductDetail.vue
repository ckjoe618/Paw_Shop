<!-- ProductDetail.vue -->
<template>
    <v-dialog :model-value="visible" @update:modelValue="$emit('update:visible', $event)" max-width="600">
        <v-card>
            <v-card-title>商品詳情</v-card-title>
            <v-card-text>
                <v-img :src="product.productPhoto" max-height="200" cover />
                <p><strong>名稱：</strong>{{ product.productName }}</p>
                <p><strong>價格：</strong>{{ product.productPrice }}</p>
                <p><strong>分類：</strong>{{ product.categoryName }}</p>
                <p><strong>庫存：</strong>{{ product.productStock }}</p>
                <p><strong>描述：</strong>{{ product.productDes }}</p>
            </v-card-text>
            <v-card-actions>
                <v-spacer />
                <v-btn @click="$emit('update:visible', false)">關閉</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup>
const props = defineProps({
    visible: Boolean,
    product: Object,
});
const emit = defineEmits(['update:visible']);
</script>

<style scoped></style>