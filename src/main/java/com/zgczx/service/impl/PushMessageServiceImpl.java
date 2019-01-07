package com.zgczx.service.impl;

import com.zgczx.config.PushMessageConfig;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.enums.ResultEnum;
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

import java.util.Arrays;
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
    /**
     * 推送给学生的预约课程模板状态消息
     *
     * @param courseDTO 封装的课程消息对象
     */
    @Override
    public void pushSubSuccessMessage(CourseDTO courseDTO) {
        WxMpTemplateMessage templateMessage
                = new WxMpTemplateMessage();
        //设置模板消息id
        templateMessage.setTemplateId(PushMessageConfig.SUB_SUCCESS_MESSAGE_ID);

        if(courseDTO == null ){
            log.error("【模板消息推送】课程或学生信息为空");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }
        //设置发送给用户的openid
        //String stuOpenID = stuBaseRepository.findOne(courseDTO.getStudentCode()).getStuOpenid();
        //templateMessage.setToUser(stuOpenID);
        //设置返回数据
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","预约课程成功！"),
                new WxMpTemplateData("keyword1",courseDTO.getTeaCourse().getCourseName()),
                new WxMpTemplateData("keyword2",courseDTO.getTeaBase().getTeaName()),
                new WxMpTemplateData("keyword3",courseDTO.getTeaCourse().getCourseStartTime().toString()),
                new WxMpTemplateData("keyword4",courseDTO.getTeaCourse().getCourseEndTime().toString()),
                //new WxMpTemplateData("keyword5",SubStatusEnum.SUB_SUCCESS.getCode().toString()),
                new WxMpTemplateData("keyword6",courseDTO.getTeaCourse().getCourseLocation() == null?"在线互动平台":courseDTO.getTeaCourse().getCourseLocation()),
                new WxMpTemplateData("remark","同学们上门不要迟到哦")

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
     * @param courseDTO
     */
    @Override
    public void pushSubFailMessage(CourseDTO courseDTO) {
        WxMpTemplateMessage templateMessage
                = new WxMpTemplateMessage();
        //设置模板消息id
        templateMessage.setTemplateId(PushMessageConfig.SUB_FAIL_MESSAGE_ID);

        if(courseDTO == null ){
            log.error("【模板消息推送】课程或学生信息为空");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }
        //设置发送给用户的openid
        //String stuOpenID = stuBaseRepository.findOne(courseDTO.getStudentCode()).getStuOpenid();
        //templateMessage.setToUser(stuOpenID);
        //设置返回数据
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","预约课程失败"),
                new WxMpTemplateData("keyword1",courseDTO.getTeaCourse().getCourseName()),
                new WxMpTemplateData("keyword2",courseDTO.getTeaBase().getTeaName()),
                new WxMpTemplateData("keyword3",courseDTO.getTeaCourse().getCourseStartTime().toString()),
                new WxMpTemplateData("keyword4",courseDTO.getTeaCourse().getCourseEndTime().toString()),
                //new WxMpTemplateData("keyword5",SubStatusEnum.SUB_SUCCESS.getCode().toString()),
                new WxMpTemplateData("keyword6",courseDTO.getTeaCourse().getCourseLocation() == null?"在线互动平台":courseDTO.getTeaCourse().getCourseLocation()),
                new WxMpTemplateData("remark","祝下次好运")
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
     * @param teaCourse 课程信息
     */
    @Override
    public void pushCancelCourseMessageToStu(TeaCourse teaCourse) {
        WxMpTemplateMessage templateMessage
                = new WxMpTemplateMessage();
        //设置模板消息id
        templateMessage.setTemplateId(PushMessageConfig.SUB_FAIL_MESSAGE_ID);

        if(teaCourse == null ){
            log.error("【模板消息推送】课程信息为空");
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION);
        }
        //设置发送给用户的openid
        String stuOpenID = "123";//stuBaseRepository.findOne(teaCourse.get).getStuOpenid();
        templateMessage.setToUser(stuOpenID);
        //设置返回数据
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","您预约成功的课程被老师取消"),
                new WxMpTemplateData("keyword1",teaCourse.getCourseName()),
                //new WxMpTemplateData("keyword2",courseDTO.getTeaBase().getTeaName()),
                //new WxMpTemplateData("keyword3",courseDTO.getCourseStartTime().toString()),
                //new WxMpTemplateData("keyword4",courseDTO.getCourseEndTime().toString()),
                //new WxMpTemplateData("keyword5",SubStatusEnum.SUB_SUCCESS.getCode().toString()),
                new WxMpTemplateData("keyword6",teaCourse.getCourseLocation())
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
        } catch (WxErrorException e){
            log.error("【预约课程状态模板消息】 发送消息失败,{}",e);
        }
    }

}
