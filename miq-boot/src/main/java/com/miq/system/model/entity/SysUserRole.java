package com.miq.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole {
    private Long userId;
    private Long roleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}