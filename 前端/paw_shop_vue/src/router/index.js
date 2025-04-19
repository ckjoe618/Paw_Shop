import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/member/stores/auth";

const routes = [
  {
    path: "/login",
    component: () => import("@/layout/LoginLayout.vue"),
    meta: { requiresGuest: true },
  },
  {
    path: "/register",
    component: () => import("@/layout/RegisterLayout.vue"),
    meta: { requiresGuest: true },
  },
  {
    path: "/",
    component: () => import("@/layout/FrontLayout.vue"),
    children: [
      {
        path: "",
        redirect: "home",
      },
      {
        path: "home",
        component: () => import("@/pages/FrontHome.vue"),
      },
      {
        path: "appointments",
        component: () => import("@/appointment/pages/AppointmentFrontPage.vue"),
      },
      {
        path: "appointments/reserve",
        component: () => import("@/appointment/pages/AppointmentReserve.vue"),
      },
      {
        path: "appointments/hendlereserve",
        component: () =>
          import("@/appointment/pages/AppointmentHendlePage.vue"),
      },
      {
        path: "appointments/queryreserve",
        component: () => import("@/appointment/pages/AppointmentQueryPage.vue"),
      },
      {
        path: "checkout",
        component: () => import("@/order/pages/Checkout.vue"),
        meta: { requiresAuth: true },
      },
      {
        path: "OrderingInfo",
        component: () => import("@/order/pages/OrderingInfo.vue"),
        meta: { requiresAuth: true },
      },
      {
        path: "checkin",
        component: () => import("@/appointment/components/CheckinPage.vue"),
      },
    ],
  },
  {
    path: "/admin",
    component: () => import("@/layout/AdminLayout.vue"),
    meta: { requiresAuth: true, requiresAdmin: true },
    children: [
      {
        path: "",
        redirect: "/admin/home",
      },
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
        component: () => import("@/member/pages/AdminMemberPage.vue"),
      },
      {
        path: "products",
        component: () => import("@/product/backsite/ProductListPage.vue"),
      },
      {
        path: "appointments",
        component: () => import("@/appointment/pages/AppointmentList.vue"),
      },
      {
        path: "appointments/edit/:id",
        component: () => import("@/appointment/pages/AppointmentEditPage.vue"),
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
    return next({
      path: "/login",
      query: { redirect: to.fullPath },
    });
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
