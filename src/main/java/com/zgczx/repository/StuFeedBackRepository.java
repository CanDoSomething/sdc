package com.zgczx.repository;

import com.zgczx.dataobject.StuFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StuFeedBackRepository extends JpaRepository<StuFeedBack,Integer> {
    public StuFeedBack findByCourseId(Integer courseid);
}
