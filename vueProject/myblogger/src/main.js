import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import RelationWrapper from './components/RelationWrapper.vue'
import ChatWrapper from './components/ChatWrapper.vue'
import App from "@/App.vue";

const routes = [
    { path: '/relation', component: RelationWrapper },
    { path: '/chat', component: ChatWrapper }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

const app = createApp(App)
app.use(router)
app.mount('#app')
