package com.miq.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miq.system.mapper.SysNotesMapper;
import com.miq.system.mapper.SysWordsMapper;
import com.miq.system.model.entity.SysNotes;
import com.miq.system.model.entity.SysWords;
import com.miq.system.service.SysNotesService;
import com.miq.system.service.SysWordsService;
import org.springframework.stereotype.Service;

@Service
public class SysNotesServiceImpl extends ServiceImpl<SysNotesMapper, SysNotes> implements SysNotesService {
}

