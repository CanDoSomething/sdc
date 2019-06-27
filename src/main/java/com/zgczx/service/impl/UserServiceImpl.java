package com.zgczx.service.impl;

import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.TeaBase;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.enums.CourseEnum;
import com.zgczx.enums.UserEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.form.StuInfoForm;
import com.zgczx.form.TeaInfoForm;
import com.zgczx.repository.StuBaseRepository;
import com.zgczx.repository.TeaBaseRepository;
import com.zgczx.repository.TeaCourseRepository;
import com.zgczx.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: Jason
 * @Author: Administrator
 * @Date: 2018/12/27 14:45
 * @Description:
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private StuBaseRepository stuBaseRepository;
    @Autowired
    private TeaBaseRepository teaBaseRepository;
    @Autowired
    private TeaCourseRepository teaCourseRepository;

    @Override
    public StuBase createStuBase(String stuOpenid, String nickname, String headImgUrl) {

        StuBase stuBase_check = stuBaseRepository.findByStuOpenid(stuOpenid);
        if(stuBase_check !=null){
            log.info("【创建学生】 该学生的openid已经创建，stuOpenid={}",stuOpenid);
            throw new SdcException(UserEnum.stuOpenid_is_created);
        }
        if(nickname.contains("\\")) {
            log.info("【创建学生信息】 该学生的昵称中包含非法字符使用Openid={}代替昵称",stuOpenid);
            nickname = stuOpenid;
        }
        StuBase stuBase = new StuBase();
        // 学生学籍号暂用openid 代替
        stuBase.setStuCode(stuOpenid);
        stuBase.setStuOpenid(stuOpenid);
        stuBase.setStuNickname(nickname);
        stuBase.setStuHeadimgurl(headImgUrl);
        StuBase saved_stuBase = stuBaseRepository.save(stuBase);
        return saved_stuBase;
    }

    @Override
    public TeaBase createTeaBase(String teaOpenid, String nickname, String headImgUrl) {

        log.info("【创建老师信息】 用户昵称，nickname = {}",nickname);
        TeaBase teaBase_check = teaBaseRepository.findByTeaOpenid(teaOpenid);
        if(teaBase_check != null){
            log.info("【创建老师信息】 该老师的teaOpenid已经被创建,teaOpenid={}",teaOpenid);
        }
        if(nickname.contains("\\")){
            log.info("【创建老师】 该老师的昵称中包含非法字符使用Openid={}代替昵称",teaOpenid);
            nickname = teaOpenid;
        }
        TeaBase teaBase = new TeaBase();
        // 教师工号暂用openid 代替
        teaBase.setTeaCode(teaOpenid);
        teaBase.setTeaOpenid(teaOpenid);
        teaBase.setTeaNickname(nickname);
        teaBase.setTeaHeadimgurl(headImgUrl);
        TeaBase saved_teaBase = teaBaseRepository.save(teaBase);
        return saved_teaBase;
    }

    @Override
    public StuBase findStuBaseByOpenid(String stuOpenid) {

        return stuBaseRepository.findByStuOpenid(stuOpenid);
    }

    @Override
    public TeaBase findTeaBaseByOpenid(String teaOpenid) {
        return teaBaseRepository.findByTeaOpenid(teaOpenid);
    }

    @Override
    public StuBase registerStuBaseByOpenid(String stuOpenid, StuInfoForm stuInfoForm) {


        //1.判断stuOpenid是否已经注册过
        if(null == stuBaseRepository.findByStuOpenid(stuOpenid)){
            log.info("【学生注册】 stuOpenid没有注册过，请走'/wechat/authorize' 接口,stuOpenid = {}",stuOpenid);
            throw new SdcException(UserEnum.stuOpenid_not_registered.getCode(),
            UserEnum.stuOpenid_not_registered.getMessage());
        }

        //2.根据学生提交的信息更新
        StuBase stuBase = stuBaseRepository.findByStuOpenid(stuOpenid);

        stuBaseRepository.delete(stuBase);
        stuBase.setStuCode(stuInfoForm.getStuCode());
        stuBase.setStuName(stuInfoForm.getStuName());
        stuBase.setStuLevel(stuInfoForm.getStuLevel());
        stuBase.setStuGrade(stuInfoForm.getStuGrade());
        stuBase.setStuClass(stuInfoForm.getStuClass());
        stuBase.setStuPasswd(stuInfoForm.getStuPasswd());
        log.info("【学生注册】 更新学生个人信息 {}",stuBase.toString());
        StuBase updatedStuBase = stuBaseRepository.save(stuBase);

        if(updatedStuBase != null){
            return updatedStuBase;
        }else{
            throw new SdcException(UserEnum.DB_ERROR);
        }
    }

    @Override
    public TeaBase registerTeaBaseByOpenid(String teaOpenid, TeaInfoForm teaInfoForm) {

        //1.判断teaOpenid是否已经注册过
        if(null == teaBaseRepository.findByTeaOpenid(teaOpenid)){
            log.info("【教师注册】 teaOpenid未注册，请走'/wechat/authorize' 接口,teaOpenid = {}",teaOpenid);
            throw new SdcException(UserEnum.teaOpenid_not_registered);
        }

        //2.根据教师提交的信息更新
        TeaBase teaBase = teaBaseRepository.findByTeaOpenid(teaOpenid);
        teaBaseRepository.delete(teaBase);
        // 2.根据教师提交的信息更新
        teaBase.setTeaCode(teaInfoForm.getTeaCode());
        teaBase.setTeaName(teaInfoForm.getTeaName());
        teaBase.setTeaSubject(teaInfoForm.getTeaSubject());
        teaBase.setTeaPasswd(teaInfoForm.getTeaPasswd());

        log.info("【教师注册】 更新教师个人信息 {}",teaBase.toString());
        TeaBase updatedTeaBase = teaBaseRepository.save(teaBase);

        if(updatedTeaBase != null){
            return updatedTeaBase;
        }else {
            throw new SdcException(UserEnum.DB_ERROR);
        }
    }

    @Override
    public String deleteTeaByOpenid(String teaOpenid) {

        //1.找到该老师创建的课程，设置为课程失效
        TeaBase teaBase = teaBaseRepository.findByTeaOpenid(teaOpenid);
        List<TeaCourse> teaCourseList = teaCourseRepository.findByTeaCode(teaBase.getTeaCode());
        for(TeaCourse teaCourse:teaCourseList){
            teaCourse.setCourseStatus(CourseEnum.COURSE_CANCELED.getCode());
            teaCourseRepository.save(teaCourse);
        }

        //2.再删除该老师
        if(teaBase!=null){
            teaBaseRepository.delete(teaBase);
            return "删除成功";
        }else {
            return "没有找到，删除失败";
        }
    }

    @Override
    public String deleteStuByOpenid(String stuOpenid) {
        StuBase stuBase = stuBaseRepository.findByStuOpenid(stuOpenid);
        if(stuBase != null){
            stuBaseRepository.delete(stuBase);
            return "删除成功";
        }else{
            return "没有找到，删除失败";
        }
    }

    @Override
    public StuBase findStuBaseByStuCode(String stuCode) {
        return stuBaseRepository.findByStuCode(stuCode);
    }

    @Override
    public TeaBase findTeaBaseByTeaCode(String teaCode) {
        return teaBaseRepository.findOne(teaCode);
    }
}
