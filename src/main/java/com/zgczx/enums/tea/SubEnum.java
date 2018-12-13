package com.zgczx.enums.tea;

import lombok.Getter;

/**
 * Created by Dqd on 2018/12/12.
 */
@Getter
public enum SubEnum {
    SUB_SUCCESS(0,"预约成功状态"),
    SUB_CANCEL(1,"预约取消"),
    SUB_INTER(2,"互动进行中"),
    SUB__FINISH(3,"正常结束")
    ;
    private Integer code;
    private String msg;

    SubEnum(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }
}
