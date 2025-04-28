<template>
  <v-card class="mb-4" outlined rounded>
    <v-card-title class="text-h5 font-weight-bold">{{ title }}</v-card-title>

    <v-card-subtitle class="d-flex align-center">
      <span class="mr-2">{{ categoryName }}</span>
      <v-divider vertical class="mx-2"></v-divider>
      <span>{{ memberName }}</span>
    </v-card-subtitle>

    <v-card-text class="py-4">
      <slot />
      <!-- 內文插槽 -->
    </v-card-text>

    <v-divider></v-divider>

    <v-card-actions>
      <v-btn
        variant="text"
        prepend-icon="mdi-heart-outline"
        ripple
        @click="likeArticle"
      >
        {{ likes }}
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup>
import { ref } from "vue";

const props = defineProps({
  title: String,
  categoryName: String,
  memberName: String,
});

const likes = ref(0);

const likeArticle = () => {
  likes.value++;
};
</script>
