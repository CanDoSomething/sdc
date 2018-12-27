package com.zgczx.VO;

import lombok.Data;
import org.springframework.http.HttpStatus;

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
    public ResultVO(){

    }
    public ResultVO(T data,Integer code,String msg){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
    public ResultVO(T data,HttpStatus status) {
        this.code=status.value();
        this.msg=status.getReasonPhrase();
        this.data=data;
    }



}
