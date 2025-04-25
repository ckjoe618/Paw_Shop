<template>
  <v-card>
    <v-card-text>
      <div>收件人：{{ address.recipientName }}（{{ address.phone }}）</div>
      <div>
        地址：{{ address.zipcode }} {{ address.city }}{{ address.district
        }}{{ address.addressDetail }}
      </div>
    </v-card-text>
    <v-card-actions>
      <v-btn icon @click="$emit('edit', address)">
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
      <v-btn icon @click="$emit('delete', address)">
        <v-icon>mdi-delete</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup>
defineProps({
  address: Object,
});
</script>
