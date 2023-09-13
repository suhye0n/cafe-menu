import { createWebHistory, createRouter } from 'vue-router';

import Main from './main/main.vue';
import Cafe from './cafe/cafe.vue';
import Cart from './cart/cart.vue';
import List from './list/list.vue';

const routes = [
    {path: '/', name: "Main", component: Main },
    {path: '/cafe.page', name: "Cafe", component: Cafe },
    {path: '/cart.page', name: "Cart", component: Cart },
    {path: '/list.page', name: "List", component: List },
]

const AppRouter = createRouter({
    history: createWebHistory(),
    routes
})

export default AppRouter;