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
            :items="cities"
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
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { cityList } from "@/member/assets/city.js";
import { addressList } from "@/member/assets/zipcodes.js";
import { ref, watch, computed } from "vue";
import * as api from "@/api/memberApi/UserApi";
import { useAuthStore } from "../stores/auth";
import Swal from "sweetalert2";

const auth = useAuthStore();

const formRef = ref(null);
const isValid = ref(false);
const loading = ref(false);
const localAddress = ref({});
const cities = ref(cityList.map((city) => city.name));
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
  (val) => {
    if (val) {
      localAddress.value = { ...props.address };
      if (localAddress.value.city) {
        updateDistricts(localAddress.value.city);
      }
    } else {
      setTimeout(() => {
        formRef.value?.resetValidation();
        localAddress.value = {};
        districts.value = [];
        focusRef.value = false;
      });
    }
  },
  { immediate: true }
);

// 選城市時，更新區域下拉
const onCityChange = (cityName) => {
  localAddress.value.city = cityName;
  updateDistricts(cityName);
  // 如果只有一個區，直接選起來＋自動帶zipcode
  if (districts.value.length === 1) {
    localAddress.value.district = districts.value[0];
    const found = addressList.find(
      (item) => item.county === cityName && item.city === districts.value[0]
    );
    localAddress.value.zipcode = found ? found.zipcode : "";
  } else {
    localAddress.value.district = "";
    localAddress.value.zipcode = "";
  }
};

// 更新區域選單
const updateDistricts = (cityName) => {
  districts.value = addressList
    .filter((item) => item.county === cityName)
    .map((item) => item.city);
};

// 選區域時，自動帶入郵遞區號
const onDistrictChange = (districtName) => {
  const cityName = localAddress.value.city;
  const found = addressList.find(
    (item) => item.county === cityName && item.city === districtName
  );
  localAddress.value.zipcode = found ? found.zipcode : "";
  // 自動聚焦到詳細地址欄
  setTimeout(() => {
    focusRef.value?.focus();
  });
};

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
      Swal.fire({
        icon: "success",
        title: "新增完成",
        showConfirmButton: false,
        timer: 1000,
      });
    } else {
      await api.apiUpdateAddress(localAddress.value);
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
</script>
