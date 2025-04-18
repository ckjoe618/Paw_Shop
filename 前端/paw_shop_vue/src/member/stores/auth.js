import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: localStorage.getItem("token") || "",
    memberId: localStorage.getItem("memberId") || "",
    memberName: localStorage.getItem("memberName") || "",
    role: localStorage.getItem("role") || "",
    memberPhoto: localStorage.getItem("memberPhoto") || "",
  }),
  actions: {
    login({ token, memberId, memberName, role, memberPhoto }) {
      this.token = token;
      this.memberId = memberId;
      this.memberName = memberName;
      this.role = role;
      this.memberPhoto = memberPhoto;

      // 儲存到 localStorage
      localStorage.setItem("token", token);
      localStorage.setItem("memberId", memberId);
      localStorage.setItem("memberName", memberName);
      localStorage.setItem("role", role);
      localStorage.setItem("memberPhoto", memberPhoto);
    },
    logout() {
      this.token = "";
      this.memberId = "";
      this.role = "";
      this.memberName = "";
      this.memberPhoto = "";
      localStorage.clear();
    },
  },
  getters: {
    isLoggedIn: (state) => !!state.token,
    isAdmin: (state) => state.role === "ADMIN",
  },
});
