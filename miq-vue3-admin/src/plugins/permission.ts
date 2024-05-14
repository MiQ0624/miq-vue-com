import router from "@/router";
import { useUserStore, usePermissionStore } from "@/store";
import NProgress from "@/utils/nprogress";
import { RouteRecordRaw } from "vue-router";

export function setupPermission() {
  // 白名单路由
  const whiteList = ["/login"];

  router.beforeEach(async (to, from, next) => {
    NProgress.start();
    const hasToken = localStorage.getItem("accessToken");
    if (hasToken) {     //是否登录
      if (to.path === "/login") {
        // 如果已登录，跳转首页
        next({ path: "/" });
        NProgress.done();
      } else {
        const userStore = useUserStore();
        const hasRoles =
          userStore.user.roles && userStore.user.roles.length > 0;
        if (hasRoles) {       //是否一获取用户的角色信息
          // 未匹配到任何路由，跳转404
          if (to.matched.length === 0) {
            from.name ? next({ name: from.name }) : next("/404");
          } else {
            next();
          }
        } else {
          const permissionStore = usePermissionStore();
          try {       //获取用户角色后，根据角色生成动态权限路由
            const { roles } = await userStore.getUserInfo();
            const accessRoutes = await permissionStore.generateRoutes(roles);      //调用 permissionStore.generateRoutes(roles) 方法生成动态路由
            accessRoutes.forEach((route: RouteRecordRaw) => {
              router.addRoute(route);
            });
            next({ ...to, replace: true });
          } catch (error) {
            // 移除 token 并跳转登录页
            await userStore.resetToken();
            next(`/login?redirect=${to.path}`);
            NProgress.done();
          }
        }
      }
    } else {
      // 未登录可以访问白名单页面
      if (whiteList.indexOf(to.path) !== -1) {
        next();
      } else {
        next(`/login?redirect=${to.path}`);
        NProgress.done();
      }
    }
  });

  router.afterEach(() => {
    NProgress.done();
  });
}
