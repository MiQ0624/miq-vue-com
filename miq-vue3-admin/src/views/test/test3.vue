<!-- src/components/HelloWorld.vue -->
<template>
  <!-- 整合 Element Plus -->
  <!-- src/components/HelloWorld.vue -->
  <div>
    <el-button type="success"><i-ep-SuccessFilled />Success</el-button>
    <el-button type="info"><i-ep-InfoFilled />Info</el-button>
    <el-button type="warning"><i-ep-WarningFilled />Warning</el-button>
    <el-button type="danger"><i-ep-WarnTriangleFilled />Danger</el-button>
  </div>
  <!-- 整合 SVG 图标 -->
  <el-button type="info"><svg-icon icon-class="block"/>SVG 本地图标</el-button>
  <!-- <div style="width:100px;height:100px" :style="{ 'background-color': variables.dark }"></div> -->

  <!-- UnoCSS 是一个具有高性能且极具灵活性的即时原子化 CSS 引擎 。 -->
  <h1 class="text-red">UnoCSS</h1>

  <!-- Pinia 是 Vue 的专属状态管理库，它允许你跨组件或页面共享状态。 -->
  <!-- 根据 Pinia 官方文档-核心概念 描述 ，Store 定义分为选项式和组合式 , 先比较下两种写法的区别： -->

  <!-- Axios 基于promise可以用于浏览器和node.js的网络请求库 -->
  <!-- src/utils/request.ts -->

  <!-- 动态路由 -->
  <!-- 路由实例 src/router/index.ts  -->
  <!-- 动态权限路由 路由守卫 src/plugins/permissions.ts ，获取当前登录用户的角色信息进行动态路由的初始化 -->
  <!-- src/store/modules/permission.ts -->
  <!-- 根据路由数据 (routes)生成菜单的关键代码  src/layout/componets/Sidebar/index.vue   
    src/layout/componets/Sidebar/SidebarItem.vue -->

  <!-- 通过自定义指令的方式实现按钮权限控制 -->
  <!-- src/directive/permission/index.ts -->
 </template>
 
 <script setup lang="ts">
  // import variables from "@/styles/variables.module.scss";
  // console.log(variables)  
  // console.log(variables.bgColor)       // undefined ???
</script>
