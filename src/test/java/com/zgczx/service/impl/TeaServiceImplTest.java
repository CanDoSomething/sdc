package com.zgczx.service.impl;

import com.zgczx.dataobject.FeedBack;
import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.dto.StuBaseDTO;
import com.zgczx.form.TeaCourseForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        TeaCourseForm teaCourse = new TeaCourseForm();
        teaCourse.setCourseDate("2019-01-06");
        teaCourse.setCourseEndTime("2019-01-06 09:30:00");
        teaCourse.setCourseName("原子弹理论");
        teaCourse.setCourseStartTime("2019-01-06 08:30:00");
        //teaCourse.setCourseStatus(CourseEnum.SUB_WAIT.getCode());
        teaCourse.setTeaCode("1");
        teaCourse.setCourseInteractive(0);
        teaCourse.setCourseLocation("");
        TeaCourse course = teaService.createCourse(teaCourse,"openidjiaoshi");
        System.out.println(course);
        Assert.assertNotNull(course);
    }
    @Test
    public void cancelCourse(){
        TeaCourse course = teaService.cancelCourse(13,"openidjiaoshi","有重要会议啊！！！！");
        Assert.assertNotNull(course);
    }
    @Test
    public void findTeaHistoryCourse() {
        //String teaCode = "10";
        String openid = "openidjiaoshi";
        List<CourseDTO> list =  teaService.findTeaHistoryCourse(openid,0,10);
        for(CourseDTO courseDTO : list ){
            System.out.println(courseDTO.toString());
        }
        Assert.assertNotNull(list);
    }
    @Test
    public void findCandidateByCourseId(){
        Integer courseId = new Integer(17);
        //Integer courseId = new Integer(115);
        List<StuBaseDTO> list =  teaService.findCandidateByCourseId(courseId,"openidjiaoshi",0,10);
        System.out.println("所有预约的学生"+list);
        Assert.assertNotNull(list);
    }
    @Test
    public void  saveSelectedStu() {
        String stuOpenId = "openid1";
        Integer courseId = 13;
        SubCourse subCourse = teaService.saveSelectedStu(stuOpenId,courseId);
        System.out.println("被选中的学生"+subCourse);
        Assert.assertNotNull(subCourse);
    }

    @Test
    public void saveFeedBack() {

        FeedBack rs = teaService.saveFeedBack(8, "openidjiaoshi","积极上课", 5);
        System.out.println("教师给学生的反馈信息:"+rs);
        Assert.assertNotNull(rs);
    }
    @Test
    public void findTeaCourseById() {
        TeaCourse teaCourseById = teaService.findTeaCourseById(13);
        System.out.println(teaCourseById);
    }
    @Test
    public void updateCourse(){
        TeaCourse teaCourseById = teaService.findTeaCourseById(13);
        teaCourseById.setCourseName("数据挖掘");
        TeaCourse teaCourse = teaService.saveUpdateTeaCourse(teaCourseById);
        System.out.println(teaCourse);
    }

    @Test
    public void finishCourse(){
        Integer courseId = 13;
        TeaCourse teaCourseById = teaService.findTeaCourseById(courseId);
        TeaCourse teaCourse = teaService.finishCourse(teaCourseById.getCourseId());
        System.out.println(teaCourse);
    }
}