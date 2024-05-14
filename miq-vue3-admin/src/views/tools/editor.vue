<!-- wangEditor富文本编辑器示例 -->
<template>
  <div class="app-container">
    <el-row>
      <el-col :span="3">
        <el-input v-model="find" style="width: 140px" size="small" placeholder="search" :prefix-icon="Search" />
        <div>
          <ul v-for="(item, index) in tableData">
            <li :key="item.id" @click="handleSelCurr(item.id)">{{ item.title }}</li>
          </ul>
        </div>
      </el-col>

      <el-col :span="21">
        <el-space :size="10" spacer=" ">
          <el-switch v-model="list_flag" :active-action-icon="View" :inactive-action-icon="Hide" />
          <el-input v-model="title" style="max-width: 500px" placeholder="Please input">
            <template #prepend>notes</template>
          </el-input>
          <el-button @click="handleSave">保存</el-button>
          <el-button @click="handleLog">保存至本地</el-button>
          <el-button @click="handleLog">打开本地文件</el-button>
        </el-space>
        <editor v-model="content" style="height: calc(100vh - 180px)" />
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" title="Tips" width="500" :before-close="handleClose">
      <span>是否保存当前笔记？</span>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">不保存</el-button>
          <el-button type="primary" @click="dialogVisible = false">保存</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script setup lang="ts">
import { addData, getDataById, getPageInfo } from "@/api/note";
import { EntityColumns, QueryFilter } from "@/api/note/types";
import Editor from "@/components/WangEditor/index.vue";
import { Search } from '@element-plus/icons-vue'
import { Hide, View } from '@element-plus/icons-vue'

const title = ref("new 1");
const content = ref("初始内容111");
const dialogVisible = ref(false);
const dialogResult = ref(false);
const find = ref("");
const list_flag = ref(1);
const loading = ref(false); //  加载状态
// const tableData = ref<EntityColumns[]>(); // 用户分页数据
const tableData = ref<EntityColumns[]>(); // 用户分页数据
const total = ref(0); // 数据总数
const queryParams = reactive<QueryFilter>({
  pageNum: 1,
  pageSize: 10,
});

onMounted(() => {
  handleQuery();
});

function handleQuery() {
  loading.value = true;
  getPageInfo(queryParams)
    .then(({ data }) => {
      tableData.value = data.records as EntityColumns[];
      console.log(data);
      total.value = data.total;
    })
    // 错误处理：捕获并处理可能的错误
    .catch(error => {
      console.error('Failed to fetch page info:', error);
    })
    .finally(() => {
      loading.value = false;
    });
}

function handleLog() {
  console.log(content);
  console.log(content.value);
}

function handleSelCurr(param: any) {
  // 打开新的笔记前先保存当前笔记
  dialogVisible.value = true;
  console.log(dialogResult.value);
  
  if (dialogResult.value) {
    handleSave();
  }
  
  getDataById(param)
    .then(({ data }) => {
      title.value = data.title;
      content.value = data.content;
    })
    .finally(() => (loading.value = false));
}


function handleSave() {
  const formData = reactive<EntityColumns>({
    title: title.value,
    content: content.value,
  });

  addData(formData)
    .then(() => {
      ElMessage.success("保存成功");
    })
    .finally(() => (loading.value = false));
}

const handleClose = (done: () => void) => {
  ElMessageBox.confirm('Are you sure to close this dialog?')
    .then(() => {
      done()
    })
    .catch(() => {
      // catch error
    })
}

</script>
