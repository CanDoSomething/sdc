package com.zgczx.exception;


import com.zgczx.enums.ResultEnum;
import com.zgczx.enums.UserEnum;
import lombok.Data;


/**
 * Created by Dqd on 2018/12/11.
 *
 * @author Dqd
 */
@Data
public class SdcException extends RuntimeException {

    public Integer code;

    public SdcException(Integer code, String message){
        super(message);
        this.code =code;
    }
    public SdcException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SdcException(UserEnum userEnum){
        super(userEnum.getMessage());
        this.code = userEnum.getCode();
    }
}
