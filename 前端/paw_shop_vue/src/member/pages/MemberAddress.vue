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

    <v-row v-else>
      <v-col
        v-for="address in addresses"
        :key="address.addressId"
        cols="12"
        md="6"
      >
        <AddressCard
          :address="address"
          @edit="editAddress"
          @delete="deleteAddress"
        />
      </v-col>
    </v-row>

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

const authStore = useAuthStore();

const addresses = ref([]);
const dialog = ref(false);
const dialogMode = ref("add"); // or 'edit'
const editedAddress = ref(null);

const fetchAddresses = async () => {
  const data = await api.apiFindAddress(authStore.memberId);
  addresses.value = data;
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
