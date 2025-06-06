<template>
  <v-dialog v-model="dialog" max-width="600px">
    <v-card>
      <v-card-title class="text-h6">
        <v-icon class="mr-2">mdi-account-edit</v-icon>
        編輯會員資料
      </v-card-title>

      <v-card-text>
        <v-form ref="formRef" v-model="isValid">
          <v-text-field
            label="姓名"
            v-model="localMember.memberName"
            :rules="[rules.required]"
          />
          <v-text-field
            label="Email"
            v-model="localMember.email"
            :rules="[rules.required, rules.email]"
          />
          <v-text-field
            label="電話"
            v-model="localMember.phone"
            maxlength="10"
            :rules="[rules.required, rules.phone]"
          />
          <v-select
            label="角色"
            v-model="localMember.role"
            :items="['USER', 'ADMIN']"
            :rules="[rules.required]"
          />
          <v-select
            label="狀態"
            v-model="localMember.activeStatus"
            :items="[
              { text: '啟用', value: true },
              { text: '停用', value: false },
            ]"
            item-title="text"
            item-value="value"
            :rules="[rules.role]"
          />
        </v-form>
      </v-card-text>

      <v-card-actions>
        <v-spacer />
        <v-btn text @click="close">取消</v-btn>
        <v-btn
          color="primary"
          :disabled="!isValid"
          :loading="loading"
          @click="submit"
          >確認</v-btn
        >
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, watch, computed, nextTick } from "vue";
import { useAuthStore } from "@/member/stores/auth";
import * as api from "@/api/memberApi/AdminApi";
import Swal from "sweetalert2";

const authStore = useAuthStore();
const formRef = ref(null);
const isValid = ref(false);
const loading = ref(false);
const localMember = ref({});

const props = defineProps({
  dialog: Boolean,
  member: Object,
});

// 父層 editDialog → 傳入 props.dialog → dialog.get() 讀取
// 子層 dialog.value = false → 呼叫 emit → 父層 editDialog 也改變
const emit = defineEmits(["update:dialog", "updated"]);

const dialog = computed({
  get: () => props.dialog,
  set: (val) => emit("update:dialog", val),
});

const rules = {
  required: (v) => !!v || "必填項目",
  email: (v) => /.+@.+\..+/.test(v) || "Email 格式錯誤",
  phone: (v) => /^09\d{8}$/.test(v) || "手機號碼格式錯誤",
  role: (v) => (v !== null && v !== undefined) || "必填項目",
};

watch(
  dialog,
  async (val) => {
    if (val) {
      localMember.value = { ...props.member };
    } else {
      await nextTick(); // 等待 DOM 清空完畢後再重設
      formRef.value?.resetValidation();
      localMember.value = {};
    }
  },
  { immediate: true }
);

const close = async () => {
  emit("update:dialog", false);
  await nextTick();
  document.activeElement?.blur();
};

const submit = async () => {
  const valid = await formRef.value.validate();
  if (!valid) {
    return;
  }
  loading.value = true;
  try {
    const data = await api.apiUpdateMember(localMember.value);
    if (localMember.value.memberId == authStore.memberId) {
      authStore.update(data);
    }
    emit("updated");
    close();
  } finally {
    loading.value = false;
    Swal.fire({
      icon: "success",
      title: "修改完成",
      showConfirmButton: false,
      timer: 1000,
    });
  }
};
</script>

<style scoped>
.v-card-title {
  align-items: center;
}
</style>
