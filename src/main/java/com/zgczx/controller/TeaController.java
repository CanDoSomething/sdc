package com.zgczx.controller;

import com.zgczx.VO.ResultVO;
import com.zgczx.dataobject.FeedBack;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.dto.StuBaseDTO;
import com.zgczx.enums.ResultEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.form.TeaCourseForm;
import com.zgczx.service.CourseService;
import com.zgczx.service.StuService;
import com.zgczx.service.TeaService;
import com.zgczx.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Dqd
 * @Date: 2018/12/15 10:29
 * @Description:教师流程控制
 */


@Slf4j
@RestController
@RequestMapping("/tea")
public class TeaController {
    @Autowired
    private TeaService teaService;

    @Autowired
    private StuService stuService;

    @Autowired
    private CourseService courseService;
    private String info;

    /**
     * 创建课程
     *
     * @param teaCourseForm 教师课程表单信息
     * @param bindingResult 表单信息验证结果
     * @return  创建新创建课程的courseId
     */
    @PostMapping("/createCourse")
    public ResultVO createCourse(@Valid TeaCourseForm teaCourseForm,
                                            BindingResult bindingResult,
                                            @RequestParam("teaOpenid") String teaOpenid){
        //后台进行表单验证，若参数不正确抛出异常
        if(bindingResult.hasErrors()){
            info = "【教师课程填写】 参数不正确 ，"+ bindingResult.getFieldError().getDefaultMessage();
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }

        if(StringUtils.isEmpty(teaOpenid)){
            info = "【创建课程】 教师openid为空";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }

        //前台传递过来的参数封装之后插入课程中
        TeaCourse course = teaService.createCourse(teaCourseForm,teaOpenid);
        return ResultVOUtil.success(course);
    }

    /**
     * 查找课程候选人
     *
     * @param courserId 课程编号
     * @param teaOpenid 教师微信编号
     * @param page 当前页数
     * @param pageSize 一页所展示的数据条数
     * @return 当前所有候选人
     */
    @GetMapping("/findCandidatesByCourseId")
    public ResultVO findCandidatesByCourseId(@RequestParam(value = "courseId") Integer courserId,
                                             @RequestParam(value = "teaOpenid") String teaOpenid,
                                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        /*
         * 根据courseId和teaOpenid判断是否合法
         * 返回该课程的所有预约请求
         */

        //1.根据courseId和teaOpenid判断是否合法
        if(!teaService.legalTeacher(teaOpenid)){
            info = "【教师查看预约候选人】 【数据检验】teaOpenid 对应的教师非法存在，teaOpenid="+teaOpenid;
            log.info(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        if(!courseService.legalCourse(courserId)){
            String logInfo = "【预约课程】 【数据检验】courserId 非法，courserId="+courserId;
            log.info(logInfo);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,logInfo);
        }

        //2.返回该课程的所有预约请求
        List<StuBaseDTO> list = teaService.findCandidateByCourseId(courserId,teaOpenid, page - 1, pageSize);
        return ResultVOUtil.success(list);
    }

    /**
     * 查看教师历史
     *
     * @param teaOpenid 教师编号
     * @param page 当前页数
     * @param pageSize 一页所展示的数据条数
     * @return 展示当前老师的历史课程
     */
    @GetMapping("/findTeaHistoryCourse")
    public ResultVO findTeaHistoryCourse(@RequestParam(value = "teaOpenid") String teaOpenid,
                                                          @RequestParam(value = "page", defaultValue = "1") int page,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        /* 根据teaOpenid判断是否合法存在
         * 找到老师的所有课程，如果存在反馈，加上反馈信息
         */
        if(StringUtils.isEmpty(teaOpenid)){
            info = "【教师查看历史课程】 教师微信编号为空";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        if(!teaService.legalTeacher(teaOpenid)){
            info = "【教师查看预约候选人】 【数据检验】teaOpenid 对应的教师非法存在，teaOpenid="+teaOpenid;
            log.info(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }

        List<CourseDTO> list = teaService.findTeaHistoryCourse(teaOpenid, page-1, pageSize);
        return ResultVOUtil.success(list);

    }

    /**
     * 教师取消课程
     *
     * @param courseId 课程编号
     * @return 取消的课程状态
     */
    @PostMapping("/cancelCourse")
    public ResultVO cancelCourse(@RequestParam(value = "courseId")Integer courseId,
                                            @RequestParam(value = "teaOpenid") String teaOpenid,
                                            @RequestParam(value = "cancelReason") String cancelReason){
        TeaCourse teaCourse = teaService.cancelCourse(courseId,teaOpenid,cancelReason);
        /*
            增加给学生推送教师取消预约消息的功能
         */
        return ResultVOUtil.success(teaCourse);
    }

    /**
     * 确认候选人
     *
     * @param courseId 课程编号
     * @param stuOpenid 学生微信编号
     * @return 预定课程的信息
     */
    @PostMapping("/saveSelectedStu")
    public ResultVO saveSelectedStu(@RequestParam(value = "courseId") Integer courseId,
                                               @RequestParam(value = "stuOpenid") String stuOpenid){

        /*
         * 判断courseId和stuOpenid是否合法
         * 根据课程找到是否已经存在预约成功的学生
         * 若存在，改为预约失败；若不存在，则该学生的预约状态改为预约成功，其他提交预约请求的学生都改为预约失败
         * 注意：假如有的请求改为预约失败，就不能再预约拉。此时来了新的预约请求，请求状态为提交请求，可以选择新的
         * 请求而替代原来的请求
         */

        //1.根据courseId和stuOpenid判断是否合法
        if(stuOpenid == null || "".equals(stuOpenid)){
            info = "【教师选择候选预约学生】 学生编号为空";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        if(courseId == null ){
            info = "【教师选择候选预约学生】 课程编号为空";
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        if(!stuService.legalStudent(stuOpenid)){
            String logInfo = "【预约课程】 【数据检验】stuOpenid 非法，stuOpenid="+stuOpenid;
            log.info(logInfo);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,logInfo);
        }

        // 2.确认候选人
        SubCourse subCourse = teaService.saveSelectedStu(stuOpenid, courseId);
        return ResultVOUtil.success(subCourse);
    }

    /**
     * 教师创建反馈
     *
     * @param subId 预约课程编号
     * @param teaOpenid 教师微信编号
     * @param teaFeedBack 教师反馈内容
     * @param score 教师给学生的打分
     * @return 反馈课程信息
     */
    @PostMapping("/createFeedBack")
    public ResultVO createFeedBack(@RequestParam("subId") Integer subId,
                                             @RequestParam("teaOpenid") String teaOpenid,
                                             @RequestParam("teaFeedBack") String teaFeedBack,
                                             @RequestParam("score")Integer score){
        FeedBack feedBack = teaService.saveFeedBack(subId,teaOpenid, teaFeedBack, score);
        return ResultVOUtil.success(feedBack);
    }

    /**
     * 教师修改课程信息
     *
     * @param teaCourse 课程信息
     * @param bindingResult spring必填参数验证对象
     * @return 修改之后的课程信息
     */
    @PostMapping("/saveUpdateTeaCourse")
    public ResultVO<TeaCourse> saveUpdateTeaCourse(TeaCourse teaCourse ,BindingResult bindingResult){
        //后台进行表单验证，若参数不正确抛出异常
        if(bindingResult.hasErrors()){
            info = "【教师修改课程信息】 参数不正确 ，"+teaCourse;
            log.error(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION ,info);
        }
        TeaCourse teaCourse1 = teaService.saveUpdateTeaCourse(teaCourse);
        return ResultVOUtil.success(teaCourse1);
    }

    /**
     * 查找单个课程
     *
     * @param courseId 课程编号
     * @return 通过课程编号查找到的课程
     */
    @GetMapping("/findTeaCourseById")
    public ResultVO<TeaCourse> findTeaCourseById(Integer courseId){
        TeaCourse teaCourse = teaService.findTeaCourseById(courseId);
        return ResultVOUtil.success(teaCourse);
    }

    /**
     * 结束课程
     *
     * @param courseId 课程编号
     * @return 课程状态为结束的课程
     */
    @PostMapping("/finishCourse")
    public ResultVO<TeaCourse> finishCourse(Integer courseId) {
        TeaCourse teaCourse = teaService.finishCourse(courseId);
        return ResultVOUtil.success(teaCourse);
    }

}
