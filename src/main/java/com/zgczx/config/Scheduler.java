package com.zgczx.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zgczx.dataobject.SubCourse;
import com.zgczx.service.PushMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler{
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private PushMessageService pushMessageService;



    /**
     * 每天19：05执行 给所有明天有课的学生推送上课提示消息
     */

    @Scheduled(cron = "0 05 19 ? * *")
    public void testTasks() {
        pushMessageService.pushTipsToStu();
    }

}