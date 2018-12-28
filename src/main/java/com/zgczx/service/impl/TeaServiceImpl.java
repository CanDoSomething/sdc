package com.zgczx.service.impl;


import com.zgczx.dataobject.*;
import com.zgczx.dto.CourseDTO;
import com.zgczx.enums.SubStatusEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.*;
import com.zgczx.service.PushMessageService;
import com.zgczx.service.TeaService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private PushMessageService pushMessageService;

    /**
     *新增课程
     *
     * @param teaCourse 新增的课程信息
     * @return 新增的课程信息
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public TeaCourse createCourse(TeaCourse teaCourse) {
        if(null == teaCourse ){
            log.error("【教师创建课程】 新创建的课程信息为空");
            throw new SdcException(SubStatusEnum.INFO_NOTFOUND_EXCEPTION);
        }
        teaCourse.setCreateTime(new Date());
        //updatetime可以自动更新但是数据库是根据实体类映射生成的没有ON UPDATE CURRENT_TIMESTAMP
        //先自动生成
        teaCourse.setUpdateTime(new Date());

        teaCourse.setCourseStatus(SubStatusEnum.SUB_WAIT.getCode());
        return teaCourseRepository.save(teaCourse);
    }



    /**
     *教师取消课程
     *
     * @param courseId 课程id
     * @param cancelReason 课程取消原因
     * @return 取消的课程信息
     *
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public TeaCourse cancelCourse(Integer courseId,String cancelReason) {

        if(courseId == null){
            log.error("【教师取消课程】 该课程编号为空");
            throw new SdcException(SubStatusEnum.INFO_NOTFOUND_EXCEPTION);
        }
        TeaCourse teaCourse = teaCourseRepository.findOne(courseId);
        String teaCode = teaCourse.getTeaCode();

        //只有当前课程不为结束的情况下才能取消
        Integer status = teaCourse.getCourseStatus();
        if(status.intValue() != SubStatusEnum.SUB_COURSE_FINISH.getCode()){
            teaCourse.setCause(cancelReason);
            //如果当前已经有学生预约成功则给该学生发送消息通知
            if(status.intValue() == SubStatusEnum.SUB_SUCCESS.getCode()){
                //---------->pushMessageService.pushCancelCourseMessageToStu(teaCourse);
            }
            teaCourse.setCourseStatus(SubStatusEnum.SUB_TEAFAILURE.getCode());
        } else {
            log.error("【教师取消课程】 课程状态不正确，teaCourseId={},teaCourseIdStatus={}",
                    teaCourse.getCourserId(), teaCourse.getCourseStatus());
            throw new SdcException(SubStatusEnum.PARAM_EXCEPTION);
        }
        SubCourse subCourseByID = subCourseRepository.findByCourseId(courseId);
        subCourseByID.setUpdateTime(new Date());
        //修改预约表中的课程状态
        subCourseByID.setSubStatus(SubStatusEnum.SUB_TEAFAILURE.getCode());
        SubCourse save = subCourseRepository.save(subCourseByID);
        //先自动生成
        teaCourse.setUpdateTime(new Date());
        return teaCourseRepository.save(teaCourse);

    }

    /**
     * 根据教师编号查看其历史课程记录
     *
     * @param teaCode 教师编号
     * @param page 当前页
     * @param pageSize 当前页面大小
     * @return 当前教师的所有历史课程
     *
     */

    @Override
    public List<CourseDTO> findTeaHistoryCourse(String teaCode,int page,int pageSize) {

        if(teaCode == null || "".equals(teaCode)){
            log.error("【教师查看历史课程】 教师编号为空");
            throw new SdcException(SubStatusEnum.PARAM_EXCEPTION);
        }
        List<CourseDTO> rsList =  new ArrayList<CourseDTO>();
        /**
         * 查询当前编号教师的所有课程记录
         */
        TeaBase teaBase = teaBaseRepository.findOne(teaCode);
        if(teaBase != null) {
            //设置分页参数
            Pageable pageable = new PageRequest(page,pageSize);
            //设置查询条件
            Page<TeaCourse> all =  teaCourseRepository.find(teaCode, pageable);
            if(null == all){
                throw new SdcException(SubStatusEnum.INFO_NOTFOUND_EXCEPTION);
            }
            for(TeaCourse course : all){
                rsList.add(modelMapper.map(course,CourseDTO.class));
            }

            //去掉没有结束的课程
            java.util.function.Predicate<CourseDTO> courseStatusFilter = course -> (course.getCourseStatus()
                    .equals(SubStatusEnum.SUB_COURSE_FINISH.getCode()));
            rsList = rsList.stream().filter(courseStatusFilter).collect(Collectors.toList());
            StuFeedBack stuFeedBack = null;
            for(CourseDTO course: rsList){
                //通过课程编号查找学生给老师的反馈表
                stuFeedBack = stuFeedBackRepository.findByCourseId(course.getCourserId());
                //为了将学生给教师的反馈也展示出来这里将StuFeedBack包装到CourseDTO中，并返回courseDTO
                course.setStuFeedBack(stuFeedBack);
                course.setTeaBase(teaBase);
            }
        } else {
            log.error("【教师查看历史课程记录】 该编号的教师不存在");
            throw new SdcException(SubStatusEnum.INFO_NOTFOUND_EXCEPTION);
        }
        return rsList;
    }

    /**
     *根据课程id获取所有预约同学的基本信息
     *
     * @param courserId 课程编号
     * @param page 当前页
     * @param pageSize 当前页面大小
     * @return 所有候选人
     *
     */
    @Override
    public List<StuBase> findCandidateByCourseId(Integer courserId,int page,int pageSize){
        if(courserId == null){
            log.error("【教师查看预约候选人】 该课程编号为空");
            throw new SdcException(SubStatusEnum.INFO_NOTFOUND_EXCEPTION);
        }
        List<StuBase> stuList = new ArrayList<>();
        //设置分页参数
        Pageable pageable = new PageRequest(page,pageSize);
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
        if(stuCode == null || "".equals(stuCode)){
            log.error("【教师选择预约人】 学生编号为空");
            throw new SdcException(SubStatusEnum.PARAM_EXCEPTION);
        }
        if(courseId == null ){
            log.error("【教师选择预约人】 课程编号为空");
            throw new SdcException(SubStatusEnum.PARAM_EXCEPTION);
        }

        //组装预约课程
        SubCourse subCourse = new SubCourse();
        subCourse.setCourseId(courseId);
        subCourse.setStuCode(stuCode);
        subCourse.setSubStatus(SubStatusEnum.SUB_SUCCESS.getCode());
        subCourse.setCreateTime(new Date());
        subCourse.setUpdateTime(new Date());

        //查找课程表
        TeaCourse one = teaCourseRepository.findOne(courseId);
        Integer code = SubStatusEnum.SUB_SUCCESS.getCode();
        one.setCourseStatus(code);
        one.setStudentCode(stuCode);
        one.setUpdateTime(new Date());

        //给学生推送预约成功的模板消息
        CourseDTO courseDTO  = modelMapper.map(one,CourseDTO.class);
        courseDTO.setStudentCode(stuCode);
        TeaBase one1 = teaBaseRepository.findOne(one.getTeaCode());
        courseDTO.setTeaBase(one1);
        //------------>pushMessageService.pushSubSuccessMessage(courseDTO);

        //更新预约课程
        subCourse = subCourseRepository.save(subCourse);
        //更新课程状态
        TeaCourse teaCourseAfterUpdate = teaCourseRepository.save(one);

        //选择之后候选人之后 ，发送消息给没有入选的候选人提示预约失败并把当前课程对应的候选人清空（没有使用伪删除）
        List<StuPrepareSub> byCourserId = stuPrepareSubRepository.findByCourserId(courseId);
        for(StuPrepareSub stuPrepareSub : byCourserId){
            if(!stuPrepareSub.getStuCode().equals(stuCode)){
                //推送预约失败的模板消息
                courseDTO.setStudentCode(stuPrepareSub.getStuCode());
                //------------>pushMessageService.pushSubFailMessage(courseDTO);
            }
        }
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
        if(null == teaFeedBack){
            log.error("【教师给学生的反馈】 课程反馈内容为空");
            throw new SdcException(SubStatusEnum.PARAM_EXCEPTION);
        }
        Integer cId = teaFeedBack.getCourseId();
        if(null == cId){
            log.error("【教师给学生的反馈】 课程编号为空");
            throw new SdcException(SubStatusEnum.PARAM_EXCEPTION);
        }
        SubCourse subCourse = subCourseRepository.findByCourseId(cId);
        if(null == subCourse){
            log.error("【教师给学生的反馈】 该课程编号不正确");
            throw new SdcException(SubStatusEnum.PARAM_EXCEPTION);
        }
        subCourse.setSubStatus(SubStatusEnum.SUB_COURSE_FINISH.getCode());
        //修改预订表中课程状态
        subCourseRepository.save(subCourse);
        //修改课程表中课程状态
        TeaCourse one = teaCourseRepository.findOne(teaFeedBack.getCourseId());
        if(null == one){
            log.error("【修改课程表的状态】 该课程编号不正确");
            throw new SdcException(SubStatusEnum.PARAM_EXCEPTION);
        }
        one.setCourseStatus(SubStatusEnum.SUB_COURSE_FINISH.getCode());
        teaCourseRepository.save(one);
        return teaFeedBackRepository.save(teaFeedBack);
    }

    /**
     *教师修改没有被预约的课程信息
     *
     * @param teaCourse 前台封装的修改之后的课程信息
     * @return 修改成功的课程信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TeaCourse saveUpdateTeaCourse(TeaCourse teaCourse) {
        if(teaCourse == null){
            log.error("【修改课程表的状态】 该课程信息为空");
            throw new SdcException(SubStatusEnum.INFO_NOTFOUND_EXCEPTION);
        }

        if(!teaCourse.getCourseStatus().equals(SubStatusEnum.SUB_WAIT.getCode())){
            log.error("【修改课程表的状态】 该课程的状态status={}不能被修改",teaCourse.getCourseStatus());
            throw new SdcException(SubStatusEnum.DATEBASE_OP_EXCEPTION);
        }
        TeaCourse save = teaCourseRepository.save(teaCourse);
        return save;
    }

    /**
     *根据课程编号查找课程
     *
     * @param courseId 课程编号
     * @return 根据课程编号查找到的课程
     */
    @Override
    public TeaCourse findTeaCourseById(Integer courseId) {

        if(null == courseId){
            log.error("【查看课程课程信息】 该课程编号为空");
            throw new SdcException(SubStatusEnum.PARAM_EXCEPTION);
        }
        TeaCourse one = teaCourseRepository.findOne(courseId);

        if(null == one){
            log.error("【查看课程id={}的课程信息】 该课程没有找到",one);
            throw new SdcException(SubStatusEnum.INFO_NOTFOUND_EXCEPTION);
        }
        return one;
    }

    /**
     *根据课程编号修改课程状态为结束
     *
     * @param courseId 课程编号
     * @return 修改课程状态为结束课程
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TeaCourse finishCourse(Integer courseId) {
        if(null == courseId){
            log.error("【结束课程】 该课程编号id={}为空",courseId);
            throw new SdcException(SubStatusEnum.PARAM_EXCEPTION);
        }
        TeaCourse one = teaCourseRepository.findOne(courseId);
        if(null == one){
            log.error("【结束课程】 课程没有找到");
            throw new SdcException(SubStatusEnum.INFO_NOTFOUND_EXCEPTION);
        }
        Integer vis = one.getCourseStatus();
        //当前课程只有是处于互动状态或者线下互动才能使用此方式结束课程
        if(vis.equals(SubStatusEnum.SUB_COURSE_INTERACT.getCode()) || one.getIsOnline().equals(0) ){
            one.setCourseStatus(SubStatusEnum.SUB_COURSE_FINISH.getCode());
            one.setUpdateTime(new Date());
            TeaCourse save = teaCourseRepository.save(one);
            //同时修改预约课程的状态
            SubCourse byCourseId = subCourseRepository.findByCourseId(courseId);
            byCourseId.setSubStatus(SubStatusEnum.SUB_COURSE_FINISH.getCode());
            byCourseId.setUpdateTime(new Date());
            subCourseRepository.save(byCourseId);
            return save;
        } else {
            log.error("【结束课程】 课程状态不正确");
            throw new SdcException(SubStatusEnum.DATEBASE_OP_EXCEPTION);
        }
    }

}
