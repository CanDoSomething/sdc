package com.zgczx.dto;

import lombok.Data;

/**
 * @author Jason
 * @date 2019/3/13 14:48
 */
@Data
public class OnClassUserInfoDTO {

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 选课成功的学生id
     */
    private String stuOpenid;

    /**
     * 教师的openid
     */
    private String teaOpenid;
}
