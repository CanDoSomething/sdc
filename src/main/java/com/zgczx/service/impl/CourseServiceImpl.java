package com.zgczx.service.impl;

import com.zgczx.dataobject.*;
import com.zgczx.enums.ResultEnum;
import com.zgczx.enums.SubCourseEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.*;
import com.zgczx.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Jason
 * @date 2019/2/20 13:24
 */
@Service
public class CourseServiceImpl  implements CourseService {

    @Autowired
    private OnlineCourseRepository onlineCourseRepository;

    @Autowired
    private TeaBaseRepository teaBaseRepository;

    @Autowired
    private TeaCourseRepository teaCourseRepository;

    @Autowired
    private SubCourseRepository subCourseRepository;

    @Autowired
    private StuBaseRepository stuBaseRepository;

    @Override
    @Transactional
    public OnlineCourse getOnlineCourseGroupId(Integer courseId) {
        //1.判断当前课程是否创建groupId，若有，则返回；若没有，则创建新的群组
        OnlineCourse onlineCourse = onlineCourseRepository.findOne(courseId);
        if(null == onlineCourse){
            OnlineCourse onlineCourse_new = new OnlineCourse();
            onlineCourse_new.setCourseId(courseId);
            // 设定新的群组为courseId
            onlineCourse_new.setGroupId(courseId);
            return onlineCourseRepository.save(onlineCourse_new);
        }else{
            return onlineCourse;
        }
    }

    @Override
    public Boolean onCourse(String useropenid,Integer courseId) {

        //1.判断课程信息是否正确
        TeaCourse teaCourse = teaCourseRepository.findOne(courseId);
        if(null == teaCourse){
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,"courseId 不存在");
        }


        //2.判断useropenid为该课程的老师
        TeaBase teaBase = teaBaseRepository.findByTeaOpenid(useropenid);
        if(teaBase != null){
            //该useropenid身份是老师，判断是否是该课程的老师
            if(teaBase.getTeaCode().equals(teaCourseRepository.getOne(courseId).getTeaCode()) ){
                return true;
            }
        }
        //3.判断useropenid为该课程的预约成功的学生
        StuBase stuBase = stuBaseRepository.findByStuOpenid(useropenid);
        if(null == stuBase){
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,"useropenid 既不是老师，也不是学生，请检查");
        }

        List<SubCourse> subCourseList = subCourseRepository.
                findByCourseIdAndSubStatus(courseId, SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode());
        if(subCourseList.size()>1){
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,"存在大于1的预约成功的学生");
        }else if(subCourseList.size()==0){
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,"不存在预约成功的学生");
        }else{
            SubCourse subCourse = subCourseList.get(0);
            if(subCourse.getStuCode().equals(stuBase.getStuCode())){
                return true;
            }
        }

        return false;
    }
}
