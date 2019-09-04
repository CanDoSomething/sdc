package com.zgczx.service.impl;

import com.zgczx.config.PushMessageConfig;
import com.zgczx.dto.PushMessageDTO;
import com.zgczx.enums.ResultEnum;
import com.zgczx.enums.SubCourseEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.StuBaseRepository;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    private StuBaseRepository stuBaseRepository;
    private String info = null;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
                new WxMpTemplateData("keyword2",SubCourseEnum.SUB_CANDIDATE_FAILED.getMessage()),
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
                new WxMpTemplateData("keyword2",SubCourseEnum.TEA_CANCEL_SUB.getMessage()),
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
                new WxMpTemplateData("keyword2",SubCourseEnum.STU_CANCEL_SUB.getMessage()),
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
}
