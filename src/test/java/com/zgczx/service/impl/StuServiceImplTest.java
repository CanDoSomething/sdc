package com.zgczx.service.impl;

import com.zgczx.SellApplicationTests;
import com.zgczx.dataobject.StuFeedBack;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.enums.SubStatusEnum;
import com.zgczx.repository.SubCourseRepository;
import com.zgczx.repository.TeaCourseRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class StuServiceImplTest extends SellApplicationTests {
    @Autowired
    private StuServiceImpl stuService;
    @Autowired
    private SubCourseRepository subCourseRepository;
    @Autowired
    private TeaCourseRepository teaCourseRepository;
    /*查询可预约课程测试*/
    @Test
    public void findAllCourse() {
        List<CourseDTO> allCourse = stuService.findAllCourse(0,10);
        for (CourseDTO courseDTO : allCourse) {
            System.out.println(courseDTO);
        }
    }
    /*预约测试*/
    @Test
    public void ordertest(){
        SubCourse zx = stuService.order("zx", 2);
        System.out.println(zx);
    }
    /*未预约成功前取消预约测试*/
    @Test
    public void simpleCancel(){
        SubCourse zx = stuService.simplecancelorder("zx", 2);
        System.out.println("取消成功");

    }
    /*成功预约后取消预约测试*/
    @Test
    public void cancel(){
        TeaCourse cancelorder = stuService.cancelorder("有事", "zx", 1);
        System.out.println(cancelorder);
    }
    /*查询历史记录*/
    @Test
    public void lookhistory(){
        List<CourseDTO> courseDTOSs = stuService.lookhistory(0,10, "zx");
        for (CourseDTO courseDTO : courseDTOSs) {
            System.out.println(courseDTO);
        }
    }
    /*提交反馈信息*/
    @Test
    public void feed(){
        StuFeedBack good = stuService.feedback(6, "好的", 5);
        System.out.println(good);
    }
    @Test
    public void test(){
        Date date1=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2= null;
        try {
            date2 = df.parse("2018-12-11 11:01:11");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long l = date1.getTime() - date2.getTime();
        if (l>0){
            System.out.println("date1大于date2");
        }else {
            System.out.println("date2大于date1");
        }
    }
    @Test
    public void find(){
        Iterable<TeaCourse> all = teaCourseRepository.findAll();
        for (TeaCourse teaCourse : all) {
            System.out.println(teaCourse);
        }

    }
}