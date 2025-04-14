import { defineStore } from "pinia";
import { ref } from "vue";

export const useMemberStore = defineStore(
  "memberStore",
  () => {
    const memebr = ref({
      token: "",
      id: "",
      name: "",
    });

    const login = (info) => {
      memebr.value = info;
    };

    const logout = () => {
      memebr.value = {
        token: "",
        id: "",
        name: "",
      };
    };
    return { memebr, login, logout };
  },
  {
    persist: true,
  }
);
