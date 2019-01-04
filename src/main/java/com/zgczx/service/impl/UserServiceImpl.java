package com.zgczx.service.impl;

import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.TeaBase;
import com.zgczx.enums.ResultEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.form.StuInfoForm;
import com.zgczx.form.TeaInfoForm;
import com.zgczx.repository.StuBaseRepository;
import com.zgczx.repository.TeaBaseRepository;
import com.zgczx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: Jason
 * @Author: Administrator
 * @Date: 2018/12/27 14:45
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StuBaseRepository stuBaseRepository;
    @Autowired
    private TeaBaseRepository teaBaseRepository;

    @Override
    public StuBase createStuBase(String stuOpenid, String nickname, String headImgUrl) {
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
        return teaBaseRepository.findByteaOpenid(teaOpenid);
    }

    @Override
    public StuBase registerStuBaseByOpenid(String stuOpenid, StuInfoForm stuInfoForm) {

        // 1.根据openid找到该学生
        StuBase stuBase = stuBaseRepository.findByStuOpenid(stuOpenid);

        // 2.根据学生提交的信息更新
        stuBase.setStuCode(stuInfoForm.getStuCode());
        stuBase.setStuName(stuInfoForm.getStuName());
        stuBase.setStuLevel(stuInfoForm.getStuLevel());
        stuBase.setStuGrade(stuInfoForm.getStuGrade());
        stuBase.setStuClass(stuInfoForm.getStuClass());
        stuBase.setStuPasswd(stuInfoForm.getStuPasswd());

        StuBase updatedStuBase = stuBaseRepository.save(stuBase);

        if(updatedStuBase != null){
            return updatedStuBase;
        }else{
            //TODO 更新枚举类
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }
    }

    @Override
    public TeaBase registerTeaBaseByOpenid(String teaOpenid, TeaInfoForm teaInfoForm) {

        // 1.根据openid找到该教师
        TeaBase teaBase = teaBaseRepository.findByteaOpenid(teaOpenid);

        // 2.根据教师提交的信息更新
        teaBase.setTeaCode(teaInfoForm.getTeaCode());
        teaBase.setTeaName(teaInfoForm.getTeaName());
        teaBase.setTeaSubject(teaInfoForm.getTeaSubject());
        teaBase.setTeaPasswd(teaInfoForm.getTeaPasswd());

        TeaBase updatedTeaBase = teaBaseRepository.save(teaBase);

        if(updatedTeaBase != null){
            return updatedTeaBase;
        }else {
            // TODO 更新枚举类
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }
    }
}
