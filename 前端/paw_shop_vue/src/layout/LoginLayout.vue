<template>
  <v-container fluid class="fill-height d-flex justify-center align-center">
    <v-card class="pa-6" elevation="8" max-width="448" rounded="lg">
      <v-img
        class="mx-auto mb-6 logo-hover"
        max-width="120"
        :src="PawShopLogo"
        @click="router.push('/')"
      ></v-img>

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

        <RouterLink to="/forgot-password" class="forgot-password-link">
          忘記密碼？
        </RouterLink>
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
        class="mb-4"
        color="primary"
        size="large"
        variant="elevated"
        block
        :loading="loading"
        @click="handlerLogin"
      >
        Log in
      </v-btn>

      <!-- 分隔線 -->
      <div class="d-flex align-center my-4">
        <v-divider class="flex-grow-1"></v-divider>
        <span class="mx-3 text-medium-emphasis text-caption">or</span>
        <v-divider class="flex-grow-1"></v-divider>
      </div>

      <!-- Google 登入 -->
      <v-btn class="mb-2" variant="outlined" block @click="handleGoogleLogin">
        <img
          :src="GoogleLogo"
          alt="Google"
          style="height: 20px; margin-right: 8px; vertical-align: middle"
        />
        使用 Google 登入
      </v-btn>

      <v-btn class="mb-2" variant="outlined" block @click="handlerAdminLogin">
        管理員一鍵登入
      </v-btn>
      <v-btn class="mb-2" variant="outlined" block @click="handlerUserLogin">
        使用者一鍵登入
      </v-btn>

      <v-card-text class="text-center mt-4">
        <span class="register-link" @click="router.push('/register')">
          註冊 <v-icon class="register-icon">mdi-chevron-right</v-icon>
        </span>
      </v-card-text>
    </v-card>
  </v-container>
</template>
<script setup>
import PawShopLogo from "@/member/assets/images/PawShop_white_logo.png";
import GoogleLogo from "@/member/assets/images/Google_logo.png";
import { ref } from "vue";
import * as api from "@/api/memberApi/UserApi.js";
import { useRoute, useRouter } from "vue-router";
import { useAuthStore } from "@/member/stores/auth";
import { syncCartToBackend } from "@/order/components/frontsite/useCart";

const visible = ref(false);
const loginId = ref("");
const password = ref("");
const route = useRoute();
const router = useRouter();
const authStore = useAuthStore();
const loading = ref(false);

// 一般登入
const handlerLogin = () => performLogin(loginId.value, password.value);

// 管理者快速登入
const handlerAdminLogin = () => performLogin("wxm1", "123456");

// 使用者快速登入
const handlerUserLogin = () => performLogin("cpr6", "123456");

// Google 登入
const handleGoogleLogin = async () => {
  const data = await api.apiGoogleLogin();
  window.location.href = data.url;
};

// 統一登入模式
const performLogin = async (loginId, password) => {
  loading.value = true;
  try {
    const data = await api.apiLogin({ loginId, password });
    authStore.login(data);
    await syncCartToBackend();
    router.push(route.query.redirect || "/");
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.logo-hover {
  cursor: pointer;
  transition: transform 0.3s ease, opacity 0.3s ease;
}
.logo-hover:hover {
  transform: scale(1.08);
  opacity: 0.85;
}
.register-link {
  color: #1976d2;
  font-weight: 500;
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  transition: color 0.3s ease;
}

.register-link:hover {
  color: #0d47a1;
  text-decoration: underline;
}

.register-icon {
  transition: transform 0.3s ease;
}

.register-link:hover .register-icon {
  transform: translateX(4px);
}
.forgot-password-link {
  font-size: 0.75rem;
  text-decoration: none;
  color: #1976d2; /* Vuetify primary blue */
  cursor: pointer;
  transition: color 0.2s ease;
}

.forgot-password-link:hover {
  color: #0d47a1; /* 深一點的藍色 */
  text-decoration: underline;
}
</style>
