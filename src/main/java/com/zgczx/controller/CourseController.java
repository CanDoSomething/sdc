package com.zgczx.controller;

import com.zgczx.VO.ResultVO;
import com.zgczx.dataobject.OnlineCourse;
import com.zgczx.dto.OnClassUserInfoDTO;
import com.zgczx.enums.ResultEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.service.CourseService;
import com.zgczx.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jason
 * @date 2019/2/20 13:34
 */
@RequestMapping("/course")
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("getOnlineCourseGroupId")
    public ResultVO getOnlineCourseGroupId(@RequestParam(value = "openid")String openid,
                                           @RequestParam(value = "courseId")Integer courseId){

        //1.判断该openid是否为该课程的参与者
        Boolean onCourse = courseService.onCourse(openid,courseId);

        if(onCourse.equals(false)){
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,"该user不是该课程的参与者");
        }

        //2.返回群组id
        OnlineCourse onlineCourse = courseService.getOnlineCourseGroupId(courseId);

        return ResultVOUtil.success(onlineCourse.getGroupId());
    }

    @GetMapping("getCountDown")
    public ResultVO getCountDown(@RequestParam(value = "courseId")Integer courseId){

        //1.返回课程结束的倒计时
        String countDown = courseService.getCountDown(courseId);

        return  ResultVOUtil.success(countDown);
    }

    @GetMapping("onCourseEnd")
    public ResultVO onCourseEnd(@RequestParam(value = "courseId")Integer courseId){

        //2.返回课程结束的倒计时
        Boolean onCourseEnd = courseService.onCourseEnd(courseId);

        return  ResultVOUtil.success(onCourseEnd);
    }

    @GetMapping("getOnClassUserOpenid")
    public ResultVO getOnClassUserOpenid(@RequestParam(value = "courseId") Integer courseId){

        OnClassUserInfoDTO onClassUserInfoDTO = courseService.getOnClassUserOpenid(courseId);

        return ResultVOUtil.success(onClassUserInfoDTO);
    }
}