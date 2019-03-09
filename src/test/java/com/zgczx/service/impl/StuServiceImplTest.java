package com.zgczx.service.impl;

import com.zgczx.SellApplicationTests;
import com.zgczx.dataobject.FeedBack;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.dto.SubDTO;
import com.zgczx.service.StuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class StuServiceImplTest extends SellApplicationTests {
    @Autowired
    private StuService stuService;
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
    public void orderTest(){
        SubCourse zx = stuService.order("openid1", 1);
        System.out.println(zx);
    }
    /*取消预约测试*/
    @Test
    public void cancel(){
        SubCourse subCourse = stuService.cancelOrder("有事", "openid1", 6,1);
        System.out.println(subCourse);
    }
    /*查询历史记录*/
    @Test
    public void lookHistory(){

        List<SubDTO> courseDTOSs = stuService.lookHistory(0,10, "openid1");
        for (SubDTO courseDTO : courseDTOSs) {
            System.out.println(courseDTO);
        }
    }
    /*提交反馈信息*/
    @Test
    public void feed(){
        FeedBack good = stuService.feedBack(1, "好的", 5,6);
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
}