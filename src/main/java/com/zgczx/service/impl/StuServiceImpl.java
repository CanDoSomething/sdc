package com.zgczx.service.impl;


import com.zgczx.dataobject.*;
import com.zgczx.dto.CourseDTO;
import com.zgczx.dto.SubDTO;
import com.zgczx.enums.CourseEnum;
import com.zgczx.enums.ResultEnum;
import com.zgczx.enums.SubCourseEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.*;
import com.zgczx.service.StuService;
import com.zgczx.service.TeaService;
import com.zgczx.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private  String info = null;
    /**
     *
     * 功能描述: 显示所有课程信息
     *
     * @param page 页面数
     * @param size 页面大小
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 17:54
     */
    @Override
    public List<CourseDTO> findAllCourse(Integer page,Integer size) {
        //先按照课程日期降序排序
        Sort sort =new Sort(Sort.Direction.DESC,"courseDate");
        /*设置分页*/
        Pageable pageable = new PageRequest(page, size, sort);
        Page<TeaCourse> byCourseStatus = teaCourseRepository.findAllCourse( new Date(), pageable);
        /*如果课程不存在，返回预约课程不存在*/
        if (byCourseStatus.getContent().isEmpty()){
            info = "【学生查看所有课程】 没有正在发布的课程";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        /*封装课程信息到消息中间类*/
        List<CourseDTO> course = getCourse(byCourseStatus);
        return course;
    }
    /**
     *
     * 功能描述: 提交预约请求
     *
     * @param stuOpenid 学生微信Id
     * @param courserId  课程id
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 17:57
     */
    @Override
    public SubCourse order(String stuOpenid, Integer courserId) {
        /*查找学生预约的课程信息*/
        TeaCourse teaCourse = teaCourseRepository.findOne(courserId);
        /*如果预约课程不存在，抛出异常*/
        if (teaCourse==null){
            info = "【学生发起预约课程请求】 预约课程信息不存在";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        StuBase byStuOpenid = stuBaseRepository.findByStuOpenid(stuOpenid);
        if (byStuOpenid==null){
            info = "学生信息不存在";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        /*查询该学生的预约列表*/
        List<SubCourse> subCourses = subCourseRepository.findByStuCode(byStuOpenid.getStuCode());
        for (SubCourse subCourse : subCourses) {
            /*找到等待预约和预约成功的所有预约信息*/
            if (subCourse.getSubStatus().equals(SubCourseEnum.SUB_WAIT.getCode()) || subCourse.getSubStatus().equals(SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode())){
                /*根据课程id查找到课程信息*/
                TeaCourse one = teaCourseRepository.findOne(subCourse.getCourseId());
                if (one==null){
                    info = "【学生已经存在的预约课程请求】 预约课程对应的老师信息不存在";
                    log.error(info);
                    throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
                }
                /*判断预约时间是否冲突*/
                if(DateUtil.compareTime(teaCourse.getCourseStartTime(),one.getCourseEndTime()) || DateUtil.compareTime(one.getCourseStartTime(),teaCourse.getCourseEndTime())){
                }else {
                    /*抛出预约冲突异常*/
                    info = "【学生发起预约课程请求】 预约课程信息冲突";
                    log.error(info);
                    throw new SdcException(ResultEnum.SUB_FAIL,info);
                }
            }
        }
        /*如果运行到这里证明预约时间不冲突可以建立预约对象*/
        SubCourse subCourse=new SubCourse();
        subCourse.setStuCode(byStuOpenid.getStuCode());
        subCourse.setCourseId(courserId);
        /*设置新的预约对象为等待预约状态*/
        subCourse.setSubStatus(SubCourseEnum.SUB_WAIT.getCode());
        /*保存到数据库*/
        SubCourse save = subCourseRepository.save(subCourse);
        if (save==null){
            info = "【学生发起预约课程请求】 预约信息没有保存到数据库，预约课程失败";
            log.error(info);
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
        }
        /**
         * 课程状态改为已被预约
         */
        teaCourse.setCourseStatus(CourseEnum.SUB_SUCCESS.getCode());
        teaCourseRepository.save(teaCourse);
        return save;
    }

    /**
     *
     * 功能描述:取消预约请求
     *
     * @author Jason
     * @param cause 取消原因
     * @param courserId courserId课程id
     * @param stuOpenid 学生微信id
     * @return SubCourse 取消后的课程内容
     */
    @Override
    public SubCourse cancelOrder(String cause,String stuOpenid,Integer courserId) {
        StuBase byStuOpenid = stuBaseRepository.findByStuOpenid(stuOpenid);
        if (byStuOpenid==null){
            info = "找不到该微信id对应的学生";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }

        //查找到该学生的学籍号
        String stuCode = stuBaseRepository.findByStuOpenid(stuOpenid).getStuCode();
        TeaCourse teaCourse = teaCourseRepository.findOne(courserId);
        Integer courseStatus = teaCourse.getCourseStatus();

        // 1.查到该条取消预约请求的当前状态，查到该课程所有的预约请求
        SubCourse subCourse = subCourseRepository.findByCourseIdAndStuCode(courserId,stuCode);
        List<SubCourse> subCourseList = subCourseRepository.findByCourseId(courserId);

        // 2.若预约状态为提交预约请求，则将状态改为学生取消预约；检查是否包含其他提交预约请求，
        // 若有，则保持已被预约,若没有，则改为待预约
        Integer subStatus = subCourse.getSubStatus();
        log.info("预约状态subStatus"+subStatus);
        if(subStatus.equals(SubCourseEnum.SUB_WAIT.getCode())){

            if(!courseStatus .equals(CourseEnum.SUB_SUCCESS.getCode())){
                log.info("【学生取消课程】【学生请求状态为预约等待】 课程状态不等于“已被预约”,错误!!!");
                teaCourse.setCourseStatus(CourseEnum.SUB_SUCCESS.getCode());
                teaCourseRepository.save(teaCourse);
            }

            // 判断是否包含其他预约请求
            int numSUB_WAIT = 0;
            for(SubCourse subCourse1 : subCourseList){
                if(subCourse1.getSubStatus().equals(SubCourseEnum.SUB_WAIT.getCode())){
                    numSUB_WAIT++;
                }
            }
            if(numSUB_WAIT >1){
                log.info("【学生取消课程】【提交预约请求个数,取消后仍存在预约请求】");
            }else {
                log.info("【学生取消课程】【提交预约请求个数,取消后不存在其他预约请求】");
                teaCourse.setCourseStatus(CourseEnum.SUB_WAIT.getCode());
                teaCourseRepository.save(teaCourse);
                log.info("【学生取消课程】【提交预约请求个数,取消后不存在其他预约请求】 课程状态改为“待预约”");
            }
            subCourse.setSubStatus(SubCourseEnum.STU_CANCEL_SUB.getCode());
            return subCourseRepository.save(subCourse);

        }else if(subStatus.equals(SubCourseEnum.SUB_CANDIDATE_FAILED.getCode())){
            // 3.若预约状态为预约失败，则直接将状态修改为学生取消预约
            log.info("【学生取消课程】【学生请求状态为预约失败】");
            subCourse.setSubStatus(SubCourseEnum.STU_CANCEL_SUB.getCode());
            return subCourseRepository.save(subCourse);

        }else if(subStatus.equals(SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode())){
            // 4.若预约状态为预约成功，则将状态改为学生取消预约;检查该课程是否包含其他预约失败请求，
            // 若有，则把其余预约失败的请求，修改为提交预约请求，课程改为已被预约;若没有，将课程改为待预约
            int numSUB_CANDIDATE_FAILED = 0;
            for(SubCourse subCourse1 : subCourseList){
                if(subCourse1.getSubStatus().equals(SubCourseEnum.SUB_WAIT.getCode())){
                    numSUB_CANDIDATE_FAILED++;
                }
            }

            if(numSUB_CANDIDATE_FAILED > 0){
                //存在其他预约失败请求
                log.info("【学生取消课程】【学生请求状态为预约成功】存在其他预约请求");
                for(SubCourse subCourse1: subCourseList){
                    if(subCourse1.getSubStatus().equals(SubCourseEnum.SUB_CANDIDATE_FAILED.getCode())){
                        subCourse1.setSubStatus(SubCourseEnum.SUB_WAIT.getCode());
                        subCourseRepository.save(subCourse1);
                    }
                }
                teaCourse.setCourseStatus(CourseEnum.SUB_SUCCESS.getCode());
                log.info("【学生取消课程】【学生请求状态为预约失成功】课程状态变为已被预约");
            }else{
                //不存在其他预约失败请求
                teaCourse.setCourseStatus(CourseEnum.SUB_WAIT.getCode());
                teaCourseRepository.save(teaCourse);
            }
            subCourse.setSubStatus(SubCourseEnum.STU_CANCEL_SUB.getCode());
            return subCourseRepository.save(subCourse);
        }else{
            log.info("【学生取消课程】【学生请求状态为非法】");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION.getCode(),ResultEnum.INFO_NOTFOUND_EXCEPTION.getMessage());
        }
    }










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
     * @auther: 陈志恒
     * @date: 2018/12/16 19:24
     */
    @Override
    public FeedBack feedBack(Integer courseId, String message, Integer score,Integer subId) {
        /*如果评分不正确，抛出异常*/
        if (score>5 || score<0){
            info = "【学生发起反馈请求】 反馈参数异常";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        /*查找课程信息*/
        TeaCourse one = teaCourseRepository.findOne(courseId);
        /*判断课程信息是否正常结束,如果不是抛出异常*/
        if (one==null || !one.getCourseStatus().equals(CourseEnum.COURSE_FINISH.getCode())){
            info = "课程没有正常结束,不能提交反馈信息";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
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
            info = "【学生发起反馈】 报存到数据库中失败";
            log.error(info);
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
        }
        FeedBack one1 = feedBackRepository.findOne(save.getFeedbackId());
        return one1;
    }
    /**
     *查询历史记录课程
     *
     * @Author chen
     * @Date 21:02 2018/12/20
     * @param page 页数
     * @param size 页面大小
     * @param stuOpenid 学生微信id
     * @return List<CourseDTO> 课程封装对象的集合
     **/
    @Override
    public List<SubDTO> lookHistory(Integer page, Integer size, String stuOpenid) {
        StuBase byStuOpenid = stuBaseRepository.findByStuOpenid(stuOpenid);
        if (byStuOpenid==null){
            info = "学生信息不存在";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        Sort sort =new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<SubCourse> byStuCode = subCourseRepository.findByStuCode(byStuOpenid.getStuCode(), pageable);
        if (byStuCode.getContent().isEmpty()){
            info = "【学生查看历史记录】 没有预约的课程";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }

        List<SubDTO> list = new ArrayList<>();
        for (SubCourse subCourse : byStuCode) {
            TeaCourse teaCourse = teaService.finishCourse(subCourse.getCourseId());

            if(teaCourse!=null){
                int subId = subCourseRepository.findByCourseIdAndSubStatus(teaCourse.getCourseId(),
                        SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode()).get(0).getSubId();

                if(feedBackRepository.findBySubId(subId)!=null){
                    continue;
                }else{
                    FeedBack feedBack = new FeedBack();
                    feedBack.setSubId(subId);
                    feedBackRepository.save(feedBack);
                }


            }



            SubDTO map = modelMapper.map(subCourse, SubDTO.class);
            TeaCourse one = teaCourseRepository.findOne(subCourse.getCourseId());
            if (one==null){
                info =  "预约表中的课程信息未发现";
                log.error(info);
                throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
            }
            TeaBase one1 = teaBaseRepository.findOne(one.getTeaCode());
            if (one1==null){
                info =  "教师信息未发现" + one1.toString();
                log.error(info);
                throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
            }
            map.setTeaName(one1.getTeaName());
            map.setTeaCourse(one);
            FeedBack feedBack = feedBackRepository.findBySubId(subCourse.getSubId());
            map.setFeedBack(feedBack);
            list.add(map);
        }
        return list;
    }

    /**
     *
     * 功能描述: 封装信息到CourseDTO中
     *
     * @param:
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 19:39
     */
    public List<CourseDTO> getCourse(Page<TeaCourse> byCourseStatus){
        /*创建一个CourseDTO对象用来封装查找到的信息*/
        List<CourseDTO> list=new ArrayList<>();
        /*遍历查找到的信息*/
        for (TeaCourse courseStatus : byCourseStatus) {
            /*根据教师编号查找到教师完整信息*/
            TeaBase one = teaBaseRepository.findOne(courseStatus.getTeaCode());
            /*如果教师不存在，抛出教师不存在异常*/
            if (one==null){
                info = "预约课程对应的老师信息不存在";
                log.error(info);
                throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
            }
            /*将CourseStatus中的属性映射到一个封装对象中*/
            CourseDTO courseDTO = modelMapper.map(courseStatus, CourseDTO.class);
            /*将教师信息放入到封装对象中*/
            courseDTO.setTeaBase(one);
            /*添加封装对象到list中*/
            list.add(courseDTO);
        }
        return list;
    }

}