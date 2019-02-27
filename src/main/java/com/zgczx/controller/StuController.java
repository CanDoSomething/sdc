package com.zgczx.controller;

import com.zgczx.VO.ResultVO;
import com.zgczx.dataobject.FeedBack;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.dto.SubDTO;
import com.zgczx.enums.ResultEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.service.CourseService;
import com.zgczx.service.impl.StuServiceImpl;
import com.zgczx.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生控制器界面
 *
 * @author  陈志恒
 * @date 2018/12/11 10:29
 */
@RequestMapping("/stu")
@Controller
@Slf4j
public class StuController {
    @Autowired
    private StuServiceImpl stuService;
    @Autowired
    private CourseService courseService;
    /**
     * 功能描述: 查找所有课程信息
     *
     * @param  page 分页，
     * @param size 页面大小
     * @return 展示可预约和进行中的课程
     */
    @GetMapping(value = "/findAllCourse")
    @ResponseBody
    public ResultVO findAllCourse(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size){

        List<CourseDTO> allCourse = stuService.findAllCourse(page - 1,size);

        return ResultVOUtil.success(allCourse);
    }
    /**
     * 功能描述: 预约课程
     *
     * @param stuOpenid 学生微信id
     * @param courserId 课程编号
     * @return ResultVO
     */
    @GetMapping(value = "/order")
    @ResponseBody
    public ResultVO getOrder(@RequestParam(value = "stuOpenid")String stuOpenid,
                          @RequestParam(value = "courserId")Integer courserId){

        /*
         * 根据stuOpenid和courserId判断是否合法
         * 比较学生的历史预约信息与目标课程的时间是否冲突
         * 保存预约请求，课程状态改为已被学生预约
         */

        //1.根据stuOpenid和courserId判断是否合法
        if(!stuService.legalStudent(stuOpenid)){
            String logInfo = "【预约课程】 【数据检验】stuOpenid 非法，stuOpenid="+stuOpenid;
            log.info(logInfo);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,logInfo);
        }
        if(!courseService.legalCourse(courserId)){
            String logInfo = "【预约课程】 【数据检验】courserId 非法，courserId="+courserId;
            log.info(logInfo);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,logInfo);
        }

        //2.提交预约请求
        SubCourse order = stuService.order(stuOpenid, courserId);

        return ResultVOUtil.success(order) ;
    }
    /**
     * 功能描述:取消预约课程
     *
     * @param cause 取消预约原因
     * @param courserId 课程id
     * @param stuOpenid 学生微信id
     */
    @PostMapping(value = "/cancelOrder")
    @ResponseBody
    public ResultVO cancelOrder(@RequestParam(value = "cause")String cause,
                                 @RequestParam(value = "stuOpenid")String stuOpenid,
                                 @RequestParam(value = "courserId")Integer courserId){

        //1.根据stuOpenid和courserId判断是否合法
        if(!stuService.legalStudent(stuOpenid)){
            String logInfo = "【预约课程】 【数据检验】stuOpenid 非法，stuOpenid="+stuOpenid;
            log.info(logInfo);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,logInfo);
        }
        if(!courseService.legalCourse(courserId)){
            String logInfo = "【预约课程】 【数据检验】courserId 非法，courserId="+courserId;
            log.info(logInfo);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,logInfo);
        }


        SubCourse subCourse = stuService.cancelOrder(cause, stuOpenid, courserId);
        return ResultVOUtil.success(subCourse);

    }
    /**
     * 功能描述:学生反馈接口
     *
     * @param courserId 课程id
     * @param message 反馈信息
     * @param score 学生向老师打分
     * @param subId 预约课程id
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
     * 功能描述: 显示历史课程信息
     *
     * @param page 页面数
     * @param size 页面大小
     * @param stuOpenid 学生微信id
     */
    @GetMapping(value = "/lookHistory")
    @ResponseBody
    public ResultVO lookHistory(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "size", defaultValue = "10") Integer size,
                                @RequestParam(value = "stuOpenid") String stuOpenid){

        /*
         * 根据 stuOpenid 检查是否合法存在
         * 查找该学生的所有预约请求，打包返回
         */

        if(!stuService.legalStudent(stuOpenid)){
            String logInfo = "【预约课程】 【数据检验】stuOpenid 非法，stuOpenid="+stuOpenid;
            log.info(logInfo);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,logInfo);
        }

        List<SubDTO> courseDTOSs = stuService.lookHistory(page-1, size, stuOpenid);
        return ResultVOUtil.success(courseDTOSs);
    }
}
