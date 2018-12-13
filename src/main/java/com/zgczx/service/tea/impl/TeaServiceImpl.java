package com.zgczx.service.tea.impl;

import com.zgczx.dataobject.*;
import com.zgczx.enums.tea.CourseStatus;
import com.zgczx.enums.tea.ResultEnum;
import com.zgczx.enums.tea.SubEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.StuBaseRepository;
import com.zgczx.repository.SubCourseRepository;
import com.zgczx.repository.tea.StuPrepareSubRepository;
import com.zgczx.repository.tea.TeaBaseRepository;
import com.zgczx.repository.tea.TeaCourseRepository;
import com.zgczx.repository.tea.TeaFeedBackRepository;
import com.zgczx.service.tea.TeaService;
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
 * Created by Dqd on 2018/12/10.
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
     * @param teaCourse
     * @return
     * 教师新增课程
     */
    @Override
    @Transactional
    public TeaCourse createCourse(TeaCourse teaCourse) {
        return teaCourseRepository.save(teaCourse);
    }

    /**
     * @param teaCourse
     * @return
     * 教师取消课程
     */
    @Override
    @Transactional
    public TeaCourse cancelCourse(TeaCourse teaCourse) {
        //只有当前课程为可预约或者已预约的情况下才能取消
        Integer status = teaCourse.getCourseStatus();
        if(status.intValue() == 0 || status.intValue() == 1){
            teaCourse.setCourseStatus( CourseStatus.COURSE_CANCEL.getCode());
        } else {
            log.error("【教师取消课程】 课程状态不正确，teaCourseId={},teaCourseIdStatus={}",
                    teaCourse.getCourserId(), teaCourse.getCourseStatus());
            throw new SdcException(ResultEnum.COURSE_STATUS_ERROR);
        }
        return teaCourseRepository.save(teaCourse);
    }

    /**
     * @param teaCode
     * @return
     * 根据教师编号查看其历史课程记录
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
                if(teaCourse.getCourseStatus() == CourseStatus.COURSE_FINISH.getCode()){
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
     *
     * @param courserId
     * @return
     * 根据课程id获取所有预约同学的基本信息
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
     * @param stuCode
     * @return
     * 教师从候选人中选择预定学生
     *
     */
    @Override
    @Transactional
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
     *
     * @param teaFeedBack
     * @return
     * 提交教师给学生的信息反馈
     */
    @Override
    @Transactional
    public TeaFeedBack createFeedBack(TeaFeedBack teaFeedBack,Integer courseId) {
        SubCourse subCourse = subCourseRepository.findByCourseId(courseId);
        subCourse.setSubStatus(SubEnum.SUB__FINISH.getCode());
        //修改预订表中课程状态
        subCourseRepository.save(subCourse);
        //修改课程表中课程状态
        TeaCourse one = teaCourseRepository.findOne(courseId);
        one.setCourseStatus(CourseStatus.COURSE_FINISH.getCode());
        teaCourseRepository.save(one);
        return teaFeedBackRepository.save(teaFeedBack);
    }
}
