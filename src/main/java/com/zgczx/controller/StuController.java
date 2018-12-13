package com.zgczx.controller;

import com.zgczx.VO.ResultVO;
import com.zgczx.dataobject.StuFeedBack;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.enums.SubStatusEnum;
import com.zgczx.repository.SubCourseRepository;
import com.zgczx.service.impl.StuServiceImpl;
import com.zgczx.utils.ResultVOUtil;
import com.zgczx.utils.SearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 陈志恒
 * @Date: 2018/12/11 10:29
 * @Description:学生控制器界面
 */
@Controller
public class StuController {
    @Autowired
    private StuServiceImpl stuService;
    @Autowired
    private SubCourseRepository subCourseRepository;
    /*显示所有课程信息*/
    @GetMapping(value = "/yuyue")
    public ResultVO findAllCourse(@RequestParam(value = "page", defaultValue = "0") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size){
        /*封装查询工具类*/
        SearchUtil searchUtil=new SearchUtil();
        searchUtil.setPage(page);
        searchUtil.setSize(size);
        /*设置排序字段*/
        searchUtil.setOrderBy("courseStartTime");
        /*设置查询字段*/
        List<String> keywords=new ArrayList<>();
        keywords.add("courseStatus");
        searchUtil.setKeywords(keywords);
        /*利用查询工具类去查询数据*/
        List<CourseDTO> allCourse = stuService.findAllCourse(searchUtil);
        /*返回结果集------因为还没想好具体使用什么样的前端，所以先返回一个ResulVO*/
        return ResultVOUtil.success(allCourse);
    }
    /*预约课程*/
    @GetMapping(value = "/order")
    public ResultVO getorder(@RequestParam(value = "stuCode")String stuCode,
                          @RequestParam(value = "courserId")Integer courserId){
        SubCourse order = stuService.order(stuCode, courserId);
        if (order!=null){
            return ResultVOUtil.success(order) ;
        }else {
            return ResultVOUtil.error(500,"预约失败");
        }
    }
    /*取消预约课程*/
    @GetMapping(value = "/cancelorder")
    public ResultVO cancelorder(@RequestParam(value = "stuCode")String stuCode,
                            @RequestParam(value = "courserId")Integer courserId){
        /*根据学生code和课程id查找到预约信息*/
        SubCourse subCourse = subCourseRepository.findByStuCodeAndCourseId(stuCode, courserId);
        if (subCourse==null){
            return ResultVOUtil.error(501,"取消预约失败");
        }
        /*如果预约信息处于预约等待状态，直接删除预约信息*/
        if (subCourse.getSubStatus()== SubStatusEnum.SUB_WAIT.getCode()){
            subCourseRepository.delete(subCourse);
            return ResultVOUtil.success();
        }else {/*否则跳转到另一个预约原因提交界面，使用post方式来进行提交并且写明取消原因*/
            /*不管跳转不跳转先把预约表中的数据删除*/
            subCourseRepository.delete(subCourse);
            return ResultVOUtil.error(666,"需要重新定向");
        }
    }
    /*在已预约成功状态下取消预约课程*/
    @PostMapping(value = "/cancelorder")
    public ResultVO cancelorder2(@RequestParam(value = "cause")String cause,
                                 @RequestParam(value = "courserId")Integer courserId){
        TeaCourse teaCourse = stuService.cancelorder(cause, courserId);
        if (teaCourse!=null){
            return ResultVOUtil.success(teaCourse);
        }else {
            return ResultVOUtil.error(501,"取消预约失败");
        }

    }
    /*
    * 反馈接口
    * */
    @PostMapping(value = "/feedback")
    public ResultVO feedback(@RequestParam(value = "courserId")Integer courserId,
                         @RequestParam(value = "message")String message,
                         @RequestParam(value = "score")Integer score){
        if (score>5 || score<0){
            return ResultVOUtil.error(503,"提交反馈失败");
        }
        StuFeedBack feedback = stuService.feedback(courserId, message, score);
        if (feedback!=null){
            return ResultVOUtil.success(feedback);
        }else {
            return ResultVOUtil.error(503,"提交反馈失败");
        }

    }
    /*显示所有课程信息*/
    @GetMapping(value = "/lookhistory")
    public ResultVO lookhistory(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "10") Integer size,
                                @RequestParam(value = "stucode") String stucode){
        /*封装查询中间类*/
        SearchUtil searchUtil=new SearchUtil();
        /*设置以课程结束时间降序排序*/
        searchUtil.setOrderBy("courseEndTime");
        searchUtil.setOrderDirection(Sort.Direction.DESC);
        /*设置查询字段为学生工号,课程结束时间和课程状态*/
        List<String>list=new ArrayList<>();
        list.add("studentCode");
        list.add("courseEndTime");
        list.add("courseStatus");
        searchUtil.setKeywords(list);
        List<CourseDTO> courseDTOSs = stuService.lookhistory(searchUtil, stucode);
        List<CourseDTO> allCourse = stuService.findAllCourse(searchUtil);
        /*返回结果集------因为还没想好具体使用什么样的前端，所以先返回一个ResulVO*/
        return ResultVOUtil.success(allCourse);
    }
}
