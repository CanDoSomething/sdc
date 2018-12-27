package com.zgczx.repository;

import com.zgczx.dataobject.TeaFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Dqd
 * @Date: 2018/12/12 17:16
 * @Description:教师反馈学生repository接口
 */
public interface TeaFeedBackRepository extends JpaRepository<TeaFeedBack,Integer> {
}
