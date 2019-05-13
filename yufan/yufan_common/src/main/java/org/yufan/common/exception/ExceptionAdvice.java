package org.yufan.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yufan.common.result.Result;
import org.yufan.common.result.ResultEnum;
import org.yufan.common.result.ResultUtils;


@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(value = CustomerException.class)
    @ResponseBody
    public Result customerException(CustomerException e){
        log.error("系统定义异常:{}",e.getMessage());
        return ResultUtils.buildFail(e.getResultEnum());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exception(Exception e){
        log.error("未知异常:{}",e.getMessage());
        return ResultUtils.buildFail(ResultEnum.FAIL);
    }



}
