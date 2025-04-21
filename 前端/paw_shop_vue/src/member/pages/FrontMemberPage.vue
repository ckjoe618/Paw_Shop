<template>
  <v-container class="pt-6">
    <v-card elevation="4" rounded="lg" class="pa-6">
      <v-card-title class="text-h6 mb-4">
        <v-icon class="mr-2">mdi-account</v-icon>
        會員基本資料
      </v-card-title>

      <!-- 🖼️ 頭像區塊 -->
      <div class="text-center mb-6">
        <v-avatar size="120" class="mx-auto">
          <v-img :src="previewPhoto" cover />
        </v-avatar>
        <v-btn class="mt-3" color="primary" @click="$refs.fileInput.click()">
          上傳頭像
        </v-btn>
        <input
          ref="fileInput"
          type="file"
          accept="image/*"
          hidden
          @change="handleFileChange"
        />
      </div>

      <!-- 📝 表單 -->
      <v-form ref="formRef" v-model="isValid">
        <v-row>
          <v-col cols="12" md="6">
            <v-text-field
              label="姓名"
              v-model="form.memberName"
              :rules="[rules.required]"
            />
          </v-col>
          <v-col cols="12" md="6">
            <v-text-field label="Email" v-model="form.email" disabled />
          </v-col>
          <v-col cols="12" md="6">
            <v-text-field
              label="電話"
              v-model="form.phone"
              :rules="[rules.required]"
            />
          </v-col>
        </v-row>
        <v-btn
          color="primary"
          prepend-icon="mdi-content-save"
          class="mt-4"
          :disabled="!isValid"
          :loading="loading"
          @click="saveProfile"
        >
          儲存變更
        </v-btn>
      </v-form>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useAuthStore } from "@/member/stores/auth";
import * as api from "@/member/api/memberApi/UserApi.js";

const authStore = useAuthStore();
const formRef = ref(null);
const isValid = ref(false);
const loading = ref(false);
const uploadFile = ref(null);
const previewPhoto = ref("");

const form = ref({
  memberId: authStore.memberId,
  memberName: authStore.memberName,
  email: authStore.email,
  phone: authStore.phone,
  gender: authStore.gender,
});

const rules = {
  required: (v) => !!v || "欄位為必填",
};

const handleFileChange = (e) => {
  const file = e.target.files[0];
  if (file) {
    if (previewPhoto.value.startsWith("blob:")) {
      URL.revokeObjectURL(previewPhoto.value);
    }
    previewPhoto.value = URL.createObjectURL(file);
    uploadFile.value = file;
  }
};

const saveProfile = async () => {
  const valid = await formRef.value.validate();
  if (!valid) {
    return;
  }
  loading.value = true;
  try {
    const data = await api.apiUpdateMember(form.value, uploadFile.value);
    // 更新 Pinia 內容
    authStore.memberName = data.memberName;
    authStore.phone = data.phone;
    if (data.memberPhoto) {
      authStore.memberPhoto = data.memberPhoto;
      previewPhoto.value = data.memberPhoto;
    }
    uploadFile.value = null;
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  previewPhoto.value = authStore.memberPhoto;
});
</script>
