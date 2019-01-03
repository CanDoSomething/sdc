package com.zgczx.exception;

import lombok.Getter;

/**
 * @ClassName: Jason
 * @Author: Administrator
 * @Date: 2018/12/13 14:53
 * @Description:
 */
@Getter
public class UserAuthorizeException extends RuntimeException {

    private String returnUrl;
    private String queryString;

    public UserAuthorizeException(String url,String queryString){
        this.returnUrl = url;
        this.queryString = queryString;
    }

}
