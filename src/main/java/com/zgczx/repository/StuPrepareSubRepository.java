package com.zgczx.repository;

import com.zgczx.dataobject.StuPrepareSub;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Dqd
 * @Date: 2018/12/15 14:29
 * @Description:学生预约候选repository类
 */
public interface StuPrepareSubRepository extends JpaRepository<StuPrepareSub,String> {
    /**
     * 带分页的查找所有预约候选学生
     *
     * @param courserId 课程编号
     * @param pageable 分页设置
     * @return 带分页的预约候选学生列表
     *
     */
    @Query("select stuSub from StuPrepareSub stuSub where stuSub.courserId = ?1")
    List<StuPrepareSub> findByCourserId(Integer courserId,Pageable pageable);

    /**
     *
     * 不带分页的查找所有预约候选学生
     *
     * @param courserId 课程编号
     * @return 所有预约候选学生列表
     *
     */
    List<StuPrepareSub> findByCourserId(Integer courserId);

    /**
     *
     * 删除当前课程的候选人
     *
     * @param courseId 课程编号
     * @return 被删除的所有预约候选人列表
     */
    List<StuPrepareSub> deleteByCourserId(Integer courseId);
}