package com.zgczx.enums;

import lombok.Getter;

/**
 * @author Jason
 * @date 2019/3/12 14:40
 */
@Getter
public enum ArticleLabelEnum {

    /**
     * 推荐模块
     */
    RECOMMEND(0,"推荐"),
    /**
     * 高考模块
     */
    GAO_KAO(1,"高考"),
    /**
     * 志愿模块
     */
    ZHI_YUAN(2,"志愿"),
    /**
     * 心理模块
     */
    XIN_LI(3,"心理"),

    ;
    private Integer code;

    private String message;

    ArticleLabelEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
