package com.zgczx.service.impl;
import com.zgczx.dataobject.*;
import com.zgczx.enums.tea.CourseStatus;
import com.zgczx.enums.ResultEnum;
import com.zgczx.enums.tea.SubEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.StuBaseRepository;
import com.zgczx.repository.SubCourseRepository;
import com.zgczx.repository.TeaCourseRepository;
import com.zgczx.repository.StuPrepareSubRepository;
import com.zgczx.repository.TeaBaseRepository;
import com.zgczx.repository.TeaFeedBackRepository;
import com.zgczx.service.TeaService;
import com.zgczx.utils.tea.SplitPageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Dqd
 * @Date: 2018/12/12 14:29
 * @Description:教师模块实现类
 */
@Service
@Slf4j
public class TeaServiceImpl implements TeaService {

    @Autowired
    private TeaCourseRepository teaCourseRepository;
    @Autowired
    private TeaBaseRepository teaBaseRepository;
    @Autowired
    private StuPrepareSubRepository stuPrepareSubRepository;
    @Autowired
    private StuBaseRepository stuBaseRepository;
    @Autowired
    private SubCourseRepository subCourseRepository;
    @Autowired
    private TeaFeedBackRepository teaFeedBackRepository;


    /**
     *新增课程
     *
     * @param teaCourse 新增的课程信息
     * @return 新增的课程信息
     */
    @Override
    @Transactional(rollbackFor=SdcException.class)
    public TeaCourse createCourse(TeaCourse teaCourse) {
        teaCourse.setCreateTime(new Date());
        teaCourse.setUpdateTime(new Date());
        return teaCourseRepository.save(teaCourse);
    }

    /**
     *教师取消课程
     *
     * @param courseId 课程id
     * @param teaCode 教师编号
     * @return 取消的课程信息
     *
     */
    @Override
    @Transactional(rollbackFor=SdcException.class)
    public TeaCourse cancelCourse(Integer courseId,String teaCode,String cancelReason) {
        //只有当前课程不为结束的情况下才能取消
        TeaCourse teaCourse = teaCourseRepository.findOne(courseId);
        //只有当登陆者的teaCode的和发布课程者的teaCode相等才能取消
        System.out.println("课程信息" + teaCourse.toString());
        if(!teaCourse.getTeaCode().equals(teaCode)){
            log.error("【教师取消课程】 不是课程的发布者不可以更改课程状态，teaCode={}",
                    teaCode);
            throw new SdcException(ResultEnum.TEACODE_INCORRECT);
        }
        Integer status = teaCourse.getCourseStatus();
        if(status.intValue() != CourseStatus.COURSE_FINISH.getCode()){
            teaCourse.setCourseStatus( CourseStatus.COURSE_CANCEL.getCode());
            teaCourse.setCause(cancelReason);
            teaCourse.setCourseStatus(CourseStatus.COURSE_CANCELBYTEA.getCode());
        } else {
            log.error("【教师取消课程】 课程状态不正确，teaCourseId={},teaCourseIdStatus={}",
                    teaCourse.getCourserId(), teaCourse.getCourseStatus());
            throw new SdcException(ResultEnum.COURSE_STATUS_ERROR);
        }
        return teaCourseRepository.save(teaCourse);
    }

    /**
     * 根据教师编号查看其历史课程记录
     *
     * @param teaCode 教师编号
     * @return 当前教师的所有历史课程
     *
     */

    @Override
    public List<TeaCourse> findTeaHistoryCourse(String teaCode,int page,int pageSize) {
        List<TeaCourse> list = null;
        List<TeaCourse> rsList = null;
        /**
         * 查询当前编号教师的所有课程记录
         */
        TeaBase teaBase = teaBaseRepository.findOne(teaCode);
        if(teaBase != null){
            //设置分页条件
            Pageable pageable = SplitPageUtils.getPageable(page,pageSize,"courseEndTime");
            list = teaCourseRepository.findByTeaCode(teaCode,pageable);
            rsList = new ArrayList<TeaCourse>();
            //去掉没有结束的课程
            for(TeaCourse teaCourse : list){
                if(teaCourse.getCourseStatus().equals(CourseStatus.COURSE_FINISH.getCode())){
                    rsList.add(teaCourse);
                }
            }
        } else {
            log.error("【教师查看历史课程记录】 该编号的教师不存在，teaCode={}",
                    teaBase.getTeaCode());
            throw new SdcException(ResultEnum.TEA_NOT_EXIST);
        }
        return rsList;
    }

    /**
     *根据课程id获取所有预约同学的基本信息
     *
     * @param courserId 课程编号
     * @return 所有候选人
     *
     */
    @Override
    public List<StuBase> findCandidateByCourseId(Integer courserId,int page,int pageSize){
        List<StuBase> stuList = new ArrayList<>();
        Pageable pageable = SplitPageUtils.getPageable(page,pageSize,"creditScore");
        List<StuPrepareSub> list = stuPrepareSubRepository.findByCourserId(courserId,pageable);
        StuBase stuBase = null;
        for( StuPrepareSub stuPrepareSub : list) {
            stuBase = stuBaseRepository.findOne(stuPrepareSub.getStuCode());
            stuList.add(stuBase);
        }
        return stuList;
    }

    /**
     * 教师从候选人中选择预定学生
     *
     * @param stuCode 学生学籍号
     * @param courseId 课程编号
     * @return 选中的学生信息
     */
    @Override
    @Transactional(rollbackFor=SdcException.class)
    public SubCourse saveSelectedStu(String stuCode,Integer courseId) {
        SubCourse subCourse = new SubCourse();
        subCourse.setCourseId(courseId);
        subCourse.setStuCode(stuCode);
        subCourse.setSubStatus(SubEnum.SUB_SUCCESS.getCode());
        subCourse.setCreateTime(new Date());
        subCourse.setUpdateTime(new Date());
        subCourse = subCourseRepository.save(subCourse);

        //更新课程表状态
        TeaCourse one = teaCourseRepository.findOne(courseId);
        Integer code = CourseStatus.COURSE_ORDERED.getCode();
        one.setCourseStatus(code);
        teaCourseRepository.save(one);

        //选择之后将候选人表当前课程对应的候选人清空
        List<StuPrepareSub> list = stuPrepareSubRepository.deleteByCourserId(courseId);
        System.out.println("删除学生个数"+list.size());
        return subCourse;
    }

    /**
     *提交教师给学生的信息反馈
     *
     * @param teaFeedBack 封装的教师反馈信息
     * @return 教师反馈信息
     *
     */
    @Override
    @Transactional(rollbackFor=SdcException.class)
    public TeaFeedBack createFeedBack(TeaFeedBack teaFeedBack) {
        SubCourse subCourse = subCourseRepository.findByCourseId(teaFeedBack.getCourseId());
        subCourse.setSubStatus(SubEnum.SUB__FINISH.getCode());
        //修改预订表中课程状态
        subCourseRepository.save(subCourse);
        //修改课程表中课程状态
        TeaCourse one = teaCourseRepository.findOne(teaFeedBack.getCourseId());
        one.setCourseStatus(CourseStatus.COURSE_FINISH.getCode());
        teaCourseRepository.save(one);
        return teaFeedBackRepository.save(teaFeedBack);
    }
}
