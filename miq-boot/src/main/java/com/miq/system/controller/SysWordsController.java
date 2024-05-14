package com.miq.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miq.system.common.R;
import com.miq.system.model.entity.SysPassCode;
import com.miq.system.model.entity.SysWords;
import com.miq.system.service.SysPassCodeService;
import com.miq.system.service.SysWordsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/words")
@Slf4j
public class SysWordsController {
    @Autowired
    private SysWordsService SysWordsService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int pageNum, int pageSize, String keyword){
        log.info("page = {},pageSize = {},name = {}" ,pageNum,pageSize,keyword);

        //构造分页构造器
        Page pageInfo = new Page(pageNum,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<SysWords> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
//        queryWrapper.like(StringUtils.isNotEmpty(keyword),SysWords::getChineseWord,keyword);
        queryWrapper.like(StringUtils.isNotEmpty(keyword),SysWords::getChineseWord,keyword)
                .or()
                .like(StringUtils.isNotEmpty(keyword),SysWords::getEnglishWord, keyword);
        //添加排序条件
        queryWrapper.orderByAsc(SysWords::getEnglishWord);

        //执行查询
        SysWordsService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }


}
