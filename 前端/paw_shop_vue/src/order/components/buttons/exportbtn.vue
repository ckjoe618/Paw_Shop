<template>
  <v-btn
    :icon="icon !== undefined"
    color="green"
    :size="size"
    :density="density"
    @click="$emit('click')"
  >
    <v-icon :size="iconSize">{{ icon }}</v-icon>
  </v-btn>
</template>

<script setup>
defineProps({
  icon: {
    type: String,
    default: "mdi-export-variant",
  },
  color: {
    type: String,
    default: "default",
  },
  size: {
    type: String,
    default: "default",
  },
  iconSize: {
    type: [String, Number],
    default: "24",
  },
});
defineEmits(["click"]);
</script>

<style scoped></style>
