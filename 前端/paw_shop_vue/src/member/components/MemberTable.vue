<template>
  <v-card>
    <v-card-title>
      <v-icon class="mr-2">mdi-account-multiple</v-icon>
      會員列表
      <!-- <v-spacer></v-spacer> -->
      <!-- <v-btn color="primary" @click="$emit('refresh')">重新整理</v-btn> -->
    </v-card-title>

    <v-data-table
      :headers="headers"
      :items="members"
      :loading="loading"
      class="elevation-1"
      item-value="memberId"
    >
      <template #loading>
        <v-skeleton-loader type="table-row@10" />
      </template>

      <template #item.photo="{ item }">
        <v-avatar size="36">
          <v-img :src="item.photo" />
        </v-avatar>
      </template>

      <template #item.activeStatus="{ item }">
        <v-chip :color="item.activeStatus === true ? 'green' : 'grey'" dark>
          {{ item.activeStatus === true ? "啟用" : "停用" }}
        </v-chip>
      </template>

      <template #item.role="{ item }">
        <v-chip :color="item.role === 'ADMIN' ? 'red' : 'blue'" dark>
          {{ item.role }}
        </v-chip>
      </template>

      <template #item.birthDate="{ item }">
        {{ new Date(item.birthDate).toLocaleDateString() }}
      </template>

      <template #item.actions="{ item }">
        <v-btn icon color="blue" @click="$emit('edit', item)">
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
        <v-btn icon color="red" @click="$emit('deactivate', item)">
          <v-icon>mdi-close-circle</v-icon>
        </v-btn>
      </template>
    </v-data-table>
  </v-card>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  members: Array,
  loading: Boolean,
});

const headers = [
  { title: "ID", value: "memberId" },
  { title: "頭像", value: "photo" },
  { title: "姓名", value: "memberName" },
  { title: "性別", value: "gender" },
  { title: "身分證", value: "idno" },
  { title: "Email", value: "email" },
  { title: "電話", value: "phone" },
  { title: "生日", value: "birthDate" },
  { title: "角色", value: "role" },
  { title: "建立時間", value: "createAccountDate" },
  { title: "狀態", value: "activeStatus" },
  { title: "操作", value: "actions", sortable: false },
];
</script>

<style scoped>
.v-data-table .v-btn {
  margin: 0 4px;
}
</style>
