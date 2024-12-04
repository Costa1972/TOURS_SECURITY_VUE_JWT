import { createApp } from 'vue';
import App from './App.vue';
import { createRouter, createWebHistory } from 'vue-router';
import RegisterComponent from './components/RegisterComponent.vue';
import LoginComponent from './components/LoginComponent.vue';
import 'bootstrap/dist/css/bootstrap.min.css';

const routes = [
    { path: '/', component: RegisterComponent },
    { path: '/register', component: RegisterComponent },
    { path: '/login', component: LoginComponent }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

const app = createApp(App);
app.use(router);
app.mount('#app');