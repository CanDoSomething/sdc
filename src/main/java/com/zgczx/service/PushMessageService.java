package com.zgczx.service;

import com.zgczx.dto.PushMessageDTO;

/**
 * @Author: Dqd
 * @Date: 2018/12/18 18:01
 * @Description:
 */
public interface PushMessageService {
    void pushSubSuccessMessage(PushMessageDTO pushMessageDTO);
    void pushSubFailMessage(PushMessageDTO pushMessageDTO);
    void pushCancelCourseMessageToStu(PushMessageDTO pushMessageDTO);
    void pushFeedBackMessageToStu(PushMessageDTO pushMessageDTO);
    void pushFeedBackMessageToTea(PushMessageDTO pushMessageDTO);
    void pushCancelCourseMessageToTea(PushMessageDTO pushMessageDTO);
    void pushSubcourseMessageToTea(PushMessageDTO pushMessageDTO);
    void pushTipsToStu();
}
