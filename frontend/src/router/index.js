import Vue from 'vue';
import Router from 'vue-router';
import Home from '../components/Home.vue';
import NotFound from '../components/NotFound.vue';
import SignIn from '../components/SignIn.vue';
import ProjectList from "../pages/ProjectList";
import Project from "../pages/Project";
import Task from "../pages/Task";
import ProjectForm from "../components/project/ProjectForm";

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
      meta: { nonRequiresAuth: true },
    },
    {
      path: '/newproject',
      component: ProjectForm
    },
    {
      path: '/signIn',
      name: 'signIn',
      component: SignIn,
      meta: { loginPage: true, nonRequiresAuth: true },
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
  if (requiresAuth && !isAuthenticated) {
    next('/signIn');
  } else if (isLoginPage && isAuthenticated) {
    router.push('/home');
  }
  next();
});

export default router;
