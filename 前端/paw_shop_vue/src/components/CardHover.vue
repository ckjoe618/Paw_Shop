<template>
  <v-container fluid>
    <v-row>
      <v-col
        v-for="item in items"
        :key="item.title"
        cols="12"
        sm="6"
        md="4"
        lg="4"
      >
        <v-card
          class="mx-auto hover-scale"
          max-width="400"
          elevation="6"
          rounded="xl"
        >
          <v-img :src="item.image" height="160" cover class="rounded-t-xl" />
          <v-card-item>
            <v-card-title class="text-center text-h5 font-weight-bold">
              {{ item.title }}
            </v-card-title>
          </v-card-item>

          <v-divider class="mx-4 mb-2"></v-divider>

          <v-card-actions class="d-flex flex-column align-center px-4 pb-4">
            <v-btn
              v-for="button in item.button"
              :key="button.label"
              :to="button.link"
              variant="outlined"
              color="primary"
              class="my-1 w-100 text-body-1"
            >
              {{ button.label }}
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref } from "vue";

const items = ref([
  {
    title: "使用者",
    icon: "fas fa-user",
    image: "https://cdn.vuetifyjs.com/images/cards/foster.jpg",
    button: [
      { label: "用戶管理", link: "/admin/member" },
      { label: "角色權限", link: "" },
    ],
  },
  {
    title: "賣場",
    icon: "fas fa-store",
    image: "https://cdn.vuetifyjs.com/images/cards/house.jpg",
    button: [
      { label: "商品管理", link: "" },
      { label: "訂單管理", link: "/admin/orders" },
    ],
  },
  {
    title: "美容預約",
    icon: "fas fa-cut",
    image: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
    button: [
      { label: "預約紀錄", link: "" },
      { label: "服務項目資訊", link: "" },
    ],
  },
  {
    title: "論壇",
    icon: "fas fa-comments",
    image: "https://cdn.vuetifyjs.com/images/cards/road.jpg",
    button: [
      { label: "文章管理", link: "" },
      { label: "分類", link: "" },
    ],
  },
  {
    title: "首頁內容",
    icon: "fas fa-home",
    image: "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
    button: [
      { label: "最新活動", link: "" },
      { label: "公司資訊", link: "" },
      { label: "常見問答", link: "" },
    ],
  },
]);
</script>

<style scoped>
.hover-scale {
  transition: transform 0.25s ease, box-shadow 0.25s ease;
}
.hover-scale:hover {
  transform: scale(1.03);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}
</style>
