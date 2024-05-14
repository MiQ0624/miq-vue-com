<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :lg="24" :xs="24">
        <div class="search-container">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="类别" prop="status">
              <el-select v-model="queryParams.status" placeholder="全部" clearable class="!w-[100px]">
                <el-option label="启用" value="1" />
                <el-option label="禁用" value="0" />
              </el-select>
            </el-form-item>

            <el-form-item label="查找" prop="keywords">
              <el-input v-model="queryParams.keywords" placeholder="模糊匹配" clearable style="width: 200px" @keyup.enter="handleQuery" />
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
                <el-button v-hasPerm="['sys:user:add']" type="success" @click="openDialog('user-form')"><i-ep-plus />新增</el-button>
                <el-button v-hasPerm="['sys:user:delete']" type="danger" :disabled="removeIds.length === 0" @click="handleDelete()"><i-ep-delete />批量删除</el-button>
              </div>
            </div>
          </template>

          <el-table v-loading="loading" :data="pageData" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="50" align="center" />
            <!-- <el-table-column key="id" label="编号" align="center" prop="id" width="100" /> -->
            <el-table-column key="username" label="类别" align="center" prop="username" />
            <el-table-column label="名称" width="120" align="center" prop="nickname" />
            <el-table-column label="地址" width="100" align="center" prop="genderLabel" />
            <el-table-column label="用户名" width="120" align="center" prop="deptName" />
            <el-table-column label="密码" align="center" prop="mobile" width="120" />
            <el-table-column label="状态" align="center" prop="status">
              <template #default="scope">
                <el-tag :type="scope.row.status == 1 ? 'success' : 'info'">{{
                  scope.row.status == 1 ? "可用" : "不可用"
                }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
            <el-table-column label="操作" fixed="right" width="150">
              <template #default="scope">
                <!-- <el-button v-hasPerm="['sys:user:reset_pwd']" type="primary" size="small" link @click="resetPassword(scope.row)"><i-ep-refresh-left />重置密码</el-button> -->
                <el-button v-hasPerm="['sys:user:edit']" type="primary" link size="small" @click="openDialog('user-form', scope.row.id)"><i-ep-edit />详情</el-button>
                <el-button v-hasPerm="['sys:user:delete']" type="primary" link size="small" @click="handleDelete(scope.row.id)"><i-ep-delete />删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <pagination v-if="total > 0" v-model:total="total" v-model:page="queryParams.pageNum"
            v-model:limit="queryParams.pageSize" @pagination="handleQuery" />
        </el-card>
      </el-col>
    </el-row>

    <!-- 弹窗 -->
    <el-dialog v-model="dialog.visible" :title="dialog.title" width=40% append-to-body @close="closeDialog">
      <!-- 用户新增/编辑表单 -->
      <el-form v-if="dialog.type === 'user-form'" ref="userFormRef" :model="formData" :rules="rules" label-width="100px">
        <el-form-item label="类型:" prop="roleIds">
          <dictionary v-model="formData.gender" type-code="gender" />
        </el-form-item>
        
        <el-form-item label="名称:" prop="username">
          <el-input v-model="formData.username" :readonly="!!formData.id" placeholder="请输入口令名称" />
        </el-form-item>

        <el-form-item label="地址:" prop="nickname">
          <el-input v-model="formData.nickname" width=40% placeholder="请输入口令地址" />
        </el-form-item>

        <el-form-item label="用户名:" prop="gender">
          <el-input v-model="formData.username" :readonly="!!formData.id" placeholder="请输入用户名" />
        </el-form-item>

        <el-form-item label="密码:" prop="gender">
          <el-input v-model="formData.username" :readonly="!!formData.id" placeholder="请输入密码" />
        </el-form-item>

        <el-form-item label="状态:" prop="status">
          <el-radio-group v-model="formData.status">
            <el-radio :label="1">可用</el-radio>
            <el-radio :label="0">不可用</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="备注:" prop="email">
          <el-input v-model="formData.email" placeholder="备注" maxlength="500" />
        </el-form-item>
      </el-form>

      <!-- 用户导入表单 -->
      <el-form v-else-if="dialog.type === 'user-import'" :model="importData" label-width="100px">
        <el-form-item label="部门">
          <el-tree-select v-model="importData.deptId" placeholder="请选择部门" :data="deptList" filterable check-strictly />
        </el-form-item>

        <el-form-item label="Excel文件">
          <el-upload ref="uploadRef" action="" drag
            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
            :limit="1" :auto-upload="false" :file-list="importData.fileList" :on-change="handleFileChange"
            :on-exceed="handleFileExceed">
            <el-icon class="el-icon--upload">
              <i-ep-upload-filled />
            </el-icon>
            <div class="el-upload__text">
              将文件拖到此处，或
              <em>点击上传</em>
            </div>
            <template #tip>
              <div>xls/xlsx files</div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <!-- 弹窗底部操作按钮 -->
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="handleSubmit">确 定</el-button>
          <el-button @click="closeDialog">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
defineOptions({
  name: "User",
  inheritAttrs: false,
});

import {  getPassCodeList } from "@/api/passcode";

import { UserForm, UserQuery, UserPageVO } from "@/api/user/types";
import type { UploadInstance } from "element-plus";

const queryFormRef = ref(ElForm); // 查询表单
const userFormRef = ref(ElForm); // 用户表单
const uploadRef = ref<UploadInstance>(); // 上传组件

const loading = ref(false); //  加载状态
const removeIds = ref([]); // 删除用户ID集合 用于批量删除
const queryParams = reactive<UserQuery>({
  pageNum: 1,
  pageSize: 10,
});
const dateTimeRange = ref("");
const total = ref(0); // 数据总数
const pageData = ref<UserPageVO[]>(); // 用户分页数据
const deptList = ref<OptionType[]>(); // 部门下拉数据源
const roleList = ref<OptionType[]>(); // 角色下拉数据源

watch(dateTimeRange, (newVal) => {
  if (newVal) {
    queryParams.startTime = newVal[0];
    queryParams.endTime = newVal[1];
  }
});

// 弹窗对象
const dialog = reactive({
  visible: false,
  type: "user-form",
  width: 800,
  title: "",
});

// 用户表单数据
const formData = reactive<UserForm>({
  status: 1,
});

/** 查询 */
function handleQuery() {
  loading.value = true;
  getUserPage(queryParams)
    .then(({ data }) => {
      pageData.value = data.list;
      total.value = data.total;
    })
    .finally(() => {
      loading.value = false;
    });
}

/** 重置查询 */
function resetQuery() {
  queryFormRef.value.resetFields();
  dateTimeRange.value = "";
  queryParams.pageNum = 1;
  queryParams.deptId = undefined;
  queryParams.startTime = undefined;
  queryParams.endTime = undefined;
  handleQuery();
}

/** 行选中 */
function handleSelectionChange(selection: any) {
  removeIds.value = selection.map((item: any) => item.id);
}


onMounted(() => {
  handleQuery();
});
</script>

<style scoped>
el-input {
  width: 70%;
}
</style>
