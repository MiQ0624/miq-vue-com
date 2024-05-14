// 查询过滤器
export interface QueryFilter extends PageQuery {
  keyword?: string;
}

// 分页对象
export interface EntityColumns {
  type?: string;
  englishWord?: string;
  pronunciation?: string;
  chineseWord?: string;
  englishInstance1?: string;//性别
  chineseInstance1?: string;
  englishInstance2?: string;
  chineseInstance2?: string;
  collect?: number;
  pronUrl?: string;
}
