<template>
  <v-menu offset-y>
    <template #activator="{ props }">
      <v-btn icon v-bind="props">
        <v-icon>mdi-dots-vertical</v-icon>
      </v-btn>
    </template>

    <v-list>
      <!-- ✅ 非本人 且 是文章 => 收藏 -->
      <v-list-item v-if="!isOwner && isArticle" @click="$emit('favorite')">
        <v-list-item-title>收藏</v-list-item-title>
      </v-list-item>

      <!-- ✅ 只有非作者才可以檢舉 -->
      <v-list-item v-if="!isOwner" @click="$emit('report')">
        <v-list-item-title>檢舉</v-list-item-title>
      </v-list-item>

      <!-- ✅ 本人才可以編輯/刪除 -->
      <v-list-item v-if="isOwner" @click="$emit('edit')">
        <v-list-item-title>編輯</v-list-item-title>
      </v-list-item>
      <v-list-item v-if="isOwner" @click="$emit('delete')">
        <v-list-item-title>刪除</v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>
</template>

<script setup>
defineProps({
  isOwner: Boolean, // 是否是本人（發文者 / 留言者）
  isArticle: Boolean, // 是否是主文
});
defineEmits(["favorite", "report", "edit", "delete"]);
</script>
