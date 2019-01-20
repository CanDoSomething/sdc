package com.zgczx.repository;

import com.zgczx.dataobject.SubCourse;
import org.apache.lucene.index.DocIDMerger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface SubCourseRepository extends JpaRepository<SubCourse,Integer> {

    List<SubCourse> findByCourseId(Integer courseId);
    /**
     *查找到学生对应的所有课程信息
     *
     * @Author chen
     * @Date 11:11 2018/12/29
     * @param stuCode 学生编号
     * @return
     **/

     List<SubCourse> findByStuCode(String stuCode);
    /**
     *查找到学生对应的所有课程信息
     *
     * @Author chen
     * @Date 11:11 2018/12/29
     * @param stuCode 学生编号
     * @param pageable 分页器
     * @return
     **/
    Page<SubCourse> findByStuCode(String stuCode, Pageable pageable);


    /**
     * 找到学生对某节课的预约请求
     *
     * @param courseId 课程id
     * @param stuCode 学生id
     * @return 选课关系
     */
    SubCourse findByCourseIdAndStuCode(Integer courseId,String stuCode);

    /**
     *
     * 通过学生编号，课程编号，预约状态确定唯一课程记录
     *
     * @param stuCode 学生编号
     * @param courseId 课程编号
     * @param subStatus 预约状态
     * @return 预约课程
     */
    public SubCourse findByStuCodeAndCourseIdAndSubStatus(String stuCode,Integer courseId,Integer subStatus);
    /**
     *找到预约等待或预约成功状态的预约信息
     *
     * @Author chen
     * @Date 11:10 2018/12/29
     * @param stuCode 学生编号
     * @param courseId 课程id
     * @param subStatus 课程状态
     * @return
     **/

    SubCourse findByStuCodeAndCourseIdAndSubStatusIsIn(String stuCode,Integer courseId,List<Integer> subStatus);
    List<SubCourse> findByCourseIdAndSubStatus(Integer courseId,Integer status);

    /**
     *
     * 查找当前课程的所有候选人(选课请求),包括提交预约请求 and 预约成功 and 预约失败
     *
     * @param courseId 课程编号
     * @param pageable 分页设置
     * @return List<SubCourse>
     */
    List<SubCourse> findByCourseId(Integer courseId,Pageable pageable);




}
