package com.zgczx.exception;


import com.zgczx.enums.ResultEnum;
import com.zgczx.enums.SubStatusEnum;
import lombok.Data;


/**
 * Created by Dqd on 2018/12/11.
 */
@Data
public class SdcException extends RuntimeException {
    public Integer code;

    public SdcException(Integer code, String message){
        super(message);
        this.code =code;
    }
    public SdcException(SubStatusEnum notSubcourse) {
        super(notSubcourse.getMessage());
        this.code = notSubcourse.getCode();
    }
}
