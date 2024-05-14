import request from "@/utils/request";
import axios from "axios";
import { AxiosPromise } from "axios";
import { EntityColumns, QueryFilter } from "./types";

// 获取用户分页列表
export function getPageInfo(queryParams: QueryFilter): AxiosPromise<PageResult<EntityColumns[]>> {
  return request({
    url: "/notes/page",
    method: "get",
    params: queryParams,
  });
}

// 添加用户
export function addData(data: any) {
  return request({
    url: "/notes",
    method: "post",
    data: data,
  });
}

// 根据id查询
export function getDataById (id: any) {
  return request({
    url: `/notes/${id}`,
    method: 'get'
  })
}

