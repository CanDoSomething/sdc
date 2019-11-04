package com.zgczx.service.impl;

import com.zgczx.config.PushMessageConfig;
import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.PushMessageDTO;
import com.zgczx.enums.CourseEnum;
import com.zgczx.enums.ResultEnum;
import com.zgczx.enums.SubCourseEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.StuBaseRepository;
import com.zgczx.repository.SubCourseRepository;
import com.zgczx.repository.TeaCourseRepository;
import com.zgczx.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Dqd
 * @Date: 2018/12/18 18:05
 * @Description:
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private TeaCourseRepository teaCourseRepository;

    @Autowired
    private SubCourseRepository subCourseRepository;
    @Autowired
    private StuBaseRepository stuBaseRepository;
    private String info = null;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 推送给学生的预约课程模板状态消息
     *
     * @param pushMessageDTO 封装的课程消息对象
     */

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void pushSubSuccessMessage(PushMessageDTO pushMessageDTO) {
        WxMpTemplateMessage templateMessage
                = new WxMpTemplateMessage();
        //设置模板消息id
        templateMessage.setTemplateId(PushMessageConfig.SUBCOURSE_SUCCESS_MESSAGE_ID);

        if(pushMessageDTO == null ){
            info = "【预约课程状态模板消息】课程或学生信息为空";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        //设置发送给用户的openid
        templateMessage.setToUser(pushMessageDTO.getStuBase().getStuOpenid());
        //设置返回数据
        boolean empty = StringUtils.isEmpty(pushMessageDTO.getTeaCourse().getCourseLocation());
        String tmp =( empty == true ?"在线互动平台":pushMessageDTO.getTeaCourse().getCourseLocation());
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","课程名称为: "+ pushMessageDTO.getTeaCourse().getCourseName()),
                new WxMpTemplateData("keyword1",pushMessageDTO.getTeaBase().getTeaName()),
                //new WxMpTemplateData("keyword2","行了嘛"),
                new WxMpTemplateData("keyword3",tmp),
                new WxMpTemplateData("keyword4",pushMessageDTO.getTeaCourse().getCourseStartTime().toString()+"至"+pushMessageDTO.getTeaCourse().getCourseEndTime().toString()),
                //new WxMpTemplateData("keyword5", SubCourseEnum.SUB_CANDIDATE_SUCCESS.getMessage()),
                //new WxMpTemplateData("keyword6",tmp),
                new WxMpTemplateData("remark",pushMessageDTO.getTeaCourse().getCourseCause())
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
        } catch (WxErrorException e){
            log.error("【预约课程状态模板消息】 发送消息失败,{}",e);
        }
    }

    /**
     * 给预约失败的候选人发送消息
     *
     * @param pushMessageDTO
     */
    @Override
    public void pushSubFailMessage(PushMessageDTO pushMessageDTO) {
        WxMpTemplateMessage templateMessage
                = new WxMpTemplateMessage();
        //设置模板消息id
        templateMessage.setTemplateId(PushMessageConfig.SUBCOURSE_FAIL_OR_CANCEL_MESSAGE_ID);

        if(pushMessageDTO == null ){
            info = "【预约课程状态模板消息】课程或学生信息为空";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        //设置发送给用户的openid
        templateMessage.setToUser(pushMessageDTO.getStuBase().getStuOpenid());
        //设置返回数据
        boolean empty = StringUtils.isEmpty(pushMessageDTO.getTeaCourse().getCourseLocation());
        String tmp =( empty == true ?"在线互动平台":pushMessageDTO.getTeaCourse().getCourseLocation());
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","课程名称为: "+ pushMessageDTO.getTeaCourse().getCourseName()),
                //new WxMpTemplateData("keyword1",pushMessageDTO.getTeaCourse().getCourseName()),
                //new WxMpTemplateData("keyword2",pushMessageDTO.getTeaBase().getTeaName()),
                //new WxMpTemplateData("keyword3",pushMessageDTO.getTeaCourse().getCourseStartTime().toString()),
                //new WxMpTemplateData("keyword4",pushMessageDTO.getTeaCourse().getCourseEndTime().toString()),
                new WxMpTemplateData("keyword2", SubCourseEnum.SUB_CANDIDATE_FAILED.getMessage()),
                //new WxMpTemplateData("keyword6",tmp),
                new WxMpTemplateData("keyword3",pushMessageDTO.getTeaCourse().getCourseCause()),
                new WxMpTemplateData("remark","时间:" + pushMessageDTO.getTeaCourse().getCourseStartTime().toString()+"至"+pushMessageDTO.getTeaCourse().getCourseEndTime().toString())
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
        } catch (WxErrorException e){
            log.error("【预约课程状态模板消息】 发送消息失败,{}",e);
        }
    }

    /**
     * 给学生发送教师取消其预约成功的课程通知
     *
     * @param pushMessageDTO 课程信息
     */
    @Override
    public void pushCancelCourseMessageToStu(PushMessageDTO pushMessageDTO) {
        WxMpTemplateMessage templateMessage
                = new WxMpTemplateMessage();
        //设置模板消息id
        templateMessage.setTemplateId(PushMessageConfig.SUBCOURSE_FAIL_OR_CANCEL_MESSAGE_ID);

        if(pushMessageDTO == null ){
            info = "【取消课程模板消息】课程信息为空";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        //设置发送给用户的openid
        templateMessage.setToUser(pushMessageDTO.getStuBase().getStuOpenid());
        //设置返回数据
        boolean empty = StringUtils.isEmpty(pushMessageDTO.getTeaCourse().getCourseLocation());
        String tmp =( empty == true ?"在线互动平台":pushMessageDTO.getTeaCourse().getCourseLocation());
        List<WxMpTemplateData> data = Arrays.asList(
               /* new WxMpTemplateData("first","您预约成功的课程被老师取消"),
                new WxMpTemplateData("keyword1",pushMessageDTO.getTeaCourse().getCourseName()),
                new WxMpTemplateData("keyword2",pushMessageDTO.getTeaBase().getTeaName()),
                new WxMpTemplateData("keyword3",pushMessageDTO.getTeaCourse().getCourseStartTime().toString()),
                new WxMpTemplateData("keyword4",pushMessageDTO.getTeaCourse().getCourseEndTime().toString()),
                new WxMpTemplateData("keyword5",SubCourseEnum.TEA_CANCEL_SUB.getMessage()),
                new WxMpTemplateData("keyword6",tmp),
                new WxMpTemplateData("keyword7",pushMessageDTO.getTeaCourse().getCourseCause()),
                new WxMpTemplateData("remark","")*/

                new WxMpTemplateData("first","课程名称为: "+ pushMessageDTO.getTeaCourse().getCourseName()),
                //new WxMpTemplateData("keyword1",pushMessageDTO.getTeaCourse().getCourseName()),
                //new WxMpTemplateData("keyword2",pushMessageDTO.getTeaBase().getTeaName()),
                //new WxMpTemplateData("keyword3",pushMessageDTO.getTeaCourse().getCourseStartTime().toString()),
                //new WxMpTemplateData("keyword4",pushMessageDTO.getTeaCourse().getCourseEndTime().toString()),
                new WxMpTemplateData("keyword2", SubCourseEnum.TEA_CANCEL_SUB.getMessage()),
                //new WxMpTemplateData("keyword6",tmp),
                new WxMpTemplateData("keyword3",pushMessageDTO.getTeaCourse().getCourseCause()),
                new WxMpTemplateData("remark","时间:" + pushMessageDTO.getTeaCourse().getCourseStartTime().toString()+"至"+pushMessageDTO.getTeaCourse().getCourseEndTime().toString())
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
        } catch (WxErrorException e){
            info = "【取消课程模板消息】 发送消息失败";
            log.error(info);
            throw new SdcException(ResultEnum.WECHAT_MP_ERROR,info);
        }
    }

    @Override
    public void pushFeedBackMessageToStu(PushMessageDTO pushMessageDTO) {
        WxMpTemplateMessage templateMessage
                = new WxMpTemplateMessage();
        //设置模板消息id
        templateMessage.setTemplateId(PushMessageConfig.FEEDBACK_MESSAGE_ID);

        if(pushMessageDTO == null ){
            info = "【教师给学生反馈模板消息】课程信息为空";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        //设置发送给用户的openid
        templateMessage.setToUser(pushMessageDTO.getStuBase().getStuOpenid());
        System.out.println("发送消息给----》"+pushMessageDTO.getStuBase().getStuOpenid());
        //设置返回数据
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first",pushMessageDTO.getTeaCourse().getCourseName()),
                new WxMpTemplateData("keyword1",pushMessageDTO.getFeedBack().getTeaScore()+"分"),
                new WxMpTemplateData("keyword2",pushMessageDTO.getFeedBack().getTeaFeedback()),
                new WxMpTemplateData("keyword3",simpleDateFormat.format(new Date())),
                //new WxMpTemplateData("keyword3",pushMessageDTO.getTeaCourse().getCourseEndTime().toString()),
                //new WxMpTemplateData("keyword4",pushMessageDTO.getTeaBase().getTeaName()),
                new WxMpTemplateData("remark","教师:"+pushMessageDTO.getTeaBase().getTeaName())
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
        } catch (WxErrorException e){
            info = "【教师给学生反馈模板消息】 发送消息失败";
            log.error(info);
            throw new SdcException(ResultEnum.WECHAT_MP_ERROR,info);
        }
    }

    @Override
    public void pushCancelCourseMessageToTea(PushMessageDTO pushMessageDTO) {
        WxMpTemplateMessage templateMessage
                = new WxMpTemplateMessage();
        //设置模板消息id
        templateMessage.setTemplateId(PushMessageConfig.SUBCOURSE_FAIL_OR_CANCEL_MESSAGE_ID);
        if(pushMessageDTO == null ){
            info = "【学生取消教师课程模板消息】课程信息为空";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        templateMessage.setToUser(pushMessageDTO.getTeaBase().getTeaOpenid());
        //设置返回数据
        boolean empty = StringUtils.isEmpty(pushMessageDTO.getTeaCourse().getCourseLocation());
        String tmp =( empty == true ?"在线互动平台":pushMessageDTO.getTeaCourse().getCourseLocation());
        List<WxMpTemplateData> data = Arrays.asList(
                /*new WxMpTemplateData("first","成功预约候选人取消参与当前课程"),
                new WxMpTemplateData("keyword1",pushMessageDTO.getTeaCourse().getCourseName()),
                new WxMpTemplateData("keyword2",pushMessageDTO.getTeaBase().getTeaName()),
                new WxMpTemplateData("keyword3",pushMessageDTO.getTeaCourse().getCourseStartTime().toString()),
                new WxMpTemplateData("keyword4",pushMessageDTO.getTeaCourse().getCourseEndTime().toString()),
                new WxMpTemplateData("keyword5",SubCourseEnum.STU_CANCEL_SUB.getMessage()),
                new WxMpTemplateData("keyword6",tmp),
                new WxMpTemplateData("keyword7",pushMessageDTO.getTeaCourse().getCourseCause()),
                new WxMpTemplateData("remark","")*/
                new WxMpTemplateData("first","课程名称为: "+ pushMessageDTO.getTeaCourse().getCourseName()),
                //new WxMpTemplateData("keyword1",pushMessageDTO.getTeaCourse().getCourseName()),
                //new WxMpTemplateData("keyword2",pushMessageDTO.getTeaBase().getTeaName()),
                //new WxMpTemplateData("keyword3",pushMessageDTO.getTeaCourse().getCourseStartTime().toString()),
                //new WxMpTemplateData("keyword4",pushMessageDTO.getTeaCourse().getCourseEndTime().toString()),
                new WxMpTemplateData("keyword2", SubCourseEnum.STU_CANCEL_SUB.getMessage()),
                //new WxMpTemplateData("keyword6",tmp),
                new WxMpTemplateData("keyword3",pushMessageDTO.getTeaCourse().getCourseCause()),
                new WxMpTemplateData("remark","时间:" + pushMessageDTO.getTeaCourse().getCourseStartTime().toString()+"至"+pushMessageDTO.getTeaCourse().getCourseEndTime().toString())

        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
        } catch (WxErrorException e){
            info = "【学生取消教师课程模板消息】 发送消息失败";
            log.error(info);
            throw new SdcException(ResultEnum.WECHAT_MP_ERROR,info);
        }
    }

    @Override
    public void pushFeedBackMessageToTea(PushMessageDTO pushMessageDTO) {
        WxMpTemplateMessage templateMessage
                = new WxMpTemplateMessage();
        //设置模板消息id
        templateMessage.setTemplateId(PushMessageConfig.FEEDBACK_MESSAGE_ID);

        if(pushMessageDTO == null ){
            info = "【学生给教师课程反馈模板消息】课程信息为空";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        //设置发送给用户的openid
        templateMessage.setToUser(pushMessageDTO.getTeaBase().getTeaOpenid());
        System.out.println("发送消息给----》"+pushMessageDTO.getTeaBase().getTeaOpenid());
        //设置返回数据
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first",pushMessageDTO.getTeaCourse().getCourseName()),
                new WxMpTemplateData("keyword1",pushMessageDTO.getFeedBack().getStuScore()+"分"),
                new WxMpTemplateData("keyword2",pushMessageDTO.getFeedBack().getStuFeedback()),
                new WxMpTemplateData("keyword3",simpleDateFormat.format(new Date())),
                //new WxMpTemplateData("keyword3",pushMessageDTO.getTeaCourse().getCourseEndTime().toString()),
                //new WxMpTemplateData("keyword4",pushMessageDTO.getTeaBase().getTeaName()),
                new WxMpTemplateData("remark","学生:"+pushMessageDTO.getStuBase().getStuName())
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
        } catch (WxErrorException e){
            info = "【学生给教师课程反馈模板消息】 发送消息失败";
            log.error(info);
            throw new SdcException(ResultEnum.WECHAT_MP_ERROR,info);
        }
    }

    /**
     * 学生预约消息推送给教师
     * @param pushMessageDTO
     */
    @Override
    public void pushSubcourseMessageToTea(PushMessageDTO pushMessageDTO) {
        WxMpTemplateMessage templateMessage
                = new WxMpTemplateMessage();
        //设置模板消息id
        templateMessage.setTemplateId(PushMessageConfig.PUSH_SUBCOURSE_MESSAGE);
        if(pushMessageDTO == null ){
            info = "【学生预约课程模板消息】学生预约教师课程";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        templateMessage.setToUser(pushMessageDTO.getTeaBase().getTeaOpenid());
        System.out.println("发给老师的消息--》" + pushMessageDTO.getTeaBase().getTeaOpenid());
        //设置返回数据
        List<WxMpTemplateData> data = Arrays.asList(
            new WxMpTemplateData("first","您有新的课程预约通知！ "),
            new WxMpTemplateData("keyword1",pushMessageDTO.getStuBase().getStuName()),
            new WxMpTemplateData("keyword2",simpleDateFormat.format(new Date())),
            new WxMpTemplateData("keyword3","预约了您的课程《" + pushMessageDTO.getTeaCourse().getCourseName() + "》"),
            new WxMpTemplateData("remark","请您及时在课程预约系统中‘我的课程’下的‘已预约’模块中选择最终上课的学生，否则学生不会收到课程预约成功通知！")
        );
        System.out.println("封装的信息：" + data);
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
        } catch (WxErrorException e){
            info = "【学生预约课程模板消息】 发送消息失败";
            log.error(info);
            throw new SdcException(ResultEnum.WECHAT_MP_ERROR,info);
        }
    }

    /**
     * 每天19：05执行 给所有明天有课的学生推送上课提示消息
     */
    @Override
    public void pushTipsToStu() {
        //获取所有被预约的教师课程信息

        List<TeaCourse> byCourseStatus1
                = teaCourseRepository.findByCourseStatus(CourseEnum.SUB_SUCCESS.getCode());
        //筛选出只有明天上课的课程
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); //得到前一天
        Date date = calendar.getTime();
        System.out.println("前一天为："+ date );
        List<TeaCourse> byCourseStatus = new ArrayList<>();
        String format;
        String day;
        for(TeaCourse teaCourse1 : byCourseStatus1 ){
            Date courseStartTime
                    = teaCourse1.getCourseStartTime();
            format = df.format(courseStartTime);
            day = df.format(date);
            System.out.println("课程日期 "  + format + "明天 日期" +day ) ;
            if(day.equals(format)){
                //获取到所有已经被学生预约的课程，并且课程是明天要开始的
                byCourseStatus.add(teaCourse1);
                System.out.println("过滤后的课程有：" + teaCourse1);
            }
        }


        WxMpTemplateMessage templateMessage
                = new WxMpTemplateMessage();
        //设置模板消息id
        templateMessage.setTemplateId(PushMessageConfig.START_COURSE_TIPS);


        for(TeaCourse teaCourse : byCourseStatus) {
            //查找当前课程在预约表中有哪些课程小节 同时查看学生预约了哪些课程
            List<SubCourse> byCourseId;
            Integer originId;
            if(teaCourse.getOriginId() == -1) {
                originId = teaCourse.getCourseId();
            } else {
                originId = teaCourse.getOriginId();
            }
            List<SubCourse> byCourseIdAndSubStatus
                    = subCourseRepository.findByCourseIdAndSubStatus(originId,
                    SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode());

            for(SubCourse subCourse : byCourseIdAndSubStatus){
                String stuCode = subCourse.getStuCode();
                TeaCourse tc = teaCourseRepository.findOne(subCourse.getCourseId());
                if(null == tc){
                    info = "【上课前一天给学生提示】没有找到对应课程！";
                    log.error(info);
                    throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
                }
                StuBase byStuCode = stuBaseRepository.findByStuCode(stuCode);
                if(null != byStuCode){
                    //封装推送信息，提示学生上课
                    templateMessage.setToUser(byStuCode.getStuOpenid());
                    System.out.println("发给学生的消息--》" + byStuCode.getStuOpenid());
                    //设置返回数据
                    List<WxMpTemplateData> data = Arrays.asList(
                            new WxMpTemplateData("first","您明天有预约的课程要上，请准时在预约地点上课"),
                            new WxMpTemplateData("keyword1",tc.getCourseName()),
                            new WxMpTemplateData("keyword2",simpleDateFormat.format(tc.getCourseStartTime())),
                            new WxMpTemplateData("keyword3",tc.getCourseInteractive() == 1?tc.getCourseLocation():"系统线上沟通"),
                            new WxMpTemplateData("remark","不要迟到哦O(∩_∩)O")
                    );
                    templateMessage.setData(data);
                    try {
                        wxMpService.getTemplateMsgService()
                                .sendTemplateMsg(templateMessage);
                    } catch (WxErrorException e){
                        info = "【上课前一天给学生提示】 发送消息失败";
                        log.error(info);
                        throw new SdcException(ResultEnum.WECHAT_MP_ERROR,info);
                    }

                }
            }


        }
    }
}
