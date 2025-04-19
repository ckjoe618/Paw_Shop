<template>
  <v-dialog v-model="dialog" max-width="600px">
    <v-card>
      <v-card-title class="text-h6">
        <v-icon class="mr-2">mdi-account-edit</v-icon>
        編輯會員資料
      </v-card-title>

      <v-card-text>
        <v-form ref="form" v-model="isValid">
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
            :rules="[rules.required]"
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
            :rules="[(v) => (v !== null && v !== undefined) || '必填項目']"
          />
        </v-form>
      </v-card-text>

      <v-card-actions>
        <v-spacer />
        <v-btn text @click="close">取消</v-btn>
        <v-btn color="primary" :disabled="!isValid" @click="submit">確認</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, watch, computed } from "vue";
import { apiUpdateMember } from "@/member/api/api";

const form = ref();
const isValid = ref(false);
const localMember = ref({});

const props = defineProps({
  dialog: Boolean,
  member: Object,
});

watch(
  () => props.member,
  (val) => {
    localMember.value = { ...val };
  },
  { immediate: true }
);

// 父層 editDialog → 傳入 props.dialog → dialog.get() 讀取
// 子層 dialog.value = false → 呼叫 emit → 父層 editDialog 也改變
const emit = defineEmits(["update:dialog", "updated"]);

const dialog = computed({
  get: () => props.dialog,
  set: (val) => emit("update:dialog", val),
});

const rules = {
  required: (v) => !!v || "必填項目",
  email: (v) => /.+@.+\..+/.test(v) || "Email 格式不正確",
};

watch(dialog, (val) => {
  if (!val) {
    form.value.reset();
    localMember.value = {};
  } else {
    // 重新打開 dialog 時，把 props.member 值套進去
    localMember.value = { ...props.member };
  }
});

const close = () => {
  emit("update:dialog", false);
};

const submit = async () => {
  if (!form.value.validate()) {
    console.log(form.value.validate());
    return; // 表單驗證失敗就中止
  }
  try {
    console.log("送出資料：", localMember.value);
    await apiUpdateMember(localMember.value);
    emit("updated");
    close();
  } catch (e) {
    console.error("更新失敗", e);
    alert("更新失敗，請稍後再試");
  }
};
</script>

<style scoped>
.v-card-title {
  align-items: center;
}
</style>
