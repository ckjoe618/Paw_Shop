<template>
  <v-card class="pa-2" width="330">
    <v-table density="compact">
      <thead>
        <tr>
          <th>商品名稱</th>
          <th>數量</th>
          <th class="text-right">小計</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in items" :key="index">
          <td>
            <router-link
              :to="{
                name: 'ProductDetailPage',
                params: { id: item.product.productId },
              }"
              style="color: black"
              @click="emit('closeMenu')"
            >
              {{ item.product.productName }}
            </router-link>
          </td>
          <td
            style="
              text-align: center;
              width: 60px;
              white-space: nowrap;
              vertical-align: top;
            "
          >
            {{ item.quantity }}
          </td>
          <td
            style="width: 80px; white-space: nowrap; vertical-align: top"
            class="text-right"
          >
            ${{ item.product.productPrice * item.quantity }}
          </td>
        </tr>
      </tbody>
    </v-table>
    <v-divider class="my-2" />
    <div class="d-flex justify-center mt-4">
      <v-btn
        width="100%"
        rounded="lg"
        elevation="6"
        style="
          background-color: #215d1e;
          color: rgb(244, 243, 243);
          text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
        "
        @click="checkout"
        >去結帳！</v-btn
      >
    </div>
  </v-card>
</template>

<script setup>
import router from "@/router";

defineProps({
  items: Array,
});
const emit = defineEmits(["closeMenu"]);

const checkout = () => {
  router.push("/checkout");
  emit("closeMenu");
};
</script>
<style scoped></style>
