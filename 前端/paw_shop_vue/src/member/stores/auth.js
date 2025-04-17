import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: localStorage.getItem("token") || "",
    memberId: localStorage.getItem("memberId") || "",
    role: localStorage.getItem("role") || "",
    memberName: localStorage.getItem("memberName") || "",
  }),
  actions: {
    login({ token, memberId, role, memberName }) {
      this.token = token;
      this.memberId = memberId;
      this.role = role;
      this.memberName = memberName;

      // 儲存到 localStorage
      localStorage.setItem("token", token);
      localStorage.setItem("memberId", memberId);
      localStorage.setItem("role", role);
      localStorage.setItem("memberName", memberName);
    },
    logout() {
      this.token = "";
      this.memberId = "";
      this.role = "";
      this.memberName = "";
      localStorage.clear();
    },
  },
  getters: {
    isLoggedIn: (state) => !!state.token,
    isAdmin: (state) => state.role === "ADMIN",
  },
});
