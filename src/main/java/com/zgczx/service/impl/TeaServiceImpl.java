package com.zgczx.service.impl;

import com.zgczx.config.MQConfig;
import com.zgczx.dataobject.*;
import com.zgczx.dto.CourseDTO;
import com.zgczx.dto.PushMessageDTO;
import com.zgczx.dto.StuBaseDTO;
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
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String info;
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
        Date strCourseStartTime = teaCourseForm.getCourseStartTime();
        Date strCourseEndTime = teaCourseForm.getCourseEndTime();
        Date strCourseDate = teaCourseForm.getCourseDate();
        Date strToday = new Date();

        //转化为Calendar类型比较日期
        Calendar courseStartTime = Calendar.getInstance();
        courseStartTime.setTime(strCourseStartTime);
        Calendar courseEndTime = Calendar.getInstance();
        courseEndTime.setTime(strCourseEndTime);
        Calendar courseDate = Calendar.getInstance();
        courseDate.setTime(strCourseDate);
        Calendar today = Calendar.getInstance();
        today.setTime(strToday);

        //1.上课开始时间不得早于或等于当前时间
        if(courseStartTime.before(today)){
            info = "【教师创建课程】 上课开始时间不得早于或等于当前时间";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        //2.结束时间不得早于开始时间
        if(courseEndTime.before(courseStartTime)){
            info = "【教师创建课程】 上课结束时间不得早于或等于上课开始时间";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        //3.上课日期不能小于当前日期
        Calendar yesterday = Calendar.getInstance();
        yesterday.setTime(new Date());
        yesterday.add(Calendar.DAY_OF_MONTH,-1);
        log.info("yesterday="+yesterday.getTime());

        if(courseDate.before(yesterday)){
            info = "【教师创建课程】 上课日期不得早于当前时间";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        TeaBase teaBase = teaBaseRepository.findByTeaOpenid(teaOpenid);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(CourseEnum.COURSE_FINISH.getCode());
        list.add(CourseEnum.COURSE_CANCELED.getCode());
        List<TeaCourse> byTeaCode = teaCourseRepository
                .findByTeaCodeAndCourseStatusNotIn(teaBase.getTeaCode(),list);

        for(TeaCourse teaCourse : byTeaCode){
            //后部交叉和新建课程包含已有课程都会拦截
            if (courseStartTime.getTime().before(teaCourse.getCourseStartTime()) &&
                    //courseStartTime.getTime().before(teaCourse.getCourseEndTime()) &&
                    courseEndTime.getTime().after(teaCourse.getCourseStartTime())){
                info = "【教师创建课程】 新课程开始时间早于已有课程的开始时间且" +
                        //"新课程的结束时间早于课程的结束时间" +
                        "且新课程的结束时间晚于已有课程的开始时间" +
                        //"即新课程与已有课程前部交叉"
                        ",既新课程与已有课程后部交叉或新课程包含已有课程";
                log.error(info);
                throw new SdcException(ResultEnum.COURSE_CONFLICT,info);
            }
            if(courseStartTime.getTime().after(teaCourse.getCourseStartTime()) &&
                    courseEndTime.getTime().before(teaCourse.getCourseEndTime())){
                info = "【教师创建课程】 新课程开始时间晚于已有课程的开始时间且新课程的结束时间早于已有课程的结束时间";
                log.error(info);
                throw new SdcException(ResultEnum.COURSE_CONFLICT,info);
            }
            if(courseStartTime.getTime().after(teaCourse.getCourseStartTime())
                    && courseEndTime.getTime().after(teaCourse.getCourseEndTime())
                    && courseStartTime.getTime().before(teaCourse.getCourseEndTime())){
                info = "【教师创建课程】 新课程开始时间晚于已有课程的开始时间且新课程的结束时间晚于已有课程的结束时间" +
                        "新课程的开始时间早于已有课程的结束时间";
                log.error(info);
                throw new SdcException(ResultEnum.COURSE_CONFLICT,info);
            }
            int startTime = courseStartTime.getTime().compareTo(teaCourse.getCourseStartTime());
            int endTime = courseEndTime.getTime().compareTo(teaCourse.getCourseEndTime());
            if(startTime == 0){
                info = "【教师创建课程】 新课程开始时间等于已有课程的开始时间";
                log.error(info);
                throw new SdcException(ResultEnum.COURSE_CONFLICT,info);
            }
            if(endTime == 0){
                info = "【教师创建课程】 新课程结束时间等于已有课程的结束时间";
                log.error(info);
                throw new SdcException(ResultEnum.COURSE_CONFLICT,info);
            }
        }
        TeaCourse teaCourse = new TeaCourse();
        //由于前台时间传递的是String不能直接使用modelMapper转化
        teaCourse.setTeaCode(teaCourseForm.getTeaCode());
        teaCourse.setCourseDate(strCourseDate);
        teaCourse.setCourseStartTime(strCourseStartTime);
        teaCourse.setCourseEndTime(strCourseEndTime);
        teaCourse.setCourseLocation(teaCourseForm.getCourseLocation());
        teaCourse.setCourseName(teaCourseForm.getCourseName());
        teaCourse.setCourseStatus(CourseEnum.SUB_WAIT.getCode());
        teaCourse.setCourseInteractive(teaCourseForm.getCourseInteractive());
        teaCourse.setCourseLocation(teaCourseForm.getCourseLocation());
        teaCourse.setCourseType(teaCourseForm.getCourseType());

        TeaCourse save = teaCourseRepository.save(teaCourse);
        if(null == save){
            info = "【教师创建课程】 课程信息插入数据库出现异常";
            log.error(info);
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
        }
        //当课程创建成功之后，将课程Id放入MQ中,目前没有设置回调函数
        String courseId = save.getCourseId().toString();
        //过期时间设置为当前时间距离课程开始时间的毫秒数
        Long dateout = courseStartTime.getTimeInMillis() - System.currentTimeMillis();
        System.out.println("过期时间为:"+ dateout+"转为分钟是："+ (dateout/(1000*60) ) );
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration(dateout.toString());
        messageProperties.setCorrelationId(UUID.randomUUID().toString().getBytes());
        Message message = new Message(courseId.getBytes(), messageProperties);
        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTINGKEY1,message);
        return save;
    }


    /**
     *教师取消课程
     *
     * @param courseId 课程id
     * @param cancelReason 课程取消原因
     * @return 取消的课程信息
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public TeaCourse cancelCourse(Integer courseId,String teaOpenid,String cancelReason) {

        // 取消课程之前需要判断当前课程是否为该老师的课程
        TeaBase teaBase = teaBaseRepository.findByTeaOpenid(teaOpenid);

        TeaCourse teaCourse = teaCourseRepository.findByTeaCodeAndCourseId(teaBase.getTeaCode(),
                courseId);
        if(null == teaCourse){
            info = "【教师取消课程】 该课程和您当前身份不匹配，取消课程失败";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        //只有当前课程为等待预约和已被预约的情况下才能取消，课程进行时的取消应当属于提前结束课程
        Integer status = teaCourse.getCourseStatus();
        if(status.intValue() == CourseEnum.SUB_WAIT.getCode() ||
                status.intValue() == CourseEnum.SUB_SUCCESS.getCode() ){
            teaCourse.setCourseCause(cancelReason);
            //如果当前已经有学生预约成功则给该学生发送消息通知
           /* if(status.intValue() == SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode()){
                pushMessageService.pushCancelCourseMessageToStu();
            }*/
            teaCourse.setCourseStatus(CourseEnum.COURSE_CANCELED.getCode());
        } else {
            info = "【教师取消课程】 课程状态不正确";
            log.error("【教师取消课程】 课程状态不正确，teaCourseId={},teaCourseIdStatus={}",
                    teaCourse.getCourseId(), teaCourse.getCourseStatus());
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        List<SubCourse> list  = subCourseRepository.findByCourseId(courseId);

        PushMessageDTO pushMessageDTO = new PushMessageDTO();
        pushMessageDTO.setTeaCourse(teaCourse);
        pushMessageDTO.setTeaBase(teaBase);

        for(SubCourse subCourseByID : list ){
            //修改预约成功学生的预约状态为教师取消课程，预约失败的学生状态依然是预约失败
            //对于处于预约等待的学生我们需要设置其状态为教师取消课程
            if(subCourseByID.getSubStatus().equals(SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode()) ||
                    subCourseByID.getSubStatus().equals(SubCourseEnum.SUB_WAIT.getCode())   ) {
                subCourseByID.setSubStatus(SubCourseEnum.TEA_CANCEL_SUB.getCode());
                SubCourse save = subCourseRepository.save(subCourseByID);
                if (null == save) {
                    String info = "【取消课程】取消课程失败！";
                    log.error(info);
                    throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
                }
                //给学生推送微信消息
                //仅仅是对等待和预约成功的学生进行消息推送
                StuBase byStuCode = stuBaseRepository.findByStuCode(subCourseByID.getStuCode());
                pushMessageDTO.setStuBase(byStuCode);
                pushMessageService.pushCancelCourseMessageToStu(pushMessageDTO);
            }
        }
        TeaCourse save = teaCourseRepository.save(teaCourse);
        if(null == save) {
            info = "【取消课程】取消课程失败！";
            log.error(info);
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
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

        TeaBase teaBase = teaBaseRepository.findByTeaOpenid(teaOpenid);
        String teaCode = teaBase.getTeaCode();

        /*
         * 查询当前编号教师的所有课程记录
         */
        Pageable pageable = new PageRequest(page,pageSize);
        Page<TeaCourse> teaCoursePage = teaCourseRepository.findAllTeaCourse(teaCode, pageable);
        if(null == teaCoursePage){
            info = "【教师查看历史课程】 没有找到教师课程";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        List<CourseDTO> courseDTOList = new ArrayList<>();
        for(TeaCourse teaCourse : teaCoursePage){
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setTeaCourse(teaCourse);
            courseDTO.setTeaBase(teaBaseRepository.findOne(teaCourse.getTeaCode()));


            List<SubCourse> subCourseList = subCourseRepository.findByCourseIdAndSubStatus(teaCourse.getCourseId(),
                    SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode());
            if(subCourseList.size()>0){
                int subId = subCourseList.get(0).getSubId();
                FeedBack feedBack = feedBackRepository.findBySubId(subId);
                if(null != feedBack){
                    courseDTO.setFeedBack(feedBack);
                }else{
                    //没有创建反馈，则先创建一条记录，保证可以通过subId提交反馈
                    FeedBack addFeedBack = new FeedBack();
                    addFeedBack.setSubId(subId);
                    feedBackRepository.save(addFeedBack);
                    courseDTO.setFeedBack(addFeedBack);
                }
            }

            //更新课程状态
            finishCourse(teaCourse.getCourseId());

            courseDTOList.add(courseDTO);
        }
        return courseDTOList;
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
    public List<StuBaseDTO> findCandidateByCourseId(Integer courserId, String teaOpenid, int page, int pageSize){
        TeaBase teaBase = teaBaseRepository.findByTeaOpenid(teaOpenid);
        //判断该教师是否有该课程
        TeaCourse teaCourse = teaCourseRepository.findByTeaCodeAndCourseId(teaBase.getTeaCode(),courserId);
        if(null == teaCourse){
            info ="【教师查看预约候选人】 该课程和您当前身份不匹配，不能查看课程预约候选人";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }

        //设置分页参数
        Pageable pageable = new PageRequest(page, pageSize);
        //查找提交预约请求的候选人
        List<SubCourse> allCandidate = subCourseRepository.findByCourseId(courserId, pageable);
        List<StuBaseDTO > stuBaseDTOList = new ArrayList<>();

        for(SubCourse candidate : allCandidate){
            String stuCode = candidate.getStuCode();
            Integer subStatus = candidate.getSubStatus();
            StuBase stuBaseInfo = stuBaseRepository.findByStuCode(stuCode);

            //封装stuBaseDTO
            StuBaseDTO stuBaseDTO = new StuBaseDTO(subStatus,stuBaseInfo);
            stuBaseDTOList.add(stuBaseDTO);
        }
        return stuBaseDTOList;
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

        //1.通过学生微信id来进行获取学生编号
        StuBase byStuOpenid = stuBaseRepository.findByStuOpenid(stuOpenId);
        String stuCode = byStuOpenid.getStuCode();
        //2.若存在已经预约成功的学生，且不为之前的预约成功的学生，则成功预约的转改为预约失败。
        List<SubCourse> subCourseSuccessUpdateList = subCourseRepository.findByCourseIdAndSubStatus(courseId,
                SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode());
        //预约成功的候选人只可能有一个
        if(null != subCourseSuccessUpdateList && subCourseSuccessUpdateList.size() > 0){
            SubCourse subCourseSuccessUpdate = subCourseSuccessUpdateList.get(0);
            String stuCode1 = subCourseSuccessUpdate.getStuCode();
            //两次选择同一个成功预约候选人
            if(stuCode.equals(stuCode1)){
                return subCourseSuccessUpdate;
            }
            //修改原来预约成功候选人状态为失败
            subCourseSuccessUpdate.setSubStatus(SubCourseEnum.SUB_CANDIDATE_FAILED.getCode());
            SubCourse save = subCourseRepository.save(subCourseSuccessUpdate);
            if(null == save){
                info = "【教师选择候选预约学生】 替换原预约成功学生失败！";
                log.error(info);
                throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
            }
            //给原成功预约候选人发送预约被替代的模板消息
            TeaCourse tmpTeaCourse  = teaCourseRepository.findOne(courseId);
            String teaCode = tmpTeaCourse.getTeaCode();
            TeaBase useTeaBase = teaBaseRepository.findOne(teaCode);
            PushMessageDTO tmpCourseDTO = new PushMessageDTO();
            tmpCourseDTO.setTeaCourse(tmpTeaCourse);
            tmpCourseDTO.setTeaBase(useTeaBase);
            tmpCourseDTO.setStuBase(stuBaseRepository.findByStuCode(stuCode1));
            //tmpCourseDTO.setStudentCode(stuCode1);
            pushMessageService.pushSubFailMessage(tmpCourseDTO);
        }

        //修改预约表中被成功选择的学生的学号
        List<Integer> list = new ArrayList<Integer>();
        list.add(SubCourseEnum.SUB_WAIT.getCode());
        list.add(SubCourseEnum.SUB_CANDIDATE_FAILED.getCode());
        SubCourse subCourse = subCourseRepository.findByStuCodeAndCourseIdAndSubStatusIn(stuCode,courseId,list);
        subCourse.setSubStatus(SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode());
        //查找课程表
        TeaCourse one = teaCourseRepository.findOne(courseId);

        //更新预约课程
        subCourse = subCourseRepository.save(subCourse);
        if(null == subCourse){
            info = "【教师选择候选预约学生】更新预约表失败";
            log.error(info);
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
        }

        //给学生推送预约成功的模板消息
        PushMessageDTO pushMessageDTO = new PushMessageDTO();
        pushMessageDTO.setTeaCourse(one);
        pushMessageDTO.setStuBase(stuBaseRepository.findByStuCode(stuCode));
        TeaBase one1 = teaBaseRepository.findOne(one.getTeaCode());
        pushMessageDTO.setTeaBase(one1);
        pushMessageService.pushSubSuccessMessage(pushMessageDTO);

        //选择之后候选人之后 ，发送消息给没有入选的候选人提示预约失败
        List<SubCourse> byCourseIdAndSubStatus = subCourseRepository.findByCourseIdAndSubStatus(courseId,
                SubCourseEnum.SUB_WAIT.getCode());
        for(SubCourse subCourse1 : byCourseIdAndSubStatus){
            if(!subCourse1.getStuCode().equals(stuCode)){
                //将当前预约信息改成学生预约失效
                subCourse1.setSubStatus(SubCourseEnum.SUB_CANDIDATE_FAILED.getCode());
                SubCourse save = subCourseRepository.save(subCourse1);
                if(null == save){
                    info = "【教师选择候选预约学生】更新教师课程表失败";
                    log.error(info);
                    throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
                }
                //推送预约失败的模板消息
                String rsStuCode = subCourse1.getStuCode();
                pushMessageDTO.setStuBase(stuBaseRepository.findByStuCode(rsStuCode));
                pushMessageService.pushSubFailMessage(pushMessageDTO);
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
            info = "【教师给学生的反馈】 预约课程编号为空";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        if(null == feedBack || "".equals(feedBack) || null == score){
            info  = "【教师给学生的反馈】 课程反馈信息不为空";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        //通过预约课程信息获取课程信息
        SubCourse subCourse = subCourseRepository.findOne(subId);
        Integer courseId = subCourse.getCourseId();
        TeaBase byteaOpenid = teaBaseRepository.findByTeaOpenid(teaOpenid);
        TeaCourse byTeaCodeAndCourseId = teaCourseRepository.findByTeaCodeAndCourseId(byteaOpenid.getTeaCode(), courseId);
        if(null == byTeaCodeAndCourseId){
            info = "【教师给学生的反馈】 当前教师没有对应课程，提交课程反馈失败";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }

        List<SubCourse> subCourseList = subCourseRepository.findByCourseIdAndSubStatus(courseId,SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode());
        if(subCourseList.size()<1){
            info = "【教师给学生的反馈】 找不到对应的预约课程";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        //如果当前课程状态是课程交互状态或者上课状态为线下，则可以提交反馈结果
        //其中判断状态在finishCourse方法中已经处理了
        TeaCourse one = teaCourseRepository.findOne(subCourse.getCourseId());
        if(null == one){
            info = "【教师给学生的反馈】 该课程编号不正确";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
//        //修改课程表中课程状态为结束
//        finishCourse(one.getCourseId());

        FeedBack proFeedBack = feedBackRepository.findBySubId(subId);
        //限定反馈只能提交一次
        if(proFeedBack!=null && proFeedBack.getTeaScore() != null  && proFeedBack.getTeaFeedback() != null){
            info = "【教师给学生的反馈】 反馈只能提交一次";
            log.error(info);
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
        }

        //若反馈没有被创建，则创建
        if(null == proFeedBack){
            proFeedBack = new FeedBack();
            proFeedBack.setSubId(subId);
            proFeedBack.setTeaFeedback(feedBack);
            proFeedBack.setTeaScore(score);

            FeedBack rsFeedBack = feedBackRepository.save(proFeedBack);
            //推送反馈给学生
            PushMessageDTO pushMessageDTO= new PushMessageDTO();
            pushMessageDTO.setTeaCourse(one);
            pushMessageDTO.setFeedBack(proFeedBack);
            pushMessageDTO.setTeaBase(byteaOpenid);
            StuBase byStuCode = stuBaseRepository.findByStuCode(subCourseList.get(0).getStuCode());
            pushMessageDTO.setStuBase(byStuCode);
            //教师给学生发送反馈
            pushMessageService.pushFeedBackMessageToStu(pushMessageDTO);
            return rsFeedBack;
        }else{
            proFeedBack.setTeaFeedback(feedBack);
            proFeedBack.setTeaScore(score);

            //推送反馈给学生
            PushMessageDTO pushMessageDTO= new PushMessageDTO();
            pushMessageDTO.setTeaCourse(one);
            pushMessageDTO.setFeedBack(proFeedBack);
            pushMessageDTO.setTeaBase(byteaOpenid);
            StuBase byStuCode = stuBaseRepository.findByStuCode(subCourseList.get(0).getStuCode());
            pushMessageDTO.setStuBase(byStuCode);
            //教师给学生发送反馈
            pushMessageService.pushFeedBackMessageToStu(pushMessageDTO);
            return feedBackRepository.save(proFeedBack);
        }

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
            info  = "【修改课程表的状态】 该课程信息为空";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }

        if(!teaCourse.getCourseStatus().equals(CourseEnum.SUB_WAIT.getCode())){
            info = "【修改课程表的状态】 该课程的状态不能被修改";
            log.error("【修改课程表的状态】 该课程的状态status={}不能被修改",teaCourse.getCourseStatus());
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
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
            info  = "【查看课程课程信息】 该课程编号为空";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        TeaCourse one = teaCourseRepository.findOne(courseId);

        if(null == one){
            info = "【查看课程信息】 该课程没有找到";
            log.error("【查看课程id={}的课程信息】 该课程没有找到",one);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
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
            info = "【结束课程】 该课程编号为空";
            log.error("【结束课程】 该课程编号id={}为空");
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        TeaCourse teaCourse = teaCourseRepository.findOne(courseId);
        if(null == teaCourse){
            info  = "【结束课程】 课程没有找到";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        Integer courseStatus = teaCourse.getCourseStatus();

        Date now = new Date();

        //1.当课程状态为预约等待时，若当前时间大于课程开始时间，课程状态改为课程失效，防止创建聊天室。
        //2.当课程状态为预约成功时，若当前时间在课程进行中时，课程状态改为正在进行;
        //                     若当前时间晚于课程结束时间，课程状态改为正常结束。
        //3.当课程状态为正在进行时，若当前时间晚于课程结束时间，课程状态改为正常结束。
        /*1 当课程状态为预约等待*/
        if(courseStatus.equals(CourseEnum.SUB_WAIT.getCode())){
            if(now.after(teaCourse.getCourseStartTime())){
                teaCourse.setCourseStatus(CourseEnum.COURSE_CANCELED.getCode());
                TeaCourse saveTeaCourse = teaCourseRepository.save(teaCourse);
                if(null == saveTeaCourse ){
                    info = "【结束课程】 当课程状态为预约等待时，若当前时间大于课程开始时间，课程状态改为课程失效，防止创建聊天室。";
                    log.error(info);
                    throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
                }
                return saveTeaCourse;
            }
        }
        /*2 当课程状态为预约成功*/
        if(courseStatus.equals(CourseEnum.SUB_SUCCESS.getCode())){
            if(now.after(teaCourse.getCourseStartTime())){
                if(now.before(teaCourse.getCourseEndTime())){
                    teaCourse.setCourseStatus(CourseEnum.COURSE_INTERACT.getCode());
                    TeaCourse saveTeaCourse = teaCourseRepository.save(teaCourse);
                    if(null == saveTeaCourse ){
                        info = "【结束课程】 当课程状态为预约成功时，若当前时间在课程进行中时，课程状态改为正在进行;";
                        log.error(info);
                        throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
                    }
                    return saveTeaCourse;

                }else{
                    teaCourse.setCourseStatus(CourseEnum.COURSE_FINISH.getCode());
                    TeaCourse saveTeaCourse = teaCourseRepository.save(teaCourse);
                    if(null == saveTeaCourse ){
                        info = "【结束课程】 当课程状态为预约成功时，若当前时间晚于课程结束时间，课程状态改为正常结束";
                        log.error(info);
                        throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
                    }
                    return saveTeaCourse;
                }
            }
        }
        /*3 当课程状态为正在进行*/
        if(courseStatus.equals(CourseEnum.COURSE_INTERACT.getCode())){
            if(now.after(teaCourse.getCourseEndTime())){
                teaCourse.setCourseStatus(CourseEnum.COURSE_FINISH.getCode());
                TeaCourse saveTeaCourse = teaCourseRepository.save(teaCourse);
                if(null == saveTeaCourse ){
                    info = "【结束课程】 当课程状态为正在进行时，若当前时间晚于课程结束时间，课程状态改为正常结束。";
                    log.error(info);
                    throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
                }
                return saveTeaCourse;
            }
        }
        return null;

//        if(courseStatus.equals(CourseEnum.SUB_WAIT.getCode()) || courseStatus.equals(CourseEnum.SUB_SUCCESS.getCode())){
//            if(teaCourse.getCourseStartTime().before(now) && teaCourse.getCourseEndTime().after(now)){
//                teaCourse.setCourseStatus(CourseEnum.COURSE_INTERACT.getCode());
//                return teaCourseRepository.save(teaCourse);
//            }
//        }
//        if(courseStatus.equals(CourseEnum.SUB_SUCCESS.getCode()) && teaCourse.getCourseStartTime().before(now)){
//            teaCourse.setCourseStatus(CourseEnum.COURSE_INTERACT.getCode());
//            teaCourseRepository.save(teaCourse);
//        }
//        //当前课程只有是处于进行状态或者线下互动才能使用此方式结束课程
//        if(courseStatus.equals(CourseEnum.COURSE_INTERACT.getCode()) ||
//                teaCourse.getCourseInteractive().equals(CourseService.COURSEINTERACTIVE_OFFLINE) ){
//            if(teaCourse.getCourseEndTime().before(now)){
//                teaCourse.setCourseStatus(CourseEnum.COURSE_FINISH.getCode());
//                TeaCourse save = teaCourseRepository.save(teaCourse);
//                if(null == save ){
//                    info = "【结束课程】 修改课程状态异常";
//                    log.error(info);
//                    throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
//                }
//                return save;
//            }
//        }
//        // 若没人刷新，且时间到了则直接结束
//        if(teaCourse.getCourseEndTime().before(now)){
//            teaCourse.setCourseStatus(CourseEnum.COURSE_FINISH.getCode());
//            return teaCourseRepository.save(teaCourse);
//        }
    }

    /**
     * 判断该teaOpenid是否合法存在
     * @param teaOpenid 教师openid
     * @return Boolean
     */
    @Override
    public Boolean legalTeacher(String teaOpenid) {

        TeaBase teaBase = teaBaseRepository.findByTeaOpenid(teaOpenid);
        return teaBase != null;

    }
}
