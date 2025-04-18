import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: localStorage.getItem("token") || "",
    memberId: localStorage.getItem("memberId") || "",
    memberName: localStorage.getItem("memberName") || "",
    role: localStorage.getItem("role") || "",
    photo: localStorage.getItem("photo") || "",
  }),
  actions: {
    login({ token, memberId, memberName, role, photo }) {
      this.token = token;
      this.memberId = memberId;
      this.memberName = memberName;
      this.role = role;
      this.photo = photo;

      // 儲存到 localStorage
      localStorage.setItem("token", token);
      localStorage.setItem("memberId", memberId);
      localStorage.setItem("memberName", memberName);
      localStorage.setItem("role", role);
      localStorage.setItem("photo", photo);
    },
    logout() {
      this.token = "";
      this.memberId = "";
      this.role = "";
      this.memberName = "";
      this.photo = "";
      localStorage.clear();
    },
  },
  getters: {
    isLoggedIn: (state) => !!state.token,
    isAdmin: (state) => state.role === "ADMIN",
  },
});
