package com.zgczx.repository;

import com.zgczx.dataobject.SubCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface SubCourseRepository extends JpaRepository<SubCourse,Integer> {
    SubCourse findByCourseId(Integer courseId);
    public List<SubCourse> findByStuCode(String stucode);
    public SubCourse findByStuCodeAndCourseId(String stucode,Integer courseid);
}
