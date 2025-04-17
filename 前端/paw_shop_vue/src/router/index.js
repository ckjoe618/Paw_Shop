import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/member/stores/auth";

const routes = [
  {
    path: "/",
    component: () => import("@/layout/FrontLayout.vue"),
    children: [],
  },
  {
    path: "/login",
    component: () => import("@/layout/LoginLayout.vue"),
    meta: { requiresGuest: true },
  },
  {
    path: "/admin",
    component: () => import("@/layout/AdminLayout.vue"),
    meta: { requiresAuth: true, requiresAdmin: true },
    children: [
      {
        path: "appointment",
        component: () => import("@/layout/LoginLayout.vue"),
      },
      {
        path: "orders",
        component: () => import("@/order/pages/OrderListPage.vue"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();

  if (to.meta.requiresAuth && !authStore.isLoggedIn) {
    // 未登入卻想進入需要登入的頁面
    return next("/login");
  }

  if (to.meta.requiresGuest && authStore.isLoggedIn) {
    // 已登入卻想進登入頁，導回首頁
    return next("/");
  }

  if (to.meta.requiresAdmin && authStore.role !== "ADMIN") {
    // 非管理員進入管理頁，導回首頁或 403
    return next("/");
  }

  return next();
});

export default router;
