package com.miq.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.miq.system.common.base.BaseEntity;
import com.miq.system.common.enums.MenuTypeEnum;
import lombok.Data;

@TableName(value ="sys_menu")
@Data
public class SysMenu extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentId;
    private String name;
    private MenuTypeEnum type;
    private String path;
    private String component;
    private String perm;
    private Integer visible;
    private Integer sort;
    private String icon;
    private String redirect;
    private String treePath;
    private Integer keepAlive;
    private Integer alwaysShow;
}