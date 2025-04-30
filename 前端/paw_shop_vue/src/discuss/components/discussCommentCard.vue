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

    <!-- 按讚按鈕 -->
    <v-row align="center" dense>
      <v-col cols="auto">
        <v-btn
          variant="text"
          :icon="isLiked ? 'mdi-thumb-up' : 'mdi-thumb-up-outline'"
          ripple
          @click="$emit('toggle-like')"
        >
          {{ likeCount }}
        </v-btn>
      </v-col>
    </v-row>

    <!-- 放樓中樓（回覆） -->
    <slot></slot>
  </v-card>
</template>

<script setup>
const props = defineProps({
  floor: Number,
  memberName: String,
  content: String,
  deleted: {
    type: Boolean,
    default: false,
  },
  isLiked: {
    type: Boolean,
    default: false,
  },
  likeCount: {
    type: Number,
    default: 0,
  },
});

const emit = defineEmits(["toggle-like"]);
</script>

<style scoped>
.bg-grey-lighten-4 {
  background-color: #f5f5f5;
}
</style>
  