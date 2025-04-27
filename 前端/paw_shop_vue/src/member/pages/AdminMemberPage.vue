<template>
  <v-container>
    <v-row align="center" class="mb-4" style="gap: 12px">
      <h2 class="mb-0">會員資訊</h2>
      <AddBtn
        label="新增會員"
        variant="elevated"
        icon-size="25"
        @click="createDialog = true"
      />
    </v-row>

    <MemberSearchPanel @search="handleSearch" class="mb-4" />

    <MemberTable
      :members="filteredMembers"
      :loading="loading"
      @refresh="fetchMembers"
      @edit="openEditDialog"
      @deactivate="handleDeactivate"
    />

    <MemberEditDialog
      v-model:dialog="editDialog"
      :member="selectedMember"
      @updated="fetchMembers"
    />

    <MemberCreateDialog v-model:dialog="createDialog" @created="fetchMembers" />
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import * as api from "@/member/api/memberApi/AdminApi.js";
import MemberTable from "@/member/components/MemberTable.vue";
import MemberEditDialog from "@/member/components/MemberEditDialog.vue";
import MemberSearchPanel from "@/member/components/MemberSearchPanel.vue";
import MemberCreateDialog from "@/member/components/MemberCreateDialog.vue";
import AddBtn from "@/order/components/buttons/addbtn.vue";

const members = ref([]);
const filteredMembers = ref([]);
const selectedMember = ref([]);
const loading = ref(false);
const editDialog = ref(false);
const createDialog = ref(false);

// 取得會員資料
const fetchMembers = async () => {
  loading.value = true;
  try {
    const data = await api.apiFindMemberAll();
    members.value = data;
    filteredMembers.value = [...data];
  } finally {
    loading.value = false;
  }
};

// 處理搜尋事件
const handleSearch = async ({ keyword, role, status }) => {
  filteredMembers.value = members.value.filter((member) => {
    let matchKey = true;

    if (keyword) {
      if (/^\d+$/.test(keyword)) {
        matchKey =
          String(member.memberId) === keyword ||
          (keyword.length >= 4 && String(member.phone).includes(keyword));
      } else {
        matchKey = [member.memberName, member.email].some((value) =>
          String(value).toLowerCase().includes(keyword)
        );
      }
    }
    const matchRole = role ? member.role === role : true;
    const matchStatus =
      status !== null && status !== undefined
        ? member.activeStatus === status
        : true;
    return matchKey && matchRole && matchStatus;
  });
};

// 處理編輯事件
const openEditDialog = (member) => {
  selectedMember.value = { ...member };
  editDialog.value = true;
};

// 處理停用事件
const handleDeactivate = async (member) => {
  if (confirm(`確定要停用 ${member.memberName} 嗎？`)) {
    await api.apiDeleteMember(member.memberId);
    alert("會員已成功停用");
    await fetchMembers();
  }
};

onMounted(() => {
  fetchMembers();
});
</script>
