package com.zgczx.dto;

import com.zgczx.dataobject.StuBase;
import lombok.Data;
import lombok.ToString;

/**
 * 封装返回学生预约状态及学生个人信息
 *
 * @author : Dqd
 * @date : 2019/1/18 11:47
 *
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


    public StuBaseDTO(Integer subStatus,StuBase stuBase){
        this.subStatus = subStatus;
        this.stuBase = stuBase;
    }
}
