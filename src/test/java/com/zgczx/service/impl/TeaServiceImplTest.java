package com.zgczx.service.impl;

import com.zgczx.dataobject.FeedBack;
import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
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

        teaCourse.setCourseDate(new Date());
        teaCourse.setCourseEndTime(new Date());
        teaCourse.setCourseName("大数据");
        teaCourse.setCourseStartTime(new Date());
        teaCourse.setCourseStatus(SubStatusEnum.SUB_WAIT.getCode());
        teaCourse.setCourseLocation("计算所");
        teaCourse.setCourseInteractive(1);
        teaCourse.setTeaCode("1");

        teaCourse.setCreateTime(new Date());
        teaCourse.setUpdateTime(new Date());
        TeaCourse course = teaService.createCourse(teaCourse);
        System.out.println(course);

        Assert.assertNotNull(course);
    }
    @Test
    public void cancelCourse(){
        TeaCourse course = teaService.cancelCourse(3,"有重要会议啊！！！！");
        Assert.assertNotNull(course);
    }
    @Test
    public void findTeaHistoryCourse() {
        //String teaCode = "10";
        String teaCode = "1";
        List<CourseDTO> list =  teaService.findTeaHistoryCourse(teaCode,0,10);
        for(CourseDTO courseDTO : list ){
            System.out.println(courseDTO.toString());
        }
        Assert.assertNotNull(list);
    }
    @Test
    public void findCandidateByCourseId(){
        Integer courseId = new Integer(4);
        //Integer courseId = new Integer(115);
        List<StuBase> list =  teaService.findCandidateByCourseId(courseId,1,1);
        System.out.println("所有预约的学生"+list);
        Assert.assertNotNull(list);
    }
    @Test
    public void  saveSelectedStu() {
        //String stuCode = "";
        String stuCode = "1";
        Integer courseId = 3;
        SubCourse subCourse = teaService.saveSelectedStu(stuCode,courseId);
        System.out.println("被选中的学生"+subCourse);
        Assert.assertNotNull(subCourse);
    }

    @Test
    public void saveFeedBack() {

        FeedBack rs = teaService.saveFeedBack(6, "作为学生干就完了", 5);
        System.out.println("教师给学生的反馈信息:"+rs);
        Assert.assertNotNull(rs);
    }
    @Test
    public void findTeaCourseById() {
        TeaCourse teaCourseById = teaService.findTeaCourseById(3);
        System.out.println(teaCourseById);
    }
    @Test
    public void updateCourse(){
        TeaCourse teaCourseById = teaService.findTeaCourseById(3);
        teaCourseById.setCourseName("大数据分析");
        TeaCourse teaCourse = teaService.saveUpdateTeaCourse(teaCourseById);
        System.out.println(teaCourse);
    }

    @Test
    public void finishCourse(){
        Integer courseId = 3;
        TeaCourse teaCourseById = teaService.findTeaCourseById(courseId);
        TeaCourse teaCourse = teaService.finishCourse(teaCourseById.getCourseId());
        System.out.println(teaCourse);
    }
}