package com.zgczx.service;

import com.zgczx.dataobject.OnlineCourse;

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
     * 判断useropenid是否是课程的参与者
     * @param useropenid 用户的唯一凭证
     * @param courseId 课程编号
     * @return 是否
     */
    Boolean onCourse(String useropenid,Integer courseId);




}
