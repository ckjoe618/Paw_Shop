<template>
  <v-container class="d-flex justify-center align-center fill-height">
    <v-progress-circular indeterminate size="64" color="primary" />
  </v-container>
</template>

<script setup>
import { onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useAuthStore } from "@/member/stores/auth";
import * as api from "@/member/api/memberApi/UserApi.js";

const route = useRoute();
const router = useRouter();
const authStore = useAuthStore();

onMounted(async () => {
  const code = route.query.code;
  if (!code) return router.push("/login");

  try {
    const data = await api.apiLoginByGoogleCallback(code);
    authStore.login({ ...data });
    router.replace("/home");
  } catch (error) {
    console.error("Google 登入失敗", error);
    router.push("/login");
  }
});
</script>
