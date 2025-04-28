<template>
  <v-card class="pa-3" outlined color="#f9f9f9">
    <v-card-title class="text-subtitle-2 font-weight-bold">
      {{ memberName }}
    </v-card-title>
    <v-card-text>{{ content }}</v-card-text>
    <v-card-actions class="d-flex justify-start">
      <v-btn icon size="small">
        <v-icon size="20">mdi-thumb-up-outline</v-icon>
      </v-btn>
      <span>0</span>
    </v-card-actions>
  </v-card>
</template>

<script setup>
defineProps({
  memberName: String,
  content: String,
});
</script>
