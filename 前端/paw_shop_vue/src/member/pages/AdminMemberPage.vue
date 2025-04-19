<template>
  <v-container fluid>
    <MemberSearchPanel
      @search="handleSearch"
      @clear="clearSearch"
      class="mb-4"
    />

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
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { apiFindMemberAll, apiDeleteMember } from "@/member/api/api.js";
import MemberTable from "@/member/components/MemberTable.vue";
import MemberEditDialog from "@/member/components/MemberEditDialog.vue";
import MemberSearchPanel from "@/member/components/MemberSearchPanel.vue";

const members = ref([]);
const filteredMembers = ref([]);
const loading = ref(false);
const editDialog = ref(false);
const selectedMember = ref(null);

// 取得會員資料
const fetchMembers = async () => {
  loading.value = true;
  try {
    const response = await apiFindMemberAll();
    members.value = response.data;
    filteredMembers.value = response.data;
  } catch (e) {
    console.error("載入會員失敗", e);
  } finally {
    loading.value = false;
  }
};

// 處理搜尋事件
const handleSearch = async ({ keyword, role, status }) => {
  const response = await apiFindMemberAll();
  members.value = response.data;

  let matchKey = "";

  filteredMembers.value = members.value.filter((member) => {
    if (/^\d+$/.test(keyword)) {
      matchKey =
        String(member.memberId) === keyword ||
        (keyword.length >= 4 && String(member.phone).includes(keyword));
    } else {
      matchKey = [member.memberName, member.email].some((value) =>
        String(value || "")
          .toLowerCase()
          .includes(keyword)
      );
    }
    const matchRole = role ? member.role === role : true;
    const matchStatus =
      status !== null && status !== undefined
        ? member.activeStatus === status
        : true;
    return matchKey && matchRole && matchStatus;
  });
};

// 處理清除搜尋事件
const clearSearch = () => {
  // 建立一份淺拷貝
  filteredMembers.value = [...members.value];
};

// 處理編輯事件
const openEditDialog = (member) => {
  selectedMember.value = member;
  editDialog.value = true;
};

// 處理停用事件
const handleDeactivate = async (member) => {
  if (confirm(`確定要停用 ${member.memberName} 嗎？`)) {
    try {
      await apiDeleteMember(member.memberId);
      alert("會員已成功停用");
      fetchMembers();
    } catch (e) {
      console.error("停用失敗", e.response?.data || e.message);
      alert("停用失敗：" + (e.response?.data || "請稍後再試"));
    }
  }
};

onMounted(() => {
  fetchMembers();
});
</script>
