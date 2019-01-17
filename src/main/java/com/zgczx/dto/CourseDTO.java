package com.zgczx.dto;

import com.zgczx.dataobject.FeedBack;
import com.zgczx.dataobject.TeaBase;
import com.zgczx.dataobject.TeaCourse;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: 陈志恒
 * @Date: 2018/12/10 23:42
 * @Description:课程信息
 */
@Data
@ToString
public class CourseDTO {

    /**
     * 课程信息
     */
    private TeaCourse teaCourse;
    /**
     * 教师信息
     */
    private TeaBase teaBase;
    /**
     * 反馈对象
     */
    private FeedBack feedBack;

}
