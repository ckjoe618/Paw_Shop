<template>
  <v-container>
    <div class="orderingInfo-container">
      <div class="d-flex justify-center mt-8 mb-10">
        <img src="@/order/assets/pictures/step1.jpg" />
        <img src="@/order/assets/pictures/step2ing.jpg" />
        <img src="@/order/assets/pictures/step3.jpg" />
      </div>
      <!-- 訂購人資料 -->
      <h4><v-icon class="mr-2">mdi-account</v-icon>訂購人資料</h4>
      <v-container>
        <v-row>
          <!-- 會員姓名 -->
          <v-col cols="12" md="6">
            <v-text-field label="姓名" v-model="memberName" disabled />
          </v-col>
          <!-- 會員手機號碼 -->
          <v-col cols="12" md="6">
            <v-text-field label="手機號碼" v-model="memberPhone" disabled />
          </v-col>
          <!-- 會員信箱 -->
          <v-col cols="12">
            <v-text-field label="信箱" v-model="memberEmail" disabled />
          </v-col>
          <!-- 會員地址 -->
          <v-col cols="12">
            <v-text-field
              label="地址"
              v-model="memberAddress"
              prepend-icon="mdi-map-marker"
              disabled
            />
          </v-col>
        </v-row>
      </v-container>

      <v-divider class="my-4" />
      <!-- 收件人資料 -->
      <v-row align="center" class="mb-2">
        <v-col cols="auto">
          <h4 class="d-flex align-center">
            <v-icon class="mr-2">mdi-package-variant-closed-check</v-icon>
            收件人資料
          </h4>
        </v-col>
        <v-col cols="auto">
          <v-checkbox
            v-model="sameAsBuyer"
            label="與訂購人相同"
            density="compact"
            hide-details
            color="success"
          />
        </v-col>
      </v-row>
      <v-form ref="formRef" v-model="formIsValid" lazy-validation>
        <v-container>
          <v-row>
            <!-- 中文姓名 -->
            <v-col cols="12" md="6">
              <v-text-field
                label="姓名"
                v-model="recipientName"
                :rules="[(v) => !!v || '※需填姓名']"
                required
              />
            </v-col>
            <!-- 手機號碼 -->
            <v-col cols="12" md="6">
              <v-text-field
                label="手機號碼"
                v-model="recipientPhone"
                :rules="[
                  (v) => !!v || '※請填寫手機號碼',
                  (v) => /^\d{10}$/.test(v) || '※需為 10 碼數字手機格式',
                ]"
                required
              />
            </v-col>
            <!-- 收貨地址 -->
            <v-col cols="12">
              <v-row>
                <v-col cols="12" md="4">
                  <v-select
                    label="縣市"
                    v-model="address.city"
                    prepend-icon="mdi-map-marker"
                    :items="cityOptions"
                    :rules="[(v) => !!v || '※請選擇縣市']"
                  />
                </v-col>
                <v-col cols="12" md="4">
                  <v-select
                    label="鄉鎮市區"
                    v-model="address.district"
                    :items="districtOptions"
                    :rules="[(v) => !!v || '※請選擇區域']"
                  />
                </v-col>
                <v-col cols="12" md="4">
                  <v-text-field
                    label="郵遞區號"
                    v-model="address.zipcode"
                    :readonly="true"
                  />
                </v-col>
                <v-col>
                  <v-text-field
                    label="詳細地址"
                    v-model="address.detail"
                    :rules="[(v) => !!v || '※請填寫地址']"
                  />
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-container>

        <div class="d-flex justify-end mt-12" style="gap: 20px">
          <v-btn
            color="#F0F0F0"
            class="mt-6"
            elevation="0"
            @click="editCheckout"
            >返回修改</v-btn
          >
          <v-btn color="#006000" class="mt-6" elevation="0" @click="orderFinish"
            >下一步</v-btn
          >
        </div>
      </v-form>
    </div>
  </v-container>
</template>

<script setup>
import { ref, watch } from "vue";
import router from "@/router";
import CityCountyData from "@/order/assets/data/CityCountyData.json";

//訂購資料
const memberName = ref("王小明");
const memberPhone = ref("0912345678");
const memberEmail = ref("m@example.com");
const memberAddress = ref("臺北市中正區仁愛路一段100號");

//收件資料
const formRef = ref(null);
const formIsValid = ref(false);

const sameAsBuyer = ref(false);
const recipientName = ref("");
const recipientPhone = ref("09");

const cities = CityCountyData.map((city) => city.CityName);
const cityOptions = ref(cities);
const districtOptions = ref([]);
const address = ref({
  city: "",
  district: "",
  zipcode: "",
  detail: "",
});

//與訂購人相同
watch(sameAsBuyer, (val) => {
  if (val) {
    recipientName.value = memberName.value;
    recipientPhone.value = memberPhone.value;
    address.value = {
      city: memberAddress.value.slice(0, 3),
      district: memberAddress.value.slice(3, 6),
      zipcode: "",
      detail: memberAddress.value.slice(6),
    };
  } else {
    recipientName.value = "";
    recipientPhone.value = "";
    address.value = {
      city: "",
      district: "",
      zipcode: "",
      detail: "",
    };
  }
});
watch(
  () => [address.value.city, address.value.district],
  ([city, district]) => {
    const matchedCity = CityCountyData.find((c) => c.CityName === city);
    const matchedArea = matchedCity?.AreaList.find(
      (a) => a.AreaName === district
    );
    address.value.zipcode = matchedArea?.ZipCode || "";
  }
);
// 地址選擇
watch(
  () => address.value.city,
  (newCity) => {
    const targetCity = CityCountyData.find((city) => city.CityName === newCity);
    districtOptions.value =
      targetCity?.AreaList.map((area) => area.AreaName) || [];
  }
);
//選擇鄉鎮區後自動帶入郵遞區號
watch(
  () => address.value.district,
  (newDistrict) => {
    const targetCity = CityCountyData.find(
      (c) => c.CityName === address.value.city
    );
    const targetDistrict = targetCity?.AreaList.find(
      (d) => d.AreaName === newDistrict
    );
    address.value.zipcode = targetDistrict?.ZipCode || "";
  }
);

//表單送出前驗證
const submitOrder = async () => {
  const valid = await formRef.value.validate();
  if (valid) {
    console.log("表單驗證成功，可送出");
  } else {
    console.warn("請確認欄位是否填寫正確");
  }
};

//按鈕下一步
const editCheckout = () => {
  router.back();
};
const orderFinish = async () => {
  router.push("/OrderFinish");
};
</script>

<style scoped>
.orderingInfo-container {
  max-width: 900px;
  margin: 0 auto;
}
</style>
