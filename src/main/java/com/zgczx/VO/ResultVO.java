package com.zgczx.VO;

import lombok.Data;

import java.util.List;

/**
 * 最外层显示对象
 * @param <T>
 */
@Data
public class ResultVO<T> {
    //错误码
    private Integer code;
    //提示信息
    private String msg;
    //具体内容
    private T data;



}
