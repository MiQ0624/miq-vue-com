import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { UserForm, UserInfo, UserPageVO, UserQuery } from "./types";

// 登录成功后获取用户信息（昵称、头像、权限集合和角色集合）
export function getUserInfoApi(): AxiosPromise<UserInfo> {
  return request({
    url: "/api/v1/users/me",
    method: "get",
  });
}

// 获取用户分页列表
export function getUserPage(queryParams: UserQuery): AxiosPromise<PageResult<UserPageVO[]>> {
  return request({
    url: "/api/v1/users/page",
    method: "get",
    params: queryParams,
  });
}

// 获取用户表单详情
export function getUserForm(userId: number): AxiosPromise<UserForm> {
  return request({
    url: "/api/v1/users/" + userId + "/form",
    method: "get",
  });
}

// 添加用户
export function addUser(data: any) {
  return request({
    url: "/api/v1/users",
    method: "post",
    data: data,
  });
}

// 修改用户
export function updateUser(id: number, data: UserForm) {
  return request({
    url: "/api/v1/users/" + id,
    method: "put",
    data: data,
  });
}

// 修改用户密码
export function updateUserPassword(id: number, password: string) {
  return request({
    url: "/api/v1/users/" + id + "/password",
    method: "patch",
    params: { password: password },
  });
}

// 删除用户
export function deleteUsers(ids: string) {
  return request({
    url: "/api/v1/users/" + ids,
    method: "delete",
  });
}

// 下载用户导入模板
export function downloadTemplateApi() {
  return request({
    url: "/api/v1/users/template",
    method: "get",
    responseType: "arraybuffer",
  });
}

// 导出用户
export function exportUser(queryParams: UserQuery) {
  return request({
    url: "/api/v1/users/_export",
    method: "get",
    params: queryParams,
    responseType: "arraybuffer",
  });
}

// 导入用户
export function importUser(deptId: number, file: File) {
  const formData = new FormData();
  formData.append("file", file);
  return request({
    url: "/api/v1/users/_import",
    method: "post",
    params: { deptId: deptId },
    data: formData,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
}
