package com.zgczx.repository;

import com.zgczx.dataobject.StuPrepareSub;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Dqd on 2018/12/15.
 */
public interface StuPrepareSubRepository extends JpaRepository<StuPrepareSub,String> {
    List<StuPrepareSub> findByCourserId(Integer courserId,Pageable pageable);
    List<StuPrepareSub> findByCourserId(Integer courserId);
    List<StuPrepareSub> deleteByCourserId(Integer courseId);
}
