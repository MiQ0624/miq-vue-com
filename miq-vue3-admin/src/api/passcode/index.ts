import request from "@/utils/request";
import axios from "axios";
import { AxiosPromise } from "axios";
import { PageInfo, QueryFilter } from "./types";

// 查询数据
function getPassCodeList (params: any) {
  return axios({
    url: '/link/page',
    method: 'get',
    params
  })
}

// 获取用户分页列表
export function getPageInfo(queryParams: QueryFilter): AxiosPromise<PageResult<PageInfo[]>> {
  return request({
    url: "/passcode/page",
    method: "get",
    params: queryParams,
  });
}



