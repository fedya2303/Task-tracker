import Vue from 'vue';
import Router from 'vue-router';
import Home from '../components/Home.vue';
import NotFound from '../components/NotFound.vue';
import SignIn from '../components/SignIn.vue';
import ProjectList from "../pages/ProjectList";
import Project from "../pages/Project";
import Task from "../pages/Task";
import ProjectForm from "../components/project/ProjectForm";
import store from "../store";
import TaskForm from "../components/task/TaskForm";
import ProjectParticipantForm from "../components/project/ProjectParticipantForm";

Vue.use(Router);

const router = new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            redirect: '/home',
        },
        {
            path: '/main',
            name: 'Main',
            component: ProjectList
        },
        {
            path: '/project/:id?',
            component: Project
        },
        {
            path: '/task/:id?',
            component: Task
        },
        {
            path: '/home',
            name: 'Home',
            component: Home,
            meta: {nonRequiresAuth: true},
        },
        {
            path: '/newproject/:id?',
            name: 'ProjectForm',
            component: ProjectForm,
            meta: {manager: true},
            props: true
        },
        {
            path: '/project/:projectId?/task',
            name: 'TaskForm',
            component: TaskForm,
            props: true
        },
        {
            path: '/project/:projectId?/users',
            name: 'ProjectParticipantForm',
            component: ProjectParticipantForm,
            props: true
        },
        {
            path: '/signIn',
            name: 'signIn',
            component: SignIn,
            meta: {loginPage: true, nonRequiresAuth: true},
        },
        {
            path: '*',
            component: NotFound,
        },
    ],
});

router.beforeEach((to, from, next) => {
    const requiresAuth = !to.matched.some((record) => record.meta.nonRequiresAuth);
    const isLoginPage = to.matched.some((record) => record.meta.loginPage);
    const isAuthenticated = localStorage.getItem('auth');
    const mustBeManager = to.matched.some((record) => record.meta.manager)
    if (requiresAuth && !isAuthenticated) {
        next('/signIn');
    } else if (isLoginPage && isAuthenticated) {
        router.push('/home');
    } else if (isAuthenticated && mustBeManager && !isManager()) {
        router.push('/main')
    }
    next();
});

function isManager() {
    if (store.state.user && store.state.user.roles) {
        return store.state.user.roles.some(role => role === 'MANAGER')
    }
    return false
}

export default router;
