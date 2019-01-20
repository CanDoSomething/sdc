package com.zgczx.repository;

import com.zgczx.dataobject.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Dqd
 * @Date: 2018/12/28 18:06
 * @Description:
 */
public interface FeedBackRepository extends JpaRepository<FeedBack,Integer> {
    /**
     * 根据subId查找预约关系表
     *
     * @param subId 预约关系主键id
     * @return 反馈对象
     */
    FeedBack findBySubId(Integer subId);
}
