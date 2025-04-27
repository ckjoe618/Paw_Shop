<template>
  <v-form ref="formRef" v-model="isValid">
    <v-row>
      <!-- 左欄：會員資訊 -->
      <v-col cols="12" md="8">
        <v-row class="mb-2">
          <v-col cols="12" class="d-flex align-center">
            <span class="text-subtitle-1 font-weight-medium mr-2"
              >使用者帳號:</span
            >
            <span class="text-body-1 text-grey-darken-1">{{
              authStore.account
            }}</span>
          </v-col>
          <v-col cols="12" class="d-flex align-center">
            <span class="text-subtitle-1 font-weight-medium mr-2">Email:</span>
            <span class="text-body-1 text-grey-darken-1">{{ form.email }}</span>
          </v-col>
        </v-row>

        <v-text-field
          label="姓名"
          v-model="form.memberName"
          :rules="[rules.required]"
        />

        <v-text-field
          label="手機號碼"
          v-model="form.phone"
          :rules="[rules.required, rules.phone]"
        />

        <v-radio-group v-model="form.gender" inline class="mt-2">
          <label class="text-subtitle-1 font-weight-medium">性別</label>
          <v-radio label="男性" value="男" />
          <v-radio label="女性" value="女" />
        </v-radio-group>

        <v-text-field
          label="生日"
          v-model="form.birthDate"
          type="date"
          :rules="[rules.required]"
        />

        <div class="text-center mt-6">
          <v-btn
            color="primary"
            prepend-icon="mdi-content-save"
            :disabled="!isValid"
            :loading="loading"
            @click="saveProfile"
          >
            儲存
          </v-btn>
        </div>
      </v-col>

      <!-- 右欄：頭像設定 -->
      <v-col cols="12" md="4" class="text-center">
        <v-avatar size="120" class="mx-auto mb-4">
          <v-img :src="previewPhoto" cover />
        </v-avatar>
        <v-btn class="mb-2" color="primary" @click="$refs.fileInput.click()">
          選擇圖片
        </v-btn>
        <input
          ref="fileInput"
          type="file"
          accept="image/png, image/jpeg"
          hidden
          @change="handleFileChange"
        />
        <div class="text-caption text-grey-darken-1">
          檔案大小：最大 1MB<br />
          檔案限制：.JPEG、.PNG
        </div>
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useAuthStore } from "@/member/stores/auth";
import * as api from "@/member/api/memberApi/UserApi.js";

const authStore = useAuthStore();
const formRef = ref(null);
const isValid = ref(false);
const loading = ref(false);
const uploadFile = ref(null);
const previewPhoto = ref(null);

const form = ref({
  memberId: authStore.memberId,
  memberName: authStore.memberName,
  gender: authStore.gender,
  idno: authStore.idno,
  email: authStore.email,
  phone: authStore.phone,
  birthDate: authStore.birthDate,
});

const rules = {
  required: (v) => !!v || "欄位為必填",
  phone: (v) => /^09\d{8}$/.test(v) || "手機號碼格式錯誤",
};

const handleFileChange = (e) => {
  const file = e.target.files[0];
  if (file) {
    if (previewPhoto.value.startsWith("blob:")) {
      URL.revokeObjectURL(previewPhoto.value);
    }
    previewPhoto.value = URL.createObjectURL(file);
    uploadFile.value = file;
  }
};

const saveProfile = async () => {
  const valid = await formRef.value.validate();
  if (!valid) {
    return;
  }
  loading.value = true;
  try {
    const data = await api.apiUpdateMember(form.value, uploadFile.value);
    authStore.update(data);
    previewPhoto.value = data.memberPhoto || previewPhoto.value;
    uploadFile.value = null;
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  previewPhoto.value = authStore.memberPhoto;
});
</script>
