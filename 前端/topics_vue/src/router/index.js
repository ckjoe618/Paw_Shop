import { createRouter, createWebHistory } from "vue-router";

const routes = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      component: () => import("@/layouts/MainLayout.vue"),
    },
    {
      path: "/admin",
      component: () => import("@/layouts/AdminLayout.vue"),
    },
    {
      path: "/login",
      component: () => import("@/layouts/LoginLayout.vue"),
    },
    // {
    //   path: "/search",
    //   component: () => import("@/views/SearchResult.vue"),
    // },
    //     {
    //       path: "register",
    //       component: () => import("@/views/Register.vue"),
    //     },
    //     {
    //       path: "/:pathMatch(.*)*",
    //       component: () => import("@/views/NotFound.vue"),
    //     },
  ],
});

// routes.beforeEach((to, from, next) => {
//   const isAuthenticated = service.isAuthenticated();
//   if (to.meta.requiresAuth && !isAuthenticated) {
//     next("/login");
//   } else {
//     next();
//   }
// });
// routes.afterEach((to, from) => {});

export default routes;
