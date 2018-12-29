package com.zgczx.repository;

import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    public List<SubCourse> findByStuCode(String stuCode);
    /**
     *查找到学生对应的所有课程信息
     *
     * @Author chen
     * @Date 11:11 2018/12/29
     * @param stuCode 学生编号
     * @param pageable 分页器
     * @return
     **/
    Page<SubCourse>findByStuCode(String stuCode,Pageable pageable);
    public SubCourse findByStuCodeAndCourseId(String stuCode,Integer courseId);
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

    public SubCourse findByStuCodeAndCourseIdAndSubStatusIsNot(String stuCode,Integer courseId,Integer subStatus);
}
