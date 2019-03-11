package com.zgczx.repository;

import com.zgczx.dataobject.TeaCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Common
 * @Date: 2018/12/15 15:10
 * @Description: 教师课程repository接口
 */
public interface TeaCourseRepository extends JpaRepository<TeaCourse,Integer> {

    /**
     * 查找教师所有的历史课程
     *
     * @param teaCode 教师编号
     * @param pageable 分页条件
     * @return 所有课程列表
     */
    @Query("select teaCourse from TeaCourse teaCourse where teaCourse.teaCode = ?1 ORDER BY teaCourse.courseDate desc")
    Page<TeaCourse> findAllTeaCourse(String teaCode,Pageable pageable);

    /**
     * 查看教师的所有课程（不分页）
     * @param teaCode 教师编号
     * @return 所有课程列表
     */
    List<TeaCourse> findByTeaCode(String teaCode);

    /**
     * 通过教师编号和课程编号查找对应课程
     * @param teaCode 教师工号
     * @param courseId 课程主键id
     * @return 课程信息
     */
    TeaCourse findByTeaCodeAndCourseId(String teaCode,Integer courseId);

    /**
     *按照学生编号跟预约情况查找预约信息
     *
     * @Author chen
     * @Date 11:15 2018/12/29
     * @param stuCode 学生编号
     * @param pageable 分页器
     * @return 分页的课程信息
     **/

    @Query("select tea from TeaCourse tea,SubCourse sub  where sub.courseId=tea.courseId and (sub.courseId=1 or sub.courseId=0) and sub.stuCode=?1 ")
    Page<TeaCourse> findLookHistory(String stuCode,Pageable pageable);

    /**
     * 查找所有可以预约的课程
     * @param date 当前日期
     * @param pageable 分页
     * @return 课程列表
     */
    @Query("select tea from TeaCourse tea where courseEndTime >= ?1 and courseStatus = 300 or courseStatus = 301 or " +
            "courseStatus = 302")
    Page<TeaCourse> findAllCourse(Date date,Pageable pageable);

    /**
     *功能描述：开始时间在当前时间之后的所有的未预约成功的课程
     *
     * @param courseStatus 课程id
     * @param date 当前时间
     * @param pageable 分页器
     * @return 分页的课程信息
     **/

    Page<TeaCourse> findByCourseStatusAndCourseStartTimeIsAfter(Integer courseStatus,Date date,Pageable pageable);

    /**
     * 通过教师编号查找课程状态不在集合中的课程
     * @param teaCode 教师编号
     * @param list 课程状态码集合
     * @return 课程列表
     */
    List<TeaCourse> findByTeaCodeAndCourseStatusNotIn(String teaCode,ArrayList<Integer> list);
}
