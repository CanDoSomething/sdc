package com.zgczx.exception;

import com.zgczx.config.ProjectUrlConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView handlerAuthorizeException(){
       // log.info("url---->"+userAuthorizeException.getReturnUrl()+"?"+userAuthorizeException.getQueryString());
       // String returnUrl = userAuthorizeException.getReturnUrl()+"?"+userAuthorizeException.getQueryString();
       // redirectAttributes.addAttribute("returnUrl",returnUrl);
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWeChatMpAuthorize())
                .concat("/wechat/authorizeByOpenid")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getSdc())
                .concat("/user/login")
        );
    }
}
