package com.zgczx.dto;

import com.zgczx.dataobject.FeedBack;
import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.TeaBase;
import com.zgczx.dataobject.TeaCourse;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: Dqd
 * @Date: 2019/3/7 17:13
 * @Description:
 */

@Data
@ToString
public class PushMessageDTO {

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
    /**
     * 学生信息
     */
    private StuBase stuBase;

}
