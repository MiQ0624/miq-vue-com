package com.miq.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysDict implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String typeCode;
    private String name;
    private String value;
    private Integer sort;
    private Integer status;
    private Integer defaulted;
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}