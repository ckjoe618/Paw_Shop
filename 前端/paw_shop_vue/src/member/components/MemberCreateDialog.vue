<template>
  <v-dialog v-model="dialog" max-width="600px">
    <v-card>
      <v-card-title class="text-h6">
        <v-icon class="mr-2">mdi-account-plus</v-icon>
        新增會員
      </v-card-title>

      <v-card-text>
        <v-form ref="formRef" v-model="isValid">
          <v-text-field
            label="姓名"
            v-model="form.memberName"
            :rules="[rules.required]"
          />
          <v-select
            label="性別"
            v-model="form.gender"
            :items="['男', '女']"
            :rules="[rules.required]"
          />
          <v-text-field
            label="身分證字號"
            maxlength="10"
            v-model="form.idno"
            :rules="[rules.required, rules.idno]"
          />
          <v-text-field
            label="Email"
            v-model="form.email"
            :rules="[rules.required, rules.email]"
          />
          <v-text-field
            label="電話"
            maxlength="10"
            v-model="form.phone"
            :rules="[rules.required, rules.phone]"
          />
          <v-text-field
            label="生日"
            v-model="form.birthDate"
            type="date"
            :rules="[rules.required]"
          />
          <v-text-field
            label="帳號"
            v-model="form.account"
            :rules="[rules.required]"
          />
          <v-text-field
            label="密碼"
            v-model="form.password"
            :type="showPassword ? 'text' : 'password'"
            :append-inner-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
            @click:append-inner="showPassword = !showPassword"
            :rules="[rules.required, rules.min(6)]"
          />
          <v-select
            label="角色"
            v-model="form.role"
            :items="['USER', 'ADMIN']"
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
          @click="submit"
        >
          確認
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, watch, computed } from "vue";
import * as api from "@/api/memberApi/AdminApi.js";

const props = defineProps({
  dialog: Boolean,
});
const emit = defineEmits(["update:dialog", "created"]);

const dialog = computed({
  get: () => props.dialog,
  set: (val) => emit("update:dialog", val),
});

const formRef = ref(null);
const isValid = ref(false);
const loading = ref(false);
const showPassword = ref(false);

const form = ref({
  memberName: "",
  gender: "",
  idno: "",
  email: "",
  phone: "",
  birthDate: "",
  account: "",
  password: "",
  role: "USER",
});

const rules = {
  required: (v) => !!v || "此欄位必填",
  email: (v) => /.+@.+\..+/.test(v) || "Email 格式錯誤",
  phone: (v) => /^09\d{8}$/.test(v) || "手機號碼格式錯誤",
  idno: (v) => /^[A-Z][12]\d{8}$/.test(v) || "身分證格式錯誤",
  min: (n) => (v) => (v && v.length >= n) || `至少輸入 ${n} 字`,
};

watch(dialog, (val) => {
  if (!val) {
    formRef.value?.resetValidation();
    form.value = {
      memberName: "",
      gender: "",
      idno: "",
      email: "",
      phone: "",
      birthDate: "",
      account: "",
      password: "",
      role: "USER",
    };
  }
});

const close = () => {
  emit("update:dialog", false);
};

const submit = async () => {
  const valid = await formRef.value.validate();
  if (!valid) {
    return;
  }
  loading.value = true;
  try {
    await api.apiAddMember(form.value);
    emit("created");
    close();
  } finally {
    loading.value = false;
  }
};
</script>
