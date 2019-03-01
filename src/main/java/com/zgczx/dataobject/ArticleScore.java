package com.zgczx.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Jason
 * @date 2019/2/28 16:04
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class ArticleScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scoreId;

    /**
     * 用户openid
     */
    private String openid;
    /**
     * 文章id
     */
    private Integer artId;
    /**
     * 文章得分
     */
    private Integer score;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
