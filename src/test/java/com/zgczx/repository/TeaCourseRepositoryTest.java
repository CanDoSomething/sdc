package com.zgczx.repository;

import com.zgczx.dataobject.TeaBase;
import com.zgczx.dataobject.TeaCourse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.ws.soap.Addressing;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeaCourseRepositoryTest {

    @Autowired
    private TeaCourseRepository teaCourseRepository;

    @Test
    public void save(){
        TeaCourse teaCourse = new TeaCourse();

        teaCourse.setTeaCode("2");
        teaCourse.setCourseName("数学");
        teaCourse.setCourse_date(new Date());
        teaCourse.setCourseStartTime(new Date());
        teaCourse.setCourseEndTime(new Date());
        teaCourse.setCourseStatus(1);
        TeaCourse result =
                teaCourseRepository.save(teaCourse);
        Assert.assertNotNull(result);
    }
}
