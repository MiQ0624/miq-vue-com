package com.miq.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miq.system.mapper.SysPassCodeMapper;
import com.miq.system.mapper.SysWordsMapper;
import com.miq.system.model.entity.SysPassCode;
import com.miq.system.model.entity.SysWords;
import com.miq.system.service.SysPassCodeService;
import com.miq.system.service.SysWordsService;
import org.springframework.stereotype.Service;

@Service
public class SysWordsServiceImpl extends ServiceImpl<SysWordsMapper, SysWords> implements SysWordsService {
}

