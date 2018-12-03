package com.zgczx.dataobject.es;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.Servlet;
import java.io.Serializable;

/**
 * @Auther: 陈志恒
 * @Date: 2018/12/3 16:06
 * @Description:
 */
@Entity
@Data
@DynamicUpdate
@Table(name = "school_ranking")
public class SchoolRanking implements Serializable {
    @Id
    private Integer schoolid;
    private String schoolname;
    //点击
    private Long clicks;
    //月点击
    private Long monthclicks;
    //周点击
    private Long weekclicks;
    //省份
    private String province;
    //学校类型
    private String schooltype;
    // 学校类别（理工、综合）
    private String  schoolproperty;
    //是否985
    private int f985;
    //是否211
    private int f211;
    //学校科属（本、专）
    private String level;
    //学校情况（公办私办）
    private String schoolnature;
    //收费情况
    private String shoufei;
    //学校简介
    private String jainjie;
    // 学校编码
    private String  schoolcode;
    //全国热度排名
    private int ranking;
    //类别热度排名
    private int rankingCollegetype;
    //学校官网
    private String guanwang;




}
