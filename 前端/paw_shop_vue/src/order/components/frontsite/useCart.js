import { ref, computed } from "vue";
import { useAuthStore } from "@/member/stores/auth";
import { apiFindShoppingCartItem, apiAddShoppingCartItem } from "@/api/api";

export const cartItems = ref([]);

export const loadCart = async () => {
  const authStore = useAuthStore();

  if (authStore.isLoggedIn) {
    try {
      const res = await apiFindShoppingCartItem();
      cartItems.value.splice(0, cartItems.value.length, ...res.data);
    } catch (err) {
      console.error("後端購物車取得失敗", err);
      cartItems.value = [];
    }
  } else {
    const localCart = localStorage.getItem("cart");
    try {
      cartItems.value = localCart ? JSON.parse(localCart) : [];
    } catch (err) {
      console.error("localStorage 資料解析錯誤", err);
      cartItems.value = [];
    }
  }
};

export const totalCartQty = computed(() =>
  cartItems.value.reduce((sum, item) => sum + item.quantity, 0)
);

export async function syncCartToBackend() {
  const cart = JSON.parse(localStorage.getItem("cart")) || [];
  // console.log("同步購物車:", cart);
  if (cart.length === 0) return;

  try {
    const res = await apiAddShoppingCartItem(cart);
    console.log("同步成功:", res.data);

    localStorage.removeItem("cart");
  } catch (err) {
    console.error("同步失敗:", err);
  }
}
