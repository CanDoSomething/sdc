package com.zgczx.service;

import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dataobject.TeaFeedBack;

import java.util.List;

/**
 * @Auther: Dqd
 * @Date: 2018/12/12 14:29
 * @Description:教师模块接口
 */

public interface TeaService {
    //创建订单
    TeaCourse createCourse(TeaCourse teaCourse);
    //取消课程
    TeaCourse cancelCourse(Integer courseId,String teaCode,String cancelReason);
    //查看教师历史课程
    List<TeaCourse> findTeaHistoryCourse(String teaCode, int page, int pageSize);
    //查看课程预约候选人
    List<StuBase> findCandidateByCourseId(Integer courserId, int page, int pageSize);
    //教师选择成功预约候选人
    SubCourse saveSelectedStu(String stuCode, Integer courseId);
    //教师提交给学生的反馈
    TeaFeedBack createFeedBack(TeaFeedBack teaFeedBack);

}
