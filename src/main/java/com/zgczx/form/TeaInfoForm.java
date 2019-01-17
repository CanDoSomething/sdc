package com.zgczx.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @ClassName: 学生信息表单信息
 * @Author: Jason
 * @Date: 2019/1/2 15:53
 * @Description:
 */
@Data
public class TeaInfoForm {


    /**
     * 教师工号
     */
    @NotEmpty(message = "教师工号 必填")
    private String teaCode;

    /**
     * 教师姓名
     */
    @NotEmpty(message = "教师姓名 必填")
    private String teaName;

    /**
     * 教师科目
     */
    @NotEmpty(message = "教师科目 必填")
    private String teaSubject;

    /**
     * 教师密码
     */
    @NotEmpty(message = "密码必填")
    private String teaPasswd;

}
