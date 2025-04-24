<template>
  <v-card elevation="4" rounded="lg">
    <v-data-table
      :headers="headers"
      :items="members"
      :loading="loading"
      class="elevation-1"
      item-value="memberId"
      no-data-text="目前沒有會員資料"
    >
      <template #loading>
        <v-skeleton-loader type="table-row@10" />
      </template>

      <template #item.memberPhoto="{ item }">
        <v-avatar size="36">
          <v-img :src="item.memberPhoto" />
        </v-avatar>
      </template>

      <template #item.activeStatus="{ item }">
        <v-chip
          :color="item.activeStatus ? 'green' : 'grey'"
          text-color="white"
        >
          {{ item.activeStatus ? "啟用" : "停用" }}
        </v-chip>
      </template>

      <template #item.role="{ item }">
        <v-chip
          :color="item.role === 'ADMIN' ? 'red' : 'blue'"
          text-color="white"
        >
          {{ item.role }}
        </v-chip>
      </template>

      <template #item.birthDate="{ item }">
        {{ new Date(item.birthDate).toLocaleDateString() }}
      </template>

      <template #item.createAccountDate="{ item }">
        {{ dayjs(item.createAccountDate).format("YYYY/M/D HH:mm") }}
      </template>

      <template #item.actions="{ item }">
        <v-btn icon color="primary" @click="$emit('edit', item)">
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
        <v-btn icon color="error" @click="$emit('deactivate', item)">
          <v-icon>mdi-delete</v-icon>
        </v-btn>
      </template>
    </v-data-table>
  </v-card>
</template>

<script setup>
import { defineProps } from "vue";
import dayjs from "dayjs";

const props = defineProps({
  members: Array,
  loading: Boolean,
});

const headers = [
  { title: "ID", value: "memberId" },
  { title: "頭像", value: "memberPhoto" },
  { title: "姓名", value: "memberName" },
  { title: "性別", value: "gender" },
  { title: "身分證", value: "idno" },
  { title: "Email", value: "email" },
  { title: "電話", value: "phone" },
  { title: "生日", value: "birthDate" },
  { title: "權限", value: "role" },
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
