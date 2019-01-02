package com.zgczx.repository;

import com.zgczx.dataobject.SubCourse;
import com.zgczx.enums.SubCourseEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubCourseRepositoryTest {

    @Autowired
    private SubCourseRepository subCourseRepository;

    @Test
    public void save(){
        SubCourse subCourse = new SubCourse();
        subCourse.setStuCode("1");
        subCourse.setCourseId(4);
        subCourse.setSubStatus(SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode());//使用枚举类增加可读性
        SubCourse result =
                subCourseRepository.save(subCourse);
        Assert.assertNotNull(result);
    }
}
