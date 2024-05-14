package com.miq.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miq.system.common.R;
import com.miq.system.common.result.PageResult;
import com.miq.system.model.entity.SysPassCode;
import com.miq.system.model.query.UserPageQuery;
import com.miq.system.model.vo.UserPageVO;
import com.miq.system.service.SysPassCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/passcode")
@Slf4j
public class SysPassCodeController {
    @Autowired
    private SysPassCodeService SysPassCodeService;

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int pageNum, int pageSize, String keyword,String kind){
        log.info("page = {},pageSize = {},keyword = {},kind = {}" ,pageNum,pageSize,keyword,kind);

        //构造分页构造器
        Page pageInfo = new Page(pageNum,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<SysPassCode> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(keyword),SysPassCode::getLinkName,keyword);
        queryWrapper.like(StringUtils.isNotEmpty(kind),SysPassCode::getKind,kind);

        //添加排序条件
        queryWrapper.orderByDesc(SysPassCode::getKind);

        //执行查询
        SysPassCodeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

//    public PageResult<UserPageVO> listPagedUsers(@ParameterObject UserPageQuery queryParams) {
//        IPage<UserPageVO> result = userService.listPagedUsers(queryParams);
//        return PageResult.success(result);
//    }


    /**
     * 新增
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody SysPassCode info){
        log.info("新增用户信息：{}",info.toString());
        info.setPwd(DigestUtils.md5DigestAsHex("123456".getBytes()));

        SysPassCodeService.save(info);

        return R.success("新增用户成功");
    }

    /**
     * 根据id删除
     */
    @DeleteMapping
    public R<String> delete(Long id){
        log.info("删除分类，id为：{}",id);

        SysPassCodeService.removeById(id);

        return R.success("分类信息删除成功");
    }

    /**
     * 根据id修改信息
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody SysPassCode info){
        log.info(info.toString());

        long id = Thread.currentThread().getId();
        log.info("线程id为：{}",id);
        info.setPwd(DigestUtils.md5DigestAsHex("123456".getBytes()));
        SysPassCodeService.updateById(info);

        return R.success("信息修改成功");
    }

    /**
     * 根据id查询信息
     */
    @GetMapping("/{id}")
    public R<SysPassCode> getById(@PathVariable Long id){
        log.info("根据id查询...");
        SysPassCode info = SysPassCodeService.getById(id);
        if(info != null){
            return R.success(info);
        }
        return R.error("没有查询到对应id信息");
    }
}
