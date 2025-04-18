import { defineStore } from "pinia";

// 設定 pinia 的全域變數
const keys = ["token", "memberId", "memberName", "role", "memberPhoto"];

export const useAuthStore = defineStore("auth", {
  state: () =>
    Object.fromEntries(
      keys.map((key) => [key, localStorage.getItem(key) || ""])
    ),
  actions: {
    login(data) {
      keys.forEach((key) => {
        this[key] = data[key];
        localStorage.setItem(key, data[key]);
      });
    },
    logout() {
      keys.forEach((key) => {
        this[key] = "";
        localStorage.removeItem(key);
      });
    },
  },
  getters: {
    isLoggedIn: (state) => !!state.token,
    isAdmin: (state) => state.role === "ADMIN",
  },
});
