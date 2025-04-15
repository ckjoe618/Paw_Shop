import { createRouter, createWebHistory } from 'vue-router'
import OrderLayout from '../../src/order/components/backsite/layout/Orderlayout.vue'
import OrderListPage from '../../src/order/pages/OrderListPage.vue'

const routes = [
  {
    path: '/orders',
    component: OrderListPage,
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router