import { createRouter, createWebHistory } from "vue-router";
import FrontLayout from "@/layout/FrontLayout.vue";
import AdminLayout from "@/layout/AdminLayout.vue";
import FrontHome from "@/pages/FrontHome.vue";
import OrderListPage from "@/order/pages/OrderListPage.vue";

const routes = [
  {
    path: "/",
    component: FrontLayout,
    children: [
      { path: "", component: FrontHome },
      // { path: "xx", component:  },
    ],
  },
  {
    path: "/admin",
    component: AdminLayout,
    children: [
      // {
      //   path: "",
      //   component: AdminDashboard, // ⬅️ 進入後台管理時看到的主頁
      // },
      {
        path: "orders",
        component: OrderListPage,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
