package com.zgczx.service;

import com.zgczx.dataobject.OnlineCourse;
import com.zgczx.dto.OnClassUserInfoDTO;

/**
 * @author  Jason
 */
public interface CourseService {

    /**
     * 上课方式，线上
     */
    int COURSEINTERACTIVE_ONLINE = 0;

    /**
     * 上课方式，线下
     */
    int COURSEINTERACTIVE_OFFLINE = 1;

    /**
     * 根据课程id获取聊天群组id
     * @param courseId  课程id
     * @return 在线课程信息
     */
    OnlineCourse getOnlineCourseGroupId(Integer courseId);

    /**
     * 判断openid是否是课程的参与者
     * @param openid 用户的唯一凭证
     * @param courseId 课程编号
     * @return 是否
     */
    Boolean onCourse(String openid,Integer courseId);


    /**
     * 返回课程结束的倒计时，单位为秒
     * @param courseId  课程编号
     * @return 距离课程结束的倒计时
     */
    String getCountDown(Integer courseId);

    /**
     * 判断课程是否结束
     * @param courseId 课程编号
     * @return 是否
     */
    Boolean onCourseEnd(Integer courseId);

    /**
     * 判断课程courserId是否合法
     * @param courserId 课程id
     * @return Boolean
     */
    Boolean legalCourse(Integer courserId);

    /**
     * 根据课程id返回这节课的学生openid和教师openid
     * @param courserId 课程id
     * @return OnClassUserInfoDTO
     */
    OnClassUserInfoDTO getOnClassUserOpenid(Integer courserId);


}
