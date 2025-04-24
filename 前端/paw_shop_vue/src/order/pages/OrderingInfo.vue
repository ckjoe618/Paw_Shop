<template>
  <v-container>
    <div class="orderingInfo-container">
      <div class="d-flex justify-center mt-14 mb-10">
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
                <!-- 選擇超商 -->
                <v-col cols="12" v-if="pickupMethodforbtn !== '宅配'">
                  <v-btn color="green" @click="openCvsMap">選擇超商門市</v-btn>

                  <div v-if="cvsInfo" class="mt-2">
                    <p>門市名稱：{{ cvsInfo.CVSStoreName }}</p>
                    <p>門市地址：{{ cvsInfo.CVSAddress }}</p>
                  </div>
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
import { ref, watch, computed, onMounted } from "vue";
import router from "@/router";
import CityCountyData from "@/order/assets/data/CityCountyData.json";
import { useAuthStore } from "@/member/stores/auth";
const auth = useAuthStore();
import { useCheckoutStore } from "@/order/stores/checkoutStore";
const checkoutStore = useCheckoutStore();
import {
  apiECpay,
  apiCreateOrder,
  apiFindShoppingCartItem,
  apiDeleteAllShoppingCartItem,
} from "@/member/api/api";
import { loadCart } from "@/order/components/frontsite/useCart";

//訂購資料
const memberName = computed(() => auth.memberName);
const memberPhone = computed(() => auth.phone);
const memberEmail = computed(() => auth.email);
const memberAddress = computed(() => auth.fullAddress);

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

//選擇取貨門市
const pickupMethodforbtn = computed(() => checkoutStore.pickupMethod);
const cvsInfo = ref(null);

const openCvsMap = () => {
  const form = document.createElement("form");
  form.method = "POST";
  form.action = "https://logistics-stage.ecpay.com.tw/Express/map";
  form.target = "cvsMapWindow";

  const params = {
    MerchantID: "2000132",
    MerchantTradeNo: `TEST${Date.now()}`,
    LogisticsType: "CVS",
    LogisticsSubType: "UNIMART",
    IsCollection: "N",
    ServerReplyURL:
      "https://dde0-111-249-4-158.ngrok-free.app/ecpay/cvsMapReply",
  };

  for (const key in params) {
    const input = document.createElement("input");
    input.type = "hidden";
    input.name = key;
    input.value = params[key];
    form.appendChild(input);
  }

  document.body.appendChild(form);

  window.open("", "cvsMapWindow", "width=500,height=600");

  form.submit();
  document.body.removeChild(form);
};
// 接收綠界回傳門市資訊（window.postMessage）
onMounted(() => {
  window.addEventListener("message", (event) => {
    if (event.origin !== "https://emap.pcsc.com.tw") return;
    cvsInfo.value = {
      storeID: event.data.storeID,
      storeName: event.data.storeName,
      storeAddress: event.data.storeAddress,
    };
    console.log("綠界門市回傳：", cvsInfo.value);
  });
});

//與訂購人相同
watch(sameAsBuyer, (val) => {
  if (val) {
    recipientName.value = memberName.value;
    recipientPhone.value = memberPhone.value;
    address.value = {
      city: memberAddress.value.slice(3, 6),
      district: memberAddress.value.slice(6, 9),
      zipcode: "",
      detail: memberAddress.value.slice(9),
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

//按鈕下一步
const editCheckout = () => {
  router.push("/checkout");
};

const orderFinish = async () => {
  //新增訂單
  const orderData = {
    recipientName: recipientName.value,
    recipientPhone: recipientPhone.value,
    recipientAddress: `${address.value.city}${address.value.district}${address.value.detail}`,
    priceTotal: checkoutStore.priceTotal,
    paymentMethod: checkoutStore.paymentMethod,
    pickupMethod: checkoutStore.pickupMethod,
    shippingFee: checkoutStore.shippingFee,
    orderDetails: checkoutStore.cartItems.map((item) => ({
      product: { productId: item.product.productId },
      quantity: item.quantity,
    })),
  };
  console.log(orderData);
  const createRes = await apiCreateOrder(orderData);

  //清空購物車
  try {
    const res = await apiFindShoppingCartItem();
    const cartItemIds = res.data.map((item) => item.cartItemId);

    await apiDeleteAllShoppingCartItem(cartItemIds);
    await loadCart();
    console.log("購物車已清空");
  } catch (err) {
    console.error("清空購物車失敗", err);
  }

  //綠界
  const payment = checkoutStore.paymentMethod;
  if (payment === "信用卡") {
    const payRes = await apiECpay({
      amount: orderData.priceTotal,
      itemName: "Paw Shop 爪娃商店",
      orderId: createRes.data.orderId,
    });

    document.open();
    document.write(payRes.data);
    document.close();
  } else {
    router.replace("/orderFinish");
  }
};
</script>

<style scoped>
.orderingInfo-container {
  max-width: 900px;
  margin: 0 auto;
}
</style>
