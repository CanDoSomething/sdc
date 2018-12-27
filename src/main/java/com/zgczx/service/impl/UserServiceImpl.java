package com.zgczx.service.impl;

import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.TeaBase;
import com.zgczx.repository.StuBaseRepository;
import com.zgczx.repository.TeaBaseRepository;
import com.zgczx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    public StuBase saveStuBase(String stuOpenid, String nickname, String headImgUrl) {
        StuBase stuBase = new StuBase();
        stuBase.setStuCode(stuOpenid);
        stuBase.setStuOpenid(stuOpenid);
        stuBase.setStuNickname(nickname);
        stuBase.setStuHeadimgurl(headImgUrl);
        StuBase saved_stuBase = stuBaseRepository.save(stuBase);
        return saved_stuBase;
    }

    @Override
    public TeaBase saveTeaBase(String teaOpenid, String nickname, String headImgUrl) {
        TeaBase teaBase = new TeaBase();
        teaBase.setTeaCode(teaOpenid);
        teaBase.setTeaOpenid(teaOpenid);
        teaBase.setTeaNickname(nickname);
        teaBase.setTeaHeadimgurl(headImgUrl);
        TeaBase saved_teaBase = teaBaseRepository.save(teaBase);
        return saved_teaBase;
    }
}
