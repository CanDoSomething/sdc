package com.zgczx.controller;

import com.zgczx.VO.ResultVO;
import com.zgczx.dataobject.OnlineCourse;
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
    public ResultVO getOnlineCourseGroupId(@RequestParam(value = "useropenid")String useropenid,
                                           @RequestParam(value = "courseId")Integer courseId){

        //1.判断该useropenid是否为该课程的参与者
        Boolean onCourse = courseService.onCourse(useropenid,courseId);

        if(onCourse.equals(false)){
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,"该user不是该课程的参与者");
        }

        //2.返回群组id
        OnlineCourse onlineCourse = courseService.getOnlineCourseGroupId(courseId);

        return ResultVOUtil.success(onlineCourse.getGroupId());
    }


}
