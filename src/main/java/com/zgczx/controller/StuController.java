package com.zgczx.controller;

import com.zgczx.VO.ResultVO;
import com.zgczx.dataobject.StuFeedBack;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.repository.SubCourseRepository;
import com.zgczx.service.impl.StuServiceImpl;
import com.zgczx.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Auther: 陈志恒
 * @Date: 2018/12/11 10:29
 * @Description:学生控制器界面
 */
@RequestMapping("/stu")
@Controller
public class StuController {
    @Autowired
    private StuServiceImpl stuService;
    @Autowired
    private SubCourseRepository subCourseRepository;
    /**
     *
     * 功能描述: 查找所有课程信息
     *
     * @param: page分页，size页面大小
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 18:41
     */
    @GetMapping(value = "/findallCourse")
    @ResponseBody
    public ResultVO findAllCourse(@RequestParam(value = "page", defaultValue = "0") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size){
        /*去stuService中查找所有课程信息*/
        List<CourseDTO> allCourse = stuService.findAllCourse(page,size);
        /*返回结果集*/
        return ResultVOUtil.success(allCourse);
    }
    /**
     *
     * 功能描述: 预约课程
     *
     * @param:stucode学生编号，courserId课程编号
     * @return: ResultVO
     * @auther: 陈志恒
     * @date: 2018/12/16 18:41
     */
    @GetMapping(value = "/order")
    @ResponseBody
    public ResultVO getorder(@RequestParam(value = "stuCode")String stuCode,
                          @RequestParam(value = "courserId")Integer courserId){
        SubCourse order = stuService.order(stuCode, courserId);
        /*返回结果集*/
        return ResultVOUtil.success(order) ;
    }
    /**
     *
     * 功能描述:在没有预约成功情况下取消预约课程
     *
     * @param: stucode学生编码，courserId课程信息
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 18:43
     */
    @GetMapping(value = "/cancelOrder")
    @ResponseBody
    public ResultVO cancelOrder(@RequestParam(value = "stuCode")String stuCode,
                            @RequestParam(value = "courserId")Integer courserId){
        SubCourse simplecancelorder = stuService.simplecancelorder(stuCode, courserId);
        return ResultVOUtil.success(simplecancelorder);
    }
    /**
     *
     * 功能描述: 在已预约成功状态下取消预约课程
     *
     * @param: cause取消预约原因，courserId课程id,stuCode学生编码
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 18:43
     */
    @PostMapping(value = "/cancelOrder")
    @ResponseBody
    public ResultVO cancelOrder2(@RequestParam(value = "cause")String cause,
                                 @RequestParam(value = "stuCode")String stuCode,
                                 @RequestParam(value = "courserId")Integer courserId){
        TeaCourse teaCourse = stuService.cancelorder(cause,stuCode, courserId);
        return ResultVOUtil.success(teaCourse);

    }
    /**
     *
     * 功能描述:学生反馈接口
     *
     * @param: courserId 课程id，message反馈信息，学生向老师打分
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 18:56
     */
    @PostMapping(value = "/feedback")
    @ResponseBody
    public ResultVO feedback(@RequestParam(value = "courserId")Integer courserId,
                         @RequestParam(value = "message")String message,
                         @RequestParam(value = "score")Integer score){
        StuFeedBack feedback = stuService.feedback(courserId, message, score);
       return ResultVOUtil.success(feedback);

    }
    /**
     *
     * 功能描述: 显示历史课程信息
     *
     * @param: page页面数，size页面大小，stucode学生编码
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 20:00
     */
    @GetMapping(value = "/lookhistory")
    @ResponseBody
    public ResultVO lookhistory(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "10") Integer size,
                                @RequestParam(value = "stucode") String stucode){
        List<CourseDTO> courseDTOSs = stuService.lookhistory(page,size, stucode);
        return ResultVOUtil.success(courseDTOSs);
    }
}
