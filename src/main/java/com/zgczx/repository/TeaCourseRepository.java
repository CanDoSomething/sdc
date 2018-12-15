package com.zgczx.repository;

import com.zgczx.dataobject.TeaCourse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Dqd on 2018/12/11.
 */
public interface TeaCourseRepository extends JpaRepository<TeaCourse,Integer> {
    List<TeaCourse> findByTeaCode(String teaCode, Pageable pageable);
}
