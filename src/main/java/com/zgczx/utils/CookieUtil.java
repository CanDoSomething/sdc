package com.zgczx.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: Jason
 * @Author: Administrator
 * @Date: 2018/12/13 14:21
 * @Description:
 */
public class CookieUtil {
    /**
     * 设置并添加cookie
     *
     * @param response 响应内容
     * @param name  Cookie名称
     * @param value Cookie内容
     * @param maxAge 最长时间
     */
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     *
     * @param request   请求内容
     * @param name  Cookie名称
     * @return  返回Cookie
     */
    public static Cookie get(HttpServletRequest request,
                             String name){
        Map<String,Cookie> cookieMap = readCookieMap(request);
        return cookieMap.getOrDefault(name,null);
    }

    /**
     * 将cookie封装成Map
     *
     * @param request 请求内容
     * @return  返回Map类型封装Cookie
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String,Cookie> cookieMap = new HashMap<>(16);
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie:cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }

}
