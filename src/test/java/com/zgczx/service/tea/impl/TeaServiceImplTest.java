package com.zgczx.service.tea.impl;

import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dataobject.TeaFeedBack;
import com.zgczx.dto.CourseDTO;
import com.zgczx.service.impl.TeaServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


/**
 * Created by Dqd on 2018/12/11.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeaServiceImplTest{
    @Autowired
    private TeaServiceImpl teaService;

    @Test
    public void createCourse(){
        TeaCourse teaCourse = new TeaCourse();
        teaCourse.setCourse_date(new Date());
        teaCourse.setCourseEndTime(new Date());
        teaCourse.setCourseName("地理");
        teaCourse.setCourseStartTime(new Date());
        teaCourse.setCourseStatus(4);
        teaCourse.setTeaCode("1111");

        teaCourse.setCreateTime(new Date());
        teaCourse.setUpdateTime(new Date());
        TeaCourse course = teaService.createCourse(teaCourse);
        Assert.assertNotNull(course);
    }
    @Test
    public void cancelCourse(){
        TeaCourse course = teaService.cancelCourse(5,"工作太忙");
        Assert.assertNotNull(course);
    }
    @Test
    public void findTeaHistoryCourse() {
        String teaCode = "1";
        List<CourseDTO> list =  teaService.findTeaHistoryCourse(teaCode,0,10);
        System.out.println("没有状态为0的list"+list);
        Assert.assertNotNull(list);
    }
    @Test
    public void findCandidate(){
        Integer courseId = new Integer(5);
        List<StuBase> list =  teaService.findCandidateByCourseId(courseId,0,10);
        System.out.println("所有预约的学生"+list);
        Assert.assertNotNull(list);
    }
    @Test
    public void  saveSelectedStu() {
        String stuCode;
        Integer courseId;
        SubCourse subCourse = teaService.saveSelectedStu("1",5);
        System.out.println("被选中的学生"+subCourse.getStuCode());
        Assert.assertNotNull(subCourse);
    }

    @Test
    public void createFeedBack() {
        TeaFeedBack teaFeedBack = new TeaFeedBack();
        teaFeedBack.setCourseId(5);
        teaFeedBack.setFeedToStuContent("学生很nice!");
        teaFeedBack.setScore(100);
        teaFeedBack.setTeaCode("1");
        teaFeedBack = teaService.createFeedBack(teaFeedBack);
        System.out.println("教师给学生的反馈信息"+teaFeedBack);
        Assert.assertNotNull(teaFeedBack);
    }
}