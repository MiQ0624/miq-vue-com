// 查询过滤器
export interface QueryFilter extends PageQuery {
  keyword?: string;
}

// 分页对象
export interface EntityColumns {
  id?: string;
  title?: string;
  content?: string;
  create_time?: Date;
  update_time?: Date;
  create_user?: string;
  update_user?: string;
}
