package com.zgczx.exception;

import com.zgczx.VO.ResultVO;
import com.zgczx.utils.ResultVOUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Dqd
 * @Date: 2019/7/3 14:32
 * @Description:
 */

@ControllerAdvice
public class UserRegisterExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = UserRegisterException.class)
    public ResultVO<?> handleSdcException(UserRegisterException ure){
        return ResultVOUtil.error(ure.getCode(),ure.getMessage(),ure.getData());
    }
}