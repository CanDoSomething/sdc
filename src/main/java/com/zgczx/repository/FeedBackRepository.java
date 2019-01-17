package com.zgczx.repository;

import com.zgczx.dataobject.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Dqd
 * @Date: 2018/12/28 18:06
 * @Description:
 */
public interface FeedBackRepository extends JpaRepository<FeedBack,Integer> {
    FeedBack findBySubId(Integer subId);
}
