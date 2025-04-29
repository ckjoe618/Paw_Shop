<template>
  <v-container class="mt-10 d-flex justify-center">
    <v-card width="900" elevation="10" rounded="xl">
      <v-row no-gutters>
        <!-- 📋 表單區 -->
        <v-col cols="12" md="7" class="pa-6">
          <v-card-title class="text-h5 font-weight-bold mb-4">
            <v-icon class="mr-2" color="primary">mdi-account-plus</v-icon>
            會員註冊
          </v-card-title>

          <v-form ref="formRef" v-model="isValid">
            <v-row dense>
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="form.memberName"
                  label="姓名"
                  prepend-inner-icon="mdi-account"
                  :rules="[rules.required]"
                  variant="outlined"
                  clearable
                />
              </v-col>

              <v-col cols="12" md="6">
                <v-select
                  v-model="form.gender"
                  :items="['男', '女']"
                  label="性別"
                  prepend-inner-icon="mdi-gender-male-female"
                  :rules="[rules.required]"
                  variant="outlined"
                  clearable
                />
              </v-col>

              <v-col cols="12" md="6">
                <v-text-field
                  v-model="form.idno"
                  label="身分證字號"
                  prepend-inner-icon="mdi-card-account-details"
                  :rules="[rules.required, rules.idno, rules.idnoGender]"
                  variant="outlined"
                  maxlength="10"
                  clearable
                />
              </v-col>

              <v-col cols="12" md="6">
                <v-text-field
                  v-model="form.email"
                  label="Email"
                  prepend-inner-icon="mdi-email"
                  :rules="[rules.required, rules.email]"
                  variant="outlined"
                  clearable
                />
              </v-col>

              <v-col cols="12" md="6">
                <v-text-field
                  v-model="form.phone"
                  label="手機"
                  prepend-inner-icon="mdi-cellphone"
                  :rules="[rules.required, rules.phone]"
                  variant="outlined"
                  maxlength="10"
                  clearable
                />
              </v-col>

              <v-col cols="12" md="6">
                <v-text-field
                  v-model="form.account"
                  label="帳號"
                  prepend-inner-icon="mdi-account-circle"
                  :rules="[rules.required]"
                  variant="outlined"
                  clearable
                />
              </v-col>

              <v-col cols="12" md="6">
                <v-text-field
                  v-model="form.password"
                  label="密碼"
                  prepend-inner-icon="mdi-lock"
                  :type="'password'"
                  :rules="[rules.required, rules.min(6)]"
                  variant="outlined"
                  clearable
                />
              </v-col>

              <v-col cols="12" md="6">
                <v-text-field
                  v-model="form.confirmPassword"
                  label="確認密碼"
                  prepend-inner-icon="mdi-lock-check"
                  :type="'password'"
                  :rules="[rules.required, rules.matchPassword]"
                  variant="outlined"
                  clearable
                />
              </v-col>

              <v-col cols="12" md="6">
                <v-text-field
                  v-model="form.birthDate"
                  label="出生日期"
                  type="date"
                  prepend-inner-icon="mdi-cake-variant"
                  :rules="[rules.required]"
                  variant="outlined"
                  min="'1990-01-01'"
                  :max="today"
                />
              </v-col>

              <v-col cols="12" class="text-center">
                <v-btn
                  color="primary"
                  block
                  size="large"
                  class="mt-4 font-weight-bold text-white"
                  :loading="loading"
                  :disabled="!isValid"
                  @click="submit"
                >
                  <v-icon start>mdi-checkbox-marked-circle-outline</v-icon>
                  註冊
                </v-btn>
                <v-btn
                  color="success"
                  block
                  size="large"
                  class="mt-2 font-weight-bold text-white"
                  @click="oneClick"
                >
                  <v-icon start>mdi-flash</v-icon>
                  一鍵輸入
                </v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-col>

        <!-- 🖼️ 輪播圖區 -->
        <v-col
          cols="12"
          md="5"
          class="pa-0"
          style="
            overflow: hidden;
            border-top-right-radius: 1rem;
            border-bottom-right-radius: 1rem;
          "
        >
          <v-carousel
            height="100%"
            hide-delimiters
            cycle
            show-arrows="hover"
            :model-value="startIndex"
            class="carousel-no-radius"
          >
            <v-carousel-item v-for="(img, index) in images" :key="index">
              <v-img
                :src="img.src"
                cover
                height="100%"
                width="100%"
                class="hover-scale"
              />
            </v-carousel-item>
          </v-carousel>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script setup>
import dog01 from "@/member/assets/images/註冊_狗01.png";
import cat01 from "@/member/assets/images/註冊_貓01.png";
import dog02 from "@/member/assets/images/註冊_狗02.png";
import cat02 from "@/member/assets/images/註冊_貓02.png";
import { ref, onMounted } from "vue";
import * as api from "@/api/memberApi/UserApi.js";
import router from "@/router";

const today = new Date().toISOString().split("T")[0];
const formRef = ref(null);
const startIndex = ref(0);
const isValid = ref(false);
const loading = ref(false);
const form = ref({
  memberName: "",
  gender: "",
  idno: "",
  email: "",
  phone: "",
  birthDate: "",
  account: "",
  password: "",
  confirmPassword: "",
});

const images = ref([
  { src: dog01 },
  { src: cat01 },
  { src: dog02 },
  { src: cat02 },
]);

const rules = {
  required: (v) => !!v || "此欄位必填",
  email: (v) => /.+@.+\..+/.test(v) || "Email 格式錯誤",
  phone: (v) => /^09\d{8}$/.test(v) || "手機號碼格式錯誤",
  idno: (v) => /^[A-Z][12]\d{8}$/.test(v) || "身分證格式錯誤",
  min: (n) => (v) => (v && v.length >= n) || `至少輸入 ${n} 字`,
  matchPassword: (v) => v === form.value.password || "密碼不一致",
  idnoGender: (v) => {
    if (!v || v.length < 2 || !form.value.gender) {
      return true;
    }
    const secondChar = v.charAt(1);
    if (form.value.gender === "男" && secondChar !== "1")
      return "男生身分證第2碼必須是1";
    if (form.value.gender === "女" && secondChar !== "2")
      return "女生身分證第2碼必須是2";
    return true;
  },
};

const submit = async () => {
  const { valid } = await formRef.value.validate();
  if (!valid) {
    return;
  }
  loading.value = true;
  try {
    const data = await api.apiAddMember(form.value);
    router.push("/login");
  } finally {
    loading.value = false;
  }
};

const oneClick = () => {
  const timestamp = Date.now();
  const uniqueSuffix = String(timestamp).slice(-6); // 取時間戳後6碼
  form.value = {
    memberName: "陳之漢",
    gender: "男",
    idno: `A1${uniqueSuffix.padEnd(8, "0")}`, // 身分證：A1+6碼+補0
    email: `test${timestamp}@example.com`,
    phone: `09${uniqueSuffix.padEnd(8, "1")}`, // 手機：09+6碼+補1
    birthDate: "1995-05-05",
    account: `testuser${timestamp}`,
    password: "123456",
    confirmPassword: "123456",
  };
};

onMounted(() => {
  startIndex.value = Math.floor(Math.random() * images.value.length);
});
</script>

<style scoped>
.carousel-no-radius {
  border-top-right-radius: 1rem;
  border-bottom-right-radius: 1rem;
  overflow: hidden;
}

.hover-scale {
  transition: transform 0.4s ease;
}
.hover-scale:hover {
  transform: scale(1.05);
}
</style>
