package com.zgczx.service.impl;

import com.zgczx.dataobject.*;
import com.zgczx.dto.CourseDTO;
import com.zgczx.enums.CourseEnum;
import com.zgczx.enums.ResultEnum;
import com.zgczx.enums.SubCourseEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.form.TeaCourseForm;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private StuBaseRepository stuBaseRepository;
    @Autowired
    private SubCourseRepository subCourseRepository;
    @Autowired
    private FeedBackRepository feedBackRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PushMessageService pushMessageService;

    /**
     *新增课程
     *
     * @param teaCourseForm 新增的课程信息
     * @return 新增的课程信息
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public TeaCourse createCourse(TeaCourseForm teaCourseForm,String teaOpenid) {

        // 判断课程上课日期，开始时间，结束时间是否合法？通过String来接收参数，格式转换后是否合法？
        //1.上课时间不得早于当前时间
        String strCourseStartTime = teaCourseForm.getCourseStartTime();
        String strCourseEndTime = teaCourseForm.getCourseEndTime();
        String strCourseTime = teaCourseForm.getCourseDate();
        Date courseStartDate = null;
        Date courseEndDate = null;
        Date courseDate = null;
        //字符串类型的时间要转化为Date类型
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            courseStartDate = sdf.parse(strCourseStartTime);
            courseEndDate = sdf.parse(strCourseEndTime);
            courseDate = sdf2.parse(strCourseTime);
        } catch (Exception e) {
            log.error("【教师创建课程】 日期格式转化错误");
            e.printStackTrace();
        }

        int startDateRs = courseStartDate.compareTo(new Date());
        int endDateRs = courseStartDate.compareTo(courseEndDate);
        int courseDateRs = 0;
        try {
            courseDateRs = courseDate.compareTo(sdf2.parse(new Date().toLocaleString()) );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(startDateRs != 1 ){
            log.error("【教师创建课程】 上课开始时间不得早于或等于当前时间");
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }

        //2.结束时间不得早于开始时间
        if(endDateRs != -1 ){
            log.error("【教师创建课程】 上课结束时间不得早于或等于上课开始时间");
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }
        //3.上课日期不能小于当前日期
        if(courseDateRs == -1){
            log.error("【教师创建课程】 上课日期不得早于当前时间");
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }
        /**
         *  逻辑过程
         * 1.判断该老师当前时间是否有课程
         * 2.再新建课程
         */
        TeaBase byteaOpenid = teaBaseRepository.findByteaOpenid(teaOpenid);
        List<TeaCourse> byTeaCode = teaCourseRepository.findByTeaCode(byteaOpenid.getTeaCode());
        //比较当前教师的所有未结束和未取消的课程的交互时间

        //s1,s2分别表示新创建课程的开始时间和当前查找的课程开始和结束时间的比较值
        //e1,e2分别表示新创建课程的结束时间和当前查找的课程开始和结束时间的比较值
        int s1,s2,e1,e2;
        for(TeaCourse teaCourse : byTeaCode){
            if( !(teaCourse.getCourseStatus().equals(CourseEnum.COURSE_CANCELED)) ||
                    !(teaCourse.getCourseStatus().equals(CourseEnum.COURSE_FINISH))){
                s1 = courseStartDate.compareTo(teaCourse.getCourseStartTime());
                s2 = courseStartDate.compareTo(teaCourse.getCourseEndTime());
                if( (s1 == 1|| s1 == 0) && (s2 == -1 || s2 == 0)  ){
                    log.error("【教师创建课程】 新创建的课程和原有课程时间冲突");
                    throw new SdcException(ResultEnum.PARAM_EXCEPTION);
                }
                e1 = courseEndDate.compareTo(teaCourse.getCourseStartTime());
                e2 = courseEndDate.compareTo(teaCourse.getCourseEndTime());

                if( (e1 == 1|| e1 == 0) && (e2 == -1 || e2 == 0) ){
                    log.error("【教师创建课程】 新创建的课程和原有课程时间冲突");
                    throw new SdcException(ResultEnum.PARAM_EXCEPTION);
                }
            }
        }

        TeaCourse teaCourse = new TeaCourse();
        //由于前台时间传递的是String不能直接使用modelMapper转化
        teaCourse.setTeaCode(teaCourseForm.getTeaCode());
        teaCourse.setCourseDate(courseDate);
        teaCourse.setCourseStartTime(courseStartDate);
        teaCourse.setCourseEndTime(courseEndDate);
        teaCourse.setCourseLocation(teaCourseForm.getCourseLocation());
        teaCourse.setCourseName(teaCourseForm.getCourseName());
        teaCourse.setCourseStatus(CourseEnum.SUB_WAIT.getCode());
        teaCourse.setCourseInteractive(teaCourseForm.getCourseInteractive());
        teaCourse.setCourseLocation(teaCourseForm.getCourseLocation());

        TeaCourse save = teaCourseRepository.save(teaCourse);
        if(null == save){
            log.error("【教师创建课程】 课程信息插入数据库出现异常");
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION);
        }
        return save;
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
    public TeaCourse cancelCourse(Integer courseId,String teaOpenid,String cancelReason) {

        if(courseId == null){
            log.error("【教师取消课程】 该课程编号为空");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }
        // 取消课程之前需要判断当前课程是否为该老师的课程
        TeaBase byteaOpenid = teaBaseRepository.findByteaOpenid(teaOpenid);
        if(byteaOpenid == null){
            log.error("【教师取消课程】 该微信id没有找到对应教师");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }
        TeaCourse byTeaCodeAndCourseId = teaCourseRepository.findByTeaCodeAndCourseId(byteaOpenid.getTeaCode(), courseId);
        if(null == byTeaCodeAndCourseId){
            log.error("【教师取消课程】 该课程和您当前身份不匹配，取消课程失败");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }
        TeaCourse teaCourse = teaCourseRepository.findOne(courseId);
        //只有当前课程为等待预约和已被预约的情况下才能取消，课程进行时的取消应当属于提前结束课程
        Integer status = teaCourse.getCourseStatus();
        if(status.intValue() == CourseEnum.SUB_WAIT.getCode() || status.intValue() == CourseEnum.SUB_SUCCESS.getCode() ){
            teaCourse.setCourseCause(cancelReason);
            //如果当前已经有学生预约成功则给该学生发送消息通知
            if(status.intValue() == SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode()){
                //---------->pushMessageService.pushCancelCourseMessageToStu(teaCourse);
            }
            teaCourse.setCourseStatus(CourseEnum.COURSE_CANCELED.getCode());
        } else {
            log.error("【教师取消课程】 课程状态不正确，teaCourseId={},teaCourseIdStatus={}",
                    teaCourse.getCourseId(), teaCourse.getCourseStatus());
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }
        List<SubCourse> list  = subCourseRepository.findByCourseId(courseId);
        for(SubCourse subCourseByID : list ){
            //修改预约成功学生的预约状态为教师取消课程，预约失败的学生状态依然是预约失败
            if(subCourseByID.getSubStatus().equals(SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode())) {
                subCourseByID.setSubStatus(SubCourseEnum.TEA_CANCEL_SUB.getCode());
                SubCourse save = subCourseRepository.save(subCourseByID);
                if (null == save) {
                    log.error("【取消课程】取消课程失败！");
                    throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION);
                }
            }
        }
        TeaCourse save = teaCourseRepository.save(teaCourse);
        if(null == save) {
            log.error("【取消课程】取消课程失败！");
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION);
        }
        return save;
    }

    /**
     * 根据教师编号查看其历史课程记录
     *
     * @param teaOpenid 教师微信编号
     * @param page 当前页
     * @param pageSize 当前页面大小
     * @return 当前教师的所有历史课程
     *
     */

    @Override
    public List<CourseDTO> findTeaHistoryCourse(String teaOpenid,int page,int pageSize) {

        if(teaOpenid == null || "".equals(teaOpenid)){
            log.error("【教师查看历史课程】 教师微信编号为空");
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }
        TeaBase byteaOpenid = teaBaseRepository.findByteaOpenid(teaOpenid);
        if(null == byteaOpenid){
            log.error("【教师查看历史课程】 根据教师微信编号未找到对应教师信息");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }
        String teaCode = byteaOpenid.getTeaCode();
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
                throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
            }
            for(TeaCourse course : all){
                rsList.add(modelMapper.map(course,CourseDTO.class));
            }

            //去掉没有结束的课程
           /* java.util.function.Predicate<CourseDTO> courseStatusFilter = course -> (course.getCourseStatus()
                    .equals(SubStatusEnum.SUB_COURSE_FINISH.getCode()));
            rsList = rsList.stream().filter(courseStatusFilter).collect(Collectors.toList());*/
            FeedBack feedBack = null;
            Integer courserId = null;
            for(CourseDTO course: rsList){
                //通过课程编号查找学生给老师的反馈表
                courserId = course.getCourseId();
                List<SubCourse> byCourseIdAndSubStatus = subCourseRepository.findByCourseId(courserId);
                for(SubCourse subCourse : byCourseIdAndSubStatus ){
                    feedBack = feedBackRepository.findBySubId(subCourse.getSubId());
                }
                if(null == feedBack){
                    feedBack = new FeedBack();
                    feedBack.setStuFeedback("课程还没有结束，暂时没有反馈");
                    feedBack.setTeaFeedback("课程还没有结束，暂时没有反馈");
                }
                //为了将学生给教师的反馈也展示出来这里将StuFeedBack包装到CourseDTO中，并返回courseDTO
                course.setFeedBack(feedBack);
                course.setTeaBase(teaBase);
            }
        } else {
            log.error("【教师查看历史课程记录】 该编号的教师不存在");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }
        return rsList;
    }

    /**
     *根据课程id获取所有预约同学的基本信息
     *
     * @param courserId 课程编号
     * @param teaOpenid 教师微信编号
     * @param page 当前页
     * @param pageSize 当前页面大小
     * @return 所有候选人
     *
     */
    @Override
    public List<StuBase> findCandidateByCourseId(Integer courserId,String teaOpenid,int page,int pageSize){

        if(courserId == null){
            log.error("【教师查看预约候选人】 该课程编号为空");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }
        //判断该课程是否为该老师的课程
        TeaBase byteaOpenid = teaBaseRepository.findByteaOpenid(teaOpenid);
        if(byteaOpenid == null){
            log.error("【教师查看预约候选人】 该微信id没有找到对应教师");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }
        TeaCourse byTeaCodeAndCourseId = teaCourseRepository.findByTeaCodeAndCourseId(byteaOpenid.getTeaCode(),courserId);
        if(null == byTeaCodeAndCourseId){
            log.error("【教师查看预约候选人】 该课程和您当前身份不匹配，不能查看课程预约候选人");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }

        //设置分页参数
        Pageable pageable = new PageRequest(page,pageSize);
        //查找候选人的时候只将预约状态为提交预约的学生查找出来
        List<StuBase> allCandidate = subCourseRepository.getAllCandidate(courserId,pageable);
        return allCandidate;
    }

    /**
     * 教师从候选人中选择预定学生
     *
     * @param stuOpenId 学生微信唯一Id
     * @param courseId 课程编号
     * @return 选中的学生信息
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public SubCourse saveSelectedStu(String stuOpenId,Integer courseId) {

        if(stuOpenId == null || "".equals(stuOpenId)){
            log.error("【教师选择候选预约学生】 学生编号为空");
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }
        if(courseId == null ){
            log.error("【教师选择候选预约学生】 课程编号为空");
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }
        //通过学生微信id来进行获取学生编号
        StuBase byStuOpenid = stuBaseRepository.findByStuOpenid(stuOpenId);
        if(null == byStuOpenid ){
            log.error("【教师选择候选预约学生】 该学生微信id没有找到对应学生");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }
        String stuCode = byStuOpenid.getStuCode();
        //修改预约表中被成功选择的学生的学号
        SubCourse subCourse = subCourseRepository.findByStuCodeAndCourseIdAndSubStatus(stuCode,courseId,SubCourseEnum.SUB_WAIT.getCode());
        subCourse.setSubStatus(SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode());
        //查找课程表
        TeaCourse one = teaCourseRepository.findOne(courseId);
        Integer code = CourseEnum.SUB_SUCCESS.getCode();
        one.setCourseStatus(code);
        one.setUpdateTime(new Date());

        //更新预约课程
        subCourse = subCourseRepository.save(subCourse);
        if(null == subCourse){
            log.error("【教师选择候选预约学生】更新预约表失败");
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION);
        }
        //更新课程状态
        TeaCourse teaCourseAfterUpdate = teaCourseRepository.save(one);
        if(null == teaCourseAfterUpdate){
            log.error("【教师选择候选预约学生】更新教师课程表失败");
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION);
        }
        //给学生推送预约成功的模板消息
        CourseDTO courseDTO  = modelMapper.map(one,CourseDTO.class);
        //courseDTO.setStudentCode(stuCode);
        TeaBase one1 = teaBaseRepository.findOne(one.getTeaCode());
        courseDTO.setTeaBase(one1);
        //------------>pushMessageService.pushSubSuccessMessage(courseDTO);

        //选择之后候选人之后 ，发送消息给没有入选的候选人提示预约失败
        List<SubCourse> byCourseIdAndSubStatus = subCourseRepository.findByCourseIdAndSubStatus(courseId, SubCourseEnum.SUB_WAIT.getCode());
        for(SubCourse subCourse1 : byCourseIdAndSubStatus){
            if(!subCourse1.getStuCode().equals(stuCode)){
                //将当前预约信息改成学生预约失效
                subCourse1.setSubStatus(SubCourseEnum.SUB_CANDIDATE_FAILED.getCode());
                SubCourse save = subCourseRepository.save(subCourse1);
                if(null == save){
                    log.error("【教师选择候选预约学生】更新教师课程表失败");
                    throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION);
                }
                //推送预约失败的模板消息
                //courseDTO.setStudentCode(subCourse1.getStuCode());
                //------------>pushMessageService.pushSubFailMessage(courseDTO);
            }
        }
        return subCourse;
    }

    /**
     *提交教师给学生的信息反馈
     *
     * @param subId 课程预约编号
     * @param teaOpenid 教师微信编号
     * @param feedBack 教师给学生的反馈信息
     * @param score 教师给学生的打分
     * @return 教师反馈信息
     *
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public FeedBack saveFeedBack(Integer subId,String teaOpenid,String feedBack,Integer score) {
        /**
         * 大致逻辑
         * 1. 判断当前课程的状态是否可以提交反馈？
         * 2. 根据subId判断是否学生已经创建记录，若未创建，则创建新纪录
         * 3. 若已创建，判断教师的反馈内容是否为空，若为空，则提交，不为空，则不能提交（限制只能提交一次）
         */

        if(null == subId){
            log.error("【教师给学生的反馈】 预约课程编号为空");
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }
        if(null == feedBack || "".equals(feedBack) || null == score){
            log.error("【教师给学生的反馈】 课程反馈信息不为空");
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }
        //通过预约课程信息获取课程信息
        SubCourse one1 = subCourseRepository.findOne(subId);
        Integer courseId = one1.getCourseId();
        TeaBase byteaOpenid = teaBaseRepository.findByteaOpenid(teaOpenid);
        TeaCourse byTeaCodeAndCourseId = teaCourseRepository.findByTeaCodeAndCourseId(byteaOpenid.getTeaCode(), courseId);
        if(null == byTeaCodeAndCourseId){
            log.error("【教师给学生的反馈】 当前教师没有对应课程，提交课程反馈失败");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }

        List<SubCourse> list = subCourseRepository.findByCourseIdAndSubStatus(courseId,SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode());
        SubCourse subCourse = list.get(0);
        if(null == subCourse){
            log.error("【教师给学生的反馈】 找不到对应的预约课程");
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }
        //如果当前课程状态是课程交互状态或者上课状态为线下，则可以提交反馈结果
        //其中判断状态在finishCourse方法中已经处理了
        TeaCourse one = teaCourseRepository.findOne(subCourse.getCourseId());
        if(null == one){
            log.error("【教师给学生的反馈】 该课程编号不正确");
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }
        //修改课程表中课程状态为结束
        finishCourse(one.getCourseId());

        FeedBack proFeedBack = feedBackRepository.findBySubId(subId);
        //若反馈没有被创建，则创建
        if(null == proFeedBack){
            proFeedBack = new FeedBack();
        }
        //限定反馈只能提交一次
        if(proFeedBack.getTeaScore() != null  && proFeedBack.getTeaFeedback() != null){
            log.error("【教师给学生的反馈】 反馈只能提交一次");
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION);
        }
        proFeedBack.setSubId(subId);
        proFeedBack.setTeaFeedback(feedBack);
        proFeedBack.setTeaScore(score);
        return feedBackRepository.save(proFeedBack);
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
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }

        if(!teaCourse.getCourseStatus().equals(CourseEnum.SUB_WAIT.getCode())){
            log.error("【修改课程表的状态】 该课程的状态status={}不能被修改",teaCourse.getCourseStatus());
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION);
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
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }
        TeaCourse one = teaCourseRepository.findOne(courseId);

        if(null == one){
            log.error("【查看课程id={}的课程信息】 该课程没有找到",one);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
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
            throw new SdcException(ResultEnum.PARAM_EXCEPTION);
        }
        TeaCourse one = teaCourseRepository.findOne(courseId);
        if(null == one){
            log.error("【结束课程】 课程没有找到");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }
        Integer vis = one.getCourseStatus();
        //当前课程只有是处于互动状态或者线下互动才能使用此方式结束课程
        if(vis.equals(CourseEnum.COURSE_INTERACT.getCode()) || one.getCourseInteractive().equals(0) ){
            one.setCourseStatus(CourseEnum.COURSE_FINISH.getCode());
            one.setUpdateTime(new Date());
            TeaCourse save = teaCourseRepository.save(one);
            if(null == save ){
                log.error("【结束课程】 修改课程状态异常");
                throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION);
            }
            return save;
        } else {
            log.error("【结束课程】 课程状态不正确");
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION);
        }
    }
}
