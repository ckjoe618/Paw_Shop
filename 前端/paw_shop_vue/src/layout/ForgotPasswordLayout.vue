<template>
  <v-container class="d-flex justify-center mt-10">
    <v-card width="500" class="pa-6">
      <v-card-title class="text-h5">忘記密碼</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="submit" ref="formRef" v-model="isValid">
          <v-text-field
            v-model="email"
            label="請輸入 Email"
            prepend-icon="mdi-email"
            :rules="[rules.required, rules.email]"
          ></v-text-field>
          <v-btn
            type="submit"
            color="primary"
            :loading="loading"
            :disabled="!isValid"
            block
            >發送重設連結</v-btn
          >
        </v-form>
        <v-alert v-if="message" type="success" class="mt-4">{{
          message
        }}</v-alert>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref } from "vue";
import * as api from "@/api/memberApi/UserApi";

const isValid = ref(false);
const loading = ref(false);
const formRef = ref(null);
const email = ref("");
const message = ref("");

const rules = {
  required: (v) => !!v || "此欄位必填",
  email: (v) => /.+@.+\..+/.test(v) || "Email 格式錯誤",
};

const submit = async () => {
  const { valid } = await formRef.value.validate();
  if (!valid) {
    return;
  }
  loading.value = true;
  try {
    await api.apiForgotPassword({
      email: email.value,
    });
    message.value = "請查收您的 Email，內含密碼重設連結";
  } catch (error) {
    message.value = "操作失敗，請確認 Email 是否正確";
  } finally {
    loading.value = false;
  }
};
</script>
