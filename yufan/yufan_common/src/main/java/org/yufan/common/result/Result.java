package org.yufan.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "返回结果")
public class Result {

    @ApiModelProperty(value = "是否成功",dataType = "boolean")
    private boolean flag;
    @ApiModelProperty(value = "返回码",dataType = "int")
    private Integer code;// 返回码
    @ApiModelProperty(value = "返回信息",dataType = "string")
    private String message;//返回信息
    @ApiModelProperty(value = "返回数据",dataType = "object")
    private Object data;

    public Result(boolean flag, Integer code, String message, Object data) {
        super(); this.flag = flag; this.code = code;
        this.message = message; this.data = data;
    }


    public Result() {
    }

    public Result(boolean flag, Integer code, String message) { super();
        this.flag = flag; this.code = code; this.message = message;
    }



}
