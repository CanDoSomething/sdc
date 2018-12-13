package com.zgczx.repository;

import com.zgczx.dataobject.TeaCourse;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
public interface TeaCourseRepository extends PagingAndSortingRepository<TeaCourse, Integer>, JpaSpecificationExecutor<TeaCourse> {
}

