package com.zgczx.repository;

import com.zgczx.dataobject.TeaCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: Dqd
 * @Date: 2018/12/15 15:10
 * @Description:教师课程repository接口
 */
public interface TeaCourseRepository extends JpaRepository<TeaCourse,Integer> {
    /**
     * 查找教师所有的历史课程
     *
     * @param teaCode 学生编号
     * @param pageable 分页条件
     * @return 所有课程列表
     */
    @Query("select teaCourse from TeaCourse teaCourse where teaCourse.teaCode = ?1")
    Page<TeaCourse> find(String teaCode,Pageable pageable);
}
