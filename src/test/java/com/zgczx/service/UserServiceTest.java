package com.zgczx.service;

import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.TeaBase;
import com.zgczx.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: Jason
 * @Author: Administrator
 * @Date: 2018/12/27 14:55
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void saveStu(){
        StuBase stuBase = userService.createStuBase("openid", "nickname", "headingurl");
        Assert.assertNotNull(stuBase);
    }
    @Test
    public void saveTea(){
        TeaBase teaBase = userService.createTeaBase("111","nickname","headimgurl");
        Assert.assertNotNull(teaBase);
    }


}
