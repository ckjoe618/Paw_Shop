<template>
  <v-dialog v-model="dialog" max-width="500">
    <v-card>
      <v-card-title>{{
        mode === "add" ? "新增地址" : "編輯地址"
      }}</v-card-title>
      <v-card-text>
        <v-form ref="formRef" v-model="isValid">
          <v-text-field
            label="收件人"
            v-model="localAddress.recipientName"
            :rules="[rules.required]"
          />
          <v-text-field
            label="電話"
            v-model="localAddress.phone"
            :rules="[rules.required, rules.phone]"
          />
          <v-text-field
            label="郵遞區號"
            v-model="localAddress.zipcode"
            :rules="[rules.required, rules.zipcode]"
          />
          <v-text-field
            label="城市"
            v-model="localAddress.city"
            :rules="[rules.required]"
          />
          <v-text-field
            label="區域"
            v-model="localAddress.district"
            :rules="[rules.required]"
          />
          <v-text-field
            label="詳細地址"
            v-model="localAddress.addressDetail"
            :rules="[rules.required]"
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
          @click="save"
          >儲存</v-btn
        >
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, watch, computed } from "vue";
import * as api from "@/member/api/memberApi/UserApi";

const formRef = ref(null);
const isValid = ref(false);
const loading = ref(false);
const localAddress = ref({});

const props = defineProps({
  dialog: Boolean,
  address: Object,
  mode: String, // 'add' or 'edit'
});
const emit = defineEmits(["update:dialog", "saved"]);

const dialog = computed({
  get: () => props.dialog,
  set: (val) => emit("update:dialog", val),
});

const rules = {
  required: (v) => !!v || "必填項目",
  phone: (v) => /^09\d{8}$/.test(v) || "請輸入正確的手機號碼",
  zipcode: (v) => /^\d{3,5}$/.test(v) || "請輸入正確的郵遞區號（3~5位數字）",
};

watch(
  dialog,
  (val) => {
    if (val) {
      localAddress.value = { ...props.address };
    } else {
      setTimeout(() => {
        formRef.value?.resetValidation();
        localAddress.value = {};
      });
    }
  },
  { immediate: true }
);

const close = () => emit("update:dialog", false);

const save = async () => {
  const valid = await formRef.value.validate();
  if (!valid) {
    return;
  }
  loading.value = true;
  try {
    if (props.mode === "add") {
      await api.apiAddAddress(localAddress.value);
    } else {
      await api.apiUpdateAddress(localAddress.value);
    }
    emit("saved");
    close();
  } finally {
    loading.value = false;
  }
};
</script>
