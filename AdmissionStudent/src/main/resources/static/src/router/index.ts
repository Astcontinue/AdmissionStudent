import { createRouter, createWebHistory } from 'vue-router';
import MainRoutes from './MainRoutes';
import PublicRoutes from './PublicRoutes';
import { useAuthStore } from '@/stores/auth';
import { useUIStore } from '@/stores/ui';

export const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/:pathMatch(.*)*',
      component: () => import('@/views/pages/maintenance/error/Error404Page.vue')
    },
    MainRoutes,
    PublicRoutes
  ]
});

interface User {
  // Define the properties and their types for the user data here
  // For example:
  id: number;
  name: string;
}

// Assuming you have a type/interface for your authentication store
interface AuthStore {
  user: User | null;
  returnUrl: string | null;
  login(username: string, password: string): Promise<void>;
  logout(): void;
}

router.beforeEach(async (to, from, next) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const publicPages = ['/'];
  const auth: AuthStore = useAuthStore();

  const isPublicPage = publicPages.includes(to.path);
  const authRequired = !isPublicPage && to.matched.some((record) => record.meta.requiresAuth);

  // User not logged in and trying to access a restricted page
  if (authRequired && !auth.user) {
    auth.returnUrl = to.fullPath; // Save the intended page
    next('/login');
  } else if (auth.user && to.path === '/login') {
    // User logged in and trying to access the login page
    next({
      query: {
        ...to.query,
        redirect: auth.returnUrl !== '/' ? to.fullPath : undefined
      }
    });
  } else {
    // All other scenarios, either public page or authorized access
    next();
  }
});

router.beforeEach(() => {
  const uiStore = useUIStore();
  uiStore.isLoading = true;
});

router.afterEach(() => {
  const uiStore = useUIStore();
  uiStore.isLoading = false;
});

/**
 * 这个文件的作用是配置和管理 Vue 应用的路由系统，确保用户在访问不同页面时能够正确地进行身份验证和页面加载状态的处理。它通过定义路由规则、设置全局守卫和管理 UI 状态，实现了应用的导航逻辑和用户体验优化。
 */