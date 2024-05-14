package com.miq.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.miq.system.common.base.BaseEntity;
import lombok.Data;

@Data
public class SysUser extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String nickname;
    private Integer gender;
    private String password;
    private Long deptId;
    private String avatar;
    private String mobile;
    private Integer status;
    private String email;
    private Integer deleted;

}