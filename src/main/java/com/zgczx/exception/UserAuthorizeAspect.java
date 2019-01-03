package com.zgczx.exception;

import com.zgczx.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: Jason
 * @Author: Administrator
 * @Date: 2018/12/13 14:13
 * @Description:
 */
@Aspect
@Component
@Slf4j
public class UserAuthorizeAspect {

    /**
     * 对需要openid验证的添加切点
     */
    @Pointcut("execution(public * com.zgczx.controller.StuController*.*(..)) ")
    public void verifyStu(){}

    @Pointcut("execution(public * com.zgczx.controller.TeaController*.*(..)) ")
    public void verifyTea(){}

    @Pointcut("execution(public * com.zgczx.controller.UserController*.*(..)) ")
    public void verifyUser(){}

    @Before(value = "verifyStu() || verifyTea() || verifyUser()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String requestURL = request.getRequestURL().toString();
        String queryString = request.getQueryString();

        Cookie cookie = CookieUtil.get(request,"openid");
        if (cookie == null){
            log.warn("【登录检验】 Cookie中查不到openid");
            throw new UserAuthorizeException(requestURL,queryString);
        }else{
            log.info("【登录检验】 Cookie查到了openid！！！");
        }

        /*
         * 或者去redis中查询
         */


    }



}
