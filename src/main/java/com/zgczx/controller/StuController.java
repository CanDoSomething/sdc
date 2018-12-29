package com.zgczx.controller;

import com.zgczx.VO.ResultVO;
import com.zgczx.dataobject.FeedBack;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.dto.SubDTO;
import com.zgczx.service.impl.StuServiceImpl;
import com.zgczx.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author: 陈志恒
 * @Date: 2018/12/11 10:29
 * @Description:学生控制器界面
 */
@RequestMapping("/stu")
@Controller
public class StuController {
    @Autowired
    private StuServiceImpl stuService;
    /**
     *
     * 功能描述: 查找所有课程信息
     *
     * @param: page分页，size页面大小
     * @return:
     * @author: 陈志恒
     * @date: 2018/12/16 18:41
     */
    @GetMapping(value = "/findAllCourse")
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
     * @param stuCode 学生编号
     * @param courserId 课程编号
     * @return: ResultVO
     * @author: 陈志恒
     * @date: 2018/12/16 18:41
     */
    @GetMapping(value = "/order")
    @ResponseBody
    public ResultVO getOrder(@RequestParam(value = "stuCode")String stuCode,
                          @RequestParam(value = "courserId")Integer courserId){
        SubCourse order = stuService.order(stuCode, courserId);
        /*返回结果集*/
        return ResultVOUtil.success(order) ;
    }
    /**
     *
     * 功能描述:取消预约课程
     *
     * @param cause 取消预约原因
     * @param courserId 课程id
     * @param stuCode 学生编码
     * @return:
     * @author: 陈志恒
     * @date: 2018/12/16 18:43
     */
    @PostMapping(value = "/cancelOrder")
    @ResponseBody
    public ResultVO cancelOrder(@RequestParam(value = "cause")String cause,
                                 @RequestParam(value = "stuCode")String stuCode,
                                 @RequestParam(value = "courserId")Integer courserId){
        SubCourse subCourse = stuService.cancelOrder(cause, stuCode, courserId);
        return ResultVOUtil.success(subCourse);

    }
    /**
     *
     * 功能描述:学生反馈接口
     *
     * @param courserId 课程id
     * @param message 反馈信息
     * @param score 学生向老师打分
     * @param subId 预约课程id
     * @return:
     * @author: 陈志恒
     * @date: 2018/12/16 18:56
     */
    @PostMapping(value = "/feedback")
    @ResponseBody
    public ResultVO feedback(@RequestParam(value = "courserId")Integer courserId,
                             @RequestParam(value = "message")String message,
                             @RequestParam(value = "score")Integer score,
                             @RequestParam(value = "subId")Integer subId){
        FeedBack feedBack = stuService.feedBack(courserId, message, score, subId);
        return ResultVOUtil.success(feedBack);

    }
    /**
     *
     * 功能描述: 显示历史课程信息
     *
     * @param page 页面数
     * @param size 页面大小
     * @param stuCode 学生编码
     * @return:
     * @author: 陈志恒
     * @date: 2018/12/16 20:00
     */
    @GetMapping(value = "/lookHistory")
    @ResponseBody
    public ResultVO lookHistory(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "10") Integer size,
                                @RequestParam(value = "stuCode") String stuCode){
        List<SubDTO> courseDTOSs = stuService.lookHistory(page,size, stuCode);
        return ResultVOUtil.success(courseDTOSs);
    }
}
