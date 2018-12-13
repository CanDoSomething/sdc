package com.zgczx.service.impl;

import com.zgczx.SellApplicationTests;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.enums.SubStatusEnum;
import com.zgczx.repository.SubCourseRepository;
import com.zgczx.utils.ResultVOUtil;
import com.zgczx.utils.SearchUtil;
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
    /*查询可预约课程测试*/
    @Test
    public void findAllCourse() {
        SearchUtil searchUtil=new SearchUtil();
        /*设置以课程开始时间升序来进行排列*/
        searchUtil.setOrderBy("courseStartTime");
        /*设置查询字段*/
        List<String> keywords=new ArrayList<>();
        keywords.add("courseStatus");
        searchUtil.setKeywords(keywords);
        List<CourseDTO> allCourse = stuService.findAllCourse(searchUtil);
        for (CourseDTO courseDTO : allCourse) {
            System.out.println(courseDTO);
        }
        System.out.println("ok");
    }
    /*预约测试*/
    @Test
    public void ordertest(){
        stuService.order("zx",4);
    }
    /*取消预约测试*/
    @Test
    public void cancel(){
        SubCourse subCourse = subCourseRepository.findByStuCodeAndCourseId("zx", 1);
        /*如果预约信息处于预约等待状态，直接删除预约信息*/
        subCourseRepository.delete(subCourse);
        if (subCourse.getSubStatus()== SubStatusEnum.SUB_SUCCESS.getCode()){
            stuService.cancelorder("有事耽误了",1);
            System.out.println("取消成功");
        }
    }
    /*查询历史记录*/
    @Test
    public void lookhistory(){
        /*封装查询中间类*/
        SearchUtil searchUtil=new SearchUtil();
        /*设置以课程结束时间降序排序*/
        searchUtil.setOrderBy("courseEndTime");
        searchUtil.setOrderDirection(Sort.Direction.DESC);
        /*设置查询字段为学生工号,课程结束时间和课程状态*/
        List<String>list=new ArrayList<>();
        list.add("studentCode");
        list.add("courseEndTime");
        list.add("courseStatus");
        searchUtil.setKeywords(list);
        List<CourseDTO> courseDTOSs = stuService.lookhistory(searchUtil, "zx");
        for (CourseDTO courseDTO : courseDTOSs) {
            System.out.println(courseDTO);
        }
        System.out.println("ok");
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