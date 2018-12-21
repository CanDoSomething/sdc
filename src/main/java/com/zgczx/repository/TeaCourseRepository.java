package com.zgczx.repository;

import com.zgczx.dataobject.TeaCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Date;

/**
 *
 *
 * @Author chen
 * @Date 21:08 2018/12/20
 * @Param
 * @return
 **/

public interface TeaCourseRepository extends JpaRepository<TeaCourse,Integer> {
    /**
     *查询学生的历史完成课程
     *
     * @Author chen
     * @Date 10:11 2018/12/21
     * @param stuCode 学生编码
     * @param date 当前时间
     * @param pageable 分页器
     * @return Page<TeaCourse>
     **/

    @Query("select bean from TeaCourse bean   where bean.studentCode=?1 and bean.courseEndTime<?2")
    Page<TeaCourse> lookHistory(String stuCode,Date date,Pageable pageable);

    /**
     *功能描述：开始时间在当前时间之后的所有的未预约成功的课程
     *
     * @Author chen
     * @Date 10:40 2018/12/21
     * @param courseStatus 课程id
     * @param date 当前时间
     * @param pageable 分页器
     * @return
     **/

    Page<TeaCourse> findByCourseStatusAndAndCourseStartTimeIsAfter(Integer courseStatus,Date date,Pageable pageable);
}
