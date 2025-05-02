<template>
  <v-card class="pa-4 mb-4" elevation="2" rounded="lg">
    <v-row dense>
      <v-col cols="12" md="4">
        <v-text-field
          v-model="keyword"
          label="關鍵字搜尋（姓名 / Email / 電話 / ID）"
          hint="📞 電話查詢請輸入 4 碼以上"
          persistent-hint
          clearable
          @click:clear="emitClear"
        />
      </v-col>

      <v-col cols="12" md="4">
        <v-select
          v-model="role"
          :items="['ADMIN', 'USER']"
          label="權限"
          clearable
        />
      </v-col>

      <v-col cols="12" md="4">
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
    </v-row>
  </v-card>
</template>

<script setup>
import { ref, watch } from "vue";

const keyword = ref("");
const role = ref(null);
const status = ref(null);

const emit = defineEmits(["search", "clear"]);

watch([keyword, role, status], () => emitSearch());

const emitSearch = () => {
  emit("search", {
    keyword: keyword.value.trim().toLowerCase() || null,
    role: role.value,
    status: status.value,
  });
};

const emitClear = () => {
  keyword.value = "";
  emitSearch();
};
</script>
