package com.zgczx.service.tea;

import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.dataobject.TeaFeedBack;

import java.util.List;

/**
 * Created by Dqd on 2018/12/10.
 */
public interface TeaService {
    TeaCourse createCourse(TeaCourse teaCourse);
    TeaCourse cancelCourse(TeaCourse teaCourse);
    List<TeaCourse> findTeaHistoryCourse(String teaCode,int page,int pageSize);
    List<StuBase> findCandidateByCourseId(Integer courserId,int page,int pageSize);
    SubCourse saveSelectedStu(String stuCode, Integer courseId);
    TeaFeedBack createFeedBack(TeaFeedBack teaFeedBack,Integer courseId);

}
