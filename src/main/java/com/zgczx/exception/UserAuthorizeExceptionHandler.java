package com.zgczx.exception;

import com.zgczx.config.ProjectUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @ResponseBody
    @ExceptionHandler(value = UserAuthorizeException.class)
    public String handlerAuthorizeException(UserAuthorizeException userAuthorizeException){
        return "redirect:".concat(projectUrlConfig.getWeChatMpAuthorize())
                .concat("/wechat/authorizeByOpenid/?returnUrl=")
                .concat(userAuthorizeException.getReturnUrl());
    }

}
