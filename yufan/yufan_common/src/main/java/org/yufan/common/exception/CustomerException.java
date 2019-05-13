package org.yufan.common.exception;

import lombok.Getter;
import org.yufan.common.result.ResultEnum;

@Getter
public class CustomerException extends Exception {

    private  ResultEnum resultEnum;
    public CustomerException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum=resultEnum;
    }


}
