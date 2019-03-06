package com.zgczx.controller;


import com.zgczx.VO.ResultVO;
import com.zgczx.config.ProjectUrlConfig;
import com.zgczx.constant.CookieConstant;
import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.TeaBase;
import com.zgczx.enums.ResultEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.form.StuInfoForm;
import com.zgczx.form.TeaInfoForm;
import com.zgczx.service.UserService;
import com.zgczx.utils.CookieUtil;
import com.zgczx.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @ClassName: UserController
 * @Author: Jason
 * @Date: 2019/1/1 14:58
 * @Description:
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final ProjectUrlConfig projectUrlConfig;

    private String info = null;
    @Autowired
    public UserController(UserService service, ProjectUrlConfig projectUrlConfig) {
        this.userService = service;
        this.projectUrlConfig = projectUrlConfig;
    }

    @GetMapping("/queryUserInfo")
    @ResponseBody
    public ResultVO<?> queryUserInfo(@RequestParam("openid") String openid){

        //1. openid是否存在数据库
        StuBase stuBase = userService.findStuBaseByOpenid(openid);
        TeaBase teaBase = userService.findTeaBaseByOpenid(openid);

        //2.判断是否为第一次注册的用户
        StuBase stuBase1 = userService.findStuBaseByStuCode(openid);
        TeaBase teaBase1 = userService.findTeaBaseByTeaCode(openid);

        //3.若都不存在，即为未注册
        if(null == stuBase1 && null ==teaBase1 &&
                null == stuBase && null ==teaBase){
            info = "【该openid没有注册】 "+openid;
            log.info(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }

        if(stuBase != null){
            return ResultVOUtil.success(stuBase);
        }else {
            return ResultVOUtil.success(teaBase);
        }

    }
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response){
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);

        if (cookie != null){
            //2. 清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
            log.info("【用户管理】 清除cookie中的openid");
        }
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getSdc()
                .concat(projectUrlConfig.homeAddress)));
    }

    /**
     * 学生根据openid创建表单信息
     *
     * @param stuInfoForm 学生信息表单信息
     * @param bindingResult 表单验证结果
     * @param stuOpenid 学生微信唯一凭证
     * @return ResultVO
     */
    @PostMapping("/registerStuBaseByOpenid")
    @ResponseBody
    public ResultVO<?> registerStuBaseByOpenid(@Valid StuInfoForm stuInfoForm, BindingResult bindingResult,
                                               @RequestParam("stuOpenid") String stuOpenid ){
        if(bindingResult.hasErrors()){
            info = "【学生注册】参数不正确，"+ stuInfoForm.toString();
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        if(StringUtils.isEmpty(stuOpenid)||stuOpenid.equals("undefined")){
            log.error("【学生注册】stuOpenid 为空,stuOpenid={}",stuOpenid);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION.getCode(),ResultEnum.PARAM_EXCEPTION.getMessage());
        }

        StuBase stuBase = userService.registerStuBaseByOpenid(stuOpenid,stuInfoForm);
        return ResultVOUtil.success(stuBase);
    }

    @PostMapping("/registerTeaBaseByOpenid")
    @ResponseBody
    public ResultVO<?> registerTeaBaseByOpenid(@Valid TeaInfoForm teaInfoForm,BindingResult bindingResult,
                                               @RequestParam("teaOpenid") String teaOpenid){
        if(bindingResult.hasErrors()){
            log.error("【教师注册】参数不正确，teaInfoForm={}",teaInfoForm.toString());
            throw new SdcException(ResultEnum.PARAM_EXCEPTION.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        if(StringUtils.isEmpty(teaOpenid)||("undefined").equals(teaOpenid)){
            log.error("【教师注册】teaOpenid 为空,teaOpenid={}",teaOpenid);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION.getCode(),ResultEnum.PARAM_EXCEPTION.getMessage());
        }

        TeaBase teaBase = userService.registerTeaBaseByOpenid(teaOpenid,teaInfoForm);

        return ResultVOUtil.success(teaBase);

    }
    @GetMapping("/deleteTeaByOpenid")
    @ResponseBody
    public  ResultVO<?> deleteTeaByOpenid(@RequestParam("teaOpenid") String teaOpenid){

        String str = userService.deleteTeaByOpenid(teaOpenid);

        return ResultVOUtil.success(str);
    }
    @GetMapping("/deleteStuByOpenid")
    @ResponseBody
    public  ResultVO<?> deleteStuByOpenid(@RequestParam("stuOpenid") String stuOpenid){

        String str = userService.deleteStuByOpenid(stuOpenid);

        return ResultVOUtil.success(str);
    }

}
