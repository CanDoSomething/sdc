package com.zgczx.service.impl;


import com.zgczx.dataobject.*;
import com.zgczx.dto.CourseDTO;
import com.zgczx.dto.PushMessageDTO;
import com.zgczx.dto.SubDTO;
import com.zgczx.enums.CourseEnum;
import com.zgczx.enums.ResultEnum;
import com.zgczx.enums.SubCourseEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.*;
import com.zgczx.service.PushMessageService;
import com.zgczx.service.StuService;
import com.zgczx.service.TeaService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @Author chen
 * @Date 10:42 2018/12/21
 **/

@Service
@Slf4j
public class StuServiceImpl implements StuService {



    @Autowired
    private TeaCourseRepository teaCourseRepository;
    @Autowired
    private TeaBaseRepository teaBaseRepository;
    @Autowired
    private SubCourseRepository subCourseRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FeedBackRepository feedBackRepository;
    @Autowired
    private StuBaseRepository stuBaseRepository;
    @Autowired
    private TeaService teaService;
    @Autowired
    private PushMessageService pushMessageService;

    private  String info = null;
    /**
     *
     * 功能描述: 显示所有课程信息
     *
     * @param page 页面数
     * @param size 页面大小
     * @return 课程信息列表
     */
    @Override
    public List<CourseDTO> findAllCourse(Integer page, Integer size) {
        //先按照课程日期降序排序
        Sort sort = new Sort(Sort.Direction.DESC,"courseDate");
        /*设置分页*/
        Pageable pageable = new PageRequest(page, size, sort);

        // 找到课程状态为300或301或302的课程，并且结束时间大于当前时间
        Page<TeaCourse> byCourseStatus = teaCourseRepository.findAllCourse( new Date(), pageable);

        /*如果课程不存在，返回预约课程不存在*/
        if (byCourseStatus.getContent().isEmpty()){
            info = "【学生查看所有课程】 没有正在发布的课程";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        /*封装课程信息到消息中间类*/
        List<CourseDTO> courseDTOList = getCourse(byCourseStatus);

        if (courseDTOList.size()==0){
            info = "【学生查看所有课程】 没有正在发布的课程";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        return courseDTOList;
    }

    /**
     * 功能描述: 提交预约请求
     *
     * @param stuOpenid 学生微信Id
     * @param courserId  课程id
     * @return 预约课程信息
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public SubCourse order(String stuOpenid, Integer courserId) {

        //1.比较学生的历史预约信息与目标课程的时间是否冲突
        TeaCourse teaCourse = teaCourseRepository.findOne(courserId);
        StuBase stuBase = stuBaseRepository.findByStuOpenid(stuOpenid);

        //2.查询学生是否提交过该课程的预约请求，若有，则不能新建
        SubCourse subCourseHistory = subCourseRepository.findByCourseIdAndStuCode(courserId,stuBase.getStuCode());

        if(null != subCourseHistory){
            info = "【学生发起预约课程请求】已经预约过该课程，不能再次预约";
            log.error(info);
            throw new SdcException(ResultEnum.SUB_FAIL,info);
        }

        //学生当前想要预约的课程列表(课时数>=1)
        List<TeaCourse> curSubCourseList
                = teaCourseRepository.findByTeaCodeAndCourseDate(teaCourse.getTeaCode(), teaCourse.getCourseDate());

        /**
         * 查询学生最近七天   ‘预约等待和预约成功’ 的预约课程列表
         * 如果最近七天有预约那么就不能再次预约课程
         */
        List<SubCourse> subCourseList = subCourseRepository.findByStuCodeAndLastSevenDays(stuBase.getStuCode());
        subCourseList.forEach(subCourse -> {
            log.info("当前学生预约的课程信息{}",subCourse);
        });
        //最近有预约或者预约成功的课程 那么不能再次预约
        if(0 != subCourseList.size()){
            info = "【学生发起预约课程请求】 最近七天预约过课程，不能再次提交预约，请等待老师审核批准原预约课程！";
            log.error(info);
            throw new SdcException(ResultEnum.CANTNOT_SUBCOURSE_LEAST_SEVEN_DAYS , info);
        }
        /*查询该学生的预约列表*/
        for (SubCourse subCourse : subCourseList) {

            /*找到提交预约请求和预约成功的所有预约信息*/
            if (subCourse.getSubStatus().equals(SubCourseEnum.SUB_WAIT.getCode()) ||
                    subCourse.getSubStatus().equals(SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode())){
                /*根据课程id查找到课程信息*/
                TeaCourse teaCourseRepositoryOne = teaCourseRepository.findOne(subCourse.getCourseId());
                if(null == teaCourseRepositoryOne) {
                    info = "【学生发起预约课程请求】未找到学生预约的某门课程";
                    log.error(info);
                    throw new SdcException(ResultEnum.SUB_FAIL,info);
                }

                List<TeaCourse> byTeaCodeAndCourseDate
                        = teaCourseRepository.findByTeaCodeAndCourseDate(teaCourseRepositoryOne.getTeaCode()
                                            , teaCourseRepositoryOne.getCourseDate());
                //逐一判断课程时间是否有冲突
                Calendar currentCourseStartCalender = Calendar.getInstance();
                Calendar currentCourseEndCalender = Calendar.getInstance();
                Calendar agoCourseStartCalender = Calendar.getInstance();
                Calendar agoCourseEndCalender = Calendar.getInstance();

                for(TeaCourse agoSubCourse : byTeaCodeAndCourseDate) {
                    agoCourseStartCalender.setTime(agoSubCourse.getCourseStartTime());
                    agoCourseEndCalender.setTime(agoSubCourse.getCourseEndTime());

                    for(TeaCourse curSubCourse: curSubCourseList){
                        currentCourseStartCalender.setTime(curSubCourse.getCourseStartTime());
                        currentCourseEndCalender.setTime(curSubCourse.getCourseEndTime());
                        /**
                         * 滑动窗口法判断当前预约的课程是和以往预约课程冲突
                         */
                        judgeCourseConflict(agoCourseStartCalender,agoCourseEndCalender,currentCourseStartCalender,currentCourseEndCalender);

                    }
                }

                /*
                 * 判断预约时间是否冲突
                 * 目标课程的开始时间晚于历史预约课程的结束时间 或者 历史预约课程的开始时间晚于目标课程的结束时间
                 *//*
                if(!DateUtil.compareTime(teaCourse.getCourseStartTime(),teaCourseRepositoryOne.getCourseEndTime()) &&
                        !DateUtil.compareTime(teaCourseRepositoryOne.getCourseStartTime(),teaCourse.getCourseEndTime())){
                    *//*抛出预约冲突异常*//*
                    info = "【学生发起预约课程请求】 与历史预约课程的时间冲突";
                    log.error(info);
                    throw new SdcException(ResultEnum.SUB_FAIL,info);
                }*/
            }
        }

        //3.保存预约请求
        SubCourse subCourse = new SubCourse();
        subCourse.setStuCode(stuBase.getStuCode());
        subCourse.setCourseId(courserId);
        /*设置新的预约对象为等待预约状态*/
        subCourse.setSubStatus(SubCourseEnum.SUB_WAIT.getCode());
        SubCourse save = subCourseRepository.save(subCourse);
        if (save == null) {
            info = "【学生发起预约课程请求】 预约信息没有保存到数据库，预约课程失败";
            log.error(info);
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
        }

        //学生预约的课程预约信息推送给教师
        PushMessageDTO pushMessageDTO = new PushMessageDTO();
        pushMessageDTO.setStuBase(stuBase);
        TeaBase byTeaCode = teaBaseRepository.findByTeaCode(teaCourse.getTeaCode());
        pushMessageDTO.setTeaBase(byTeaCode);
        pushMessageDTO.setTeaCourse(teaCourse);
        pushMessageDTO.setFeedBack(null);

        pushMessageService.pushSubcourseMessageToTea(pushMessageDTO);


        //3.课程状态改为已被学生预约
        teaCourse.setCourseStatus(CourseEnum.SUB_SUCCESS.getCode());
        List<TeaCourse> byTeaCodeAndOriginId
                = teaCourseRepository.findByTeaCodeAndOriginId(teaCourse.getTeaCode(), teaCourse.getCourseId());
        for(TeaCourse teaCourse1 : byTeaCodeAndOriginId ){
            teaCourse1.setCourseStatus(CourseEnum.SUB_SUCCESS.getCode());
        }
        TeaCourse save1 = teaCourseRepository.save(teaCourse);
        if(null == save1){
            info = "【学生发起预约课程请求】 学生预约课程，修改原课程状态失败";
            log.error(info);
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
        }
        for(TeaCourse teaCourse1 : byTeaCodeAndOriginId ){
            save1 = teaCourseRepository.save(teaCourse1);
            if(null == save1){
                info = "【学生发起预约课程请求】 学生预约课程，修改原课程状态失败";
                log.error(info);
                throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
            }
        }
        return save;
    }

    private void judgeCourseConflict(Calendar agoCourseStartCalender, Calendar agoCourseEndCalender, Calendar currentCourseStartCalender, Calendar currentCourseEndCalender) {

        //之前的课程开始时间比当前预约课程开始时间早，且结束时间比当前预约课程开始时间晚
        if(agoCourseStartCalender.before(currentCourseStartCalender)
                && agoCourseEndCalender.after(currentCourseStartCalender) ){
            info = "【学生发起预约课程请求】与历史预约课程的时间冲突，之前的课程开始时间比当前预约课程开始时间早，且结束时间比当前预约课程开始时间晚";
            log.error(info);
            throw new SdcException(ResultEnum.SUB_FAIL,info);
        }
        if(agoCourseStartCalender.before(currentCourseStartCalender)
                && agoCourseEndCalender.after(currentCourseStartCalender)){
            info = "【学生发起预约课程请求】与历史预约课程的时间冲突，之前的课程开始时间比当前预约课程开始时间早，且结束时间比当前预约课程结束时间晚";
            log.error(info);
            throw new SdcException(ResultEnum.SUB_FAIL,info);
        }
        if(agoCourseStartCalender.after(currentCourseStartCalender)
                && agoCourseEndCalender.before(currentCourseStartCalender)){
            info = "【学生发起预约课程请求】与历史预约课程的时间冲突，之前的课程开始时间比当前预约课程开始时间晚，且结束时间比当前预约课程结束时间早";
            log.error(info);
            throw new SdcException(ResultEnum.SUB_FAIL,info);
        }
        if(agoCourseStartCalender.after(currentCourseStartCalender)
                && agoCourseEndCalender.before(currentCourseStartCalender)){
            info = "【学生发起预约课程请求】与历史预约课程的时间冲突，之前的课程开始时间比当前预约课程开始时间晚，且结束时间比当前预约课程结束时间晚";
            log.error(info);
            throw new SdcException(ResultEnum.SUB_FAIL,info);
        }
    }

    /**
     * 功能描述:取消预约请求
     *
     * @author Jason
     * @param cause 取消原因
     * @param courserId courserId课程id
     * @param stuOpenid 学生微信id
     * @return SubCourse 取消后的课程内容
     */
    @Override
    public SubCourse cancelOrder(String cause, String stuOpenid, Integer courserId, Integer subId) {

        /*
         *  只有该预约状态为预约等待和预约成功时才会发起取消预约。
         *  若预约状态为预约等待，则直接修改为预约取消，并加上原因
         *  若预约状态为预约成功，则修改为预约取消，并加上原因的同时
         *  如果每次教师指定的成功预约的学生都取消了预约，没有一个学生
         *  预约当前课程的（状态均为学生取消预约），那么教师的课程状态应该改为待预约（300）。
         */
        SubCourse subCourse = subCourseRepository.findOne(subId);
        TeaCourse rsTeaCourse = teaCourseRepository.findOne(courserId);
        //如果课程结束时间早于当前时间，则不能取消预约
        if(new Date().after(rsTeaCourse.getCourseStartTime())){
            info = "【学生取消预约】 当前课程课程状态不允许取消课程！";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        if(subCourse.getSubStatus().equals(SubCourseEnum.SUB_WAIT.getCode())){
            subCourse.setSubStatus(SubCourseEnum.STU_CANCEL_SUB.getCode());
            subCourse.setStuCause(cause);
            return subCourseRepository.save(subCourse);
        }else if(subCourse.getSubStatus().equals(SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode())){
            subCourse.setSubStatus(SubCourseEnum.STU_CANCEL_SUB.getCode());
            subCourse.setStuCause(cause);
            SubCourse rsSubCourse = subCourseRepository.save(subCourse);

            //如果只有一个学生预约成功，则修改课程表状态为等待预约；若多个学生预约成功了该课程则课程状态不变
            TeaCourse changeTeaCourse = teaCourseRepository.findOne(courserId);
            List<SubCourse> subCoursesList = subCourseRepository.findByCourseId(courserId);
            Short flagSub = 0;
            for(SubCourse subCourse1 : subCoursesList){
                if(subCourse1.getSubId() != subId && (subCourse1.getSubStatus()
                        .equals(SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode()) )  ){
                    flagSub = 1;
                    break;
                } else if(subCourse1.getCourseId() != courserId && subCourse1.getSubStatus().equals(SubCourseEnum.SUB_WAIT.getCode()) ){
                    flagSub = 2;
                }
            }
            if(flagSub == 1 || flagSub == 2){
                changeTeaCourse.setCourseStatus(CourseEnum.SUB_SUCCESS.getCode());
            } else {
                changeTeaCourse.setCourseStatus(CourseEnum.SUB_WAIT.getCode());
            }
            TeaCourse rsChangeTeaCourse = teaCourseRepository.save(changeTeaCourse);
            if(null == rsChangeTeaCourse){
                info = "【学生取消预约】 修改课程表中课程字段失败";
                log.error(info);
                throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
            }

            //给教师推送模板消息，学生取消了课程
            TeaCourse teaCourse = teaCourseRepository.findOne(courserId);
            TeaBase teaBase = teaBaseRepository.findOne(teaCourse.getTeaCode());
            PushMessageDTO pushMessageDTO = new PushMessageDTO();
            teaCourse.setCourseCause(cause);
            pushMessageDTO.setTeaCourse(teaCourse);
            pushMessageDTO.setTeaBase(teaBase);
            StuBase byStuCode = stuBaseRepository.findByStuOpenid(stuOpenid);
            pushMessageDTO.setStuBase(byStuCode);

            pushMessageService.pushCancelCourseMessageToTea(pushMessageDTO);
            return rsSubCourse;
        } else {
            info = "【学生取消预约】 预约状态非法，不是预约等待和预约成功，subId="+subId;
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
    }



//        //查找到该学生的学籍号
//        String stuCode = stuBaseRepository.findByStuOpenid(stuOpenid).getStuCode();
//        TeaCourse teaCourse = teaCourseRepository.findOne(courserId);
//        Integer courseStatus = teaCourse.getCourseStatus();
//
//        // 1.查到该条取消预约请求的当前状态，查到该课程所有的预约请求
//        SubCourse subCourse = subCourseRepository.findByCourseIdAndStuCode(courserId,stuCode);
//        List<SubCourse> subCourseList = subCourseRepository.findByCourseId(courserId);
//
//        // 2.若预约状态为提交预约请求，则将状态改为学生取消预约；检查是否包含其他提交预约请求，
//        // 若有，则保持已被预约,若没有，则改为待预约
//        Integer subStatus = subCourse.getSubStatus();
//        log.info("预约状态subStatus"+subStatus);
//        if(subStatus.equals(SubCourseEnum.SUB_WAIT.getCode())){
//
//            if(!courseStatus .equals(CourseEnum.SUB_SUCCESS.getCode())){
//                log.info("【学生取消课程】【学生请求状态为预约等待】 课程状态不等于“已被预约”,错误!!!");
//                teaCourse.setCourseStatus(CourseEnum.SUB_SUCCESS.getCode());
//                teaCourseRepository.save(teaCourse);
//            }
//
//            // 判断是否包含其他预约请求
//            int numSUB_WAIT = 0;
//            for(SubCourse subCourse1 : subCourseList){
//                if(subCourse1.getSubStatus().equals(SubCourseEnum.SUB_WAIT.getCode())){
//                    numSUB_WAIT++;
//                }
//            }
//            if(numSUB_WAIT >1){
//                log.info("【学生取消课程】【提交预约请求个数,取消后仍存在预约请求】");
//            }else {
//                log.info("【学生取消课程】【提交预约请求个数,取消后不存在其他预约请求】");
//                teaCourse.setCourseStatus(CourseEnum.SUB_WAIT.getCode());
//                teaCourseRepository.save(teaCourse);
//                log.info("【学生取消课程】【提交预约请求个数,取消后不存在其他预约请求】 课程状态改为“待预约”");
//            }
//            subCourse.setSubStatus(SubCourseEnum.STU_CANCEL_SUB.getCode());
//            return subCourseRepository.save(subCourse);
//
//        }else if(subStatus.equals(SubCourseEnum.SUB_CANDIDATE_FAILED.getCode())){
//            // 3.若预约状态为预约失败，则直接将状态修改为学生取消预约
//            log.info("【学生取消课程】【学生请求状态为预约失败】");
//            subCourse.setSubStatus(SubCourseEnum.STU_CANCEL_SUB.getCode());
//            return subCourseRepository.save(subCourse);
//
//        }else if(subStatus.equals(SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode())){
//            // 4.若预约状态为预约成功，则将状态改为学生取消预约;检查该课程是否包含其他预约失败请求，
//            // 若有，则把其余预约失败的请求，修改为提交预约请求，课程改为已被预约;若没有，将课程改为待预约
//            int numSUB_CANDIDATE_FAILED = 0;
//            for(SubCourse subCourse1 : subCourseList){
//                if(subCourse1.getSubStatus().equals(SubCourseEnum.SUB_WAIT.getCode())){
//                    numSUB_CANDIDATE_FAILED++;
//                }
//            }
//
//            if(numSUB_CANDIDATE_FAILED > 0){
//                //存在其他预约失败请求
//                log.info("【学生取消课程】【学生请求状态为预约成功】存在其他预约请求");
//                for(SubCourse subCourse1: subCourseList){
//                    if(subCourse1.getSubStatus().equals(SubCourseEnum.SUB_CANDIDATE_FAILED.getCode())){
//                        subCourse1.setSubStatus(SubCourseEnum.SUB_WAIT.getCode());
//                        subCourseRepository.save(subCourse1);
//                    }
//                }
//                teaCourse.setCourseStatus(CourseEnum.SUB_SUCCESS.getCode());
//                log.info("【学生取消课程】【学生请求状态为预约失成功】课程状态变为已被预约");
//            }else{
//                //不存在其他预约失败请求
//                teaCourse.setCourseStatus(CourseEnum.SUB_WAIT.getCode());
//                teaCourseRepository.save(teaCourse);
//            }
//            subCourse.setSubStatus(SubCourseEnum.STU_CANCEL_SUB.getCode());
//            return subCourseRepository.save(subCourse);
//        }else{
//            log.info("【学生取消课程】【学生请求状态为非法】");
//            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION.getCode(),ResultEnum.INFO_NOTFOUND_EXCEPTION.getMessage());
//        }




//        /*找到预约表信息--根据学生编号与课程id查找到未失效的预约信息*/
//        List<Integer> list=new ArrayList<>();
//        list.add(SubCourseEnum.SUB_WAIT.getCode());
//        list.add(SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode());
//        SubCourse byStuCodeAndCourseId = subCourseRepository.findByStuCodeAndCourseIdAndSubStatusIsIn(byStuOpenid.getStuCode(), courserId,list);
//        if (byStuCodeAndCourseId!=null){
//            /*把预约状态更新为失效*/
//            byStuCodeAndCourseId.setSubStatus(SubCourseEnum.STU_CANCEL_SUB.getCode());
//            /*设置取消原因*/
//            byStuCodeAndCourseId.setStuCause(cause);
//        }else {
//            info = "【学生发起取消预约课程请求】 课程未发现";
//            log.error(info);
//            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
//        }
//        /*保存信息到预约表中*/
//        SubCourse subCourse = subCourseRepository.save(byStuCodeAndCourseId);
//        //查询当前课程有没有其他学生预约。如果没有，则课程状态变为等待学生预约；否则，状态不变
//        List<SubCourse> byCourseId = subCourseRepository.findByCourseId(courserId);
//        boolean courseIsBusy = false;
//        for(SubCourse subCourseLook : byCourseId){
//
//            if (subCourseLook.getStuCode() != byStuOpenid.getStuCode() ){
//                //如果当前课程有人预约或预约成功，那么课程的状态就不能设置为等待预约
//                if(subCourseLook.getSubStatus().equals(SubCourseEnum.SUB_WAIT.getCode()) ||
//                        subCourseLook.getSubStatus().equals(SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode())){
//                    courseIsBusy = true; break;
//                }
//            }
//        }
//        //如果没有其他学生预约或预约成功当前课程，那么就需要把课程状态设置为等待预约
//        if(courseIsBusy == false){
//            TeaCourse one = teaCourseRepository.findOne(courserId);
//            one.setCourseStatus(CourseEnum.SUB_WAIT.getCode());
//            TeaCourse save = teaCourseRepository.save(one);
//            if(null == save){
//                info = "【学生发起取消预约课程请求】 更新课程表中课程状态失败";
//                log.error(info);
//                throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
//            }
//        }
//
//        if (subCourse==null){
//            info = "【学生发起取消预约课程请求】 报存到数据库中失败";
//            log.error(info);
//            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
//        }
//        return subCourse;

    /**
     *
     * 功能描述: 提交反馈
     *
     * @param courseId 课程id
     * @param message 代表反馈内容
     * @param score 代表反馈评分
     * @param subId 预约课程id
     * @return:
     */
    @Override
    public FeedBack feedBack(Integer courseId, String message, Integer score, Integer subId) {
        /*如果评分不正确，抛出异常*/
        if (score>5 || score<0){
            info = "【学生发起反馈请求】 反馈参数异常";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        /*查找课程信息*/
        TeaCourse one = teaCourseRepository.findOne(courseId);
        /*
            提交反馈需要判断课程信息是否正常结束：
            1.课程状态为结束
            2.课程状态为正在进行并且状态课程的结束时间早于当前时间
        */
        //课程状态为正在进行并且状态课程的结束时间早于当前时间那么需要将课程状态修改为结束
        if(one != null ){
            if( one.getCourseStatus().equals(CourseEnum.COURSE_INTERACT.getCode())
                    &&  one.getCourseEndTime().before(new Date()) ){
                one.setCourseStatus(CourseEnum.COURSE_FINISH.getCode());
                TeaCourse updateCourseStatus = teaCourseRepository.save(one);
                if(null == updateCourseStatus ){
                    info = "【学生提交课程反馈】 更新数据库异常！";
                    log.error(info);
                    throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
                }
            }
            if( !one.getCourseStatus().equals(CourseEnum.COURSE_FINISH.getCode())){
                info = "【学生提交课程反馈】 当前课程状态不正确，不能提交反馈！";
                log.error(info);
                throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
            }
        } else {
            info = "课程信息为空！";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        /*根据预约课程id来查找用户是否提交反馈*/
        FeedBack bySubId = feedBackRepository.findBySubId(subId);
        if (bySubId!=null && bySubId.getStuFeedback() != null){
            info = "学生已经反馈成功，无需多次反馈";
            log.error(info);
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
        }
        if (bySubId==null){
            bySubId=new FeedBack();
        }
        /*封装学生反馈信息到反馈表中*/
        bySubId.setStuScore(score);
        bySubId.setSubId(subId);
        bySubId.setStuFeedback(message);
        /*保存数据到反馈表*/
        FeedBack save=feedBackRepository.saveAndFlush(bySubId);
        if (save==null){
            info = "【学生发起反馈】 数据库操作失败";
            log.error(info);
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
        }
        //模板消息的封装
        PushMessageDTO pushMessageDTO = new PushMessageDTO();
        TeaBase rsTeaBase = teaBaseRepository.findOne(one.getTeaCode());
        pushMessageDTO.setTeaBase(rsTeaBase);
        SubCourse rsSubCourse = subCourseRepository.findOne(subId);
        pushMessageDTO.setStuBase(stuBaseRepository.findByStuCode(rsSubCourse.getStuCode()));
        pushMessageDTO.setFeedBack(save);
        pushMessageDTO.setTeaCourse(teaCourseRepository.findByTeaCodeAndCourseId(rsTeaBase.getTeaCode(),courseId));
        FeedBack one1 = feedBackRepository.findOne(save.getFeedbackId());
        pushMessageService.pushFeedBackMessageToTea(pushMessageDTO);
        return one1;
    }
    /**
     *查询历史记录课程
     *
     * @param page 页数
     * @param size 页面大小
     * @param stuOpenid 学生微信id
     * @return List<CourseDTO> 课程封装对象的集合
     **/
    @Override
    public List<SubDTO> lookHistory(Integer page, Integer size, String stuOpenid) {



        StuBase byStuOpenid = stuBaseRepository.findByStuOpenid(stuOpenid);

        Sort sort =new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<SubCourse> subCoursePage = subCourseRepository.findByStuCode(byStuOpenid.getStuCode(), pageable);
        if (subCoursePage.getContent().isEmpty()){
            info = "【学生查看历史记录】 没有预约的课程";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }

        List<SubDTO> subDTOList = new ArrayList<>();
        for (SubCourse subCourse : subCoursePage) {

            SubDTO subDTO = modelMapper.map(subCourse, SubDTO.class);
            System.out.println(subCourse.getCourseId()+"--------------");
            TeaCourse teaCourse = teaCourseRepository.findOne(subCourse.getCourseId());
            if (teaCourse==null){
                info =  "预约表中的课程信息未发现";
                log.error(info);
                throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
            }
            TeaBase teaBase = teaBaseRepository.findOne(teaCourse.getTeaCode());
            if (teaBase==null){
                info =  "教师信息未发现";
                log.error(info);
                throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
            }

            //更新课程状态
            teaService.finishCourse(teaCourse.getCourseId());

            subDTO.setTeaName(teaBase.getTeaName());
            subDTO.setTeaCourse(teaCourse);
            FeedBack feedBack = feedBackRepository.findBySubId(subCourse.getSubId());
            if(null != feedBack){
                subDTO.setFeedBack(feedBack);
            }else{
                //若预约成功，且没有创建反馈，则先创建一条记录，保证可以通过subId提交反馈
                if (subCourse.getSubStatus().equals(SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode())){
                    FeedBack addFeedBack = new FeedBack();
                    addFeedBack.setSubId(subCourse.getSubId());
                    feedBackRepository.save(addFeedBack);
                    subDTO.setFeedBack(addFeedBack);
                }
            }

            subDTOList.add(subDTO);
        }
        return subDTOList;
    }

    /**
     * 判断学生openid是否合法存在
     * @param stuOpenid 学生openid
     * @return Boolean
     */
    @Override
    public Boolean legalStudent(String stuOpenid) {
        StuBase byStuOpenid = stuBaseRepository.findByStuOpenid(stuOpenid);
        if(null == byStuOpenid ){
            return false;
        }
        if(StringUtils.isEmpty(byStuOpenid.getStuName()) || StringUtils.isEmpty(byStuOpenid.getStuCode())){
            return false;
        }
        return byStuOpenid != null;
    }

    /**
     * 功能描述: 封装课程信息
     *
     * @param teaCoursePage 分页的课程
     * @return List<CourseDTO>
     */
    private List<CourseDTO> getCourse(Page<TeaCourse> teaCoursePage){
        /*创建一个CourseDTO对象用来封装查找到的信息*/
        List<CourseDTO> courseDTOList = new ArrayList<>();
        /*遍历查找到的信息*/
        for (TeaCourse teaCourse : teaCoursePage) {
            /*根据教师编号查找到教师完整信息*/
            TeaBase teaBase = teaBaseRepository.findOne(teaCourse.getTeaCode());
            /*如果教师不存在，抛出教师不存在异常*/
            if (teaBase==null){
                info = "预约课程对应的老师信息不存在";
                log.error(info);
                throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
            }
            teaService.finishCourse(teaCourse.getCourseId());

            /*将CourseStatus中的属性映射到一个封装对象中*/
            CourseDTO courseDTO = modelMapper.map(teaCourse, CourseDTO.class);
            /*将教师信息放入到封装对象中*/
            courseDTO.setTeaBase(teaBase);
            /*添加封装对象到list中*/
            courseDTOList.add(courseDTO);
        }
        return courseDTOList;
    }

}