import { createWebHistory, createRouter } from 'vue-router';

import Main from './pages/main/main.vue';
import Cafe from './pages/cafe/cafe.vue';
import Cart from './pages/cart/cart.vue';
import List from './pages/list/list.vue';
import Pet from './pages/game/pet.vue';
import Album from './pages/album/album.vue';
import Guestbook from './pages/guestbook/guestbook.vue';

const routes = [
    {path: '/', name: "Main", component: Main },
    {path: '/cafe.page', name: "Cafe", component: Cafe },
    {path: '/cart.page', name: "Cart", component: Cart },
    {path: '/list.page', name: "List", component: List },
    {path: '/game/pet.page', name: "Pet", component: Pet },
    {path: '/album.page', name: "Album", component: Album },
    {path: '/guestbook.page', name: "Guestbook", component: Guestbook },
]

const AppRouter = createRouter({
    history: createWebHistory(),
    routes
})

export default AppRouter;