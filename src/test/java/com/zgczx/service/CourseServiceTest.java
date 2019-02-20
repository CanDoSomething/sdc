package com.zgczx.service;

import com.zgczx.dataobject.OnlineCourse;
import com.zgczx.service.impl.CourseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Jason
 * @date 2019/2/20 14:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CourseServiceTest {

    @Autowired
    private CourseServiceImpl courseService;

    @Test
    public void notOnCourse(){
        //不在该课程的测试
        String openid = "oMXOb1T_tPkgAa9dUJtpjwj9ykzo";
        Integer courseId = 12;
        Boolean notOnCourse = courseService.onCourse(openid,courseId);
        log.info("notOnCourse");
    }

    @Test
    public void getOnlineCourseGroupId(){
        Integer courseId = 12;
        OnlineCourse onlineCourse = courseService.getOnlineCourseGroupId(courseId);
        log.info(onlineCourse.toString());
    }
}
