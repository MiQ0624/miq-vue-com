package com.miq.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miq.system.model.form.UserForm;
import com.miq.system.plugin.mybatis.annotation.DataPermission;
import com.miq.system.model.bo.UserBO;
import com.miq.system.model.entity.SysUser;
import com.miq.system.model.dto.UserAuthInfo;
import com.miq.system.model.query.UserPageQuery;
import com.miq.system.model.vo.UserExportVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户持久层
 *
 * @author haoxr
 * @since 2022/1/14
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 获取用户分页列表
     *
     * @param page
     * @param queryParams 查询参数
     * @return
     */
    @DataPermission(deptAlias = "u")
    Page<UserBO> listPagedUsers(Page<UserBO> page, UserPageQuery queryParams);

    /**
     * 获取用户表单详情
     *
     * @param userId 用户ID
     * @return
     */
    UserForm getUserFormData(Long userId);

    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    UserAuthInfo getUserAuthInfo(String username);

    /**
     * 获取导出用户列表
     *
     * @param queryParams
     * @return
     */
    @DataPermission(deptAlias = "u")
    List<UserExportVO> listExportUsers(UserPageQuery queryParams);
}
