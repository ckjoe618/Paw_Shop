<template>
  <v-container class="d-flex justify-center mt-10">
    <v-card width="500" class="pa-6">
      <v-card-title class="text-h5">重設密碼</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="submit" ref="formRef" v-model="isValid">
          <v-text-field
            v-model="password"
            label="新密碼"
            type="password"
            prepend-icon="mdi-lock"
            :rules="[rules.required, rules.password]"
          ></v-text-field>

          <v-text-field
            v-model="confirmPassword"
            label="確認新密碼"
            type="password"
            prepend-icon="mdi-lock-check"
            :rules="[rules.required, rules.matchPassword]"
          ></v-text-field>

          <v-btn
            type="submit"
            color="primary"
            :loading="loading"
            :disabled="!isValid"
            block
          >
            重設密碼
          </v-btn>

          <v-alert v-if="message" :type="messageType" class="mt-4" dismissible>
            {{ message }}
          </v-alert>
        </v-form>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import * as api from "@/member/api/memberApi/UserApi";

const isValid = ref(false);
const formRef = ref(null);
const password = ref("");
const confirmPassword = ref("");
const loading = ref(false);
const message = ref("");
const messageType = ref("success");

const route = useRoute();
const router = useRouter();
const token = ref("");

onMounted(() => {
  token.value = route.query.token || "";
  if (!token.value) {
    message.value = "錯誤的連結，缺少驗證資訊";
    messageType.value = "error";
  }
});

const rules = {
  required: (v) => !!v || "此欄位必填",
  password: (v) => v.length >= 6 || "密碼至少要 6 個字元",
  matchPassword: (v) => v === password.value || "兩次輸入的密碼不一致",
};

const submit = async () => {
  const { valid } = await formRef.value.validate();
  if (!valid || !token.value) {
    return;
  }
  loading.value = true;
  try {
    await api.apiResetPassword({
      token: token.value,
      password: password.value,
    });
    message.value = "✅ 密碼重設成功，請重新登入";
    messageType.value = "success";

    setTimeout(() => {
      router.push("/login"); // 自動跳轉到登入頁
    }, 2000);
  } catch (error) {
    message.value = "❌ 密碼重設失敗，請稍後再試";
    messageType.value = "error";
  } finally {
    loading.value = false;
  }
};
</script>
