<template>
    <v-container>
        <v-row align="center" class="d-flex" style="gap: 16px">
            <h2 class="mb-0">所有商品</h2>
            <addbtn icon-size="25" @click="showCreateDialog = true" />
        </v-row>

        <v-row class="mt-4 align-center">
            <!-- 商品名稱輸入 -->
            <v-col cols="12" sm="4">
                <v-text-field v-model="filterName" label="輸入商品名稱" prepend-icon="mdi-tag" variant="solo"
                    @input="filterProducts" />
            </v-col>

            <!-- 商品分類選擇 -->
            <v-col cols="12" sm="4">
                <v-select :items="categoryOptions" label="選擇分類" variant="solo" prepend-icon="mdi-format-list-bulleted"
                    v-model="filterCategory" @update:modelValue="filterProducts" />
            </v-col>

            <!-- 清除按鈕 -->
            <v-col cols="12" sm="4" class="d-flex justify-end align-center">
                <v-btn icon variant="plain" size="28" @click="clearFilter"
                    style="box-shadow: none; min-width: 0; height: 40px">
                    <v-icon size="24">mdi-close</v-icon>
                </v-btn>
            </v-col>

            <!-- ✅ 新增這個 checkbox -->
            <v-col cols="12" sm="4">
                <v-checkbox label="顯示已刪除商品" v-model="showDeleted" density="compact" @change="reloadProducts" />
            </v-col>
        </v-row>

        <v-data-table :headers="headers" :items="filteredProducts" item-value="productId" class="elevation-1"
            items-per-page-text="每頁顯示筆數：">
            <template #item.actions="{ item }">
                <div class="d-flex align-center" style="gap: 8px">
                    <formCheckbtn @click="openDetailDialog(item)" />
                    <formEditbtn @click="openEditDialog(item.productId)" />
                    <formDeletebtn @click="confirmDelete(item.productId)" />
                </div>
            </template>
        </v-data-table>

        <v-dialog v-model="showCreateDialog" max-width="600px">
            <v-card>
                <v-card-title>{{ editingProductId ? '編輯商品' : '新增商品' }}</v-card-title>
                <v-card-text>
                    <ProductForm :product-data="editingProductData" @success="onProductSaved"
                        @cancel="showCreateDialog = false" />
                </v-card-text>
            </v-card>
        </v-dialog>

        <ProductDeleteDialog :product-id="deletingProductId" v-model:visible="showDeleteDialog"
            @confirm="deleteProduct" />
        <ProductDetail :product="selectedProduct" v-model:visible="showDetailDialog" />
    </v-container>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';

import addbtn from '../../order/components/buttons/addbtn.vue';
import formCheckbtn from '../../order/components/buttons/formCheckbtn.vue';
import formEditbtn from '../../order/components/buttons/formEditbtn.vue';
import formDeletebtn from '../../order/components/buttons/formDeletebtn.vue';

import ProductForm from "@/product/components/ProductForm.vue";
import ProductDeleteDialog from "@/product/components/ProductDeleteDialog.vue";
import ProductDetail from "@/product/components/ProductDetail.vue";

const headers = [
    { title: "商品編號", key: "productId", width: "110px" },
    { title: "商品名稱", key: "productName", width: "200px" },
    { title: "價格", key: "productPrice", width: "90px" },
    { title: "庫存", key: "productStock", width: "90px" },
    { title: "分類", key: "categoryName", width: "120px" },
    { title: "操作", key: "actions", sortable: false, width: "140px" },
];

const products = ref([]);
const filteredProducts = ref([]);

const filterName = ref('');
const filterCategory = ref('');
const categoryOptions = ['food', 'supplies', 'supplements', 'pet_snacks', 'clean', 'catlitterseries'];

const reloadProducts = async () => {
    const url = showDeleted.value
        ? 'http://localhost:8080/product'
        : 'http://localhost:8080/product/available';

    const res = await axios.get(url);
    products.value = res.data;
    filterProducts();
};

const filterProducts = () => {
    filteredProducts.value = products.value.filter(p => {
        const matchName = filterName.value ? p.productName.includes(filterName.value) : true;
        const matchCategory = filterCategory.value ? p.categoryName === filterCategory.value : true;
        return matchName && matchCategory;
    });
};

const clearFilter = () => {
    filterName.value = '';
    filterCategory.value = '';
    filterProducts();
};

const showCreateDialog = ref(false);
const editingProductId = ref(null);
const onProductSaved = () => {
    showCreateDialog.value = false;
    reloadProducts();
};
const openEditDialog = (id) => {
    editingProductData.value = filteredProducts.value.find(p => p.productId === id);
    showCreateDialog.value = true;
};
watch(showCreateDialog, val => {
    if (!val) setTimeout(() => (editingProductId.value = null), 500);
});

const showDeleteDialog = ref(false);
const deletingProductId = ref(null);
const confirmDelete = id => {
    deletingProductId.value = id;
    showDeleteDialog.value = true;
};
const deleteProduct = async () => {
    await axios.delete(`http://localhost:8080/product/${deletingProductId.value}`);
    showDeleteDialog.value = false;
    reloadProducts();
};

const editingProductData = ref(null);
const showDeleted = ref(false);
const selectedProduct = ref(null);
const showDetailDialog = ref(false);
const openDetailDialog = product => {
    selectedProduct.value = product;
    showDetailDialog.value = true;
};

watch(showCreateDialog, (val) => {
    if (!val) {
        setTimeout(() => {
            editingProductData.value = null;
        }, 500);
    }
});

onMounted(reloadProducts);
</script>

<style scoped></style>