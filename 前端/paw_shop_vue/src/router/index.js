// import { createRouter, createWebHistory } from 'vue-router'
// import OrderLayout from '../../src/order/components/backsite/layout/Orderlayout.vue'
// import OrderListPage from '../../src/order/pages/OrderListPage.vue'

// const routes = [
//   {
//     path: '/orders',
//     component: OrderListPage,
//   }
// ]

// const router = createRouter({
//   history: createWebHistory(),
//   routes
// })

// export default router
import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/member/stores/auth";

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import("@/member/views/HomeView.vue"),
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("@/member/views/LoginView.vue"),
    meta: { requiresGuest: true }, // ✅ 僅未登入可進入
  },
  // {
  //   path: "/dashboard",
  //   name: "Dashboard",
  //   component: () => import("@/member/views/DashboardView.vue"),
  //   meta: { requiresAuth: true }, // ✅ 需登入才可進入
  // },
  {
    path: "/admin",
    name: "Admin",
    component: () => import("@/member/views/AdminView.vue"),
    meta: { requiresAuth: true, requiresAdmin: true }, // ✅ 限管理員
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
