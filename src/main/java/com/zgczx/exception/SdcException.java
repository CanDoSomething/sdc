package com.zgczx.exception;
import com.zgczx.enums.ResultEnum;
import lombok.Getter;

/**
 * Created by Dqd on 2018/12/11.
 */
@Getter
public class SdcException extends RuntimeException {
    private Integer code;

    public SdcException(ResultEnum resultEnum){

        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public SdcException(Integer code, String message){
        super(message);
        this.code =code;
    }
}
