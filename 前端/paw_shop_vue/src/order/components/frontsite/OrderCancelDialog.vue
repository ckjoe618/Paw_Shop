<template>
  <div>
    <v-dialog
      :model-value="visible"
      @update:model-value="emit('update:visible', $event)"
      max-width="400"
    >
      <v-card>
        <v-card-title class="text-h6">確認取消</v-card-title>
        <v-card-text>
          確定要取消訂單編號為 <strong>{{ orderId }}</strong> 的訂單嗎？
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="$emit('update:visible', false)">再想一下</v-btn>
          <v-btn color="error" @click="confirmDelete">確認取消</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup>
const props = defineProps({
  visible: Boolean,
  orderId: Number,
});
const emit = defineEmits(["update:visible", "confirm"]);
const confirmDelete = () => {
  emit("confirm");
  emit("update:visible", false);
};
</script>

<style scoped></style>
