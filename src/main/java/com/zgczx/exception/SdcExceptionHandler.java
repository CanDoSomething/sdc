package com.zgczx.exception;

import com.zgczx.VO.ResultVO;
import com.zgczx.utils.ResultVOUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: SdcExceptionHandler
 * @Author: Jason
 * @Date: 2018/12/13 13:57
 * @Description: 拦截异常
 */
@ControllerAdvice
public class SdcExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = SdcException.class)
    public ResultVO<?> handleSdcException(SdcException sdc){
        return ResultVOUtil.error(sdc.getCode(),sdc.getMessage(),sdc.getData());
    }
}
