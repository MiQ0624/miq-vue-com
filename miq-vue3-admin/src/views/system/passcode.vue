<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :lg="24" :xs="24">
        <!-- 筛选栏 -->
        <div class="search-container">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="类别" prop="kind">
              <el-select v-model="queryParams.kind" placeholder="全部分类" clearable class="!w-[150px]">
                <el-option label="娱乐类" value="娱乐类" />
                <el-option label="工作类" value="工作类" />
                <el-option label="工具类" value="工具类" />
                <el-option label="学习类" value="学习类" />
                <el-option label="其他类" value="其他类" />
              </el-select>
            </el-form-item>

            <el-form-item label="查找" prop="keyword">
              <el-input v-model="queryParams.keyword" placeholder="模糊查询" clearable style="width: 200px" @keyup.enter="handleQuery" />
            </el-form-item>

            <el-form-item label="是否可用" prop="status">
              <el-switch v-model="queryParams.status" class="ml-2" inline-prompt active-text="Y" inactive-text="N" style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"/>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="handleQuery"><i-ep-search />搜索</el-button>
              <el-button @click="resetQuery"><i-ep-refresh /> 重置</el-button>
            </el-form-item>
          </el-form>
        </div>

        <el-card shadow="never" class="table-container">
          <template #header>
            <div class="flex justify-between">
              <div>
                <el-button v-hasPerm="['sys:user:add']" type="success"><i-ep-plus />新增</el-button>
                <el-button v-hasPerm="['sys:user:delete']" type="danger" :disabled="removeIds.length === 0" ><i-ep-delete />批量删除</el-button>
              </div>
            </div>
          </template>

          <!-- 表单数据 -->
          <el-table v-loading="loading" :data="pageData" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="50" align="center" />
            <!-- <el-table-column key="id" label="编号" align="center" prop="id" width="100" /> -->
            <el-table-column label="类别" prop="kind" align="center"  />
            <el-table-column label="名称" prop="linkName" align="center" width="120" />
            <el-table-column label="地址" prop="address" align="center" width="200"  />
            <el-table-column label="用户名" prop="user" align="center" width="200"  />
            <el-table-column label="密码" prop="pwd" align="center" width="200"   />
            <el-table-column label="状态" prop="available" align="center" >
              <template #default="scope">
                <el-tag :type="scope.row.available == 1 ? 'success' : 'info'">
                  {{ scope.row.available == 1 ? "可用" : "不可用" }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="创建时间" prop="createTime" align="center"  width="180" />
            <el-table-column label="操作" fixed="right" width="150">
              <template #default="scope">
                <!-- <el-button v-hasPerm="['sys:user:reset_pwd']" type="primary" size="small" link @click="resetPassword(scope.row)"><i-ep-refresh-left />重置密码</el-button> -->
                <el-button v-hasPerm="['sys:user:edit']" type="primary" link size="small" @click="openDialog(scope.row.id)"><i-ep-edit />详情</el-button>
                <el-button v-hasPerm="['sys:user:delete']" type="primary" link size="small" @click="handleDelete(scope.row.id)"><i-ep-delete />删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <pagination v-if="total > 0" v-model:total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="handleQuery" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { getPageInfo } from '@/api/passcode';
import { PageInfo, QueryFilter } from '@/api/passcode/types';

const loading = ref(false); //  加载状态
const pageData = ref<PageInfo[]>(); // 用户分页数据
const total = ref(0); // 数据总数
const removeIds = ref([]); // 删除用户ID集合 用于批量删除

const queryParams = reactive<QueryFilter>({
  pageNum: 1,
  pageSize: 20,
});

onMounted(() => {
  handleQuery();
});

function handleQuery() {
  loading.value = true;
  getPageInfo(queryParams)
    .then(({ data }) => {
      pageData.value = data.records;
      // console.log(data);
      total.value = data.total;
    })
    .finally(() => {
      loading.value = false;
    });
}

function resetQuery() {

}

function handleSelectionChange() {

}

function openDialog(param:any) {

}

function handleDelete(param:any) {

}

</script>

<style scoped>
el-input {
  width: 70%;
}
</style>
