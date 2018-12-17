package com.zgczx.controller;

import com.zgczx.VO.ResultVO;
import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dataobject.TeaFeedBack;
import com.zgczx.dto.CourseDTO;
import com.zgczx.enums.ResultEnum;
import com.zgczx.exception.SellException;
import com.zgczx.service.TeaService;
import com.zgczx.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * @param teaCourse 前端封装传递过来的参数
     * @param bindingResult
     * @return  创建新创建课程的courseId
     */
    @PostMapping("/createCourse")
    public ResultVO<Map<String,Integer>> createCourse(@Valid TeaCourse teaCourse, BindingResult bindingResult){
        //后台进行表单验证，若参数不正确抛出异常
        if(bindingResult.hasErrors()){
            log.error("【教师课程填写】 参数不正确 ，teaCourse={}",teaCourse);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode() ,bindingResult.getFieldError().getDefaultMessage());
        }
        //前台传递过来的参数封装之后插入课程中

        TeaCourse course = teaService.createCourse(teaCourse);
        Map<String,Integer> map = new HashMap<>(2);
        map.put("courseId",course.getCourserId());
        return ResultVOUtil.success(map);
    }

    /**
     *
     * @param courserId 课程编号
     * @param page 当前页数
     * @param pageSize 一页所展示的数据条数
     * @return 当前所有候选人
     */
    @GetMapping("/findCandidatesByCourseId")
    public ResultVO<List<StuBase>> findCandidatesByCourseId(@RequestParam(value = "courseId") Integer courserId,
                                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        List<StuBase> list = teaService.findCandidateByCourseId(courserId, page, pageSize);
        return ResultVOUtil.success(list);
    }

    /**
     *
     * @param teaCode 教师编号
     * @param page 当前页数
     * @param pageSize 一页所展示的数据条数
     * @return 展示当前老师的历史课程
     */
    @GetMapping("/findTeaHistoryCourse")
    public ResultVO<List<CourseDTO>> findTeaHistoryCourse(String teaCode,
                                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        List<CourseDTO> list = teaService.findTeaHistoryCourse(teaCode, page, pageSize);
        return ResultVOUtil.success(list);

    }

    /**
     *
     * @param courseId 课程编号
     * @param teaCode 教师编号
     * @return 取消的课程状态
     */
    @PostMapping("/cancelCourse")
    public ResultVO<TeaCourse> cancelCourse(@RequestParam(value = "courseId")Integer courseId
            ,String teaCode,String cancelReason){
        TeaCourse teaCourse = teaService.cancelCourse(courseId, teaCode,cancelReason);
        return ResultVOUtil.success(teaCourse);
    }

    /**
     *
     * @param courseId 课程编号
     * @param stuCode 学生学籍号
     * @return 预定课程的信息
     */
    @PostMapping("/saveSelectedStu")
    public ResultVO<SubCourse> saveSelectedStu(@RequestParam(value = "courseId")Integer courseId,
                                               String stuCode){
        SubCourse subCourse = teaService.saveSelectedStu(stuCode, courseId);
        return ResultVOUtil.success(subCourse);
    }

    /**
     *
     * @param teaFeedBack 前端封装教师反馈内容
     * @return 反馈课程信息
     */
    @PostMapping("/createFeedBack")
    public ResultVO<TeaFeedBack> createFeedBack(@Valid TeaFeedBack teaFeedBack,BindingResult bindingResult){
        //后台进行表单验证，若参数不正确抛出异常
        if(bindingResult.hasErrors()){
            log.error("【教师反馈填写】 参数不正确 ，teaFeedBack={}",teaFeedBack);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode() ,bindingResult.getFieldError().getDefaultMessage());
        }
        TeaFeedBack feedBack = teaService.createFeedBack(teaFeedBack);
        return ResultVOUtil.success(feedBack);
    }
}
