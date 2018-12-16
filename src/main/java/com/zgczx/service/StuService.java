package com.zgczx.service;

import com.zgczx.dataobject.StuFeedBack;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.CourseDTO;

import java.util.List;


/**
 * @author 陈志恒
 */
public interface StuService {
    /**
     *
     * 功能描述: 显示所有课程信息
     *
     * @param: page页面数，size页面大小
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 17:54
     */
    public List<CourseDTO> findAllCourse(Integer page,Integer size);
    /**
     *
     * 功能描述: 提交预约请求
     *
     * @param: stucode学生编码，courseId 课程id
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 17:57
     */
    public SubCourse order(String stucode, Integer courserId);
    /**
     *
     * 功能描述:在预约成功条件下取消预约请求
     *
     * @param: cause取消原因，courserId课程id，stuCode学生编码
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 18:06
     */
    public TeaCourse cancelorder(String cause,String stuCode, Integer courserId);
    /**
     *
     * 功能描述: 提交反馈
     *
     * @param: courseid课程id，message代表反馈内容，score代表反馈评分
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 19:24
     */
    public StuFeedBack feedback(Integer courseid,String message, Integer score);
    /**
     *
     * 功能描述: 查询历史记录
     *
     * @param: stucode学生编码,page页数，size页面大小
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 19:44
     */
    public List<CourseDTO> lookhistory(Integer page,Integer size,String stucode);



}
