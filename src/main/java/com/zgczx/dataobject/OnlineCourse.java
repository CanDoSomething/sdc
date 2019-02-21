package com.zgczx.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 线上交流课程信息表
 *
 * @author Jason
 * @date 2019/2/20 11:43
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class OnlineCourse {

    /**
     * 课程id
     */
    @Id
    private Integer courseId;
    /**
     * 群组id
     */
    private Integer groupId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
