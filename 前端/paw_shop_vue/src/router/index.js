import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/member/stores/auth";

const routes = [
  {
    path: "/login",
    component: () => import("@/layout/LoginLayout.vue"),
    meta: { requiresGuest: true },
  },
  {
    path: "/",
    component: () => import("@/layout/FrontLayout.vue"),
    children: [],
  },
  {
    path: "/admin",
    component: () => import("@/layout/AdminLayout.vue"),
    meta: { requiresAuth: true, requiresAdmin: true },
    children: [
      {
        path: "home",
        component: () => import("@/components/CardHover.vue"),
      },
      {
        path: "orders",
        component: () => import("@/order/pages/OrderListPage.vue"),
      },
      {
        path: "member",
        component: () => import("@/member/components/MemberTable.vue"),
      },
      {
        path: '/admin/products',
        component: () => import('@/product/backsite/ProductListPage.vue')
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

  if (to.meta.requiresAdmin) {
    if (!authStore.isLoggedIn) {
      return next("/login");
    }
    if (authStore.role !== "ADMIN") {
      return next("/403"); // 沒權限
    }
  }

  return next();
});

export default router;
