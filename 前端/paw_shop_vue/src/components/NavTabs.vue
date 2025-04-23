<template>
  <v-tabs background-color="grey lighten-4" grow hide-slider>
    <v-tab
      v-for="item in items"
      :key="item.label"
      class="text-subtitle-2 text-capitalize"
      :to="item.link"
      replace
    >
      <i v-if="iconMap[item.label]" :class="iconMap[item.label] + ' mr-2'"></i>
      {{ item.label }}
    </v-tab>
  </v-tabs>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  items: {
    type: Array,
    required: true,
  },
});

const iconMap = {
  所有商品: "fa-solid fa-bag-shopping",
  狗狗: "fas fa-dog",
  貓咪: "fas fa-cat",
  小型寵物: "fas fa-otter",
  熱銷品牌: "fas fa-star",
  促銷活動: "fas fa-tags",
  美容預約: "fa-solid fa-calendar-check",
  寵物討論區: "fa-solid fa-comment",
};
</script>

<style scoped>
i {
  font-size: 16px;
  vertical-align: middle;
  color: #444;
}
</style>
