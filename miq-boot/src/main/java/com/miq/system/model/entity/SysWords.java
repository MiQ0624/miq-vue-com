package com.miq.system.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SysWords implements Serializable {

    private String type;
    private String englishWord;
    private String pronunciation;
    private String chineseWord;
    private String englishInstance1;
    private String chineseInstance1;
    private String englishInstance2;
    private String chineseInstance2;
    private Integer collect;
    private String pronUrl;

}
