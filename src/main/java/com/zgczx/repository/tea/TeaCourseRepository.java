package com.zgczx.repository.tea;

import com.zgczx.dataobject.TeaCourse;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Dqd on 2018/12/11.
 */
@ComponentScan
public interface TeaCourseRepository extends JpaRepository<TeaCourse,Integer> {
    List<TeaCourse> findByTeaCode(String teaCode, Pageable pageable);
}
