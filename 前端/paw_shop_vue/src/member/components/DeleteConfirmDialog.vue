<template>
  <v-dialog v-model="dialog" max-width="400">
    <v-card>
      <v-card-title class="text-h6">{{ title }}</v-card-title>
      <v-card-text>{{ message }}</v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn text @click="cancel">取消</v-btn>
        <v-btn color="error" @click="confirm">確定</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, defineExpose } from "vue";

const dialog = ref(false);
const title = ref("");
const message = ref("");
let resolveFn = null;

// 外部 openDialog 使用
const openDialog = (options = {}) => {
  title.value = options.title || "確認刪除";
  message.value = options.message || "你確定要刪除嗎？";
  dialog.value = true;
  return new Promise((resolve) => {
    resolveFn = resolve;
  });
};

const cancel = () => {
  dialog.value = false;
  resolveFn(false);
};

const confirm = () => {
  dialog.value = false;
  resolveFn(true);
};

defineExpose({ openDialog });
</script>
