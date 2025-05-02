<template>
  <v-btn
    icon
    density="compact"
    size="28"
    color="error"
    @click="$emit('delete')"
  >
    <v-icon size="18">mdi-delete</v-icon>
  </v-btn>
</template>

<script setup>
defineEmits(["delete"]);
</script>

<style scoped></style>
