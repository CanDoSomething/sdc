package com.zgczx.repository;

import com.zgczx.dataobject.TeaCourse;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Dqd on 2018/12/11.
 */
public interface TeaCourseRepository extends PagingAndSortingRepository<TeaCourse,Integer>,JpaSpecificationExecutor<TeaCourse> {
}
