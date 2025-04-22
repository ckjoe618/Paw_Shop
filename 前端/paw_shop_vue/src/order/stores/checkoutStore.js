import { defineStore } from "pinia";

export const useCheckoutStore = defineStore("checkout", {
  state: () => ({
    cartItems: [],
    paymentMethod: "",
    pickupMethod: "",
    shippingFee: 0,
    priceTotal: 0,
  }),
});
