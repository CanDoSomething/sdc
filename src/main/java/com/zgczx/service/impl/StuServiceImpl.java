package com.zgczx.service.impl;

import com.zgczx.dataobject.StuFeedBack;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaBase;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dto.CourseDTO;
import com.zgczx.enums.SubStatusEnum;
import com.zgczx.repository.StuFeedBackRepository;
import com.zgczx.repository.SubCourseRepository;
import com.zgczx.repository.TeaBaseRepository;
import com.zgczx.repository.TeaCourseRepository;
import com.zgczx.service.IStuService;
import com.zgczx.utils.DateUtil;
import com.zgczx.utils.SearchUtil;
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
public class StuServiceImpl implements IStuService {
    //引入课程表repository
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
    //查询所有课程信息
    @Override
    public List<CourseDTO> findAllCourse(SearchUtil searchUtil) {
        //先按照课程结束日期升序排序
        Sort sort =new Sort(searchUtil.getOrderDirection(),searchUtil.getOrderBy());
        /*设置分页*/
        Pageable pageable = new PageRequest(searchUtil.getPage(), searchUtil.getSize(), sort);
        /*排序比较器设置------为了以后更方便添加设置其他排序条件*/
        Specification<TeaCourse> specification = (root, query, cb) -> {
            /*获取预约等待的所有课程信息*/
            Predicate predicate = cb.equal(root.get(searchUtil.getKeywords().get(0)),SubStatusEnum.SUB_WAIT.getCode());
            /*过滤掉课程完成时间小于当前时间的所有课程*/
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("courseEndTime"), new Date()));
            return predicate;
        };
        /*按照分页信息查找相应的预约等待的课程信息*/
        Page<TeaCourse> byCourseStatus = teaCourseRepository.findAll(specification, pageable);
        /*创建一个CourseDTO对象用来封装查找到的信息*/
        List<CourseDTO> list=new ArrayList<>();
        /*遍历查找到的信息*/
        for (TeaCourse courseStatus : byCourseStatus) {
            /*根据教师编号查找到教师完整信息*/
            TeaBase one = teaBaseRepository.findOne(courseStatus.getTeaCode());
            /*将CourseStatus中的属性映射到一个封装对象中*/
            CourseDTO courseDTO = modelMapper.map(courseStatus, CourseDTO.class);
            /*将教师信息放入到封装对象中*/
            courseDTO.setTeaBase(one);
            /*添加封装对象到list中*/
            list.add(courseDTO);
        }
        return list;
    }
    /*
    * 提交预约请求
    * */
    @Override
    public SubCourse order(String stucode,Integer courserId) {
        /*查找学生预约的课程信息*/
        TeaCourse teaCourse = teaCourseRepository.findOne(courserId);
        /*查询该学生的预约列表*/
        List<SubCourse> subCourses = subCourseRepository.findByStuCode(stucode);
        for (SubCourse subCours : subCourses) {
            /*根据课程id查找到课程信息*/
            TeaCourse one = teaCourseRepository.findOne(subCours.getCourseId());
            /*找到等待预约和预约成功的所有预约信息*/
            if (one.getCourseStatus()==SubStatusEnum.SUB_WAIT.getCode() || one.getCourseStatus()==SubStatusEnum.SUB_SUCCESS.getCode()){
                /*判断学生的预约课程是否与其其他预约的课程存在时间冲突*/
                if(DateUtil.compareTime(teaCourse.getCourseStartTime(),one.getCourseEndTime()) || DateUtil.compareTime(one.getCourseStartTime(),teaCourse.getCourseEndTime())){
                }else {
                    System.out.println("预约时间冲突，请检查你的预约情况");
                    return null;
                }
            }
        }
        /*如果运行到这里证明预约时间不冲突可以建立预约对象*/
        SubCourse subCourse=new SubCourse();
        subCourse.setStuCode(stucode);
        subCourse.setCourseId(courserId);
        /*设置新的预约对象为等待预约状态*/
        subCourse.setSubStatus(SubStatusEnum.SUB_WAIT.getCode());
        /*保存到数据库*/
        SubCourse save = subCourseRepository.save(subCourse);
        if (save!=null){
            System.out.println("预约成功");
        }
        return save;
    }

    /*
    * 取消预约课程
    * */
    @Override
    public TeaCourse cancelorder(String cause,Integer courserId) {
        /*查找学生预约的课程信息*/
        TeaCourse teaCourse = teaCourseRepository.findOne(courserId);
        /*设置预约状态为学生预约失效*/
        teaCourse.setCourseStatus(SubStatusEnum.SUB_STUFAILURE.getCode());
        teaCourse.setCause(cause);
        TeaCourse save = teaCourseRepository.save(teaCourse);
        return save;
    }
    /*
    *提交反馈
    * courseid课程id
    * message代表反馈内容
    * score代表反馈评分
    * */
    @Override
    public StuFeedBack feedback(Integer courseid, String message, Integer score) {
        /*查找到信息回馈表*/
        StuFeedBack stuFeedBack = stuFeedBackRepository.findByCourseId(courseid);
        /*如果信息回馈表不存在返回null*/
        if (stuFeedBack ==null){
            return null;
        }else {
            /*将数据插入到反馈表中*/
            stuFeedBack.setStuInfo(message);
            stuFeedBack.setStuToScore(score);
            StuFeedBack save = stuFeedBackRepository.save(stuFeedBack);
            return save;
        }
    }
    /*
    *查询历史课程记录
    * */
    @Override
    public List<CourseDTO> lookhistory(SearchUtil searchUtil,String stucode) {
        //先按照课程结束日期降序排序
        Sort sort =new Sort(searchUtil.getOrderDirection(),searchUtil.getOrderBy());
        /*设置分页*/
        Pageable pageable = new PageRequest(searchUtil.getPage(), searchUtil.getSize(), sort);
        /*排序比较器设置------为了以后更方便添加设置其他排序条件*/
        Specification<TeaCourse> specification = (root, query, cb) -> {
            /*获取学生工号等于stucode的所有课程*/
            Predicate predicate = cb.equal(root.get(searchUtil.getKeywords().get(0)),stucode);
            /*找到所有日期小于当前日期的所有课程*/
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get(searchUtil.getKeywords().get(1)), new Date()));
            /*获取预约成功的所有记录*/
            predicate = cb.and(predicate, cb.equal(root.get(searchUtil.getKeywords().get(2)), SubStatusEnum.SUB_SUCCESS.getCode()));
            return predicate;
        };
        /*按照分页信息查找相应的预约等待的课程信息*/
        Page<TeaCourse> byCourseStatus = teaCourseRepository.findAll(specification, pageable);
        /*创建一个CourseDTO对象用来封装查找到的信息*/
        List<CourseDTO> list=new ArrayList<>();
        /*遍历查找到的信息*/
        for (TeaCourse courseStatus : byCourseStatus) {
            /*根据教师编号查找到教师完整信息*/
            TeaBase one = teaBaseRepository.findOne(courseStatus.getTeaCode());
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
