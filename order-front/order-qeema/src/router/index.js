import { createRouter, createWebHistory } from 'vue-router';

import Products from '../views/Products.vue';
import CreateProduct from '../views/CreateProduct.vue';
import Orders from '../views/Orders.vue';
import Dashboard from '../views/Dashboard.vue';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue'; 

const routes = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        component: Login
    },
    {
        path: '/register',
        component: Register
    },
    {
        path: '/products',
        component: Products,
        meta: { requiresAuth: true } 
    },
    {
        path: '/dashboard',
        component: Dashboard,
        children: [
            {
                path: '',
                redirect: 'dashboard/products'
            },
            {
                path: 'products',
                component: Products,
                meta: { requiresAdmin: true } 
            },
            {
                path: 'create-product',
                component: CreateProduct,
                meta: { requiresAdmin: true } 
            },
            {
                path: 'orders',
                component: Orders,
                meta: { requiresAdmin: true } 
            }
        ]
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    const isAdmin = localStorage.getItem('role') === 'ADMIN';
    const isAuth = localStorage.getItem('role') === 'ADMIN' || localStorage.getItem('role') === 'USER';
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
    const requiresAdmin = to.matched.some(record => record.meta.requiresAdmin);

    if (requiresAdmin && !isAdmin) {
        next('/login');
    } else if (requiresAuth && !isAuth) {
        next('/login');
    } else {
        next(); 
    }
});

export default router;
