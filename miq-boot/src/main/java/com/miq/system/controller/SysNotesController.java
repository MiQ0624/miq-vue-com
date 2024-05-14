package com.miq.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miq.system.common.R;
import com.miq.system.model.entity.SysNotes;
import com.miq.system.model.entity.SysWords;
import com.miq.system.service.SysNotesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/notes")
@Slf4j
public class SysNotesController {
    @Autowired
    private SysNotesService SysNotesService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int pageNum, int pageSize, String keyword){
        log.info("page = {},pageSize = {},keyword = {}" ,pageNum,pageSize,keyword);

        //构造分页构造器
        Page pageInfo = new Page(pageNum,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<SysNotes> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(keyword), SysNotes::getTitle,keyword)
                .or()
                .like(StringUtils.isNotEmpty(keyword),SysNotes::getContent, keyword);
        //添加排序条件
        queryWrapper.orderByAsc(SysNotes::getUpdateTime);

        //执行查询
        SysNotesService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody SysNotes info){
        log.info("新增数据：{}",info.toString());

        SysNotesService.save(info);

        return R.success("新增数据成功");
    }

    /**
     * id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SysNotes> getById(@PathVariable Long id){
        log.info("根据id查询...");
        SysNotes note = SysNotesService.getById(id);
        if(note != null){
            return R.success(note);
        }
        return R.error("没有查询到对应员工信息");
    }
}
