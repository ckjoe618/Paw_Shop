import { defineStore } from "pinia";

// 設定 pinia 的全域變數
const keys = [
  "token",
  "memberId",
  "memberName",
  "email",
  "phone",
  "role",
  "memberPhoto",
  "address",
];

export const useAuthStore = defineStore("auth", {
  state: () =>
    Object.fromEntries(
      keys.map((key) => {
        const raw = localStorage.getItem(key);
        try {
          return [key, JSON.parse(raw)];
        } catch {
          return [key, raw || ""];
        }
      })
    ),
  actions: {
    login(data) {
      keys.forEach((key) => {
        this[key] = data[key];
        const isObject = typeof data[key] === "object";
        localStorage.setItem(
          key,
          isObject ? JSON.stringify(data[key]) : data[key]
        );
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
