package com.zgczx.service.impl;

import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dataobject.TeaFeedBack;
import com.zgczx.dto.CourseDTO;
import com.zgczx.enums.SubStatusEnum;
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
        teaCourse.setCourseName("计算机视觉");
        teaCourse.setCourseStartTime(new Date());
        teaCourse.setCourseStatus(SubStatusEnum.SUB_WAIT.getCode());
        teaCourse.setIsOnline(1);
        teaCourse.setTeaCode("2");

        teaCourse.setCreateTime(new Date());
        teaCourse.setUpdateTime(new Date());
        TeaCourse course = teaService.createCourse(teaCourse);
        System.out.println(course);

        Assert.assertNotNull(course);
    }
    @Test
    public void cancelCourse(){
        TeaCourse course = teaService.cancelCourse(15,"有重要会议啊！！！！");
        Assert.assertNotNull(course);
    }
    @Test
    public void findTeaHistoryCourse() {
        //String teaCode = "10";
        String teaCode = "2";
        List<CourseDTO> list =  teaService.findTeaHistoryCourse(teaCode,0,10);
        for(CourseDTO courseDTO : list ){
            System.out.println(courseDTO.toString());
        }
        Assert.assertNotNull(list);
    }
    @Test
    public void findCandidateByCourseId(){
        Integer courseId = new Integer(15);
        //Integer courseId = new Integer(115);
        List<StuBase> list =  teaService.findCandidateByCourseId(courseId,0,10);
        System.out.println("所有预约的学生"+list);
        Assert.assertNotNull(list);
    }
    @Test
    public void  saveSelectedStu() {
        //String stuCode = "";
        String stuCode = "1";
        Integer courseId = 15;
        SubCourse subCourse = teaService.saveSelectedStu(stuCode,courseId);
        System.out.println("被选中的学生"+subCourse);
        Assert.assertNotNull(subCourse);
    }

    @Test
    public void createFeedBack() {
        TeaFeedBack teaFeedBack = new TeaFeedBack();
        teaFeedBack.setCourseId(15);
        teaFeedBack.setFeedToStuContent("知之为知之");
        teaFeedBack.setScore(5);
        teaFeedBack.setTeaCode("1");
        teaFeedBack = teaService.createFeedBack(teaFeedBack);
        System.out.println("教师给学生的反馈信息"+teaFeedBack);
        Assert.assertNotNull(teaFeedBack);
    }
    @Test
    public void findTeaCourseById() {
        TeaCourse teaCourseById = teaService.findTeaCourseById(15);
        System.out.println(teaCourseById);
    }
    @Test
    public void updateCourse(){
        TeaCourse teaCourseById = teaService.findTeaCourseById(15);
        teaCourseById.setCourseName("修改一下名称");
        TeaCourse teaCourse = teaService.saveUpdateTeaCourse(teaCourseById);
        System.out.println(teaCourse);
    }

    @Test
    public void finishCourse(){
        Integer courseId = 15;
        TeaCourse teaCourseById = teaService.findTeaCourseById(courseId);
        TeaCourse teaCourse = teaService.finishCourse(teaCourseById.getCourserId());
        System.out.println(teaCourse);
    }
}