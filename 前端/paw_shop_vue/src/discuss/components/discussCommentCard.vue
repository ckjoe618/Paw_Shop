<template>
  <v-card
    elevation="1"
    class="pa-4"
    :class="deleted ? 'bg-grey-lighten-4' : ''"
  >
    <v-row align="center" class="mb-2">
      <v-col cols="auto">
        <span class="text-caption font-weight-bold">#{{ floor }}F</span>
      </v-col>
      <v-col cols="auto">
        <span class="text-caption">{{ memberName }}</span>
      </v-col>
    </v-row>

    <div class="text-body-2 mb-2">
      {{ content }}
    </div>

    <!-- 按讚icon -->
    <v-row align="center" dense>
      <v-col cols="auto">
        <v-icon small>mdi-thumb-up-outline</v-icon>
        0
      </v-col>
    </v-row>

    <!-- 放樓中樓的slot -->
    <slot></slot>
  </v-card>
</template>

<script setup>
defineProps({
  floor: Number,
  memberName: String,
  content: String,
  deleted: {
    type: Boolean,
    default: false,
  },
});
</script>

<style scoped>
.bg-grey-lighten-4 {
  background-color: #f5f5f5;
}
</style>
