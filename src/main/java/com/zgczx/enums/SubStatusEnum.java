package com.zgczx.enums;

import lombok.Getter;

@Getter
public enum SubStatusEnum {

    SUB_WAIT(0,"预约等待"),
    SUB_SUCCESS(1,"预约成功"),
    SUB_FAILURE(2,"预约失效"),
    ;

    private Integer code;

    private String message;

    SubStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
