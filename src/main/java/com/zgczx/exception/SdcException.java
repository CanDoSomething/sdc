package com.zgczx.exception;

<<<<<<< HEAD

import com.zgczx.enums.ResultEnum;
=======
import com.zgczx.enums.tea.ResultEnum;
>>>>>>> 4017ceabe2bcc9643799113c949c639b0a09e36c

/**
 * Created by Dqd on 2018/12/11.
 */
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
