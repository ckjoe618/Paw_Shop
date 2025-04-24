import { defineStore } from "pinia";

// 設定 pinia 的全域變數
const keys = [
  "token",
  "memberId",
  "memberName",
  "gender",
  "idno",
  "email",
  "phone",
  "birthDate",
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
          return [key, key === "address" ? {} : raw || ""];
        }
      })
    ),
  actions: {
    /**
     * 登入時寫入所有欄位並同步到 localStorage
     */
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

    /**
     * 登出時清除所有欄位與 localStorage
     */
    logout() {
      keys.forEach((key) => {
        this[key] = key === "address" ? {} : "";
        localStorage.removeItem(key);
      });
    },

    /**
     * 更新任意欄位並同步 localStorage（單欄位或多欄位都行）
     */
    update(data) {
      Object.entries(data).forEach(([key, value]) => {
        if (keys.includes(key)) {
          this[key] = value;
          const isObject = typeof value === "object";
          localStorage.setItem(key, isObject ? JSON.stringify(value) : value);
        }
      });
    },
  },
  getters: {
    isLoggedIn: (state) => !!state.token,
    isAdmin: (state) => state.role === "ADMIN",
    fullAddress: (state) => {
      const addr = state.address || {};
      return `${addr.zipcode || ""}${addr.city || ""}${addr.district || ""}${
        addr.addressDetail || ""
      }`;
    },
  },
});
