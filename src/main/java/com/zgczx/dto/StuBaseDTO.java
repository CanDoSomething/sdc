package com.zgczx.dto;

import com.zgczx.dataobject.StuBase;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: Dqd
 * @Date: 2019/1/18 11:47
 * @Description:
 */
@Data
@ToString
public class StuBaseDTO {
    /**
     * 学生预约状态码
     */
    private Integer subStatus;
    /**
     * 学生基本信息
     */
    private StuBase stuBase;
}
