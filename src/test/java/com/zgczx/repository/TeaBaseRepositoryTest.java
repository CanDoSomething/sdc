package com.zgczx.repository;

import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.TeaBase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeaBaseRepositoryTest {

    @Autowired
    private TeaBaseRepository teaBaseRepository;

    @Test
    public void save(){
        TeaBase teaBase = new TeaBase();
        teaBase.setTeaCode("1");
        teaBase.setTeaName("amy");
        teaBase.setTeaSubject("数学");
        //teaBase.setTeaOpenid("111");

        TeaBase result =
                teaBaseRepository.save(teaBase);
        Assert.assertNotNull(result);
    }
}
