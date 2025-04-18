import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/member/stores/auth";

const routes = [
  {
    path: "/",
    component: () => import("@/layout/AdminLayout.vue"),
    children: [
      { path: "", component: () => import("@/layout/FrontLayout.vue") },
      {
        path: "login",
        component: () => import("@/layout/LoginLayout.vue"),
        meta: { requiresGuest: true },
      },
      // { path: "xx", component:  },
      {
        path: 'toappointments',
        component: () => import("@/appointment/components/AppointmentFrontPage.vue")
      },
      {
        path: 'toappointments/reserve',
        component: () => import("@/appointment/components/AppointmentReserve.vue")
      },
      {
        path: 'toappointments/hendlereserve',
        component: () => import("@/appointment/components/AppointmentHendlePage.vue")
      },
      {
        path: 'toappointments/queryreserve',
        component: () => import("@/appointment/components/AppointmentQueryPage.vue")
      }
    ],
  },
  {
    path: "/admin",
    component: () => import("@/layout/AdminLayout.vue"),
    meta: { requiresAuth: true, requiresAdmin: true },
    children: [
      // {
      //   path: "",
      //   component: AdminDashboard, // ⬅️ 進入後台管理時看到的主頁
      // },
      {
        path: "orders",
        component: () => import("@/order/pages/OrderListPage.vue"),
      },
      {
        path: 'appointments',
        component: () => import("@/appointment/components/AppointmentList.vue")
      },
      {
        path: '/appointments/edit/:id',
        name: 'AppointmentEdit',
        component: () => import('@/appointment/components/AppointmentEditPage.vue')
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// router.beforeEach((to, from, next) => {
//   const authStore = useAuthStore();

//   if (to.meta.requiresAuth && !authStore.isLoggedIn) {
//     // 未登入卻想進入需要登入的頁面
//     return next("/login");
//   }

//   if (to.meta.requiresGuest && authStore.isLoggedIn) {
//     // 已登入卻想進登入頁，導回首頁
//     return next("/");
//   }

//   if (to.meta.requiresAdmin && authStore.role !== "ADMIN") {
//     // 非管理員進入管理頁，導回首頁或 403
//     return next("/");
//   }

//   return next();
// });

export default router;
