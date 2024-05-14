package com.miq.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.miq.system.common.base.BaseEntity;
import lombok.Data;

@TableName(value ="sys_role")
@Data
public class SysRole extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String code;
    private Integer sort;
    private Integer status;
    private Integer deleted;
    private Integer dataScope;
}