<!-- wangEditor富文本编辑器示例 -->
<template>
  <div class="app-container">
    <el-input v-model="title" style="max-width: 500px" placeholder="Please input">
      <template #prepend>Http://</template>
    </el-input>
    <el-button @click="handleLog">保存</el-button>
    <el-button @click="handleLog">保存至本地</el-button>
    <el-button @click="handleLog">打开本地文件</el-button>
    <editor v-model="value" style="height: calc(100vh - 180px)" />
  </div>
</template>

<script setup lang="ts">
import Editor from "@/components/WangEditor/index.vue";

const value = ref("初始内容");
const title = ref("new 1");

function handleLog() {
  console.log(value);
  console.log(value.value);

}
</script>
