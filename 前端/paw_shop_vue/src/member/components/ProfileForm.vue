<template>
  <v-container>
    <v-row class="mb-4">
      <v-col cols="12">
        <h4>個人檔案</h4>
      </v-col>
    </v-row>
    <v-form ref="formRef" v-model="isValid">
      <v-row>
        <!-- 左欄：會員資訊 -->
        <v-col cols="12" md="8">
          <v-row class="mb-2">
            <v-col cols="12" class="d-flex align-center">
              <span class="text-subtitle-1 font-weight-medium mr-2">姓名:</span>
              <span class="text-body-1 text-grey-darken-1">{{
                form.memberName
              }}</span>
            </v-col>
          </v-row>

          <v-row class="mb-2">
            <v-col cols="12" class="d-flex align-center">
              <span class="text-subtitle-1 font-weight-medium mr-2">性別:</span>
              <span class="text-body-1 text-grey-darken-1">{{
                form.gender
              }}</span>
            </v-col>
          </v-row>

          <v-text-field
            label="Email"
            v-model="form.email"
            :rules="[rules.required, rules.email]"
          />

          <v-row class="mb-2">
            <v-col cols="12" class="d-flex align-center">
              <span class="text-subtitle-1 font-weight-medium mr-2"
                >身分證:</span
              >
              <span class="text-body-1 text-grey-darken-1">{{
                form.idno
              }}</span>
            </v-col>
          </v-row>

          <v-text-field
            label="手機號碼"
            v-model="form.phone"
            maxlength="10"
            :rules="[rules.required, rules.phone]"
          />

          <v-row class="mb-2">
            <v-col cols="12" class="d-flex align-center">
              <span class="text-subtitle-1 font-weight-medium mr-2">生日:</span>
              <span class="text-body-1 text-grey-darken-1">
                {{ new Date(form.birthDate).toLocaleDateString() }}
              </span>
            </v-col>
          </v-row>

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
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useAuthStore } from "@/member/stores/auth";
import * as api from "@/api/memberApi/UserApi.js";
import Swal from "sweetalert2";

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
  email: (v) => /.+@.+\..+/.test(v) || "Email 格式錯誤",
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
    Swal.fire({
      icon: "success",
      title: "修改完成",
      showConfirmButton: false,
      timer: 1000,
    });
  }
};

onMounted(() => {
  previewPhoto.value = authStore.memberPhoto;
});
</script>
