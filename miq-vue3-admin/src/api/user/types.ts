// 登录用户信息
export interface UserInfo {
  userId?: number;
  username?: string;
  nickname?: string;
  avatar?: string;
  roles: string[];
  perms: string[];
}

// 用户查询对象类型
export interface UserQuery extends PageQuery {
  keywords?: string;
  status?: number;
  deptId?: number;
  startTime?: string;
  endTime?: string;
}

// 用户分页对象
export interface UserPageVO {
  avatar?: string;
  createTime?: Date;
  deptName?: string;
  email?: string;
  genderLabel?: string;//性别
  id?: number;
  mobile?: string;
  nickname?: string;
  roleNames?: string;
  status?: number;
  username?: string;
}

// 用户表单类型
export interface UserForm {
  avatar?: string;
  deptId?: number;
  email?: string;
  gender?: number;
  id?: number;
  mobile?: string;
  nickname?: string;
  roleIds?: number[];
  status?: number;
  username?: string;
}

