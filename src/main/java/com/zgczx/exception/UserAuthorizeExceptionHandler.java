package com.zgczx.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: Jason
 * @Author: Administrator
 * @Date: 2018/12/13 14:55
 * @Description:
 */
@ControllerAdvice
public class UserAuthorizeExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = UserAuthorizeException.class)
    public String handlerAuthorizeException(){
        return "redirect:".concat("跳转地址");
    }

}
