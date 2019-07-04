package com.zgczx.exception;

import com.zgczx.enums.ResultEnum;
import com.zgczx.enums.UserEnum;
import lombok.Getter;

/**
 * @Author: Dqd
 * @Date: 2019/7/3 14:31
 * @Description:
 */
@Getter
public class UserRegisterException extends RuntimeException {

    public Integer code;

    public String data;

    public UserRegisterException(Integer code, String message){
        super(message);
        this.code =code;
    }
    public UserRegisterException(ResultEnum resultEnum, String data) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.data = data;
    }

    public UserRegisterException(UserEnum userEnum){
        super(userEnum.getMessage());
        this.code = userEnum.getCode();
    }
}

