<template>
  <v-container class="text-center py-16">
    <v-progress-circular
      indeterminate
      color="success"
      size="64"
    ></v-progress-circular>
    <div class="mt-4 text-h6">正在確認付款結果，請稍候...</div>
  </v-container>
</template>

<script setup>
import { onMounted } from "vue";
import router from "@/router";

onMounted(() => {
  const query = new URLSearchParams(window.location.search);
  const rtnCode = query.get("RtnCode"); // 付款狀態碼，1 = 成功
  console.log(rtnCode);

  if (rtnCode === "1") {
    router.replace("/orderFinish");
  } else {
    router.replace("/orderFinish");
  }
});
</script>
