package com.zgczx.service;

import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.CourseDTO;

/**
 * @Author: Dqd
 * @Date: 2018/12/18 18:01
 * @Description:
 */
public interface PushMessageService {
    void pushSubSuccessMessage(CourseDTO courseDTO);
    void pushSubFailMessage(CourseDTO courseDTO);
    void pushCancelCourseMessageToStu(TeaCourse teaCourse);
}
