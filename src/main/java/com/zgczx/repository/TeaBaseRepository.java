package com.zgczx.repository;

import com.zgczx.dataobject.TeaBase;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Dqd
 * @Date: 2018/12/12 17:36
 * @Description:教师基本信息repository接口
 */
public interface TeaBaseRepository extends JpaRepository<TeaBase,String> {

}
