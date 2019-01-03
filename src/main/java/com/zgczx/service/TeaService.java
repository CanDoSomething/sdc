package com.zgczx.service;

import com.zgczx.dataobject.FeedBack;
import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.form.TeaCourseForm;

import java.util.List;

/**
 * @Author: Dqd
 * @Date: 2018/12/12 14:29
 * @Description:教师模块接口
 */

public interface TeaService {

    /**
     * 创建课程
     *
     * @param teaCourseForm 封装的课程信息表单
     * @param teaOpenid 教师微信唯一凭证
     * @return 创建成功的课程
     */
    TeaCourse createCourse(TeaCourseForm teaCourseForm,String teaOpenid);

    /**
     *
     * 取消课程
     *
     * @param courseId 课程编号
     * @param teaOpenid 教师微信编号
     * @param cancelReason 取消课程原因
     * @return 取消课程之后的信息
     */
    TeaCourse cancelCourse(Integer courseId,String teaOpenid,String cancelReason);

    /**
     *
     * 查看教师历史课程
     *
     * @param teaCode 教师编号
     * @param page 当前页码
     * @param pageSize 当前分页大小
     * @return 教师历史课程列表(仅仅包含结束的课程)
     */
    List<CourseDTO> findTeaHistoryCourse(String teaCode, int page, int pageSize);

    /**
     *
     * 查看课程预约候选人
     *
     * @param courserId 课程编号
     * @param teaOpenid 教师微信编号
     * @param page 当前页码
     * @param pageSize 当前分页大小
     * @return 当前课程所有预约候选人列表
     */
    List<StuBase> findCandidateByCourseId(Integer courserId,String teaOpenid, int page, int pageSize);

    /**
     *
     * 教师选择成功预约候选人
     *
     * @param stuCode 学生编号
     * @param courseId 课程编号
     * @return 成功预约课程信息
     */
    SubCourse saveSelectedStu(String stuCode, Integer courseId);

    /**
     * 教师提交给学生的反馈
     *
     * @param subId 预约课程编号
     * @param teaOpenid 教师微信编号
     * @param teaFeedBack 教师给学生的反馈信息
     * @param score 教师给学生的打分
     * @return 该预约课程的反馈信息
     */
    FeedBack saveFeedBack(Integer subId,String teaOpenid,String teaFeedBack,Integer score);

    /**
     *
     * 教师修改课程信息
     *
     * @param teaCourse 课程信息
     * @return 课程信息
     */
    TeaCourse saveUpdateTeaCourse(TeaCourse teaCourse);

    /**
     * 根据课程编号查看教师
     * @param courseId 课程编号
     * @return 课程信息
     */
    TeaCourse findTeaCourseById(Integer courseId);

    /**
     *
     * 教师结束课程
     *
     * @param courseId 课程编号
     * @return 课程状态为结束的课程
     */
    TeaCourse finishCourse(Integer courseId);
}
