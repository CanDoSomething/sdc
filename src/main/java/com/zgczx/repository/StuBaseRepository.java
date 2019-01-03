package com.zgczx.repository;

import com.zgczx.dataobject.StuBase;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @Author chen
 * @Date 11:12 2018/12/29
 **/

public interface StuBaseRepository extends JpaRepository<StuBase,String> {

    /**
     * 根据学生的openid确定学生
     *
     * @param stuOpenid 学生的openid
     * @return  StuBase
     */
    StuBase findByStuOpenid(String stuOpenid);

}
