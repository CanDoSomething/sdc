package com.zgczx.service.impl;


import com.zgczx.dataobject.StuFeedBack;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaBase;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.enums.SubStatusEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.StuFeedBackRepository;
import com.zgczx.repository.SubCourseRepository;
import com.zgczx.repository.TeaBaseRepository;
import com.zgczx.repository.TeaCourseRepository;
import com.zgczx.service.StuService;
import com.zgczx.utils.DateUtil;
import com.zgczx.utils.SeachIndexKey;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 陈志恒
 * @Date: 2018/12/10 23:50
 * @Description:
 */
@Service
public class StuServiceImpl implements StuService {


    @Autowired
    private TeaCourseRepository teaCourseRepository;
    @Autowired
    private TeaBaseRepository teaBaseRepository;
    @Autowired
    private SubCourseRepository subCourseRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StuFeedBackRepository stuFeedBackRepository;
    /**
     *
     * 功能描述: 显示所有课程信息
     *
     * @param: page页面数，size页面大小
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 17:54
     */
    @Override
    public List<CourseDTO> findAllCourse(Integer page,Integer size) {
        //先按照课程开始日期升序排序
        Sort sort =new Sort(Sort.Direction.ASC, SeachIndexKey.COURSE_STATR_TIME);
        /*设置分页*/
        Pageable pageable = new PageRequest(page, size, sort);
        /*排序比较器设置------为了以后更方便添加设置其他排序条件*/
        Specification<TeaCourse> specification = (root, query, cb) -> {
            /*获取预约等待的所有课程信息*/
            Predicate predicate = cb.equal(root.get(SeachIndexKey.COURSE_STATUS), SubStatusEnum.SUB_WAIT.getCode());
            /*过滤掉课程完成时间小于当前时间的所有课程*/
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("courseEndTime"), new Date()));
            return predicate;
        };
        /*按照分页信息查找相应的预约等待的课程信息*/
        Page<TeaCourse> byCourseStatus = teaCourseRepository.findAll(specification, pageable);
        /*如果课程不存在，返回预约课程不存在*/
        if (byCourseStatus==null){
            throw new SdcException(SubStatusEnum.NOTFIND_TEACOURSE);
        }
        /*封装课程信息到消息中间类*/
        List<CourseDTO> course = getCourse(byCourseStatus);
        return course;
    }
    /**
     *
     * 功能描述: 提交预约请求
     *
     * @param: stucode学生编码，courseId 课程id
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 17:57
     */
    @Override
    public SubCourse order(String stucode, Integer courserId) {
        /*查找学生预约的课程信息*/
        TeaCourse teaCourse = teaCourseRepository.findOne(courserId);
        /*如果预约课程不存在，抛出异常*/
        if (teaCourse==null){
            throw new SdcException(SubStatusEnum.NOTFIND_TEACHER);
        }
        /*查询该学生的预约列表*/
        List<SubCourse> subCourses = subCourseRepository.findByStuCode(stucode);
        for (SubCourse subCours : subCourses) {
            /*找到等待预约和预约成功的所有预约信息*/
            if (subCours.getSubStatus().equals(SubStatusEnum.SUB_WAIT.getCode()) || subCours.getSubStatus().equals(SubStatusEnum.SUB_SUCCESS.getCode())){
                /*根据课程id查找到课程信息*/
                TeaCourse one = teaCourseRepository.findOne(subCours.getCourseId());
                if (one==null){
                    throw new SdcException(SubStatusEnum.NOTFIND_TEACHER);
                }
                /*判断预约时间是否冲突*/
                if(DateUtil.compareTime(teaCourse.getCourseStartTime(),one.getCourseEndTime()) || DateUtil.compareTime(one.getCourseStartTime(),teaCourse.getCourseEndTime())){
                }else {
                    /*抛出预约冲突异常*/
                    throw new SdcException(SubStatusEnum.SUB_EXIST);
                }
            }
        }
        /*如果运行到这里证明预约时间不冲突可以建立预约对象*/
        SubCourse subCourse=new SubCourse();
        subCourse.setStuCode(stucode);
        subCourse.setCourseId(courserId);
        subCourse.setCreateTime(new Date());
        /*设置新的预约对象为等待预约状态*/
        subCourse.setSubStatus(SubStatusEnum.SUB_WAIT.getCode());
        /*保存到数据库*/
        SubCourse save = subCourseRepository.save(subCourse);
        if (save==null){
            throw new SdcException(SubStatusEnum.SUB_FAIL);
        }
        return save;
    }

    /**
     *
     * 功能描述:在预约成功条件下取消预约请求
     *
     * @param: cause取消原因，courserId课程id，stuCode学生编码
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 18:06
     */
    @Override
    public TeaCourse cancelorder(String cause,String stuCode,Integer courserId) {
        /*查找要取消预约的课程信息*/
        TeaCourse teaCourse = teaCourseRepository.findOne(courserId);
        if (teaCourse==null){
            /*如果课程不存在，抛出课程不存在异常*/
            throw new SdcException(SubStatusEnum.NOTFIND_TEACOURSE);
        }
        /*如果课程中的学生不是提交预约学生，抛出取消预约失败异常*/
        if (!teaCourse.getStudentCode().equals(stuCode) || !teaCourse.getCourseStatus().equals(SubStatusEnum.SUB_SUCCESS.getCode())){
            throw new SdcException(SubStatusEnum.SUB_ERROR_CANCEL);
        }
        /*设置预约状态为学生预约失效*/
        teaCourse.setCourseStatus(SubStatusEnum.SUB_STUFAILURE.getCode());
        teaCourse.setCause(cause);
        TeaCourse save = teaCourseRepository.save(teaCourse);
        if (save==null){
            /*如果save等于null，抛出课程更新异常*/
            throw new SdcException(SubStatusEnum.SUB_UPDATE_FAIL);
        }
        SubCourse byStuCodeAndCourseId = subCourseRepository.findByStuCodeAndCourseId(stuCode, courserId);
        byStuCodeAndCourseId.setUpdateTime(new Date());
        byStuCodeAndCourseId.setSubStatus(SubStatusEnum.SUB_STUFAILURE.getCode());
        SubCourse subCourse = subCourseRepository.save(byStuCodeAndCourseId);
        return save;
    }
    /**
     *
     * 功能描述: 在预约未成功条件下取消预约课程
     *
     * @param:
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 18:28
     */
    public SubCourse simplecancelorder(String stuCode,Integer courserId){
        /*查找要取消预约的课程信息*/
        TeaCourse teaCourse = teaCourseRepository.findOne(courserId);
        if (teaCourse==null){
            /*如果课程不存在，抛出课程不存在异常*/
            throw new SdcException(SubStatusEnum.NOTFIND_TEACOURSE);
        }
        /*如果该学生成功预约课程，抛出取消预约原因必须填写异常*/
        if (teaCourse.getCourseStatus().equals(SubStatusEnum.SUB_SUCCESS.getCode()) && teaCourse.getStudentCode().equals(stuCode)){
            throw new SdcException(SubStatusEnum.SUB_FAIL_CANCEL);
        }
        /*根据学生code和课程id查找到预约信息*/
        SubCourse subCourse = subCourseRepository.findByStuCodeAndCourseId(stuCode, courserId);
        if (subCourse==null){
            /*抛出预约信息没有发现异常*/
            throw new SdcException(SubStatusEnum.NOTFIND_SUBCOURE);
        }
        /*删除预约信息*/
        subCourse.setSubStatus(SubStatusEnum.SUB_STUFAILURE.getCode());
        subCourse.setUpdateTime(new Date());
        SubCourse save = subCourseRepository.save(subCourse);
        return save;
    }
    /**
     *
     * 功能描述: 提交反馈
     *
     * @param: courseid课程id，message代表反馈内容，score代表反馈评分
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 19:24
     */
    @Override
    public StuFeedBack feedback(Integer courseid, String message, Integer score) {
        /*如果评分不正确，抛出异常*/
        if (score>5 || score<0){
            throw new SdcException(SubStatusEnum.FEED_STUFAIL);
        }
        StuFeedBack stuFeedBack=new StuFeedBack();
        stuFeedBack.setCourseId(courseid);
        stuFeedBack.setStuInfo(message);
        stuFeedBack.setStuToScore(score);
        StuFeedBack save = stuFeedBackRepository.save(stuFeedBack);
        /*如果信息回馈表为空，抛出学生反馈表不存在异常*/
        if (save ==null){
            throw new SdcException(SubStatusEnum.FEED_STUFAIL);
        }
        return save;
    }
    /**
     *
     * 功能描述: 查询历史记录
     *
     * @param: stucode学生编码,page页数，size页面大小
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 19:44
     */
    @Override
    public List<CourseDTO> lookhistory(Integer page,Integer size,String stucode) {
        //先按照课程结束日期降序排序
        Sort sort =new Sort(Sort.Direction.DESC,SeachIndexKey.COURSE_END_TIME);
        /*设置分页*/
        Pageable pageable = new PageRequest(page, size, sort);
        /*排序比较器设置------按照所有条件过滤*/
        Specification<TeaCourse> specification = (root, query, cb) -> {
            /*增加学生工号等于stucode条件*/
            Predicate predicate = cb.equal(root.get(SeachIndexKey.STUDENT_CODE),stucode);
            /*增加课程结束日期小于当前日期的所有课程条件*/
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get(SeachIndexKey.COURSE_END_TIME), new Date()));
            /*增加预约成功条件*/
            predicate = cb.and(predicate, cb.equal(root.get(SeachIndexKey.COURSE_STATUS), SubStatusEnum.SUB_SUCCESS.getCode()));
            return predicate;
        };
        /*按照分页信息查找相应的预约等待的课程信息*/
        Page<TeaCourse> byCourseStatus = teaCourseRepository.findAll(specification, pageable);
        /*将课程信息封装到消息中间件*/
        List<CourseDTO> getcourse = getCourse(byCourseStatus);
        return getcourse;

    }
    /**
     *
     * 功能描述: 封装信息到CourseDTO中
     *
     * @param:
     * @return:
     * @auther: 陈志恒
     * @date: 2018/12/16 19:39
     */
    public List<CourseDTO> getCourse(Page<TeaCourse> byCourseStatus){
        /*创建一个CourseDTO对象用来封装查找到的信息*/
        List<CourseDTO> list=new ArrayList<>();
        /*遍历查找到的信息*/
        for (TeaCourse courseStatus : byCourseStatus) {
            /*根据教师编号查找到教师完整信息*/
            TeaBase one = teaBaseRepository.findOne(courseStatus.getTeaCode());
            /*如果教师不存在，抛出教师不存在异常*/
            if (one==null){
                throw new SdcException(SubStatusEnum.NOTFIND_TEACHER);
            }
            /*将CourseStatus中的属性映射到一个封装对象中*/
            CourseDTO courseDTO = modelMapper.map(courseStatus, CourseDTO.class);
            /*将教师信息放入到封装对象中*/
            courseDTO.setTeaBase(one);
            /*根据课程编号查找到反馈信息*/
            StuFeedBack byCourseId = stuFeedBackRepository.findByCourseId(courseStatus.getCourserId());
            /*将反馈信息封装到封装对象中去*/
            courseDTO.setStuFeedBack(byCourseId);
            /*添加封装对象到list中*/
            list.add(courseDTO);
        }
        return list;
    }
}