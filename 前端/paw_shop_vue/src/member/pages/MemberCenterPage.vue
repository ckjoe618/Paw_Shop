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
              :active="item.route === selected"
              @click="selected = item.route"
              class="rounded"
            >
              <v-list-item-title>{{ item.label }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-col>

        <!-- 右側內容顯示區塊 -->
        <v-col cols="12" md="9" class="pa-6">
          <component :is="currentComponent" />
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref, computed } from "vue";
import { useAuthStore } from "@/member/stores/auth";
import ProfileForm from "@/member/components/ProfileForm.vue";

const authStore = useAuthStore();
const selected = ref("profile");

const menuItems = [
  { label: "個人檔案", route: "profile" },
  { label: "地址", route: "address" },
  { label: "通知設定", route: "notifications" },
];

const currentComponent = computed(() => {
  switch (selected.value) {
    case "profile":
      return ProfileForm;
    default:
      return { template: "<div>此功能尚未實作</div>" };
  }
});
</script>
