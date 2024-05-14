package com.miq.system.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miq.system.model.entity.SysPassCode;
import com.miq.system.mapper.SysPassCodeMapper;
import com.miq.system.service.SysPassCodeService;

@Service
public class SysPassCodeServiceImpl extends ServiceImpl<SysPassCodeMapper, SysPassCode> implements SysPassCodeService {
}

