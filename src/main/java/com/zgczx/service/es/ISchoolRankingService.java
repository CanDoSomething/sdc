package com.zgczx.service.es;

public interface ISchoolRankingService {
    /**
     * 索引学校排名索引
     * @param schoolid
     */
    void index(Integer schoolid);

    /**
     * 移除学校排名索引
     * @param schoolid
     */
    void remove(Integer schoolid);
    /*
    * 根据学校索引查找学校
    * */
    void find(Integer docid);
    /*
    * 根据用户搜索词来查找数据
    * */
    void query(String keywords);
}
