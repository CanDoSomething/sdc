package com.zgczx.repository;

import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.SubCourse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface SubCourseRepository extends JpaRepository<SubCourse,Integer> {
    List<SubCourse> findByCourseId(Integer courseId);
    public List<SubCourse> findByStuCode(String stucode);
    public SubCourse findByStuCodeAndCourseId(String stucode,Integer courseid);
    List<SubCourse> findByCourseIdAndSubStatus(Integer courseId,Integer status);
    @Query("select stuBase from com.zgczx.dataobject.StuBase stuBase,SubCourse subCourse where subCourse.courseId = ?1 and subCourse.stuCode = stuBase.stuCode order by stuBase.creditScore desc ")
    List<StuBase> getAllCandidate(Integer courseId, Pageable pageable);
}
