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
    public ResultVO<List<StuBaseDTO>> findCandidatesByCourseId(@RequestParam(value = "courseId") Integer courserId,
                                                            @RequestParam(value = "teaOpenid") String teaOpenid,
                                                            @RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){

        if(courserId == null){
            info = "【教师查看预约候选人】 该课程编号为空";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }


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
    public ResultVO<List<CourseDTO>> findTeaHistoryCourse(@RequestParam(value = "teaOpenid") String teaOpenid,
                                                          @RequestParam(value = "page", defaultValue = "1") int page,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
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
    public ResultVO<TeaCourse> cancelCourse(@RequestParam(value = "courseId")Integer courseId,
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
    public ResultVO<SubCourse> saveSelectedStu(@RequestParam(value = "courseId")Integer courseId,
                                               @RequestParam(value = "stuOpenid") String stuOpenid){
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
