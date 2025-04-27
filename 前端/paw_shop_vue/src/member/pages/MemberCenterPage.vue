<template>
  <v-container class="pt-6">
    <v-card elevation="4" rounded="lg">
      <v-row>
        <!-- 左側導覽列 -->
        <v-col cols="12" md="3" class="pa-6 grey-lighten-4">
          <div class="text-h6 font-weight-bold mb-6">
            {{ authStore.memberName }}
          </div>
          <v-list nav dense>
            <v-list-item
              v-for="item in menuItems"
              :key="item.route"
              :to="item.route"
              :active="$route.path === item.route"
              class="rounded"
              link
            >
              <v-list-item-title>{{ item.label }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-col>

        <!-- 右側內容區：動態子路由 -->
        <v-col cols="12" md="9" class="pa-6">
          <router-view />
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script setup>
import { useAuthStore } from "@/member/stores/auth";
import { useRoute } from "vue-router";

const authStore = useAuthStore();
const $route = useRoute();

const menuItems = [
  { label: "個人檔案", route: "/member/profile" },
  { label: "地址", route: "/member/address" },
  { label: "修改密碼", route: "/member/password" },
];
</script>
