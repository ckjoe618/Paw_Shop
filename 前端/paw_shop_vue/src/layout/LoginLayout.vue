<template>
  <v-container fluid class="fill-height d-flex justify-center align-center">
    <v-card class="pa-6" elevation="8" max-width="448" rounded="lg">
      <v-img class="mx-auto mb-6" max-width="120" :src="PawShopLogo"></v-img>

      <div class="text-subtitle-1 text-medium-emphasis">帳號</div>

      <v-text-field
        density="compact"
        placeholder="Email、account、phone"
        prepend-inner-icon="mdi-email-outline"
        variant="outlined"
        v-model="loginId"
      ></v-text-field>

      <div
        class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between"
      >
        密碼

        <a
          class="text-caption text-decoration-none text-blue"
          href="#"
          rel="noopener noreferrer"
          target="_blank"
        >
          忘記密碼?</a
        >
      </div>

      <v-text-field
        :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
        :type="visible ? 'text' : 'password'"
        density="compact"
        placeholder="Enter your password"
        prepend-inner-icon="mdi-lock-outline"
        variant="outlined"
        @click:append-inner="visible = !visible"
        v-model="password"
      ></v-text-field>

      <v-card class="mb-12" color="surface-variant" variant="tonal">
        <v-card-text class="text-medium-emphasis text-caption">
          警告：連續 3
          次登入失敗後，您的帳號將被暫時鎖定三個小時。如果您現在必須登錄，您也可以點選「忘記密碼？」重設登入密碼。
        </v-card-text>
      </v-card>

      <v-btn
        class="mb-8"
        color="blue"
        size="large"
        variant="tonal"
        block
        @click="handlerLogin"
      >
        登入
      </v-btn>
      <v-btn
        class="mb-2"
        color="blue"
        size="large"
        variant="tonal"
        block
        @click="handlerAdminLogin"
      >
        管理員一鍵登入
      </v-btn>
      <v-btn
        class="mb-8"
        color="blue"
        size="large"
        variant="tonal"
        block
        @click="handlerUserLogin"
      >
        使用者一鍵登入
      </v-btn>

      <v-card-text class="text-center">
        <a
          class="text-blue text-decoration-none"
          href="#"
          rel="noopener noreferrer"
          target="_blank"
        >
          註冊 <v-icon icon="mdi-chevron-right"></v-icon>
        </a>
      </v-card-text>
    </v-card>
  </v-container>
</template>
<script setup>
import PawShopLogo from "@/member/assets/images/PawShop_white_logo.png";
import { ref } from "vue";
import { apiLogin } from "@/member/api/api";
import { useRoute, useRouter } from "vue-router";
import { useAuthStore } from "@/member/stores/auth";

const visible = ref(false);
const loginId = ref("");
const password = ref("");
const route = useRoute();
const router = useRouter();
const authStore = useAuthStore();

const redirectPath = route.query.redirect || "/";

// 一般登入
const handlerLogin = async () => {
  const response = await apiLogin({
    loginId: loginId.value,
    password: password.value,
  });
  if (response.data.success) {
    authStore.login({
      token: response.data.token,
      memberId: response.data.memberId,
      memberName: response.data.memberName,
      role: response.data.role,
      memberPhoto: response.data.memberPhoto,
    });
    router.push("/");
  } else {
    alert("登入失敗：" + response.data.message);
  }
};

// 管理者快速登入
const handlerAdminLogin = async () => {
  const response = await apiLogin({
    loginId: "lzx5",
    password: "123456",
  });
  if (response.data.success) {
    authStore.login({
      token: response.data.token,
      memberId: response.data.memberId,
      memberName: response.data.memberName,
      role: response.data.role,
      memberPhoto: response.data.memberPhoto,
    });
    router.push("/");
  } else {
    alert("登入失敗：" + response.data.message);
  }
};

// 使用者快速登入
const handlerUserLogin = async () => {
  const response = await apiLogin({
    loginId: "wxm1",
    password: "123456",
  });
  if (response.data.success) {
    authStore.login({
      token: response.data.token,
      memberId: response.data.memberId,
      memberName: response.data.memberName,
      role: response.data.role,
      memberPhoto: response.data.memberPhoto,
    });
    router.push(redirectPath);
  } else {
    alert("登入失敗：" + response.data.message);
  }
};
</script>

<style scoped></style>
