<template>
  <v-card class="pa-4 mb-4" elevation="2" rounded="lg">
    <v-row dense>
      <!-- 關鍵字搜尋 -->
      <v-col cols="12" md="4">
        <v-text-field
          v-model="keyword"
          label="關鍵字搜尋（姓名 / Email / 電話 / ID）"
          hint="📞 電話查詢請輸入 4 碼以上"
          persistent-hint
          clearable
          append-inner-icon="mdi-magnify"
          @click:append-inner="emitSearch"
          @keyup.enter="emitSearch"
          @click:clear="emitClear"
        />
      </v-col>

      <!-- 角色 -->
      <v-col cols="12" md="3">
        <v-select
          v-model="role"
          :items="['ADMIN', 'USER']"
          label="角色"
          clearable
        />
      </v-col>

      <!-- 狀態 -->
      <v-col cols="12" md="3">
        <v-select
          v-model="status"
          :items="[
            { title: '啟用', value: true },
            { title: '停用', value: false },
          ]"
          item-title="title"
          item-value="value"
          label="狀態"
          clearable
        />
      </v-col>

      <!-- 查詢按鈕 -->
      <v-col cols="12" md="2" class="d-flex align-end">
        <div class="w-100" style="padding-bottom: 20px">
          <v-btn
            color="primary"
            class="w-100 text-subtitle-1 rounded-lg"
            height="56"
            style="line-height: 56px; padding: 0"
            @click="emitSearch"
          >
            <v-icon start class="me-1">mdi-magnify</v-icon>
            查詢
          </v-btn>
        </div>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup>
import { ref } from "vue";

const keyword = ref("");
const role = ref(null);
const status = ref(null);
const emit = defineEmits(["search", "clear"]);

const emitSearch = () => {
  emit("search", {
    keyword: keyword.value.trim().toLowerCase(),
    role: role.value,
    status: status.value,
  });
};

const emitClear = () => {
  keyword.value = "";
  role.value = null;
  status.value = null;
  emit("clear");
};
</script>
