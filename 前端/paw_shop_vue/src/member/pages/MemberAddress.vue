<template>
  <v-container>
    <v-row class="mb-4">
      <v-col cols="12" class="d-flex justify-space-between align-center">
        <h4>地址管理</h4>
        <v-btn color="primary" @click="openAddDialog">新增地址</v-btn>
      </v-col>
    </v-row>

    <v-row v-if="addresses.length === 0">
      <v-col class="text-center">
        <p>目前尚無地址資料</p>
      </v-col>
    </v-row>

    <draggable
      v-else
      v-model="addresses"
      @end="onDragEnd"
      item-key="addressId"
      handle=".drag-handle"
      animation="300"
    >
      <template #item="{ element, index }">
        <v-col cols="12" md="6">
          <v-sheet class="pa-2 draggable-card">
            <div class="d-flex justify-space-between align-center">
              <div class="d-flex align-center">
                <!-- 拖曳Icon -->
                <v-icon class="mr-3 drag-handle" size="24">mdi-drag</v-icon>

                <!-- 地址卡片 -->
                <AddressCard
                  :address="element"
                  :is-default="index === 0"
                  @edit="editAddress"
                  @delete="deleteAddress"
                  @set-default="setAsDefault"
                />
              </div>

              <!-- 第一筆才顯示預設 -->
              <v-chip
                v-if="index === 0"
                color="green"
                text-color="white"
                label
                small
              >
                預設
              </v-chip>
            </div>
          </v-sheet>
        </v-col>
      </template>
    </draggable>

    <AddressEditDialog
      v-model:dialog="dialog"
      :address="editedAddress"
      :mode="dialogMode"
      @saved="fetchAddresses"
    />
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useAuthStore } from "@/member/stores/auth";
import * as api from "@/member/api/memberApi/UserApi";
import AddressCard from "@/member/components/AddressCard.vue";
import AddressEditDialog from "@/member/components/AddressEditDialog.vue";
import draggable from "vuedraggable";

const authStore = useAuthStore();
const addresses = ref([]);
const dialog = ref(false);
const dialogMode = ref("add");
const editedAddress = ref(null);

const fetchAddresses = async () => {
  const data = await api.apiFindAddress(authStore.memberId);
  addresses.value = data;
};

// 拖曳完更新預設地址
const onDragEnd = async () => {
  if (addresses.value.length > 0) {
    const newDefaultId = addresses.value[0].addressId;
    await api.apiUpdateDefaultAddress(newDefaultId);
    // 直接標記第一筆
    addresses.value = addresses.value.map((address, index) => ({
      ...address,
      defaultStatus: index === 0,
    }));
  }
};

// 點選設為預設
const setAsDefault = async (address) => {
  await api.apiUpdateDefaultAddress(address.addressId);
  // 把被選中的那筆移到陣列最前面
  addresses.value = [
    address,
    ...addresses.value.filter((item) => item.addressId !== address.addressId),
  ].map((addr, index) => ({
    ...addr,
    defaultStatus: index === 0,
  }));
};

const openAddDialog = () => {
  dialogMode.value = "add";
  editedAddress.value = {
    memberId: authStore.memberId,
    recipientName: "",
    phone: "",
    zipcode: "",
    city: "",
    district: "",
    addressDetail: "",
  };
  dialog.value = true;
};

const editAddress = (address) => {
  dialogMode.value = "edit";
  editedAddress.value = { ...address };
  dialog.value = true;
};

const deleteAddress = async (address) => {
  if (confirm("確定要刪除這筆地址嗎？")) {
    await api.apiDeleteAddress(address.addressId);
    fetchAddresses();
  }
};

onMounted(() => {
  fetchAddresses();
});
</script>

<style scoped>
.draggable-card {
  cursor: grab;
  transition: box-shadow 0.2s;
}
.draggable-card:active {
  cursor: grabbing;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}
.drag-handle {
  cursor: grab;
  color: #aaa;
}
.drag-handle:active {
  cursor: grabbing;
}
</style>
