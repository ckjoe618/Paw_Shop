<template>
  <v-container>
    <MemberTable
      :members="members"
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
import { apiFindMemberAll } from "@/member/api/api.js";
import MemberTable from "@/member/components/MemberTable.vue";
import MemberEditDialog from "@/member/components/MemberEditDialog.vue";
import { apiDeleteMember } from "@/member/api/api.js";

const members = ref([]);
const loading = ref(false);
const editDialog = ref(false);
const selectedMember = ref(null);

// 取得會員資料
const fetchMembers = async () => {
  loading.value = true;
  try {
    const response = await apiFindMemberAll();
    members.value = response.data;
    console.log(members.value);
  } catch (e) {
    console.error("載入會員失敗", e);
  } finally {
    loading.value = false;
  }
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
