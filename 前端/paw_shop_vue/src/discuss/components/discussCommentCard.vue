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
    <v-row align="center" dense class="justify-space-between">
      <v-col cols="auto">
        <v-btn variant="text" ripple @click="$emit('toggle-like')">
          <v-icon start>
            {{ isLiked ? "mdi-thumb-up" : "mdi-thumb-up-outline" }}
          </v-icon>
          {{ likeCount }}
        </v-btn>
      </v-col>

      <v-col cols="auto" v-if="!deleted">
        <ActionMenu
          :isOwner="isOwner"
          :showFavorite="false"
          @delete="$emit('delete')"
        />
      </v-col>
    </v-row>

    <!-- 放樓中樓（回覆） -->
    <slot></slot>
  </v-card>
</template>

<script setup>
import { useAuthStore } from "@/member/stores/auth";
import ActionMenu from "@/discuss/components/comment/actionMenu.vue";

const authStore = useAuthStore();

const props = defineProps({
  floor: Number,
  memberId: Number,
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

const isOwner = props.memberId === authStore.memberId;

const emit = defineEmits(["toggle-like", "delete"]);
</script>

<style scoped>
.bg-grey-lighten-4 {
  background-color: #f5f5f5;
}
</style>
