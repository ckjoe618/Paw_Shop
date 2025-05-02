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
            v-model="localAddress.recipientPhone"
            maxlength="10"
            :rules="[rules.required, rules.phone]"
          />
          <v-text-field
            label="郵遞區號"
            v-model="localAddress.zipcode"
            :rules="[rules.required, rules.zipcode]"
            readonly
          />
          <v-select
            label="城市"
            v-model="localAddress.city"
            :items="county"
            :rules="[rules.required]"
            @update:modelValue="onCityChange"
          />
          <v-select
            label="區域"
            v-model="localAddress.district"
            :items="districts"
            :rules="[rules.required]"
            :disabled="!localAddress.city"
            @update:modelValue="onDistrictChange"
          />
          <v-text-field
            label="詳細地址"
            ref="focusRef"
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
        <v-btn
          color="success"
          class="font-weight-bold text-white"
          @click="oneClick"
        >
          一鍵輸入
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { addressList } from "@/member/assets/city";
import { ref, watch, computed, nextTick } from "vue";
import { useAuthStore } from "@/member/stores/auth";
import * as api from "@/api/memberApi/UserApi";
import { getRandomNumber, getRandomName } from "@/member/assets/GetRandom";
import Swal from "sweetalert2";

const authStore = useAuthStore();
const formRef = ref(null);
const isValid = ref(false);
const loading = ref(false);
const localAddress = ref({});
const county = ref([...new Set(addressList.map((item) => item.county))]);
const districts = ref([]);
const focusRef = ref(false);

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
  async (val) => {
    if (val) {
      localAddress.value = { ...props.address };
      if (localAddress.value.city) {
        updateDistricts(localAddress.value.city);
      }
    } else {
      await nextTick(); // 等待 DOM 清空完畢後再重設
      formRef.value?.resetValidation();
      localAddress.value = {};
      districts.value = [];
      focusRef.value = false;
    }
  },
  { immediate: true }
);

// 切換城市時，更新對應地區與郵遞區號
const onCityChange = (county) => {
  localAddress.value.city = county;
  updateDistricts(county);
  // 如果只有一個區，直接選起來＋自動帶zipcode
  if (districts.value.length === 1) {
    localAddress.value.district = districts.value[0];
    onDistrictChange(districts.value[0]);
  } else {
    localAddress.value.district = "";
    localAddress.value.zipcode = "";
  }
};

// 更新區域選單
const updateDistricts = (county) => {
  districts.value = addressList
    .filter((item) => item.county === county)
    .map((item) => item.city);
};

// 選區域時，自動帶入郵遞區號
const onDistrictChange = (districtName) => {
  const county = localAddress.value.city;
  const found = addressList.find(
    (item) => item.county === county && item.city === districtName
  );
  localAddress.value.zipcode = found ? found.zipcode : "";
  // 自動聚焦到詳細地址欄
  setTimeout(() => {
    focusRef.value?.focus();
  });
};

const close = () => {
  document.activeElement?.blur();
  emit("update:dialog", false);
};

const save = async () => {
  const valid = await formRef.value.validate();
  if (!valid) {
    return;
  }
  loading.value = true;
  try {
    if (props.mode === "add") {
      const data = await api.apiAddAddress(localAddress.value);
      if (data.defaultStatus) {
        authStore.update({ address: data });
      }
      Swal.fire({
        icon: "success",
        title: "新增完成",
        showConfirmButton: false,
        timer: 1000,
      });
    } else {
      const data = await api.apiUpdateAddress(localAddress.value);
      if (data.defaultStatus) {
        authStore.update({ address: data });
      }
      Swal.fire({
        icon: "success",
        title: "修改完成",
        showConfirmButton: false,
        timer: 1000,
      });
    }
    emit("saved");
    close();
  } finally {
    loading.value = false;
  }
};

const oneClick = () => {
  const randomAddress =
    addressList[Math.floor(Math.random() * addressList.length)];
  localAddress.value = {
    ...localAddress.value,
    recipientName: getRandomName(),
    recipientPhone: "09" + getRandomNumber(8),
    city: randomAddress.county,
    district: randomAddress.city,
    zipcode: randomAddress.zipcode,
    addressDetail: "中山路" + getRandomNumber(3) + "號",
  };
  updateDistricts(randomAddress.county);
  onDistrictChange(randomAddress.city);
};
</script>
