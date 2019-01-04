package com.zgczx.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @ClassName: 学生信息表单信息
 * @Author: Jason
 * @Date: 2019/1/2 15:10
 * @Description:
 */
@Data
public class StuInfoForm {

    /**
     * 学籍
     */
    @NotEmpty(message = "学籍号必填")
    private String stuCode;

    /**
     * 学生姓名
     */
    @NotEmpty(message = "学生姓名必填")
    private String stuName;

    /**
     *  学生年级，如2002级
     */
    @NotEmpty(message = "学生年级必填")
    private String stuLevel;

    /**
     * 中学分段，如初中或高中
     */
    @NotEmpty(message = "中学分段必填")
    private String stuGrade;

    /**
     * 班级：如 1班
     */
    @NotEmpty(message = "班级必填")
    private String stuClass;

    /**
     * 学生密码
     */
    @NotEmpty(message = "密码必填")
    private String stuPasswd;



}
