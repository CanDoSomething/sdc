package com.zgczx.exception;

/**
 * @ClassName: Jason
 * @Author: Administrator
 * @Date: 2018/12/13 14:53
 * @Description:
 */
public class UserAuthorizeException extends RuntimeException {

    private String returnUrl;

    public UserAuthorizeException(String url){
        this.returnUrl = url;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
