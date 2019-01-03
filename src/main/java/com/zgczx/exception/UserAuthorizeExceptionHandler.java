package com.zgczx.exception;

import com.zgczx.config.ProjectUrlConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @ClassName: Jason
 * @Author: Administrator
 * @Date: 2018/12/13 14:55
 * @Description:
 */
@ControllerAdvice
@Slf4j
public class UserAuthorizeExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;


    @ExceptionHandler(value = UserAuthorizeException.class)
    public String handlerAuthorizeException(UserAuthorizeException userAuthorizeException){
        log.info("url---->"+userAuthorizeException.getReturnUrl()+"?"+userAuthorizeException.getQueryString());
        return "redirect:".concat(projectUrlConfig.getWeChatMpAuthorize())
                .concat("/wechat/authorizeByOpenid/?returnUrl=")
                .concat(userAuthorizeException.getReturnUrl())
                .concat("?"+userAuthorizeException.getQueryString());
    }

}
