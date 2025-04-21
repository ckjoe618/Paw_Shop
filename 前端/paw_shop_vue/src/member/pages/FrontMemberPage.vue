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
          <v-img :src="form.memberPhoto" cover />
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
            <v-text-field label="性別" v-model="form.gender" disabled />
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
const form = ref({
  memberId: authStore.memberId,
  memberName: authStore.memberName,
  email: authStore.email,
  phone: authStore.phone,
  gender: authStore.gender,
  memberPhoto: authStore.memberPhoto,
});

const rules = {
  required: (v) => !!v || "欄位為必填",
};
</script>
