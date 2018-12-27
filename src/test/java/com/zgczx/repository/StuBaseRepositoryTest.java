package com.zgczx.repository;

import com.zgczx.dataobject.StuBase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StuBaseRepositoryTest {

    @Autowired
    private StuBaseRepository stuBaseRepository;

    @Test
    public void save(){
        StuBase stuBase = new StuBase();
        stuBase.setStuCode("1");
        stuBase.setStuName("jason");
        stuBase.setStuLevel("高中");
        stuBase.setStuGrade("一班");
        stuBase.setStuClass("2");

        StuBase result =
                stuBaseRepository.save(stuBase);
        Assert.assertNotNull(result);
    }
}
