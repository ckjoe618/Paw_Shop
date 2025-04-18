<!-- ProductDeleteDialog.vue -->
<template>
    <v-dialog :model-value="visible" @update:modelValue="$emit('update:visible', $event)" max-width="400">
        <v-card>
            <v-card-title class="text-h6">確認刪除</v-card-title>
            <v-card-text>您確定要刪除此商品嗎？</v-card-text>
            <v-card-actions>
                <v-spacer />
                <v-btn color="grey" @click="$emit('update:visible', false)">取消</v-btn>
                <v-btn color="red" @click="$emit('confirm')">刪除</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup>
const props = defineProps({
    visible: Boolean,
    productId: Number,
});
const emit = defineEmits(['update:visible', 'confirm']);
</script>

<style scoped></style>