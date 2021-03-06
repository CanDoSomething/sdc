package com.zgczx.controller;

import com.zgczx.config.ProjectUrlConfig;
import com.zgczx.enums.ResultEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.service.StuService;
import com.zgczx.service.TeaService;
import com.zgczx.service.UserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;


/**
 * 微信验证控制器
 *
 * @author Jason
 */

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {

    private final WxMpService wxMpService;

    private final ProjectUrlConfig projectUrlConfig;

    private final UserService userService;

    @Autowired
    private  StuService stuService;
    @Autowired
    private  TeaService teaService;

    @Autowired
    WeChatController(WxMpService wxMpService,ProjectUrlConfig projectUrlConfig,UserService userService){
        this.wxMpService = wxMpService;
        this.projectUrlConfig = projectUrlConfig;
        this.userService = userService;
    }

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl,
                            @RequestParam("role") Integer role,
                            @RequestParam("path") String path){

        //1.配置
        //2.调用方法
        String url = projectUrlConfig.getWeChatMpAuthorize() + "/sdc/sdc/wechat/userInfo?role="+role+"&path="+path;
        String redirectUrl =  wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】 获取code，result={}",redirectUrl);
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl,
                           @RequestParam("role") Integer role,
                           @RequestParam("path") String path){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        WxMpUser wxMpUser = new WxMpUser();
        try{
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        }catch (WxErrorException e){
            log.error("【微信网页授权】 {}",e);
            throw new SdcException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openid = wxMpOAuth2AccessToken.getOpenId();
        String nickname = wxMpUser.getNickname();
        String headImgUrl = wxMpUser.getHeadImgUrl();

        // 首次创建用户信息
        if(role.equals(UserService.STU_ROLE)){
            // 创建学生账号
            if(!stuService.legalStudent(openid)){
                userService.createStuBase(openid,nickname,headImgUrl);
            }
        }else if(role.equals(UserService.TEA_ROLE)){
            // 创建教师账号
            if(!teaService.legalTeacher(openid)){
                userService.createTeaBase(openid,nickname,headImgUrl);
            }
        }
        return "redirect:"+projectUrlConfig.getSdc()
                .concat("/sdc")
                .concat("/sdc")
                .concat("/"+returnUrl)
                .concat("?openid="+openid)
                .concat("&path="+path);
    }

    @GetMapping("/authorizeByOpenid")
    public String authorizeByOpenid(@RequestParam("returnUrl") String returnUrl,
                                    @RequestParam("path") String path) {

        log.info("returnUrl-->"+returnUrl);
        //1.配置
        //2.调用方法
        String url = projectUrlConfig.getWeChatMpAuthorize() + "/sdc/sdc/wechat/userInfoByOpenid?path="+path;
        String redirectUrl =  wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, returnUrl);
        log.info("【微信网页授权 仅获取openid】 获取code，result={}",redirectUrl);
        return "redirect:" + redirectUrl;
    }
    @GetMapping("/userInfoByOpenid")
    public String userInfoByOpenid(@RequestParam("code") String code,
                                   @RequestParam("state") String returnUrl,
                                   @RequestParam("path") String path) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessTokenByOpenid;
        try {
            wxMpOAuth2AccessTokenByOpenid = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权 仅获取openid】 {}", e);
            throw new SdcException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openid = wxMpOAuth2AccessTokenByOpenid.getOpenId();
        log.info("returnUrl-->"+returnUrl);
        return "redirect:"+projectUrlConfig.getSdc()
                .concat("/sdc")
                .concat("/sdc")
                .concat("/"+returnUrl)
                .concat("?openid="+openid)
                .concat("&path="+path);
    }
}