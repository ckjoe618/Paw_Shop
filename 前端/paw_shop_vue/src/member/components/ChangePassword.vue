<template>
  <v-container>
    <v-row class="mb-4">
      <v-col cols="12">
        <h4>修改密碼</h4>
      </v-col>
    </v-row>
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
      <div class="text-center mt-6">
        <v-btn
          color="primary"
          prepend-icon="mdi-lock-reset"
          :disabled="!isValid"
          :loading="loading"
          @click="changePassword"
          >修改密碼</v-btn
        >
        <v-btn
          color="success"
          class="font-weight-bold text-white mx-2"
          @click="oneClick"
        >
          <v-icon start>mdi-flash</v-icon>
          一鍵輸入
        </v-btn>
      </div>
    </v-form>
  </v-container>
</template>

<script setup>
import { ref } from "vue";
import { useAuthStore } from "@/member/stores/auth";
import * as api from "@/api/memberApi/UserApi";
import Swal from "sweetalert2";

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
    Swal.fire({
      icon: "success",
      title: "修改完成",
      showConfirmButton: false,
      timer: 1000,
    });
    setTimeout(() => {
      authStore.logout();
      window.location.href = "/login";
    }, 1000);
  } finally {
    loading.value = false;
  }
};

const oneClick = () => {
  form.value = {
    memberId: authStore.memberId,
    password: "123456",
    newPassword: "123456789",
    confirmPassword: "123456789",
  };
};
</script>
