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
     * 功能描述: 在预约未成功条件下取消预约课程
     *
     * @param stuCode 学生编码
     * @param courserId 课程id
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 18:28
     */
    public SubCourse simpleCancelOrder(String stuCode,Integer courserId);
    /**
     *
     * 功能描述: 显示所有课程信息
     *
     * @param page 页面数
     * @param size size页面大小
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 17:54
     */
    public List<CourseDTO> findAllCourse(Integer page,Integer size);
    /**
     *
     * 功能描述: 提交预约请求
     *
     * @param stuCode 学生编码
     * @param courserId  课程id
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 17:57
     */
    public SubCourse order(String stuCode, Integer courserId);
    /**
     *
     * 功能描述:在预约成功条件下取消预约请求
     *
     * @param cause 取消原因
     * @param courserId 课程id
     * @param stuCode 学生编码
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 18:06
     */
    public TeaCourse cancelOrder(String cause,String stuCode, Integer courserId);
    /**
     *
     * 功能描述: 提交反馈
     *
     * @param courseId 课程id
     * @param message 代表反馈内容
     * @param score 代表反馈评分
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 19:24
     */
    public StuFeedBack feedBack(Integer courseId,String message, Integer score);
    /**
     *查询历史记录课程
     *
     * @Author chen
     * @Date 21:02 2018/12/20
     * @param page 页数
     * @param size 页面大小
     * @param stuCode 学生编码
     * @return List<CourseDTO> 课程封装对象的集合
     **/
    public List<CourseDTO> lookHistory(Integer page,Integer size,String stuCode);



}
