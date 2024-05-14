// 查询过滤器
export interface QueryFilter extends PageQuery {
  kind?:string;
  keyword?: string;
  status?: number;
}

// 分页对象
export interface PageInfo {
  id?: number;
  kind?: string;
  linkName?: string;
  address?: string;
  avatar?: string;//性别
  user?: string;
  pwd?: string;
  available?: number;
  des?: string;
  createTime?: Date;
  updateTime?: Date;
  createUser?: string;
  updateUser?: string;
}
