package com.zgczx.repository;

import com.zgczx.dataobject.TeaBase;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Dqd
 * @Date: 2018/12/12 17:36
 * @Description: 教师基本信息repository接口
 */
public interface TeaBaseRepository extends JpaRepository<TeaBase,String> {

    /**
     * 根据教师openid确定该教师
     *
     * @param teaOpenid 微信唯一凭证
     * @return  TeaBase
     */
    TeaBase findByTeaOpenid(String teaOpenid);

}
