package com.zgczx.service;

import com.zgczx.dataobject.StuFeedBack;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.utils.SearchUtil;

import java.util.List;

/*
* 学生service类
* */
public interface IStuService {
    /*
    * 查找所有的课程信息
    * */
    public List<CourseDTO> findAllCourse(SearchUtil searchUtil);
    /*
    * 提交预约请求
    * */
    public SubCourse order(String stucode, Integer courserId);
    /*
    *取消预约请求
    * */
    public TeaCourse cancelorder(String cause, Integer courserId);
    /*
    * 提交用户反馈,courseid是课程id,message代表消息内容,score代表评分
    * */
    public StuFeedBack feedback(Integer courseid,String message, Integer score);
    /*
    *查询历史课程记录
    * */
    public List<CourseDTO> lookhistory(SearchUtil searchUtil,String stucode);



}
