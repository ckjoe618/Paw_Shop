<template>
  <v-form ref="formRef" v-model="isValid">
    <v-text-field
      label="舊密碼"
      v-model="form.password"
      :type="showPassword ? 'text' : 'password'"
      :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
      @click:append="showPassword = !showPassword"
      :rules="[rules.required]"
    />
    <v-text-field
      label="新密碼"
      v-model="form.newPassword"
      :type="showPassword ? 'text' : 'password'"
      :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
      @click:append="showPassword = !showPassword"
      :rules="[rules.required, rules.password]"
    />
    <v-text-field
      label="確認新密碼"
      v-model="form.confirmPassword"
      :type="showPassword ? 'text' : 'password'"
      :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
      @click:append="showPassword = !showPassword"
      :rules="[rules.required, rules.matchPassword]"
    />
    <v-btn
      color="primary"
      :disabled="!isValid"
      :loading="loading"
      @click="changePassword"
      >修改密碼</v-btn
    >
  </v-form>
</template>

<script setup>
import { ref } from "vue";
import { useAuthStore } from "@/member/stores/auth";
import * as api from "@/api/memberApi/UserApi";

const authStore = useAuthStore();
const formRef = ref(null);
const isValid = ref(false);
const loading = ref(false);
const showPassword = ref(false);

const form = ref({
  memberId: authStore.memberId,
  password: "",
  newPassword: "",
  confirmPassword: "",
});

const rules = {
  required: (v) => !!v || "必填",
  password: (v) => v.length >= 6 || "至少6個字元",
  matchPassword: (v) => v === form.value.newPassword || "密碼不一致",
};

const changePassword = async () => {
  const valid = await formRef.value.validate();
  if (!valid) {
    return;
  }
  loading.value = true;
  try {
    await api.apiUpdatePassword(form.value);
    // 可以做自動登出或跳回登入頁
  } finally {
    loading.value = false;
  }
};
</script>
