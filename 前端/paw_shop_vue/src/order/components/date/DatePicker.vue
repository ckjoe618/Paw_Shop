<template>
  <div>
    <v-text-field
      v-model="displayRange"
      label="選擇日期區間"
      prepend-icon="mdi-calendar"
      readonly
      density="compact"
      @click="showPicker = true"
    />

    <v-dialog v-model="showPicker" width="auto">
      <v-card>
        <v-card-text>
          <v-date-picker v-model="startDate" title="開始日期" />
          <v-divider class="my-2" />
          <v-date-picker v-model="endDate" title="結束日期" />
        </v-card-text>

        <v-card-actions>
          <v-spacer />
          <v-btn text @click="confirmDateRange">確認</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";

const emit = defineEmits(["update:modelValue"]);
const props = defineProps({
  modelValue: Object, // { start: 'yyyy-mm-dd', end: 'yyyy-mm-dd' }
});

// 工具：轉成 yyyy-mm-dd 格式
const formatDate = (date) => {
  if (!(date instanceof Date)) return "";
  return date.toISOString().split("T")[0];
};

const showPicker = ref(false);
const startDate = ref(
  props.modelValue?.start ? new Date(props.modelValue.start) : null
);
const endDate = ref(
  props.modelValue?.end ? new Date(props.modelValue.end) : null
);
const displayRange = ref("");

// 監聽 props 更新時同步顯示
watch(
  () => props.modelValue,
  (val) => {
    if (val?.start && val?.end) {
      startDate.value = new Date(val.start);
      endDate.value = new Date(val.end);
      displayRange.value = `${val.start} ~ ${val.end}`;
    }
  }
);

watch(
  () => props.modelValue,
  (val) => {
    startDate.value = val?.start ? new Date(val.start) : null;
    endDate.value = val?.end ? new Date(val.end) : null;
    displayRange.value =
      val?.start && val?.end ? `${val.start} ~ ${val.end}` : "";
  },
  { immediate: true, deep: true }
);

// 點擊「確認」按鈕時
const confirmDateRange = () => {
  if (startDate.value && endDate.value) {
    const formattedStart = formatDate(startDate.value);
    const formattedEnd = formatDate(endDate.value);

    displayRange.value = `${formattedStart} ~ ${formattedEnd}`;
    emit("update:modelValue", {
      start: formattedStart,
      end: formattedEnd,
    });
    showPicker.value = false;
  }
};
</script>
<style scoped>
.v-text-field {
  width: 300px;
}
</style>
