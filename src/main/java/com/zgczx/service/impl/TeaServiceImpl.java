package com.zgczx.service.impl;


import com.zgczx.dataobject.*;
import com.zgczx.dto.CourseDTO;
import com.zgczx.enums.SubStatusEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.*;
import com.zgczx.service.TeaService;
import com.zgczx.utils.SeachIndexKey;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Dqd
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
    @Autowired
    private StuFeedBackRepository stuFeedBackRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     *新增课程
     *
     * @param teaCourse 新增的课程信息
     * @return 新增的课程信息
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public TeaCourse createCourse(TeaCourse teaCourse) {
        teaCourse.setCreateTime(new Date());
        //updatetime可以自动更新
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
    @Transactional(rollbackFor=Exception.class)
    public TeaCourse cancelCourse(Integer courseId,String teaCode,String cancelReason) {

        TeaCourse teaCourse = teaCourseRepository.findOne(courseId);
        //只有当登陆者的teaCode的和发布课程者的teaCode相等才能取消
        if (teaCode != null){
            if(!teaCourse.getTeaCode().equals(teaCode)){
                log.error("【教师取消课程】 不是课程的发布者不可以更改课程状态，teaCode={}",
                        teaCode);
                throw new SdcException(SubStatusEnum.TEACHERCODE_INCORRECT);
            }
        } else {
            log.error("【教师取消课程】 没有找到当前教师");
            throw new SdcException(SubStatusEnum.NOTFIND_TEACHER);
        }
        //只有当前课程不为结束的情况下才能取消
        Integer status = teaCourse.getCourseStatus();
        if(status.intValue() != SubStatusEnum.SUB_COURSE_FINISH.getCode()){
            //teaCourse.setCourseStatus( CourseStatus.COURSE_CANCEL.getCode());
            teaCourse.setCause(cancelReason);
            teaCourse.setCourseStatus(SubStatusEnum.COURSE_CANCELBYTEA.getCode());
        } else {
            log.error("【教师取消课程】 课程状态不正确，teaCourseId={},teaCourseIdStatus={}",
                    teaCourse.getCourserId(), teaCourse.getCourseStatus());
            throw new SdcException(SubStatusEnum.COURSE_STATUS_ERROR);
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
    public List<CourseDTO> findTeaHistoryCourse(String teaCode,int page,int pageSize) {
        //List<TeaCourse> list = new ArrayList<>();
        List<CourseDTO> rsList =  new ArrayList<CourseDTO>();
        /**
         * 查询当前编号教师的所有课程记录
         */
        TeaBase teaBase = teaBaseRepository.findOne(teaCode);
        if(teaBase != null){
            //设置分页条件
            Sort sort = new Sort(Sort.Direction.DESC,SeachIndexKey.COURSE_END_TIME);
            Pageable pageable = new PageRequest(page,pageSize,sort);
            //设置查询条件
            Specification<TeaCourse> specification1 = (root,query,cb) -> {
                Predicate predicate = cb.equal(root.get(SeachIndexKey.TEA_CODE),teaCode);
                return predicate;
            };
            Page<TeaCourse> all =  teaCourseRepository.findAll(specification1, pageable);
            if(null == all){
                throw new SdcException(SubStatusEnum.NOTFIND_TEACOURSE);
            }
            for(TeaCourse course : all){
                rsList.add(modelMapper.map(course,CourseDTO.class));
            }
            //去掉没有结束的课程
            java.util.function.Predicate<CourseDTO> courseStatusFilter = course -> (course.getCourseStatus()
                    .equals(SubStatusEnum.SUB_COURSE_FINISH.getCode()));
            rsList = rsList.stream().filter(courseStatusFilter).collect(Collectors.toList());

            StuFeedBack stuFeedBack = new StuFeedBack();

            for(CourseDTO course: rsList){
                //通过课程编号查找学生给老师的反馈表
                stuFeedBack = stuFeedBackRepository.findByCourseId(course.getCourserId());
                //为了将学生给教师的反馈也展示出来这里将StuFeedBack包装到CourseDTO中，并返回courseDTO
                course.setStuFeedBack(stuFeedBack);
            }
        } else {
            log.error("【教师查看历史课程记录】 该编号的教师不存在");
            throw new SdcException(SubStatusEnum.NOTFIND_TEACHER);
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
        //设置分页条件
        Sort sort = new Sort(Sort.Direction.DESC,"creditScore" );
        Pageable pageable = new PageRequest(page,pageSize,sort);
        //查看所有候选人
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
    @Transactional(rollbackFor=Exception.class)
    public SubCourse saveSelectedStu(String stuCode,Integer courseId) {
        //组装预约课程
        SubCourse subCourse = new SubCourse();
        subCourse.setCourseId(courseId);
        subCourse.setStuCode(stuCode);
        subCourse.setSubStatus(SubStatusEnum.SUB_SUCCESS.getCode());
        subCourse.setCreateTime(new Date());
        subCourse = subCourseRepository.save(subCourse);

        //更新课程表状态
        TeaCourse one = teaCourseRepository.findOne(courseId);
        Integer code = SubStatusEnum.SUB_SUCCESS.getCode();
        one.setCourseStatus(code);
        teaCourseRepository.save(one);

        //选择之后将候选人表当前课程对应的候选人清空
        List<StuPrepareSub> list = stuPrepareSubRepository.deleteByCourserId(courseId);
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
    @Transactional(rollbackFor=Exception.class)
    public TeaFeedBack createFeedBack(TeaFeedBack teaFeedBack) {
        SubCourse subCourse = subCourseRepository.findByCourseId(teaFeedBack.getCourseId());
        if(null == subCourse){
            log.error("【教师给学生的反馈】 该课程编号不正确");
            throw new SdcException(SubStatusEnum.NOTFIND_TEACOURSE);
        }
        subCourse.setSubStatus(SubStatusEnum.SUB_COURSE_FINISH.getCode());
        //修改预订表中课程状态
        subCourseRepository.save(subCourse);
        //修改课程表中课程状态
        TeaCourse one = teaCourseRepository.findOne(teaFeedBack.getCourseId());
        if(null == one){
            log.error("【修改课程表的状态】 该课程编号不正确");
            throw new SdcException(SubStatusEnum.NOTFIND_TEACOURSE);
        }
        one.setCourseStatus(SubStatusEnum.SUB_COURSE_FINISH.getCode());
        teaCourseRepository.save(one);
        return teaFeedBackRepository.save(teaFeedBack);
    }
}
