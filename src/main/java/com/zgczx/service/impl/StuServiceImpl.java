package com.zgczx.service.impl;


import com.zgczx.dataobject.FeedBack;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaBase;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.dto.SubDTO;
import com.zgczx.enums.SubStatusEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.FeedBackRepository;
import com.zgczx.repository.SubCourseRepository;
import com.zgczx.repository.TeaBaseRepository;
import com.zgczx.repository.TeaCourseRepository;
import com.zgczx.service.StuService;
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
        //先按照课程开始日期升序排序
        Sort sort =new Sort(Sort.Direction.ASC,"courseStartTime");
        /*设置分页*/
        Pageable pageable = new PageRequest(page, size, sort);
        Page<TeaCourse> byCourseStatus = teaCourseRepository.findByCourseStatusAndAndCourseStartTimeIsAfter(SubStatusEnum.SUB_WAIT.getCode(), new Date(), pageable);
        /*如果课程不存在，返回预约课程不存在*/
        if (byCourseStatus==null){
            log.error("【学生查看所有课程】 没有发布的课程");
            throw new SdcException(SubStatusEnum.INFO_NOTFOUND_EXCEPTION);
        }
        /*封装课程信息到消息中间类*/
        List<CourseDTO> course = getCourse(byCourseStatus);
        return course;
    }
    /**
     *
     * 功能描述: 提交预约请求
     *
     * @param stuCode 学生编码
     * @param courserId  课程id
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 17:57
     */
    @Override
    public SubCourse order(String stuCode, Integer courserId) {
        /*查找学生预约的课程信息*/
        TeaCourse teaCourse = teaCourseRepository.findOne(courserId);
        /*如果预约课程不存在，抛出异常*/
        if (teaCourse==null){
            log.error("【学生发起预约课程请求】 预约课程信息不存在");
            throw new SdcException(SubStatusEnum.INFO_NOTFOUND_EXCEPTION);
        }
        /*查询该学生的预约列表*/
        List<SubCourse> subCourses = subCourseRepository.findByStuCode(stuCode);
        for (SubCourse subCourse : subCourses) {
            /*找到等待预约和预约成功的所有预约信息*/
            if (subCourse.getSubStatus().equals(SubStatusEnum.SUB_WAIT.getCode()) || subCourse.getSubStatus().equals(SubStatusEnum.SUB_SUCCESS.getCode())){
                /*根据课程id查找到课程信息*/
                TeaCourse one = teaCourseRepository.findOne(subCourse.getCourseId());
                if (one==null){
                    log.error("【学生已经存在的预约课程请求】 预约课程对应的老师信息不存在");
                    throw new SdcException(SubStatusEnum.INFO_NOTFOUND_EXCEPTION);
                }
                /*判断预约时间是否冲突*/
                if(DateUtil.compareTime(teaCourse.getCourseStartTime(),one.getCourseEndTime()) || DateUtil.compareTime(one.getCourseStartTime(),teaCourse.getCourseEndTime())){
                }else {
                    /*抛出预约冲突异常*/
                    log.error("【学生发起预约课程请求】 预约课程信息冲突");
                    throw new SdcException(SubStatusEnum.SUB_FAIL);
                }
            }
        }
        /*如果运行到这里证明预约时间不冲突可以建立预约对象*/
        SubCourse subCourse=new SubCourse();
        subCourse.setStuCode(stuCode);
        subCourse.setCourseId(courserId);
        /*设置新的预约对象为等待预约状态*/
        subCourse.setSubStatus(SubStatusEnum.SUB_WAIT.getCode());
        /*保存到数据库*/
        SubCourse save = subCourseRepository.save(subCourse);
        if (save==null){
            log.error("【学生发起预约课程请求】 预约信息没有保存到数据库，预约课程失败");
            throw new SdcException(SubStatusEnum.DATEBASE_OP_EXCEPTION);
        }
        return save;
    }

    /**
     *
     * 功能描述:取消预约请求
     *
     * @param: cause取消原因，courserId课程id，stuCode学生编码
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 18:06
     */
    @Override
    public SubCourse cancelOrder(String cause,String stuCode,Integer courserId) {
        /*找到预约表信息--根据学生编号与课程id查找到未失效的预约信息*/
        SubCourse byStuCodeAndCourseId = subCourseRepository.findByStuCodeAndCourseIdAndSubStatusIsNot(stuCode, courserId,SubStatusEnum.SUB_CANDIDATE_FAILD.getCode());
        if (cause!=null){
            /*把预约状态更新为失效*/
            byStuCodeAndCourseId.setSubStatus(SubStatusEnum.SUB_CANDIDATE_FAILD.getCode());
            /*设置取消原因*/
            byStuCodeAndCourseId.setStuCause(cause);
        }else {
            log.error("【学生发起取消预约课程请求】 参数不正确");
            throw new SdcException(SubStatusEnum.PARAM_EXCEPTION);
        }
        /*保存信息到预约表中*/
        SubCourse subCourse = subCourseRepository.save(byStuCodeAndCourseId);
        if (subCourse==null){
            log.error("【学生发起取消预约课程请求】 报存到数据库中失败");
            throw new SdcException(SubStatusEnum.DATEBASE_OP_EXCEPTION);
        }
        return subCourse;
    }
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
            log.error("【学生发起反馈请求】 反馈参数异常");
            throw new SdcException(SubStatusEnum.PARAM_EXCEPTION);
        }
        /*查找反馈表*/
        FeedBack feedBack=feedBackRepository.findOne(courseId);
        /*如果反馈表不存在，建立新的反馈表，并设置反馈表主键为课程id*/
        if (feedBack==null){
            feedBack=new FeedBack();
            feedBack.setFeedbackId(courseId);
        }
        /*封装学生反馈信息到反馈表中*/
        feedBack.setStuScore(score);
        feedBack.setSubId(subId);
        feedBack.setStuFeedback(message);
        /*保存数据到反馈表*/
        FeedBack save=feedBackRepository.save(feedBack);
        if (save==null){
            log.error("【学生发起反馈】 报存到数据库中失败");
            throw new SdcException(SubStatusEnum.DATEBASE_OP_EXCEPTION);
        }
        return save;
    }
    /**
     *查询历史记录课程
     *
     * @Author chen
     * @Date 21:02 2018/12/20
     * @param page 页数
     * @param size 页面大小
     * @param stuCode 学生编码
     * @return List<CourseDTO> 课程封装对象的集合
     **/
    @Override
    public List<SubDTO> lookHistory(Integer page, Integer size, String stuCode) {
        Sort sort =new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<SubCourse> byStuCode = subCourseRepository.findByStuCode(stuCode, pageable);
        List<SubDTO> list=new ArrayList<>();
        for (SubCourse subCourse : byStuCode) {
            SubDTO map = modelMapper.map(subCourse, SubDTO.class);
            TeaCourse one = teaCourseRepository.findOne(subCourse.getCourseId());
            if (one==null){
                log.error("预约表中的课程信息未发现");
                throw new SdcException(SubStatusEnum.INFO_NOTFOUND_EXCEPTION);
            }
            map.setTeaCourse(one);
            FeedBack feedBack = feedBackRepository.findOne(one.getCourseId());
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
                log.error("预约课程对应的老师信息不存在");
                throw new SdcException(SubStatusEnum.INFO_NOTFOUND_EXCEPTION);
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
